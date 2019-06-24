package com.jpotify.logic.network.message;

import com.jpotify.logic.Music;
import com.jpotify.logic.PlayList;

public class CommandMessage extends AbstractMessage {

    private CommandType commandType;
    private Music music;
    private PlayList sharedPlayList;

    public CommandMessage(String senderUsername, CommandType commandType) {
        super(senderUsername);
        this.commandType = commandType;
    }

    public CommandMessage(String senderUsername, CommandType commandType, Music music) {
        this(senderUsername, commandType);
        this.music = music;
    }

    public CommandMessage(String senderUsername, CommandType commandType, PlayList sharedPlayList) {
        this(senderUsername, commandType);
        this.sharedPlayList = sharedPlayList;
    }

//    public CommandMessage(CommandType commandType) {
//        this("System", commandType);
//    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Music getMusic() {
        return music;
    }

    public PlayList getSharedPlayList() {
        return sharedPlayList;
    }


}
