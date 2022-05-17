package com.example.wecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;

public class Reciever extends BroadcastReceiver {

    public static MediaPlayer mediaPlayer;
    public static boolean isplayingAudio=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNot();
        notificationHelper.getManager().notify(1, nb.build()); //build a Notification
        playAudio(context); //starts the music
        Intent i = new Intent();  //to direct to the Alert if the App is open
        i.setClassName("com.example.wecker", "com.example.wecker.empfaengerActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    //starts the Music
    public static void playAudio(Context c){
        mediaPlayer = MediaPlayer.create(c,R.raw.sound);
        if(!mediaPlayer.isPlaying())
        {
            isplayingAudio=true;
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }
    //stops the music
    public static void stopMusic(){
        isplayingAudio=false;
        mediaPlayer.stop();
    }

}