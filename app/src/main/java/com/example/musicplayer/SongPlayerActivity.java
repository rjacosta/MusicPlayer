package com.example.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class SongPlayerActivity extends AppCompatActivity {

    private static final String SONG_NUMBER = "SongNumber";

    private MasterMediaList masterMediaList;
    private String songFile;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_player);
        masterMediaList = MasterMediaList.getInstance();
        Intent intent = getIntent();
        int songNumber = intent.getExtras() != null ? intent.getExtras().getInt(SONG_NUMBER) : 0;
        if (masterMediaList.getMediaDataSize() != 0) {
            songFile = masterMediaList.getMediaData().get(songNumber);
            Uri songUri = Uri.fromFile(new File(songFile));
            mediaPlayer = MediaPlayer.create(SongPlayerActivity.this, songUri);
            if (intent.getExtras() != null) {
                mediaPlayer.start();
            }
        }
    }

    public void playSong(View view) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pauseSong(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}
