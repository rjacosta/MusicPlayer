package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MediaListActivity extends AppCompatActivity {

    private static final String LIST_TYPE = "ListType";

    private static final String SONG_NUMBER = "SongNumber";

    private MasterMediaList masterMediaList;

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_list);
        masterMediaList = MasterMediaList.getInstance();
        Intent intent = getIntent();
        MediaListType listType = (MediaListType) intent.getExtras().get(LIST_TYPE);
        ArrayList<String> songList = masterMediaList.getAllSongTitles();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        list = findViewById(R.id.listView);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent songPlayerIntent = new Intent(this, SongPlayerActivity.class);
            songPlayerIntent.putExtra(SONG_NUMBER, id);
            startActivity(songPlayerIntent);
        });
    }

    public enum MediaListType {
        Song, Artist, Album;
    }

}
