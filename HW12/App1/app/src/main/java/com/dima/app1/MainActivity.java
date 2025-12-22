package com.dima.app1;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;
import androidx.lifecycle.*;
import androidx.room.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    public static class StudentViewModel extends AndroidViewModel {
        private final StudentRepository repo;
        private final LiveData<List<Student>> allStudents;

        public StudentViewModel(android.app.Application app) {
            super(app);
            repo = new StudentRepository(app);
            allStudents = repo.getAllStudents();
        }

        public LiveData<List<Student>> getAllStudents() { return allStudents; }
        public void insert(Student s) { repo.insert(s); }
        public void deleteAll() { repo.deleteAll(); }
    }

    private StudentViewModel viewModel;
    private final StudentAdapter adapter = new StudentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etLastName  = findViewById(R.id.etLastName);
        EditText etAge       = findViewById(R.id.etAge);
        Button btnAdd        = findViewById(R.id.btnAdd);
        Button btnClear      = findViewById(R.id.btnClear);
        RecyclerView rv      = findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        // отримуємо ViewModel, який має доступ до Application (потрібно для Room)
        // без другого параметра (фабрики) крошиться, бо AndroidViewModel вимагає Application у конструкторі
        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(StudentViewModel.class);

        viewModel.getAllStudents().observe(this, adapter::setStudents);

        btnAdd.setOnClickListener(v -> {
            String fn = etFirstName.getText().toString().trim();
            String ln = etLastName.getText().toString().trim();
            String ageStr = etAge.getText().toString().trim();
            if (!fn.isEmpty() && !ln.isEmpty() && !ageStr.isEmpty()) {
                int age = Integer.parseInt(ageStr);
                viewModel.insert(new Student(fn, ln, age));
                etFirstName.setText("");
                etLastName.setText("");
                etAge.setText("");
            }
        });

        btnClear.setOnClickListener(v -> viewModel.deleteAll());
    }
}