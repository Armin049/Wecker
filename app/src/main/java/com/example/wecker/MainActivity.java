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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimePicker timePicker=findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
    }

    public void setTimer(View view){
        TimePicker timePicker=findViewById(R.id.timePicker1);
        System.out.println(timePicker.getHour());
        System.out.println(timePicker.getMinute());
        int hour=timePicker.getHour();
        int min=timePicker.getMinute();

        onTimeSet(timePicker,hour,min);
        Toast.makeText(getApplicationContext(),"Wecker gestellt",Toast.LENGTH_SHORT).show();
    }

    public void cancel(View view){
        Toast.makeText(getApplicationContext(),"Weker abgeschaltet",Toast.LENGTH_SHORT).show();
        cancelAlarm();
    }

    public void onTimeSet(TimePicker view,int hourOfDay,int minute){
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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1 ,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,Reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1 ,intent,0);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.einstellungen:
                Intent intent= new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}