package com.example.musicplayer;

import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MasterMediaList {

    private static MasterMediaList masterMediaList;

    private ArrayList<String> mediaData;

    private MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

    private MasterMediaList() {

    }

    public static MasterMediaList getInstance() {
        if (masterMediaList == null) {
            masterMediaList = new MasterMediaList();
        }
        return masterMediaList;
    }

    public ArrayList<String> getMediaData() {
        return mediaData;
    }

    public void setMediaData(ArrayList<String> mediaData) {
        this.mediaData = mediaData;
    }

    public ArrayList<String> getAllSongTitles() {
        return (ArrayList<String>) mediaData.stream()
                .map(mediaFileString -> getMediaMetadata(mediaFileString, MediaMetadataRetriever.METADATA_KEY_TITLE))
                .collect(Collectors.toList());
    }

    private String getMediaMetadata(String mediaFileString, int metadataKey) {
        Uri songUri = Uri.fromFile(new File(mediaFileString));
        mediaMetadataRetriever.setDataSource(mediaFileString);
        //mediaMetadataRetriever.setDataSource(MasterMediaList.this, songUri);
        return mediaMetadataRetriever.extractMetadata(metadataKey);
    }

}
