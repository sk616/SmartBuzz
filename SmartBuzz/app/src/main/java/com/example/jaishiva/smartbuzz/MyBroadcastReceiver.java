package com.example.jaishiva.smartbuzz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    String mcurrentTime;
    String systemTime;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp=MediaPlayer.create(context, R.raw.alarm);
            mp.start();


    }
}
