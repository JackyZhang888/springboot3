package com.example.demo.Bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Task {
    private int id;
    private String taskId;
    private String taskType;
    private String createTime;
    private String updateTime;
    private String progress;
    private String status;
}
