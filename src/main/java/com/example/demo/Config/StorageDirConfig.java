package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageDirConfig {
    public enum DIR  {
        IMAGES ,
        VIDEOS,
        DOCS
    }
}
