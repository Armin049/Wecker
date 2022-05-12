package com.example.wecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.time.LocalTime;
import java.util.Calendar;

public class empfaengerActivity extends AppCompatActivity {

    String snoozeTime;
    NotificationHelper notificationHelper;
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);
        notificationHelper = new NotificationHelper(this);
    }

    public void stop(View view){
        Reciever.stopMusic();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void snooze(View view){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String snooze = preferences.getString("snooze", "");
        LocalTime localTime = LocalTime.now();
        int hour=localTime.getHour();
        int min=localTime.getMinute()+Integer.parseInt(snooze);
        snoozeTime="Snooze time auf "+Long.toString(hour)+":"+Long.toString(min)+" Uhr gestellt";
        setSnoozeTime(hour,min);
        Reciever.stopMusic();
    }

    public void setSnoozeTime(int hourOfDay,int minute){
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        startAlert(c);
    }

    private void startAlert(Calendar c){
        if (c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,Reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1 ,intent,PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);

        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(snoozeTime);
        notificationHelper.getManager().notify(2,nb.build());
        finish();
    }
}
