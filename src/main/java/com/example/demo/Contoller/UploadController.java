package com.example.demo.Contoller;

import com.example.demo.Config.StorageDirConfig;
import com.example.demo.Service.StorageService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Slf4j
public class UploadController {

    @Autowired
    private StorageService storageService;

    @Value("${file.upload.path}")
    private String path;

    // GET请求，路径"/"
    // 用于显示upload.html这个文件上传页面
    @GetMapping("/upload")
    public String uploadPage() {
        // uploadPage2.html
        return "upload";
    }

    // POST请求路径"/upload"
    // 用于处理上传的文件，即保存到file.upload.path配置的路径下面
    @PostMapping("/upload")
    //@ResponseBody
    // MultipartFile使用数组，参数名称files对应html页面中input的name，一定要对应
    public String upload(@RequestPart MultipartFile[] files, Model model) throws IOException {
        log.info("upload start");
        String message = storageService.upload(files);
        log.info("upload send");
        model.addAttribute("tip", message);
        return "upload";
    }

    @GetMapping("/images")
    public String getImages(Model model) {
        log.info("get images start");
        List<String> imageList = storageService.getFiles(StorageDirConfig.DIR.IMAGES);
        log.info("get images end");

        model.addAttribute("imageList", imageList);
        return "file-list";
    }

    @GetMapping("/videos")
    public String getVideos(Model model) {
        log.info("get videos start");
        List<String> videoList = storageService.getFiles(StorageDirConfig.DIR.VIDEOS);
        log.info("get videos end");
        model.addAttribute("videoList", videoList);
        return "file-list";
    }

    @GetMapping("/docs")
    public String getDocs(Model model) {
        log.info("get docs start");
        List<String> docsList = storageService.getFiles(StorageDirConfig.DIR.DOCS);
        log.info("get docs end");
        model.addAttribute("docList", docsList);
        return "file-list";
    }

    @GetMapping("/files")
    public String getFiles(Model model) {
        log.info("get files start");
        File dir = new File(path);
        String files = storageService.printDirectoryStructure(dir);
        log.info("get files end");

        model.addAttribute("files", files);
        return "all-list";
    }
}


