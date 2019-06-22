package com.jpotify.controller;

import com.jpotify.logic.DataBase;
import com.jpotify.logic.Music;
import com.jpotify.logic.exceptions.NoTagFoundException;
import com.jpotify.view.Listeners.*;
import mpatric.mp3agic.InvalidDataException;
import mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;

public class PanelManager extends ManagerListener {

    private DataBase dataBase;

    public PanelManager(DataBase dataBase) {
        this.dataBase = dataBase;
    }

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
            getGUI().addPanel(music);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (NoTagFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void albums() {

    }

    @Override
    public void playListClicked(String name) {

    }

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

    @Override
    public void panelClicked(String id) {

    }
}
