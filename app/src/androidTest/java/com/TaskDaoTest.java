package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;

import com.database.TodocDatabase;
import com.model.Project;
import com.model.Task;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class TaskDaoTest {

    private TodocDatabase database;
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project("Random name", 123456);

    @Rule


    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before


    public void initDb() throws Exception {


        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),


                TodocDatabase.class)


                .allowMainThreadQueries()


                .build();

    }


    private static Task NEW_TASK_PLACE_TO_VISIT = new Task(0, "Visite cet endroit de rêve !", 1230

    );


    private static Task NEW_TASK_IDEA = new Task(1, "On pourrait faire du chien de traîneau ?", 1250

    );


    private static Task NEW_TASK_RESTAURANTS = new Task(2, "Ce restaurant à l'air sympa", 2030

    );


    @Test


    public void getItemsWhenNoItemInserted() throws InterruptedException {


        // TEST


        List<Task> items = this.database.taskDao().getTasks(PROJECT_ID);

        assertTrue(items.isEmpty());

    }

    @Test


    public void insertAndGetItems() throws InterruptedException {


        // BEFORE : Adding demo project & demo tasks



        this.database.projectDao().createProject(PROJECT_DEMO);


        this.database.taskDao().insertTask(NEW_TASK_PLACE_TO_VISIT);


        this.database.taskDao().insertTask(NEW_TASK_IDEA);


        this.database.taskDao().insertTask(NEW_TASK_RESTAURANTS);


        // TEST


        List<Task> items = this.database.taskDao().getTasks(PROJECT_ID);


        assertEquals(3, items.size());


    }


    @Test


    public void insertAndUpdateItem() throws InterruptedException {


        // BEFORE : Adding demo project & demo task. Next, update task added & re-save it


        this.database.projectDao().createProject(PROJECT_DEMO);


        this.database.taskDao().insertTask(NEW_TASK_PLACE_TO_VISIT);


        Task taskAdded = this.database.taskDao().getTasks(PROJECT_ID).get(0);


        taskAdded.setSelected(true);


        this.database.taskDao().updateTask(taskAdded);



        //TEST


        List<Task> items = this.database.taskDao().getTasks(PROJECT_ID);


        assertTrue(items.size() == 1 && items.get(0).getSelected());

    }

    @Test

    public void insertAndDeleteItem() throws InterruptedException {


        // BEFORE : Adding demo project & demo task. Next, get the task added & delete it.


        this.database.projectDao().createProject(PROJECT_DEMO);


        this.database.taskDao().insertTask(NEW_TASK_PLACE_TO_VISIT);


        Task taskAdded = this.database.taskDao().getTasks(PROJECT_ID).get(0);


        this.database.taskDao().deleteTask(taskAdded.getId());


        //TEST


        List<Task> items = this.database.taskDao().getTasks(PROJECT_ID);


        assertTrue(items.isEmpty());


    }

}