package com.dima.hw06;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        List<Map<String, Object>> chats = createChats();

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                chats,
                R.layout.item_chat,
                new String[]{
                        "avatar",
                        "name",
                        "message",
                        "time",
                        "unread",
                        "read"
                },
                new int[]{
                        R.id.avatar,
                        R.id.name,
                        R.id.last_message,
                        R.id.time,
                        R.id.unread,
                        R.id.checkmark
                }
        );

        adapter.setViewBinder((view, data, text) -> {
            int id = view.getId();

            if (id == R.id.avatar) {
                ImageView img = (ImageView) view;
                int res = (int) data;
                if (res == 0) img.setImageResource(R.drawable.avatar1);
                else img.setImageResource(res);
                return true;
            }

            if (id == R.id.unread) {
                TextView counter = (TextView) view;
                int count = (int) data;
                if (count > 0) {
                    counter.setVisibility(View.VISIBLE);
                    counter.setText(String.valueOf(count));
                } else {
                    counter.setVisibility(View.GONE);
                }
                return true;
            }
            if (id == R.id.checkmark) {
                ImageView check = (ImageView) view;
                boolean isRead = (boolean) data;

                if (isRead) {
                    check.setImageResource(R.drawable.ic_double_check);
                }
                else {
                    check.setImageResource(R.drawable.ic_single_check);
                }
                return true;
            }
            return false;
        });

        listView.setAdapter(adapter);
    }

    private List<Map<String, Object>> createChats() {
        List<Map<String, Object>> list = new ArrayList<>();

        list.add(chat(
                R.drawable.avatar1,
                "Telegram Group",
                "Alex: Meeting at 6?",
                "18:12",
                3,
                false
        ));

        list.add(chat(
                R.drawable.avatar2,
                "Mom",
                "Don't forget to eat 😊",
                "14:45",
                1,
                false
        ));

        list.add(chat(
                R.drawable.avatar3,
                "Maria",
                "Thanks!",
                "Yesterday",
                0,
                true
        ));

        list.add(chat(
                R.drawable.avatar4,
                "Work",
                "Deadline moved to Friday",
                "Mon",
                0,
                true
        ));

        return list;
    }

    private Map<String, Object> chat(
            int avatar,
            String name,
            String message,
            String time,
            int unread,
            boolean isRead
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("avatar", avatar);
        map.put("name", name);
        map.put("message", message);
        map.put("time", time);
        map.put("unread", unread);
        map.put("read", isRead);
        return map;
    }
}
