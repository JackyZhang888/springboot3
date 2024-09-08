package com.example.demo.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ManageService {
    void upload(MultipartFile[] files, String savePath, String taskId);
    List<String> getFiles(String dir, List<String> fileList);
    String printDirectoryStructure(File dir, String type, boolean setType, StringBuilder sb);

}