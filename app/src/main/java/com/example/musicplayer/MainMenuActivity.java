package com.example.musicplayer;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.musicplayer.builder.MasterMediaListBuilder;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private MediaRetrieverActivity mediaRetrieverActivity;

    private final int REQUEST_CODE = 2;

    private static final String MEDIA_DATA_INTENT_NAME = "media data";

    private static final String LIST_TYPE = "ListType";

    private MasterMediaList masterMediaList;
    private MasterMediaListBuilder masterMediaListBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mediaRetrieverIntent = new Intent(this, MediaRetrieverActivity.class);
        masterMediaList = MasterMediaList.getInstance();
        startActivityForResult(mediaRetrieverIntent, REQUEST_CODE);
    }

    public void goToSongPlayer(View view) {
        Intent songPlayerIntent = new Intent(this, SongPlayerActivity.class);
        startActivity(songPlayerIntent);
    }

    public void goToAllSongs(View view) {
        Intent songListIntent = new Intent(this, MediaListActivity.class);
        songListIntent.putExtra(LIST_TYPE, MediaListActivity.MediaListType.Song);
        startActivity(songListIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> mediaData = data.getStringArrayListExtra(MEDIA_DATA_INTENT_NAME);
                masterMediaList.setMediaData(mediaData);
                masterMediaListBuilder = new MasterMediaListBuilder(mediaData);
                masterMediaList.setMasterList(masterMediaListBuilder.build());
            }
        }
    }
}
