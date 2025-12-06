package com.dima.hw05;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private char currentPlayer = 'X';
    private final Button[][] buttons = new Button[3][3];
    private TextView statusText;
    private FrameLayout gameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.textCurrentPlayer);
        gameContainer = findViewById(R.id.gameContainer);

        createGameField();

        if (savedInstanceState != null) {
            loadGame(savedInstanceState);
        } else {
            resetGame(null);
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadGame(Bundle state) {
        String status = state.getString("statusText");
        statusText.setText(status);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String text = state.getString("btn_" + row + "_" + col, "");
                boolean enabled = state.getBoolean("btnEnabled_" + row + "_" + col, true);

                buttons[row][col].setText(text);
                buttons[row][col].setEnabled(enabled);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("statusText", statusText.getText().toString());
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                outState.putString("btn_" + row + "_" + col, buttons[row][col].getText().toString());
                outState.putBoolean("btnEnabled_" + row + "_" + col, buttons[row][col].isEnabled());
            }
        }
    }

    private void createGameField() {
        final int GRID_SIZE = 3;
        final int BTN_SIZE = 250;
        final int BTN_MARGIN = 10;

        GridLayout grid = new GridLayout(this);
        grid.setRowCount(GRID_SIZE);
        grid.setColumnCount(GRID_SIZE);
        grid.setPadding(0, 0, 0, 0);
        grid.setAlignmentMode(GridLayout.ALIGN_MARGINS);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Button btn = new Button(this);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = BTN_SIZE;
                params.height = BTN_SIZE;
                params.setMargins(BTN_MARGIN, BTN_MARGIN, BTN_MARGIN, BTN_MARGIN);

                btn.setLayoutParams(params);
                btn.setTextSize(40);
                btn.setAllCaps(false);

                int finalRow = row;
                int finalCol = col;
                btn.setOnClickListener(v -> handleMove(finalRow, finalCol));

                buttons[row][col] = btn;
                grid.addView(btn);
            }
        }

        gameContainer.removeAllViews();
        gameContainer.addView(grid);
    }

    @SuppressLint("SetTextI18n")
    private void handleMove(int row, int col) {
        if (!buttons[row][col].getText().toString().isEmpty()) return;

        buttons[row][col].setText(String.valueOf(currentPlayer));

        if (checkIsWin(currentPlayer)) {
            statusText.setText("Player " + currentPlayer + " win!");
            disableButtons();
            return;
        } else if (checkIsDraw()) {
            statusText.setText("Draw!");
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusText.setText("Player move: " + currentPlayer);
    }

    private boolean checkIsWin(char p) {
        // Lines check
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals("" + p) &&
                    buttons[row][1].getText().equals("" + p) &&
                    buttons[row][2].getText().equals("" + p)) return true;
        }

        // Columns check
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals("" + p) &&
                    buttons[1][col].getText().equals("" + p) &&
                    buttons[2][col].getText().equals("" + p)) return true;
        }

        // Diagonal check
        if (buttons[0][0].getText().equals("" + p) &&
                buttons[1][1].getText().equals("" + p) &&
                buttons[2][2].getText().equals("" + p)) {
            return true;
        } else if (buttons[0][2].getText().equals("" + p) &&
                buttons[1][1].getText().equals("" + p) &&
                buttons[2][0].getText().equals("" + p)) {
            return true;
        }

        return false;
    }

    private boolean checkIsDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) return false;
            }
        }
        return true;
    }

    private void disableButtons() {
        for (Button[] row : buttons) {
            for (Button b : row) {
                b.setEnabled(false);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void resetGame(View view) {
        Random r = new Random();
        currentPlayer = r.nextBoolean() ? 'X' : 'O';
        statusText.setText("Player move: " + currentPlayer);

        for (Button[] row : buttons) {
            for (Button b : row) {
                b.setText("");
                b.setEnabled(true);
            }
        }
    }
}
