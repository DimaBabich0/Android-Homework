package com.dima.hw03;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView flagImage = findViewById(R.id.imageView);
        TextView flagText = findViewById(R.id.textView);

        String lang = Locale.getDefault().getLanguage();

        Flag flag = getFlagByLang(lang);

        Glide.with(this)
                .load(flag.getFlagUrl())
                .centerCrop()
                .into(flagImage);

        flagText.setText(flag.getFlagName());
    }

    private Flag getFlagByLang(String lang) {
        switch (lang) {
            case "uk":
                return new Flag("Україна", "https://flagcdn.com/w640/ua.png");
            case "en":
                return new Flag("United Kingdom", "https://flagcdn.com/w640/gb.png");
            case "de":
                return new Flag("Deutschland", "https://flagcdn.com/w640/de.png");
            default:
                return new Flag("Somewhere on Earth :)", "https://flagcdn.com/w640/un.png");
        }
    }
}