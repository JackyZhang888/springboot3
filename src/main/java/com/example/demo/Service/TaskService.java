package com.example.demo.Service;

import com.example.demo.Bean.Task;

import java.util.List;

public interface TaskService {
    void createTask(String taskId, String type);
    void deleteTask(Task task);
    void updateTask(String taskId, String type, String progress, String status);
    List<Task> getTasks();
}
