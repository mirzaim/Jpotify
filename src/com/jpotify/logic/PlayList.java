package com.jpotify.logic;

import java.io.Serializable;

public class PlayList extends MusicList implements Serializable {

    public PlayList(String playlistTitle){
        super(playlistTitle);
    }

    public PlayList(String playlistTitle, Music playlistFirstMusic) {
        super(playlistTitle,playlistFirstMusic);
    }
}
