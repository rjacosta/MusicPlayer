package com.example.musicplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class SongPlayerActivity extends AppCompatActivity {

    private MasterMediaList masterMediaList;
    private String songFile;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_player);
        masterMediaList = MasterMediaList.getInstance();
        songFile = masterMediaList.getMediaData().get(0);
        Uri songUri = Uri.fromFile(new File(songFile));
        mediaPlayer = MediaPlayer.create(SongPlayerActivity.this, songUri);
    }

    public void playSong(View view) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pauseSong(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}
