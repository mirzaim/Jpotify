package com.jpotify.controller;

import com.jpotify.logic.DataBase;
import com.jpotify.logic.Music;
import com.jpotify.logic.exceptions.NoTagFoundException;
import com.jpotify.view.Listeners.*;
import mpatric.mp3agic.InvalidDataException;
import mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;

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

    }

    @Override
    public void addSong(File file) {
        try {
            Music music = new Music(file);
            getGUI().getMainPanel().addPanel(music);
            dataBase.addSong(music);
        } catch (IOException | UnsupportedTagException | InvalidDataException | NoTagFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void albums() {

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
