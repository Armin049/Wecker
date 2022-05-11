package com.example.wecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;

public class Reciever extends BroadcastReceiver {
    MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNot();
        notificationHelper.getManager().notify(1, nb.build());
        mediaPlayer = MediaPlayer.create(context, R.raw.sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Intent i = new Intent();
        i.setClassName("com.example.wecker", "com.example.wecker.empfaengerActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}