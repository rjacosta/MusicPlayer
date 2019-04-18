package com.example.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MediaRetrieverActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;

    private static final String MEDIA_DATA_INTENT_NAME = "media data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(MediaRetrieverActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MediaRetrieverActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MediaRetrieverActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MediaRetrieverActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
            ArrayList<String> mediaData = retrieveMedia();
            setResultOfMediaRetrieval(mediaData);
            finish();
        }
    }

    public ArrayList<String> retrieveMedia() {
        ArrayList<String> mediaData = new ArrayList<>();
        ContentResolver cR = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = cR.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                mediaData.add(songCursor.getString(songData));
            } while (songCursor.moveToNext());
            songCursor.close();
        }
        return mediaData;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(MediaRetrieverActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    ArrayList<String> mediaData = retrieveMedia();
                    setResultOfMediaRetrieval(mediaData);
                }
            }
            else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private void setResultOfMediaRetrieval(ArrayList<String> mediaData) {
        Intent resultsIntent = new Intent();
        resultsIntent.putExtra(MEDIA_DATA_INTENT_NAME, mediaData);
        setResult(RESULT_OK, resultsIntent);
    }
}
