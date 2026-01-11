package com.dima.hw08;

public class WishItem {
    private final int imageResId;
    private final String name;
    private final int price;
    private boolean checked;

    public WishItem(int imageResId, String name, int price, boolean checked) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.checked = checked;
    }

    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public boolean getChecked() { return checked; }
    public void setChecked(boolean checked) { this.checked = checked; }
}