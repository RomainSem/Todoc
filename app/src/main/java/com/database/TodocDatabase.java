package com.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.database.dao.ProjectDao;
import com.database.dao.TaskDao;
import com.model.Project;
import com.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)

public abstract class TodocDatabase extends RoomDatabase {


    // --- SINGLETON ---

    private static volatile TodocDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    // --- DAO ---

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();


    // --- INSTANCE ---

    public static TodocDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (TodocDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                            TodocDatabase.class, "MyDatabase.db")

                            .addCallback(prepopulateDatabase())

                            .build();

                }

            }

        }

        return INSTANCE;

    }


    private static Callback prepopulateDatabase() {

        return new Callback() {

            @Override

            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);

                //Executors.newSingleThreadExecutor().execute(() -> {
                databaseWriteExecutor.execute(() -> {
                    ProjectDao dao = INSTANCE.projectDao();
                    dao.deleteAll();
                    dao.createProject(new Project("Projet Tartampion", 0xFFEADAD1));
                    dao.createProject(new Project("Projet Lucidia", 0xFFB4CDBA));
                    dao.createProject(new Project("Projet Circus", 0xFFA3CED2));
                    dao.createProject(new Project("Projet Random", 0xFFEADAFF));
                    dao.createProject(new Project("Projet Random2", 0xFFEADAFF));
                } );


            }

        };

    }

}

