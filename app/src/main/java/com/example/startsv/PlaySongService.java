package com.example.startsv;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * Created by : Thinhvh on 3/14/2022
 * Phone      : 0962890153 - 0398477967
 * Facebook   : https://www.facebook.com/thinh.super22
 * Skype      : https://join.skype.com/invite/fvfRTDLcGPJN
 * Mail       : thinhvhph04204@gmail.com
 */
public class PlaySongService extends Service {
    private MediaPlayer mediaPlayer;
    //
    public static final String CHANNEL_ID = "DEMO_CHANNEL";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("thinhvh ", "onCreate: ");
        mediaPlayer = MediaPlayer.create(this, R.raw.ytiet);

        createNotificationChannel();
        createNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // khởi tạo nottificationChannal()

        String action = intent.getAction();
        if (action == null){
            return START_NOT_STICKY;
        }
        if (action.equals("PLAY")){
            mediaPlayer.start();
        }else if (action.equals("STOP")){
            mediaPlayer.stop();
        }

        return START_NOT_STICKY;
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "DEMO NOTI CHANNAL", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void createNotification(){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.noti);
        notificationLayout.setOnClickPendingIntent(R.id.button_play, actionClickOnNotification(1));
        notificationLayout.setOnClickPendingIntent(R.id.button_stop, actionClickOnNotification(2));

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .build();


        startForeground(1, notification);
    }

    private PendingIntent actionClickOnNotification(int id) {
        Intent intent = new Intent("CLICK");
        intent.putExtra("ID", id);
        int requestID = (int) System.currentTimeMillis();
        return PendingIntent.getBroadcast(this, requestID, intent, 0);
    }

    @Override
    public void onDestroy() {
        Log.d("thinhvh ", "onDestroy: ");
        super.onDestroy();
    }

}
