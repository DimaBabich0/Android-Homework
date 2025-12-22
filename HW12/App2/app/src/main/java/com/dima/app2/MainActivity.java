package com.dima.app2;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final Uri STUDENTS_URI = Uri.parse(
            "content://com.dima.app1.provider/students");

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        // колонки, які ми хочемо показати
        String[] from = {"firstName", "lastName"};
        int[] to = {android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                from,
                to,
                0);

        listView.setAdapter(adapter);

        loadStudents();
    }

    private void loadStudents() {
        // запит даних через ContentResolver
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
                    STUDENTS_URI,
                    null, null, null, null);

            if (cursor != null) {
                adapter.swapCursor(cursor);
            } else {
                Toast.makeText(this, "Немає доступу до даних або додаток не встановлений", Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Немає дозволу! Перевірте, чи перший додаток встановлений", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Помилка: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
