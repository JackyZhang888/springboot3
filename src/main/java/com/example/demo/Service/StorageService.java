package com.example.demo.Service;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StorageService {
    void upload(MultipartFile[] files, String savePath, String taskId);
    List<String> getFiles(String dir, List<String> fileList);
    String printDirectoryStructure(File dir, String type, boolean setType, StringBuilder sb);

}