package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * StorageDirConfig 类用于配置存储目录。
 */
public class StorageDirConfig {
    public enum DIR  {
        IMAGES ,
        VIDEOS,
        MOVIES,
        DOCUMENTS
    }
}
