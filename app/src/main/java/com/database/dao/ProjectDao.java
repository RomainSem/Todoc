package com.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Query("SELECT * FROM Project")
    List<Project> getAllProjects();

    @Query("SELECT * FROM Project WHERE project_id = :projectId")
    Project getProject(long projectId);

    @Query("DELETE FROM Project")
    void deleteAll();
}
