package com.jpotify.logic;

import java.io.Serializable;

public class Album extends MusicList implements Serializable {

    public Album(String albumTitle, Music albumFirstMusic) {
        super(albumTitle,albumFirstMusic);
    }


}
