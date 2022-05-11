package com.example.wecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Reciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNot();
        notificationHelper.getManager().notify(1, nb.build());
//        context.startActivity(new Intent(context, empfaengerActivity.class));
        Intent i = new Intent();
        i.setClassName("com.example.wecker", "com.example.wecker.empfaengerActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
