package com.jpotify.logic;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase implements Serializable {

    private List<Album> albums;
    private List<PlayList> playLists;
    private List<Music> musics;

    public DataBase() {

        albums = new LinkedList<>();
        playLists = new LinkedList<>();
        musics = new LinkedList<>();

    }

    public static DataBase loadDataBase() {
        DataBase dataBase = null;
        try {
            dataBase = (DataBase) (new ObjectInputStream(new FileInputStream("database.m"))).readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataBase;
    }

    public void saveDataBase() {
        try {
            new ObjectOutputStream(new FileOutputStream("database.m")).writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Album album = new Album(music.getAlbum(), music);
        album.add(music);
        albums.add(album);
        return 1;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public Album[] getAlbumsArray() {
        return albums.toArray(new Album[0]);
    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public Music[] getMusicsArray() {
        return (Music[]) musics.toArray(new Music[0]);
    }

    public Music getMusicById(String id) {
        for (Music music : musics)
            if (music.getId().equals(id))
                return music;
        return null;
    }


}
