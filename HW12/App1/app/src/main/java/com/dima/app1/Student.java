package com.dima.app1;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
class Student {
    @PrimaryKey(autoGenerate = true)
    public long _id; // для майбутнього контент резолвера з його курсорами бажано поставити саме тип лонг + _ для поля айді
    public String firstName;
    public String lastName;
    public int age;

    public Student() {}
    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + " " + lastName + ", вік: " + age;
    }
}