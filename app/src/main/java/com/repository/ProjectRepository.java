package com.repository;

import androidx.lifecycle.LiveData;

import com.database.dao.ProjectDao;
import com.model.Project;

import java.util.List;

public class ProjectRepository {
    private final ProjectDao mProjectDao;


    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }


    public LiveData<List<Project>> getAllProjects() {
        return mProjectDao.getAllProjects();
    }


    public void createProject(Project project) {
        mProjectDao.createProject(project);
    }

    public void delete(Project project) {
        mProjectDao.delete(project.getId());
    }
}
