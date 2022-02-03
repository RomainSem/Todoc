package com;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.database.TodocDatabase;
import com.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)

public class ProjectDaoTest {



    // FOR DATA

    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Random name", 123456);

    private TodocDatabase database;


    @Rule


    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before


    public void initDb() throws Exception {


        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),


                TodocDatabase.class)


                .allowMainThreadQueries()


                .build();

    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        // BEFORE : Adding a new user


        this.database.ProjectDao().createProject(PROJECT_DEMO);


        // TEST


        Project project = LiveDataTestUtil.getValue(this.database.ProjectDao().getProject(PROJECT_ID));


        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }


    @After

    public void closeDb() throws Exception {


        database.close();


    }
}