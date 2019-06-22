package com.jpotify.logic;

import java.util.ArrayList;
import java.util.Iterator;

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

        for (Iterator<Music> it = songs.iterator(); it.hasNext(); ) {
            Music m = it.next();
            if (music.equals(m))
                return 0;
        }

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
}
