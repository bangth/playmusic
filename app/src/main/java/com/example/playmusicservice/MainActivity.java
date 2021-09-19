package com.example.playmusicservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.playmusicservice.contract.PlayMusicInterface;
import com.example.playmusicservice.model.Song;
import com.example.playmusicservice.presenter.PlayMusicPresenter;
import com.example.playmusicservice.service.PlayMusicService;

public class MainActivity extends AppCompatActivity implements PlayMusicInterface {

    private Button btnPlay;
    private Button btnPause;

    private TextView tvNameSong;

    private PlayMusicPresenter mPlayMusicPresenter;

    private PlayMusicService playMusicService;
    private boolean isServiceConnected; // mac dinh dang false

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayMusicService.PlayMusicBinder playMusicBinder = (PlayMusicService.PlayMusicBinder) service;
            playMusicService = playMusicBinder.getPlayMusicService();
            isServiceConnected = true ;

            handleMusic ();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            playMusicService = null;
            isServiceConnected = false ;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.button_play);
        btnPause = findViewById(R.id.button_pause);

        mPlayMusicPresenter = new PlayMusicPresenter(this);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();

                if (playMusicService!= null) {
                    if (playMusicService.isPlaying()) {
                        playMusicService.pause2Music();
                    } else {
                        playMusicService.resumeMusic();
                    }
                    setStatusPlayorPause();
                }
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseMusic();
            }
        });
    }


    public void playMusic() {
//        Intent myIntent = new Intent(MainActivity.this, PlayMusicService.class);
//        this.startService(myIntent);
        String name = "mua tren pho hue";
        String time = "";
        Song song = new Song (name , time);
        mPlayMusicPresenter.playMusic(song);
    }
    public void pauseMusic() {
//        Intent myIntent = new Intent(MainActivity.this, PlayMusicService.class);
//        this.stopService(myIntent);
        String name = "";
        String time = "";
        Song song = new Song (name , time);
        mPlayMusicPresenter.pauseMusic(song);
    }

    @Override
    public void playSuccess() {
        Intent myIntent = new Intent(MainActivity.this, PlayMusicService.class);
        this.startService(myIntent);

        bindService(myIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void pauseSuccess() {
        Intent myIntent = new Intent(MainActivity.this, PlayMusicService.class);
        this.stopService(myIntent);

        if (isServiceConnected){
            unbindService(mServiceConnection);
            isServiceConnected = false;
        }
    }

    private void handleMusic() {
        tvNameSong = findViewById(R.id.tv_songname);
//        tvNameSong.setText(playMusicService.getSong().getName());
        setStatusPlayorPause();
    }

    private void setStatusPlayorPause (){
        if (playMusicService==null){
            return;
        }
        if (playMusicService.isPlaying()){
            btnPlay.setText("Pause");
        } else {
            btnPlay.setText("Play");
        }
    }
}