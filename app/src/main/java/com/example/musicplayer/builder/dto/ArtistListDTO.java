package com.example.musicplayer.builder.dto;

import java.util.HashMap;

public class ArtistListDTO {

    public HashMap<String, AlbumListDTO> artistListMap;

    public HashMap<String, AlbumListDTO> getArtistListMap() {
        return artistListMap;
    }

    public void setArtistListMap(HashMap<String, AlbumListDTO> artistListMap) {
        this.artistListMap = artistListMap;
    }

}
