package com.dima.hw08;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView totalCostTextView;
    private List<WishItem> wishItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        totalCostTextView = findViewById(R.id.totalCostTextView);

        wishItems = new ArrayList<>();
        wishItems.add(new WishItem(
                R.drawable.gift1,
                "Phone",
                12999,
                false)
        );
        wishItems.add(new WishItem(
                R.drawable.gift2,
                "Keyboard",
                2899,
                false)
        );
        wishItems.add(new WishItem(
                R.drawable.gift3,
                "Coffee machine",
                18999,
                false)
        );

        WishListAdapter adapter = new WishListAdapter(wishItems, new WishListAdapter.OnItemCheckedListener() {
            @Override
            public void onItemCheckedChanged() {
                updateTotalCost();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotalCost();
    }

    private void updateTotalCost() {
        int total = 0;
        for (WishItem item : wishItems) {
            if (item.getChecked()) {
                total += item.getPrice();
            }
        }
        totalCostTextView.setText("Total price: " + total + " ₴");
    }
}