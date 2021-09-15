package com.example.playmusicservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.playmusicservice.contract.PlayMusicInterface;
import com.example.playmusicservice.model.Song;
import com.example.playmusicservice.presenter.PlayMusicPresenter;
import com.example.playmusicservice.service.PlayMusicService;

public class MainActivity extends AppCompatActivity implements PlayMusicInterface {

    private Button btnPlay;
    private Button btnPause;

    private PlayMusicPresenter mPlayMusicPresenter;

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
        String name = "";
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
    }

    @Override
    public void pauseSuccess() {
        Intent myIntent = new Intent(MainActivity.this, PlayMusicService.class);
        this.stopService(myIntent);
    }
}