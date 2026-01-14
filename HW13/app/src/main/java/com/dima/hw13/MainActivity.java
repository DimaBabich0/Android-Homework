package com.dima.hw13;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private RecyclerView allContactsRecyclerView, decemberContactsRecyclerView;
    private ContactAdapter allContactsAdapter, decemberContactsAdapter;

    private List<Contact> allContacts = new ArrayList<>();
    private List<Contact> decemberContacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allContactsRecyclerView = findViewById(R.id.allContactsRecyclerView);
        decemberContactsRecyclerView = findViewById(R.id.decemberContactsRecyclerView);

        allContactsAdapter = new ContactAdapter(allContacts);
        decemberContactsAdapter = new ContactAdapter(decemberContacts);

        allContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allContactsRecyclerView.setAdapter(allContactsAdapter);

        decemberContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        decemberContactsRecyclerView.setAdapter(decemberContactsAdapter);

        Button reloadButton = findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(v -> loadContacts());

        checkReadContactsPermission();
    }

    private void checkReadContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            loadContacts();
        }
    }

    private void loadContacts() {
        allContacts.clear();
        decemberContacts.clear();

        String[] projection = {
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.START_DATE,
                ContactsContract.CommonDataKinds.Event.TYPE
        };

        String selection = ContactsContract.Data.MIMETYPE + "=? AND " +
                ContactsContract.CommonDataKinds.Event.TYPE + "=?";

        String[] selectionArgs = {
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                String.valueOf(ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY)
        };

        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                ContactsContract.Data.DISPLAY_NAME + " ASC"
        );

        if (cursor != null) {
            int nameIdx = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
            int birthdayIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);

            while (cursor.moveToNext()) {
                String name = nameIdx != -1 ? cursor.getString(nameIdx) : "Unknown";
                String birthdayRaw = birthdayIdx != -1 ? cursor.getString(birthdayIdx) : "";

                if (!birthdayRaw.isEmpty()) {
                    String day = "";
                    String month = "";
                    String year = "";

                    if (birthdayRaw.startsWith("--") && birthdayRaw.length() >= 7) {
                        // Format: --MM-dd
                        month = birthdayRaw.substring(2, 4);
                        day = birthdayRaw.substring(5, 7);
                        year = "----";
                    } else if (birthdayRaw.length() == 10) {
                        // Format: yyyy-MM-dd
                        year = birthdayRaw.substring(0, 4);
                        month = birthdayRaw.substring(5, 7);
                        day = birthdayRaw.substring(8, 10);
                    }

                    if (!day.isEmpty() && !month.isEmpty()) {
                        String birthday = day + "." + month + (year.equals("----") ? "" : "." + year);
                        Contact contact = new Contact(name, birthday);

                        allContacts.add(contact);
                        if ("12".equals(month)) {
                            decemberContacts.add(contact);
                        }
                    }
                }
            }
            cursor.close();
        }

        allContactsAdapter.notifyDataSetChanged();
        decemberContactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            }
        }
    }
}
