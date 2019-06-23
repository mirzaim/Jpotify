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

    public int addSong(Music music) {

        if (songs.contains(music))
            return 0;


        for (Album album : this.albums) {
            if (album.getAlbumTitle().equals(music.getAlbum())) {
                album.add(music);
                this.songs.add(music);
                return 1;
            }
        }


        this.songs.add(music);
        Album album = new Album(music.getAlbum());
        album.add(music);
        album.setAlbumImage(music.getAlbumImage());
        albums.add(album);
        return 1;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public ArrayList<PlayList> getPlayLists() {
        return playLists;
    }

    public MusicList getSongs() {
        return songs;
    }

    public Music getMusicById(String id) {
        return null;
    }


}
