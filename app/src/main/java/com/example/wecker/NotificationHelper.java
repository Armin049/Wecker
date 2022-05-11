package com.example.wecker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_1_ID = "channel1ID";
    public static final String CHANNEL_1_Name = "channel1";
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
    }

    public NotificationManager getManager(){
        if(mManager==null){
            mManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNot(){
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_1_ID)
                .setContentTitle("Wecker")
                .setContentText("Aufstehen")
                .setSmallIcon(R.drawable.ic_baseline_access_time_24);
    }
}
