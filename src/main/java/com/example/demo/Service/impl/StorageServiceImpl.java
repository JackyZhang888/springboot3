package com.example.demo.Service.impl;

import com.example.demo.Service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class StorageServiceImpl implements StorageService {
    // 成员变量path，通过@Value注入配置文件中的file.upload.path属性
    // 这个配置用来定义文件上传后要保存的目录位置
    @Value("${file.upload.path}")
    private String path;

    @Override
    public String upload(MultipartFile[] files, Model model) {

        StringBuffer message = new StringBuffer();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String filePath = path + fileName;
            File dest = new File(filePath);
            try {
                if (dest.exists()) {
                    dest.delete();
                }
                Files.copy(file.getInputStream(), dest.toPath());
                message.append("Upload file success : " + dest.getAbsolutePath()).append("<br>");
            } catch (IOException e) {
                message.append("Upload file failed : " + e).append("<br>");
            }
        }
        model.addAttribute("tip", message.toString());
        return "upload";
    }
}
