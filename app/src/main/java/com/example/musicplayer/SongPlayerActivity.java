package com.example.musicplayer;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;

public class SongPlayerActivity extends AppCompatActivity {

    final Uri songURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_player);

    }
}
