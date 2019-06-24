package com.jpotify.logic.network;

import com.jpotify.logic.Music;
import com.jpotify.logic.PlayList;

public interface ServerListener {

    void friendBecomeOnline(String username);

    void friendMusicStarted(String username, Music music);

    void friendMusicEnded(String username, Music music);

    void sharedPlayListData(String username, PlayList playList);

    void musicDownloaded(Music music);
}
