package com.jpotify.logic;

import java.util.ArrayList;

public class DataBase {

    private ArrayList<Album> albums;
    private ArrayList<PlayList> playLists;
    private MusicList musics;

    public DataBase() {

        this.albums = new ArrayList<>();
        this.playLists = new ArrayList<>();
        this.musics = new MusicList();

    }

    public int addSong(Music music) {

        if (musics.contains(music))
            return 0;


        for (Album album : this.albums) {
            if (album.getAlbumTitle().equals(music.getAlbum())) {
                album.add(music);
                this.musics.add(music);
                return 1;
            }
        }


        this.musics.add(music);
        Album album = new Album(music.getAlbum());
        album.add(music);
        album.setAlbumImage(music.getAlbumImage());
        albums.add(album);
        return 1;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public Album[] getAlbumsArray(){
        return albums.toArray(new Album[0]);
    }

    public ArrayList<PlayList> getPlayLists() {
        return playLists;
    }

    public MusicList getMusics() {
        return musics;
    }

    public Music[] getMusicsArray() {
        return (Music[]) musics.toArray(new Music[0]);
    }

    public Music getMusicById(String id) {
        return null;
    }


}
