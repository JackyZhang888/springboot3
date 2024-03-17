package com.example.demo.Dao;

import com.example.demo.Bean.Task;
import com.example.demo.Bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    void createTask(@Param("task") Task task);
    void updateTask(@Param("task") Task task);
    List<Task> getTasks();

}
