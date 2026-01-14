package com.dima.hw11;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnToggleScreen = findViewById(R.id.btnToggleScreen);

        btnToggleScreen.setOnClickListener(v -> {
            turnOffScreenFor5Seconds();
        });
    }

    private void turnOffScreenFor5Seconds() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(
                    PowerManager.PARTIAL_WAKE_LOCK |
                            PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.ON_AFTER_RELEASE,
                    "ScreenControl:WakeLock"
            );

            wakeLock.acquire(1000);
            wakeLock.release();

            new Handler().postDelayed(() -> {
                wakeLock.acquire(5000);
                wakeLock.release();
            }, 5000);
        }
    }
}