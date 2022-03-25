package com.repository;

import androidx.lifecycle.LiveData;

import com.database.dao.TaskDao;
import com.model.Task;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;


    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }


    public LiveData<List<Task>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }


    public void insert(Task task) {
        mTaskDao.insertTask(task);
    }

    public void delete(Task task) {
        mTaskDao.deleteTask(task.getId());
    }
}
