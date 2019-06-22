package com.jpotify.controller;

import com.jpotify.logic.DataBase;
import com.jpotify.logic.Music;
import com.jpotify.view.Listeners.*;

import java.io.File;

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
        try{
        Music music = new Music(file);
        getGUI().getMainPanel().addPanel(music);

        }catch (Exception e){
            // something do
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
