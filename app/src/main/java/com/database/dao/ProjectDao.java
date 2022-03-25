package com.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();

    @Insert
    long createProject(Project project);

    @Query("DELETE FROM Project WHERE project_id = :projectId")
    int delete(long projectId);

    @Query("DELETE FROM Project")
    void deleteAll();
}
