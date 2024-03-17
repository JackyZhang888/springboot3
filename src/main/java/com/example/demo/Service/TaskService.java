package com.example.demo.Service;

import com.example.demo.Bean.Task;

public interface TaskService {
    void createTask(String taskId, String type);
    void deleteTask(Task task);
    void updateTask(String taskId, String type, String progress, String status);

}
