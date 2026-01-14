package com.dima.hw14;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_TIME = 30;
    private static final int FOOD_TIME = 10;
    private int timeLeft = MAX_TIME;
    private TextView tvLives;
    private TextView tvTime;
    private TextView tvMood;
    private Button btnFeed;
    private TextView tvFood;
    private String[] catBehaviors = {
            "The cat is looking with hungry eyes",
            "The cat is sharpening his claws",
            "The cat is pacing impatiently",
            "The cat meows loudly"
    };
    private boolean isMoodRunning = false;
    private boolean isRunning = true;
    private boolean isFeeding = false;
    private Cat cat;
    private int foodTimeLeft;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFeed = findViewById(R.id.btnFeed);
        tvFood = findViewById(R.id.tvFood);
        tvMood = findViewById(R.id.tvMood);

        btnFeed.setOnClickListener(v -> feedCat());

        tvLives = findViewById(R.id.tvLives);
        tvTime = findViewById(R.id.tvTime);

        cat = new Cat();

        updateUI();
        startCountdown();
    }
    private void startCountdown() {
        new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }

                if (!isFeeding) {
                    timeLeft--;
                }

                handler.post(() -> {
                    updateUI();

                    if (!isFeeding && timeLeft % 5 == 0) {
                        cat.askForFood();
                        updateUI();
                    }

                    if (!isFeeding && (timeLeft <= 0 || !cat.isAlive())) {
                        isRunning = false;
                        showCatLeftDialog();
                    }
                });
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        tvLives.setText("Lives: " + cat.getLives());
        tvTime.setText("Time left: " + timeLeft + "s");
    }
    private void showCatLeftDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Too late!")
                .setMessage("The cat wasn't fed and left to find a more responsible owner.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .show();
    }
    private void feedCat() {
        if (isFeeding) return;

        isFeeding = true;
        foodTimeLeft = FOOD_TIME;

        tvFood.setVisibility(TextView.VISIBLE);
        tvMood.setVisibility(TextView.VISIBLE);
        btnFeed.setEnabled(false);

        startFoodCountdown();
        startMoodThread();
    }

    private void startMoodThread() {
        isMoodRunning = true;

        new Thread(() -> {
            int index = 0;
            while (isFeeding && isMoodRunning) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    return;
                }
                int currentIndex = index;
                handler.post(() -> tvMood.setText(catBehaviors[currentIndex]));
                index = (index + 1) % catBehaviors.length;
            }
        }).start();
    }
    @SuppressLint("SetTextI18n")
    private void startFoodCountdown() {
        new Thread(() -> {
            while (isRunning && foodTimeLeft > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                foodTimeLeft--;

                handler.post(() -> {
                    tvFood.setText("Food ready in: " + foodTimeLeft + "s");
                    if (foodTimeLeft <= 0) {
                        finishFeeding();
                    }
                });
            }
        }).start();
    }
    private void finishFeeding() {
        isFeeding = false;
        isMoodRunning = false;

        timeLeft = MAX_TIME;

        btnFeed.setEnabled(true);
        tvFood.setVisibility(TextView.GONE);
        tvMood.setVisibility(TextView.GONE);

        updateUI();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
