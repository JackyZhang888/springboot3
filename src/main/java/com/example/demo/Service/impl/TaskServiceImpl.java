package com.example.demo.Service.impl;

import com.example.demo.Bean.Task;
import com.example.demo.Dao.TaskMapper;
import com.example.demo.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Override
    public void createTask(String taskId, String type) {
        // 将毫秒值转换为Date对象
        Date date = new Date(System.currentTimeMillis());
        // 创建一个SimpleDateFormat对象，并指定日期时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将Date对象格式化为字符串
        String formattedDate = sdf.format(date);

        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskType(type);
        task.setCreateTime(formattedDate);
        task.setProgress("0");
        task.setStatus("init");
        taskMapper.createTask(task);
    }

    @Override
    public void updateTask(String taskId, String type, String progress, String status) {
        // 将毫秒值转换为Date对象
        Date date = new Date(System.currentTimeMillis());
        // 创建一个SimpleDateFormat对象，并指定日期时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将Date对象格式化为字符串
        String formattedDate = sdf.format(date);

        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskType(type);
        task.setUpdateTime(formattedDate);
        task.setProgress(progress);
        task.setStatus(status);
        taskMapper.updateTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        System.out.println("deleteTask");
    }

    @Override
    public List<Task> getTasks() {
        return taskMapper.getTasks();
    }
}
