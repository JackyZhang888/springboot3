package com.example.demo.Service.impl;

import com.example.demo.Config.StorageDirConfig;
import com.example.demo.Service.StorageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StorageServiceImpl extends HttpServlet implements StorageService  {
    // 成员变量path，通过@Value注入配置文件中的file.upload.path属性
    // 这个配置用来定义文件上传后要保存的目录位置
    @Value("${file.upload.path}")
    private String path;

    @Autowired
    StorageDirConfig storageDirConfig;

    @Override
    public String upload(MultipartFile[] files) {

        StringBuffer message = new StringBuffer();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            try {
                Path pathToSave = getTargetLocation(fileName);
                log.info("get pathToSave: {}", pathToSave);

                File destFile = new File(String.valueOf(pathToSave));
                if (destFile.exists()) {
                    destFile.delete();
                }
                Files.copy(file.getInputStream(), pathToSave);
                message.append("Upload file success : " + destFile.getAbsolutePath()).append("<br>");
            } catch (IOException e) {
                message.append("Upload file failed : " + e).append("<br>");
            }
        }
        return  message.toString();
    }

    private Path getTargetLocation(String filename) {
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
                dir = StorageDirConfig.DIR.VIDEOS.toString().toLowerCase();
                break;
            default:
                dir = StorageDirConfig.DIR.DOCS.toString().toLowerCase();
                break;
        }
        File dirPath = new File(path + dir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
            log.info("mkdir {}", path + dir);
        }

        return Path.of(path, dir, filename);
    }

    @Override
    public List<String> getFiles(Enum dirType) {
        List<String> fileList = new ArrayList<>();

        // 创建File对象并指定路径
        String dir = dirType.toString().toLowerCase();
        File directory = new File(path + dir);

        if (directory.exists() && directory.isDirectory()) {
            // 获取该目录下所有文件及子目录
            File[] files = directory.listFiles();
            log.info("{}", files.toString());
            for (File file : files) {
                // 输出文件名或者目录名
                fileList.add(dir + "/" + file.getName());

                // 如果是目录则递归调用getFiles方法进行深度优先搜索
                // if (file.isDirectory()) {
                //     fileList.append(getFiles(file.getAbsolutePath(), model));
                // }
            }
        } else {
            return fileList;
        }

        log.info("fileList:{}", fileList);
        return fileList;
    }


    public String printDirectoryStructure(File dir) {
        StringBuilder sb = new StringBuilder();

        if (dir != null && dir.isDirectory()) {
            sb.append("<ul>");
            for (File child : dir.listFiles()) {
                if (child.isDirectory()) {
                    sb.append("<li><a href=\"" + child.getName() + "\">" + child.getName() + "</a></li>\n");
                    printDirectoryStructure(child);
                } else {
                    sb.append("<li>" + child.getName() + "</li>\n");
                }
            }

            sb.append("</ul>");
        }
        log.info("files:{}", sb.toString());
        return sb.toString();
    }
}
