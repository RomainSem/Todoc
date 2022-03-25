package com.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.repository.ProjectRepository;
import com.repository.TaskRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    public ViewModelFactory(TaskRepository taskRepository,
                            ProjectRepository projectRepository,
                            Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mTaskRepository, mProjectRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
