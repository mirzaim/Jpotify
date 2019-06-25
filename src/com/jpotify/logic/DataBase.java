package com.jpotify.logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DataBase implements Serializable {

    private String username;
    private List<Album> albums;
    private List<PlayList> playLists;
    private List<Music> musics;

    public DataBase(String username) {
        this.username = username;
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
            file.createNewFile();
            new ObjectOutputStream(new FileOutputStream(file)).writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addSong(Music music) {

        if (musics.contains(music))
            return false;

        this.musics.add(music);
        Album album;
        if ((album = getAlbumById(music.getAlbumTitle())) != null)
            album.add(music);
        else
            makeAlbum(music);
        return true;
    }

    public int addSongToPlayList(Music music, PlayList playList) {
        if (playList.contains(music))
            return 0;

        playList.add(music);
        if (playList.getTitle().equals("Favourites"))
            music.setLiked(true);

        if (playList.getTitle().equals("Shared PlayList"))
            music.setShared(true);
        return 1;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public Album[] getAlbumsArray() {
        albums.sort((o1, o2) -> (int) (o2.getLastPlayedTime() - o1.getLastPlayedTime()));
        return albums.toArray(new Album[0]);
    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public String getUsername() {
        return username;
    }

    public Music[] getMusicsArray() {
        musics.sort((o1, o2) -> (int) (o2.getLastPlayedTime() - o1.getLastPlayedTime()));
        return musics.toArray(new Music[0]);
    }

    public Music getMusicById(String id) {
        for (Music music : musics)
            if (music.getId().equals(id))
                return music;
        return null;
    }


    public Music[] getMusicsByAlbumTitle(String albumTitle) {
        musics.sort((o1, o2) -> (int) (o2.getLastPlayedTime() - o1.getLastPlayedTime()));
        List<Music> album = new LinkedList<>();
        for (Music music : musics)
            if (music.getAlbumTitle() != null)
                if (music.getAlbumTitle().equals(albumTitle))
                    album.add(music);

        if (album.size() > 0)
            return album.toArray(new Music[0]);

        return null;
    }


    public Music[] getMusicsInPlayListTitle(String playListTitle) {
        for (PlayList playList : playLists)
            if (playList.getTitle().equals(playListTitle))
                if (playList.size() == 0)
                    return null;
                else
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
        playLists.add(new PlayList(name));
    }

    public String[] getPlaylistsNames() {
        ArrayList<String> names = new ArrayList<>();
        for (PlayList playList : playLists)
            names.add(playList.getTitle());
        return names.toArray(new String[0]);
    }

    public PlayList getPlayListByTitle(String name) {
        for (PlayList playlist : playLists) {
            if (playlist.getTitle().equals(name))
                return playlist;
        }
        return null;
    }

    public PlayList getSharedPlayList() {
        return playLists.get(1);
    }

    private Album makeAlbum(Music firstMusicForAlbum) {
        if (firstMusicForAlbum.getAlbumTitle() == null)
            return null;
        Album album = new Album(firstMusicForAlbum.getAlbumTitle(), firstMusicForAlbum);
        firstMusicForAlbum.setAlbum(album);
        album.add(firstMusicForAlbum);
        albums.add(album);
        album.updateLastPlayedTime(firstMusicForAlbum.getLastPlayedTime());
        return album;
    }
}
