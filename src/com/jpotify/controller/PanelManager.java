package com.jpotify.controller;

import com.jpotify.logic.DataBase;
import com.jpotify.logic.Music;
import com.jpotify.logic.Player;
import com.jpotify.view.Listeners.ListenerManager;

import javax.swing.*;
import java.io.File;

public class PanelManager extends ListenerManager {

    private DataBase dataBase;
    private Player player;

    public PanelManager(DataBase dataBase, Player player) {
        this.dataBase = dataBase;
        this.player = player;
    }


    // MenuPanelListener implementation
    @Override
    public void home() {

    }

    @Override
    public void songs() {
        getGUI().getMainPanel().removeAll();
        getGUI().getMainPanel().addPanels(dataBase.getMusicsArray());
        getGUI().getMainPanel().setCurrentDisplayingPanels(0);
    }

    @Override
    public void addSong(File file) {
        try {
            Music music = new Music(file);

            if (dataBase.addSong(music) == 0)
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
        getGUI().getMainPanel().addPanels(dataBase.getAlbumsArray());
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
        //#Test
        player.updateMusic(dataBase.getMusicById(id));
        player.playMusic();
    }

    @Override
    public void closingProgram() {
        dataBase.saveDataBase();
    }
}
