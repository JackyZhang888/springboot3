package com.example.demo.Contoller;

import com.example.demo.Config.StorageDirConfig;
import com.example.demo.Service.StorageService;
import com.example.demo.Service.TaskService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件上传控制器
 */
@Controller
@Slf4j
public class UploadController {

    private static final String DOCUMENTS = StorageDirConfig.DIR.DOCUMENTS.toString().toLowerCase();

    @Autowired
    private StorageService storageService; // 依赖注入文件存储服务

    @Autowired
    private TaskService taskService;

    @Value("${file.upload.path}")
    private String path; // 从配置文件中读取文件上传路径

    /**
     * 显示文件上传页面
     *
     * @return 返回页面名称
     */
    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }


    private boolean fileExist(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return false;
        }

        if (files.length == 1 && files[0].getOriginalFilename().equals("")) {
            return false;
        }

        return true;
    };

    /**
     * 处理文件上传请求，将文件保存到指定路径
     *
     * @param savePath 保存路径
     * @param files 要上传的文件数组
     * @param model 模型，用于在前后台间传递数据
     * @return 返回页面名称
     * @throws IOException 文件操作异常
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("savePath") String savePath, @RequestPart MultipartFile[] files, Model model) throws IOException {
        log.info("upload start. Total files: {}", files.length);
        if (!fileExist(files)) {
            model.addAttribute("tip", "请选择文件.");
            log.warn("upload failed. No file selected.");
            return "upload";
        }

        String taskId = UUID.randomUUID().toString();
        taskService.createTask(taskId, "upload");
        storageService.upload(files, savePath, taskId);
        model.addAttribute("tip", "异步任务后台执行成功.");
        return "upload";
    }

    /**
     * 获取图片文件列表
     *
     * @param model 模型，用于在前后台间传递数据
     * @return 返回页面名称
     */
    @GetMapping("/images")
    public String getImages(Model model) {
        log.info("get images start");
        List<String> fileList = new ArrayList<>();
        List<String> imageList = storageService.getFiles(StorageDirConfig.DIR.IMAGES.toString().toLowerCase(), fileList);
        log.info("get images end");

        model.addAttribute("imageList", imageList);
        return "file-list";
    }

    /**
     * 获取视频文件列表
     *
     * @param model 模型，用于在前后台间传递数据
     * @return 返回页面名称
     */
    @GetMapping("/videos")
    public String getVideos(Model model) {
        log.info("get videos start");
        List<String> fileList = new ArrayList<>();
        List<String> videoList = storageService.getFiles(StorageDirConfig.DIR.VIDEOS.toString().toLowerCase(), fileList);
        log.info("get videos end");
        model.addAttribute("videoList", videoList);
        return "file-list";
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        log.info("get videos start");
        List<String> fileList = new ArrayList<>();
        List<String> videoList = storageService.getFiles(StorageDirConfig.DIR.MOVIES.toString().toLowerCase(), fileList);
        log.info("get videos end");
        model.addAttribute("videoList", videoList);
        return "file-list";
    }

    /**
     * 获取文档文件列表
     *
     * @param model 模型，用于在前后台间传递数据
     * @return 返回页面名称
     */
    @GetMapping("/documents")
    public String getDocs(Model model) {
        log.info("get docs start");
        List<String> fileList = new ArrayList<>();
        List<String> docsList = storageService.getFiles(StorageDirConfig.DIR.DOCUMENTS.toString().toLowerCase(), fileList);
        log.info("get docs end");
        model.addAttribute("docList", docsList);
        return "file-list";
    }

    /**
     * 获取所有文件列表
     *
     * @param model 模型，用于在前后台间传递数据
     * @return 返回页面名称
     */
    @GetMapping("/directoryStructure")
    public String getDirectoryStructure(Model model) {
        log.info("get directoryStructure start");
        File dir = new File(path);
        StringBuilder sb = new StringBuilder();
        String files = storageService.printDirectoryStructure(dir, null, true, sb);
        log.info("get directoryStructure end");
        model.addAttribute("files", files);
        return "directory-list";
    }

    @GetMapping("/list")
    public String getList(@RequestParam("dir") String listDir,
                          @RequestParam("type") String listType, Model model) {
        log.info("get list start");
        List<String> list = new ArrayList<>();
        List<String> fileList = storageService.getFiles(listDir ,list);
        log.info("get list end");
        switch (listType) {
            case "documents":
                model.addAttribute("docList", fileList);
                break;
            case "images":
                model.addAttribute("imageList", fileList);
                break;
            case "videos":
            case "movies":
                model.addAttribute("videoList", fileList);
                break;
            default:
                break;
        }

        return "file-list";
    }
}
