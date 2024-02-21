package com.example.demo.Service;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface StorageService {
    String upload(MultipartFile[] files);
    List<String> getFiles(Enum dirType);
    String printDirectoryStructure(File dir);

}