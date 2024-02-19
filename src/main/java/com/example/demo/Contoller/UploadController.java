package com.example.demo.Contoller;

import com.example.demo.Service.StorageService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@Slf4j
public class UploadController {

    @Autowired
    private StorageService storageService;

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
        return storageService.upload(files, model);
    }
}

