package com.example.wecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimePicker timePicker = findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);    //setze denn Datetimepicker auf eine 24h sicht anstatt eine Am/PM
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);    //settings default value
    }

    public void setTimer(View view) {
        TimePicker timePicker = findViewById(R.id.timePicker1);
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();

        onTimeSet(hour, min);
        Toast.makeText(getApplicationContext(), "Wecker gestellt", Toast.LENGTH_SHORT).show();
        //if the timer gets started a Toast is created and the Values from the TimePicker gets send to the methode onTimeSet
    }

    public void cancel(View view) {
        Toast.makeText(getApplicationContext(), "Weker abgeschaltet", Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
        //stops the PendingIntent
    }

    public void onTimeSet(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        startAlert(c);
        //converts the Inputs from the TimePicker to a Date and sends it to the function startAlert
    }

    private void startAlert(Calendar c) {
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }//make sure the time isn't in the Past
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        //creates an PendingIntent to send a Notification on time c
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
        //to create the menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.einstellungen:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);    //settings Button
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}