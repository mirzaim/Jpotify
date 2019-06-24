package com.jpotify.logic.network.message;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class AbstractMessage implements Serializable {

    private final String senderUsername;
    private final long sentTime;

    protected AbstractMessage(String senderUsername) {
        this.senderUsername = senderUsername;
        this.sentTime = new Date().getTime();
    }

    public final String getSenderUsername() {
        return senderUsername;
    }

    public final long getPublishDate() {
        return sentTime;
    }

}
