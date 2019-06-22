package com.jpotify.view.Listeners;

import java.io.File;

public interface MenuPanelListener {

    void home();

    void songs();

    void addSong(File file);

    void albums();

    void playListClicked(String name);

}
