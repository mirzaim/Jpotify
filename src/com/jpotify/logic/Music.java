package com.jpotify.logic;

import com.jpotify.logic.exceptions.NoTagFoundException;
import mpatric.mp3agic.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Music implements Comparable<Music>{

    private Mp3File mp3File;
    private ID3v2 id3v2Tag;

    private String title;
    private String artist;
    private String album;
    private String year;
    private int genre;
    private byte[] albumImageData;


    private int size;
    private int lastPlayedTime;

    // for sorting music in playlist
    private int addingToListTime;

    public Music(File file) throws IOException, UnsupportedTagException, InvalidDataException, NoTagFoundException {
        
        try (FileInputStream fis = new FileInputStream(file)) {

            this.size = (int) file.length();
            fis.skip(this.size - 128);
            byte[] last128 = new byte[128];
            fis.read(last128);
            
            String id3v1 = new String(last128);
            String tag = id3v1.substring(0, 3);
            
            if (tag.equals("TAG")) {
                this.title =  id3v1.substring(3, 32);
                this.artist = id3v1.substring(33, 62);
                this.album =  id3v1.substring(63, 91);
                this.year =  id3v1.substring(93, 97);
            } else {
                throw new NoTagFoundException("There is No id3v1v1 Tag");
            }
        } 

        this.mp3File = new Mp3File(file.getAbsolutePath());
        if (mp3File.hasId3v2Tag()) {

            this.id3v2Tag = mp3File.getId3v2Tag();
//            this.artist = id3v2Tag.getArtist();
//            this.album = id3v2Tag.getAlbum();
//            this.year = id3v2Tag.getYear();
            this.genre = id3v2Tag.getGenre();
            albumImageData = id3v2Tag.getAlbumImage();
        } else {
            throw new NoTagFoundException("There is No ID3v1 Tag");
        }
    }

    @Override
    public int compareTo(Music otherMusic) {
        if(this.addingToListTime >  otherMusic.addingToListTime)
            return 1;
        else if(this.addingToListTime < otherMusic.addingToListTime)
            return -1;
        else
            return 0;
    }
}
