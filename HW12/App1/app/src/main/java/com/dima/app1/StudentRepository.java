package com.dima.app1;

import androidx.lifecycle.LiveData;

import java.util.List;

class StudentRepository {
    private final StudentDao dao;
    private final LiveData<List<Student>> allStudents;

    StudentRepository(android.app.Application app) {
        AppDatabase db = AppDatabase.getDatabase(app);
        dao = db.studentDao();
        allStudents = dao.getAllStudents();
    }

    LiveData<List<Student>> getAllStudents() { return allStudents; }
    void insert(Student s) { new Thread(() -> dao.insert(s)).start(); }
    void deleteAll() { new Thread(dao::deleteAll).start(); }
}
