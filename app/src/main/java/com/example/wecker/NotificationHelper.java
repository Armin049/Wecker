package com.example.wecker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_1_ID = "channel1ID";
    public static final String CHANNEL_1_Name = "channel1";
    public static final String CHANNEL_2_ID = "channel2ID";
    public static final String CHANNEL_2_Name = "channel2";
    private  NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createNotificationChannels();
        }
    }

    private void createNotificationChannels() {

        NotificationChannel channel1 = new NotificationChannel(
                CHANNEL_1_ID,
                CHANNEL_1_Name,
                NotificationManager.IMPORTANCE_HIGH
        );
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.teal_200);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);

        NotificationChannel channel2 = new NotificationChannel(
                CHANNEL_2_ID,
                CHANNEL_2_Name,
                NotificationManager.IMPORTANCE_HIGH
        );
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.teal_200);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel2);
    }

    public NotificationManager getManager(){
        if(mManager==null){
            mManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    //The Alert that gets triggered the first time
    public NotificationCompat.Builder getChannelNot(){
        Intent intent=new Intent(this,empfaengerActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_1_ID)
                .setAutoCancel(true)
                .setContentTitle("Wecker")
                .setContentText("Aufstehen")
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentIntent(pIntent);
    }

    //The second Alert for snooze
    public NotificationCompat.Builder getChannelNotification(String time){
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_2_ID)
                .setAutoCancel(true)
                .setContentTitle("Wecker")
                .setContentText(time)
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentIntent(pIntent);
    }
}
