package com.example.playmusicservice.presenter;

import com.example.playmusicservice.contract.PlayMusicInterface;
import com.example.playmusicservice.model.Song;

// đảm nhận xử lý tất cả logic nghiệp vụ
public class PlayMusicPresenter {

    private PlayMusicInterface mPlayMusicInterface ;

    public PlayMusicPresenter(PlayMusicInterface playMusicInterface) {
        this.mPlayMusicInterface = playMusicInterface;
    } // tạo 1 contructor

    public void playMusic (Song song){
        mPlayMusicInterface.playSuccess();
    }

    public void pauseMusic (Song song){
        mPlayMusicInterface.pauseSuccess();
    }
}
