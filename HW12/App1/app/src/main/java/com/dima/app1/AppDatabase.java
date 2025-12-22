package com.dima.app1;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final android.content.Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "student_database").build();
                    // Room працює саме з SQLite - це не окрема БД, а зручна надбудова (обгортка) над класичною Android-базою SQLite
                }
            }
        }
        return INSTANCE;
    }
}