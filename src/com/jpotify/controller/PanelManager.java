package com.jpotify.controller;

import com.jpotify.logic.Album;
import com.jpotify.logic.DataBase;
import com.jpotify.logic.Music;
import com.jpotify.view.Listeners.ListenerManager;

import javax.swing.*;
import java.io.File;
import java.util.Iterator;

public class PanelManager extends ListenerManager {

    private DataBase dataBase;

    public PanelManager(DataBase dataBase) {
        this.dataBase = dataBase;
    }


    // MenuPanelListener implementation
    @Override
    public void home() {

    }

    @Override
    public void songs() {
        getGUI().getMainPanel().removeAll();

        for (Iterator<Music> it = dataBase.getSongs().iterator(); it.hasNext(); ) {
            Music music = it.next();
            getGUI().getMainPanel().addPanel(music);
        }

        getGUI().getMainPanel().setCurrentDisplayingPanels(0);
        getGUI().getMainPanel().revalidate();
        getGUI().getMainPanel().repaint();
    }

    @Override
    public void addSong(File file) {

        try {
            Music music = new Music(file);

            if(dataBase.addSong(music) == 0)
                JOptionPane.showMessageDialog(getGUI().getMainPanel(),
                        "File is already exist in your library");
            else {

                if (getGUI().getMainPanel().getCurrentDisplayingPanels() == 0)
                    getGUI().getMainPanel().addPanel(music);

                if (getGUI().getMainPanel().getCurrentDisplayingPanels() == 1)
                    this.albums();

                JOptionPane.showMessageDialog(getGUI().getMainPanel(),
                        music.getTitle() + " added to your Library");
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(getGUI().getMainPanel(),
                    "Can't Add file",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    @Override
    public void albums() {
        getGUI().getMainPanel().removeAll();

        for (Album album : dataBase.getAlbums()) {
            getGUI().getMainPanel().addPanel(album);
        }

        getGUI().getMainPanel().setCurrentDisplayingPanels(1);
    }

    @Override
    public void playListClicked(String name) {

    }


    // PlayerPanelListener implementation
    @Override
    public void play() {

    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void shuffle() {

    }

    @Override
    public void replay() {

    }

    @Override
    public void sliderChanged(int newPosition) {

    }


    // MainPanelListener implementation
    @Override
    public void panelClicked(String id) {

    }
}
