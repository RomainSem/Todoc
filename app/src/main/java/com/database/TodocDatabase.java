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

    // create a singleton
    private static volatile TodocDatabase INSTANCE;
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // create a thread pool to execute asynchronous tasks
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    // --- INSTANCE ---
    public static TodocDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "TodocDatabase.db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    // ---

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(TodocDatabase::prepopulateTasks);
        }
    };

    private static void prepopulateTasks() {
        ProjectDao dao = INSTANCE.projectDao();
        dao.deleteAll();
        dao.createProject(new Project("Projet Tartampion", 0xFFEADAD1));
        dao.createProject(new Project("Projet Lucidia", 0xFFB4CDBA));
        dao.createProject(new Project("Projet Circus", 0xFFA3CED2));
    }
}

