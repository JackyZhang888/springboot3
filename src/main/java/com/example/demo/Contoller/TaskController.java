package com.example.demo.Contoller;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;

@Slf4j
public class TaskController {
    @GetMapping("/files")
    public String getFiles(Model model) {
        log.info("get files start");
      //  File dir = new File(path);
     //   String files = storageService.printDirectoryStructure(dir);
        log.info("get files end");

    //    model.addAttribute("files", files);
        return "all-list";
    }
}
