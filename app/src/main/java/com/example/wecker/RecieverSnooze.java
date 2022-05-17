package com.example.wecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.time.LocalTime;

public class RecieverSnooze extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String snooze = preferences.getString("snooze", "");
        int min=LocalTime.now().getMinute()+Integer.parseInt(snooze);
        String time=String.valueOf(LocalTime.now().getHour()+":"+min);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(time);
        notificationHelper.getManager().notify(2, nb.build());
        Reciever.playAudio(context);
        Intent i = new Intent(); //to direct
        i.setClassName("com.example.wecker", "com.example.wecker.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //this Reciever gets triggert when the user clicks on the snooze Button, a Notification containing the new time gets created
    }
}