package com.dima.hw10;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class WallpaperChangeService extends Service {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private WallpaperManager wallpaperManager;

    private final int[] wallpapers = new int[]{
            R.drawable.wallpaper1,
            R.drawable.wallpaper2,
            R.drawable.wallpaper3,
            R.drawable.wallpaper4,
    };

    private int currentIndex = 0;

    private final Runnable changeRunnable = new Runnable() {
        @Override
        public void run() {
            setNextWallpaper();
            handler.postDelayed(this, 10000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        wallpaperManager = WallpaperManager.getInstance(this);
        startForegroundWithNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(changeRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(changeRunnable);
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setNextWallpaper() {
        try {
            int resId = wallpapers[currentIndex];
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
            wallpaperManager.setBitmap(bitmap);
            currentIndex = (currentIndex + 1) % wallpapers.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startForegroundWithNotification() {
        String channelId = "wallpaper_service_channel";
        String channelName = "Wallpaper changer";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Changeable wallpapers")
                .setContentText("Changes every 10 seconds")
                .setSmallIcon(android.R.drawable.ic_menu_gallery)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        if (Build.VERSION.SDK_INT >= 34) {
            startForeground(1001, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
        } else {
            startForeground(1001, notification);
        }
    }
}