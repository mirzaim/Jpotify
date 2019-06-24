package com.jpotify.logic;

import com.jpotify.view.helper.DrawableItem;
import java.io.Serializable;

public class Album extends MusicList implements DrawableItem, Serializable {

    private long lastPlayedTime;

    public Album(String albumTitle, Music albumFirstMusic) {
        super(albumTitle,albumFirstMusic);
    }

    public void updateLastPlayedTime(long time) {
        lastPlayedTime = time;
    }


    public long getLastPlayedTime() {
        return lastPlayedTime;
    }


}
