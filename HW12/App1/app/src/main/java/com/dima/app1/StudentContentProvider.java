package com.dima.app1;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.*;
import androidx.room.Room;
import java.util.Objects;

public class StudentContentProvider extends ContentProvider {
    // authority - це унікальне ім'я провайдера (має збігатися в обох додатках!)
    public static final String AUTHORITY = "com.dima.app1.provider";

    // шляхи доступу до ресурсів
    private static final String PATH_STUDENTS = "students";
    private static final String PATH_STUDENTS_ID = "students/#";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_STUDENTS);

    // якщо прийшов Uri виду content://com.dima.app1.provider/students >> повернеться код 100
    // якщо прийшов Uri виду content://com.dima.app1.provider/students/8 (будь-яке число) >> повернеться код 101
    private static final int CODE_STUDENTS = 100;
    private static final int CODE_STUDENT_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, PATH_STUDENTS, CODE_STUDENTS);
        uriMatcher.addURI(AUTHORITY, PATH_STUDENTS + "/#", CODE_STUDENT_ID);
    }

    private AppDatabase database;

    @Override
    public boolean onCreate() {
//        database = Room.databaseBuilder(Objects.requireNonNull(getContext()).getApplicationContext(),
//                        AppDatabase.class, "student_database")
//                // .allowMainThreadQueries() // тільки для прикладу! в реальному коді — не треба
//                .build();
        database = AppDatabase.getDatabase(
                Objects.requireNonNull(getContext()).getApplicationContext()
        );
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_STUDENTS:
                cursor = database.studentDao().getAllStudentsBlocking();
                break;

            case CODE_STUDENT_ID:
                long id = ContentUris.parseId(uri);
                cursor = database.studentDao().getStudentByIdBlocking(id);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CODE_STUDENTS:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + PATH_STUDENTS;

            case CODE_STUDENT_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + PATH_STUDENTS;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // поки що не реалізуємо запис ззовні (можна додати)
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
