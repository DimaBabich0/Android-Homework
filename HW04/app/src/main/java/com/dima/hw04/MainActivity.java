package com.dima.hw04;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        shareButton = findViewById(R.id.button);

        shareButton.setOnClickListener(v -> {
            String textToShare = editText.getText().toString().trim();

            if (textToShare.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter text to send", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent, "Share through:"));
            } else {
                Toast.makeText(MainActivity.this, "No app for sharing", Toast.LENGTH_SHORT).show();
            }
        });
    }
}