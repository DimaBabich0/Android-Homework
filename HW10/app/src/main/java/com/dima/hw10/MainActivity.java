package com.dima.hw10;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Switch switchService;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchService = findViewById(R.id.switch_service);
        serviceIntent = new Intent(this, WallpaperChangeService.class);

        switchService.setChecked(false);

        switchService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
                    } else {
                        startService(serviceIntent);
                    }
                } else {
                    stopService(serviceIntent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}