package com.example.musicplayer.builder;

import android.media.MediaMetadataRetriever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MasterMediaListBuilder {

    private ArrayList<String> mediaData;

    private MediaMetadataRetriever mediaMetadataRetriever;

    public MasterMediaListBuilder(ArrayList<String> mediaData) {
        this.mediaData = mediaData;
        this.mediaMetadataRetriever = new MediaMetadataRetriever();
    }

    public HashMap<String, HashMap<String, HashMap<String, String>>> build() {
        ArrayList<SongDetails> allSongs = new ArrayList<>();
        ArrayList<String> artistsList = new ArrayList<>();
        ArrayList<String> albumsList = new ArrayList<>();
        HashMap<String, HashMap<String, HashMap<String, String>>> masterList = new HashMap<>();
        for (String mediaDataFile : mediaData) {
            mediaMetadataRetriever.setDataSource(mediaDataFile);
            String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String album = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);

            if (artist == null) {
                artist = "unknown artist";
            }
            if (album == null) {
                album = "unknown album";
            }
            if (title == null) {
                title = "unknown song";
            }

            if (!artistsList.contains(artist)) {
                artistsList.add(artist);
            }
            if (!albumsList.contains(album)) {
                albumsList.add(album);
            }

            SongDetails currSong = new SongDetails(artist, title, album, mediaDataFile);
            allSongs.add(currSong);
        }
        HashMap<String, HashMap<String, HashMap<String, String>>> artistToAlbumToTitleToSongFileMap = new HashMap<>();
        for (String artist : artistsList) {

            ArrayList<SongDetails> allSongsByArtist = (ArrayList<SongDetails>) allSongs.stream()
                    .filter(song -> song.getArtist().equals(artist))
                    .collect(Collectors.toList());

            ArrayList<String> albums = (ArrayList<String>) allSongsByArtist.stream()
                    .map(song -> song.getAlbum())
                    .distinct()
                    .collect(Collectors.toList());
            HashMap<String, HashMap<String, String>> albumToTitleToSongFileMap = new HashMap<>();

            for (String album : albums) {
                ArrayList<SongDetails> songsOnAlbum = (ArrayList<SongDetails>) allSongsByArtist.stream()
                        .filter(song -> song.getAlbum().equals(album))
                        .collect(Collectors.toList());
                HashMap<String, String> titleToSongFileMap = new HashMap<>();
                for (SongDetails song : songsOnAlbum) {
                    titleToSongFileMap.put(song.getTitle(), song.getSongFile());
                }
                albumToTitleToSongFileMap.put(album, titleToSongFileMap);
            }

            artistToAlbumToTitleToSongFileMap.put(artist, albumToTitleToSongFileMap);
        }
        return artistToAlbumToTitleToSongFileMap;
    }

    private class SongDetails {
        String artist;
        String title;
        String album;
        String songFile;

        public SongDetails(String artist, String title, String album, String songFile) {
            this.artist = artist;
            this.title = title;
            this.album = album;
            this.songFile = songFile;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getSongFile() {
            return songFile;
        }

        public void setSongFile(String songFile) {
            this.songFile = songFile;
        }
    }
}
