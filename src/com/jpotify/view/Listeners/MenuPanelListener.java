package com.jpotify.view.Listeners;

import java.io.File;

public interface MenuPanelListener {

    void home();

    void songs();

    void addSongButton(File file);

    void albums();

    void playListClicked(String name);

    void newPlayList(String name);

    void loadPlaylists();


}
