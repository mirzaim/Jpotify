package com.jpotify.logic;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayList extends MusicList implements Serializable {

    public PlayList(String playlistTitle) {
        super(playlistTitle);
    }

    public PlayList(String playlistTitle, Music playlistFirstMusic) {
        super(playlistTitle, playlistFirstMusic);
    }

    public int name2index(String name) {
        for (Music music : this)
            if (music.getTitle().equals(name)) {
                return this.indexOf(music);
            }
        return -1;
    }



}
