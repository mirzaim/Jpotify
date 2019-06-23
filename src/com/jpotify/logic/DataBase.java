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
        playLists.add(new PlayList("Favourites"));
        playLists.add(new PlayList("Shared PlayList"));
    }

    public static DataBase loadDataBase() {
        DataBase dataBase = null;
        File file = new File("database.m");
        try {
            if (file.exists())
                dataBase = (DataBase) (new ObjectInputStream(new FileInputStream("database.m"))).readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataBase;
    }

    public void saveDataBase() {
        File file = new File("database.m");

        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
            new ObjectOutputStream(new FileOutputStream(file)).writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int addSong(Music music) {

        if (musics.contains(music))
            return 0;


        for (Album album : this.albums) {
            if (album.getTitle().equals(music.getAlbum())) {
                album.add(music);
                musics.add(music);
                    music.setShared(true);
                return 1;
            }
        }

        //some musics don't have album
        musics.add(music);
        if (music.getAlbum() != null) {
            Album album = new Album(music.getAlbum(), music);
            // first music is separate filed (not in list)
            album.add(music);
            albums.add(album);
        }
        return 1;
    }

    public int addSongToPlayList(Music music, PlayList playList){
        if(playList.contains(music))
            return 0;

        playList.add(music);
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
        musics.sort((o1, o2) -> {
            return (int) (o2.getLastPlayedTime() - o1.getLastPlayedTime());
        });
        return musics.toArray(new Music[0]);
    }


    public Music getMusicById(String id) {
        for (Music music : musics)
            if (music.getId().equals(id))
                return music;
        return null;
    }

    public Music[] getMusicByAlbumTitle(String albumTitle) {
        for (Album album : albums)
            if (album.getTitle().equals(albumTitle))
                return album.toArray(new Music[0]);

        return null;
    }

    public Music[] getMusicByPlayListTitle(String playListTitle) {
        for (PlayList playList : playLists)
            if (playList.getTitle().equals(playListTitle))
                return playList.toArray(new Music[0]);
        return null;
    }

    public Album getAlbumById(String id) {
        for (Album album : albums)
            if (album.getId().equals(id))
                return album;
        return null;
    }

    public void createPlayList(String name) {
        PlayList playList = new PlayList(name);
        playLists.add(playList);
    }


}
