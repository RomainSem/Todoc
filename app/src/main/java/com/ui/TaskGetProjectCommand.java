package com.ui;


import com.model.Project;

public interface TaskGetProjectCommand {

    /**
     * Returns the project with the given unique identifier, or null if no project with that
     * identifier can be found.
     *
     * @param projectId the unique identifier of the project to return
     * @return the project with the given unique identifier, or null if it has not been found
     */
    Project getProjectById(long projectId);
}
