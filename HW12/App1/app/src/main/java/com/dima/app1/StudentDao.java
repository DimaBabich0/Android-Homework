package com.dima.app1;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao // DAO (Data Access Object) - це інтерфейс, який відповідає за всі операції з базою даних
// для таблиці "students". Room автоматично згенерує реалізацію цього інтерфейсу під час компіляції
interface StudentDao {
    @Insert void insert(Student student);
    @Query("SELECT * FROM students ORDER BY lastName") LiveData<List<Student>> getAllStudents(); // клас з Android Architecture Components,
    // який дозволяє спостерігати за даними і автоматично оновлювати UI, коли вони змінюються
    @Query("DELETE FROM students") void deleteAll();

    @Query("SELECT * FROM students ORDER BY lastName")
    List<Student> getAllStudentsBlockingList(); // новий

    @Query("SELECT * FROM students ORDER BY lastName")
    Cursor getAllStudentsBlocking(); // цей для ContentProvider

    @Query("SELECT * FROM students WHERE _id = :id")
    Cursor getStudentByIdBlocking(long id);
}
