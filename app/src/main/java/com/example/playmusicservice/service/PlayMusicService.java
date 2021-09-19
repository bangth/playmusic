package com.example.playmusicservice.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.playmusicservice.R;
import com.example.playmusicservice.model.Song;

import static com.example.playmusicservice.service.NotificationApplication.CHANNEL_ID;

public class PlayMusicService extends Service {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    private Song song;

    public PlayMusicService() {
    }

    private PlayMusicBinder playMusicBinder = new PlayMusicBinder();

    public class PlayMusicBinder extends Binder {
        public PlayMusicService getPlayMusicService() {
            return PlayMusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("bangth5", "playmussic service OnBind");
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return playMusicBinder;
    }

    @Override
    public void onCreate() {
        Log.e("bangth5", "playmussic service OnCreate");
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.muatrenphohue);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("bangth5", "playmussic service OnStartCommand");
        mediaPlayer.start();

        sendNotification ();
//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public void pause2Music (){
        if (mediaPlayer !=null && isPlaying) {
            mediaPlayer.pause();
            isPlaying=false;
        }
    }
    public void resumeMusic (){
        if (mediaPlayer !=null && !isPlaying) {
            mediaPlayer.start();
            isPlaying=true;
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText("bangth5");
        builder.setSmallIcon(R.drawable.ic_small_notification_audiotrack);

        Notification notify = builder.build();
        startForeground(1, notify);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("bangth5", "playmussic service OnUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("bangth5", "playmussic service OnDestroy");
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer= null;
        }

        //mediaPlayer.release();

        super.onDestroy();
    }

    public Song getSong() {
        return song;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
