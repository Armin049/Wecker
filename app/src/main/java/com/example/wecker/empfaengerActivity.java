package com.example.wecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.time.LocalTime;

public class empfaengerActivity extends AppCompatActivity {

    MainActivity mainActivity;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);
    }

    public void stop(View view){
        mediaPlayer.stop();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void snooze(View view){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String snooze = preferences.getString("snooze", "");
        TimePicker timePicker=findViewById(R.id.timePicker1);
        LocalTime localTime = LocalTime.now();
        int hour=localTime.getHour();
        int min=localTime.getMinute()+Integer.parseInt(snooze);
        mainActivity.onTimeSet(timePicker,hour,min);
        mediaPlayer.stop();
    }
}
