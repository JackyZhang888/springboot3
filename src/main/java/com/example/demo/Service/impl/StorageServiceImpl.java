package com.example.demo.Service.impl;

import com.example.demo.Bean.Task;
import com.example.demo.Config.StorageDirConfig;
import com.example.demo.Service.StorageService;
import com.example.demo.Service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用于处理上传存储相关的服务，自动按文件类别分类存储
 */
@Service
@Slf4j
public class StorageServiceImpl extends HttpServlet implements StorageService  {
    @Value("${file.upload.path}")
    private String path;

    @Value("${logging.file.dir}")
    private String logDir;

    @Autowired
    StorageDirConfig storageDirConfig;

    @Autowired
    TaskService taskService;

    @Override
    public void upload(MultipartFile[] files, String savePath, String taskId) {
        // 从lambda表达式引用的本地变量必须是最终变量或实际上的最终变量
        // 定义单元素数组绕过编译器限制
        int[] i = {0};
        long start = System.currentTimeMillis();
        long cnt = files.length;
        if (cnt == 0) {
            return;
        }
        taskService.updateTask(taskId, "upload", String.valueOf(i) + "/" + String.valueOf(cnt), "running");
        List<CompletableFuture<Void>> uploadFutures = Stream.of(files)
                .map(file -> {
                    final String fileName = file.getOriginalFilename();
                    Path pathToSave = getTargetLocation(fileName, savePath);
                    return CompletableFuture.runAsync(() -> {
                        try (InputStream inputStream = file.getInputStream()) {
                            File destFile = new File(String.valueOf(pathToSave));
                            if (destFile.exists()) {
                                destFile.delete();
                            }
                            Files.copy(inputStream, pathToSave);
                            log.info("Upload file success: {}", pathToSave);
                            synchronized (this) {
                                i[0]++;
                                if (i[0] % 10 == 0) {
                                    taskService.updateTask(taskId, "upload", String.valueOf(i[0]) + "/" + String.valueOf(cnt), "running");
                                }
                            }
                        } catch (IOException e) {
                            log.error("Upload file failed: {}", pathToSave, e);
                        }
                    });
                })
                .collect(Collectors.toList());

        CompletableFuture<Void> allUploadsDone = CompletableFuture.allOf(uploadFutures.toArray(new CompletableFuture[0]));

        allUploadsDone.thenRun(() -> {
            int uploadedFilesCount = i[0];
            taskService.updateTask(taskId, "upload", String.valueOf(uploadedFilesCount) + "/" + String.valueOf(cnt),
                    uploadedFilesCount == cnt ? "success" : "failed");
            log.info("upload end, cost {} ms", System.currentTimeMillis() - start);
        });
    }

    private Path getTargetLocation(String filename, String savePath) {
        int dotIndex = filename.lastIndexOf('.');

        if (dotIndex == -1 || dotIndex >= filename.length() - 2) {
            throw new IllegalArgumentException("无效的文件名或格式错误");
        }

        String extension = filename.substring(dotIndex + 1).toLowerCase();
        String dir = null;

        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
                dir = StorageDirConfig.DIR.IMAGES.toString().toLowerCase();
                break;
            case "mov":
            case "mp4":
            case "mkv":
            case "avi":
                dir = StorageDirConfig.DIR.VIDEOS.toString().toLowerCase();
                break;
            default:
                dir = StorageDirConfig.DIR.DOCUMENTS.toString().toLowerCase();
                break;
        }
        if (savePath != null && !savePath.isEmpty()) {
            dir = dir + "/" + savePath;
        }

        File dirPath = new File(path + dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
            log.info("mkdir {}", path + dir);
        }

        return Path.of(path, dir, filename);
    }

    @Override
    public List<String> getFiles(String dir, List<String> fileList) {
        String dirPath = path + dir;
        File directory = new File(dirPath);
        if (directory.exists() && directory.isDirectory()) {
            // 获取该目录下所有文件及子目录
            File[] files = directory.listFiles();
            for (File file : files) {
                // 如果是目录则递归调用getFiles方法进行深度优先搜索
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath().replace(path, "").replace("\\", "/"), fileList).toString();
                } else {
                    fileList.add(dir + "/" + file.getName());
                }
            }
        } else {
            return fileList;
        }

        log.info("fileList:{}", fileList);
        return fileList;
    }

    public String printDirectoryStructure(File dir, String type, boolean setType, StringBuilder sb) {

        if (dir != null && dir.isDirectory()) {
            sb.append("<ul>");
            for (File child : dir.listFiles()) {
                if (setType) {
                    type = child.getName();
                }
                if (child.isDirectory()) {
                    sb.append("<li><a href=\"list?type=" + type + "&dir=" + child.getAbsolutePath().replace(path, "").replace("\\", "/") + "\">" + child.getName() + "</a></li>\n");
                    printDirectoryStructure(child, type, false, sb);
                }
            }

            sb.append("</ul>");
        }
        log.info("files:{}", sb.toString());
        return sb.toString();
    }
}
