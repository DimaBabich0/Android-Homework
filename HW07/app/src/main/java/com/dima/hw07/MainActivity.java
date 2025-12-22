package com.dima.hw07;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        checkAppLaunchCount();
    }

    private void checkAppLaunchCount() {
        final int finalCount = 3;

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int launchCount = prefs.getInt("launch_count", 0);
        launchCount++;
        Log.d("AppTest", String.valueOf(launchCount));
        if (launchCount >= finalCount) {
            launchCount = 0;
            showFeedbackDialog();
        } else {
            Toast.makeText(this, "You need restart the app " + (finalCount - launchCount) + " times for feedback", Toast.LENGTH_SHORT).show();
        }
        prefs.edit().putInt("launch_count", launchCount).apply();
    }

    private void showFeedbackDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_feedback, null);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Submit", (d, w) -> {
                    float rating = ratingBar.getRating();
                    if (rating <= 3) {
                        showDetailedFeedbackDialog();
                    } else {
                        showRateOnPlayStoreDialog();
                    }
                })
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .create();

        dialog.show();
    }

    private void showDetailedFeedbackDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("We're sorry :(")
                .setMessage("Do you want to leave detailed feedback?")
                .setPositiveButton("Yes", (d, w) -> showDetailedFeedbackInput())
                .setNegativeButton("No", (d, w) -> d.dismiss())
                .create();
        dialog.show();
    }

    private void showDetailedFeedbackInput() {
        View inputView = LayoutInflater.from(this).inflate(R.layout.dialog_feedback_input, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Feedback")
                .setView(inputView)
                .setPositiveButton("Submit", (d, w) -> {
                    String feedback = ((EditText) inputView.findViewById(R.id.editFeedback)).getText().toString();
                    Toast.makeText(this, "Thanks for your feedback!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .create();
        dialog.show();
    }
    private void showRateOnPlayStoreDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Thank you!")
                .setMessage("Rate us on Google Play?")
                .setPositiveButton("Yes", (d, w) -> openPlayStore())
                .setNegativeButton("No", (d, w) -> d.dismiss())
                .create();
        dialog.show();
    }

    private void openPlayStore() {
        String url = "https://play.google.com/store/";
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open link", Toast.LENGTH_SHORT).show();
        }
    }
}
