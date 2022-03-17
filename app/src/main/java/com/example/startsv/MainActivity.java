package com.example.startsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonStop;

    private PlaySongService  mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, PlaySongService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }

        registerReceiver(receiver, new IntentFilter("CLICK"));

        this.buttonPlay =  this.findViewById(R.id.button_play);
        this.buttonStop =  this.findViewById(R.id.button_stop);
    }



    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("CLICK")){
                int id = intent.getIntExtra("ID",0);
                if (id == 1){
                   startMusic();
                }else if (id == 2 ){
                   stopMusic();
                }
            }
        }
    };

    private void startMusic() {
        Intent intent = new Intent(MainActivity.this, PlaySongService.class);
        intent.setAction("PLAY");
        startService(intent);
    }

    private void stopMusic(){
        Intent intent = new Intent(MainActivity.this, PlaySongService.class);
        intent.setAction("STOP");
        startService(intent);
    }
}