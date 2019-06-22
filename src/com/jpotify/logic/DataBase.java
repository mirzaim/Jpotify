package com.jpotify.logic;

import java.util.ArrayList;

public class DataBase {

    private ArrayList<Album> albums;
    private ArrayList<PlayList> playLists;
    private MusicList songs;

    public DataBase() {

        this.albums = new ArrayList<>();
        this.playLists = new ArrayList<>();
        this.songs = new MusicList();

    }

    public void addSong(Music music) {
        this.songs.add(music);
    }

    public Music getMusicById(String id) {
        return null;
    }


}
