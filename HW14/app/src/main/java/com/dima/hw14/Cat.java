package com.dima.hw14;

public class Cat {

    private static final String TAG = "CAT";

    private int lives = 9;

    public void askForFood() { lives--; }
    public int getLives() {
        return lives;
    }
    public boolean isAlive() {
        return lives > 0;
    }
}
