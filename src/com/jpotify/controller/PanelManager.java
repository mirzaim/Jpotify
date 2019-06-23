package com.jpotify.controller;

import com.jpotify.logic.DataBase;
import com.jpotify.logic.Music;
import com.jpotify.logic.Player;
import com.jpotify.logic.PlayerListener;
import com.jpotify.logic.exceptions.NoTagFoundException;
import com.jpotify.view.Listeners.ListenerManager;
import com.jpotify.view.helper.MainPanelState;
import mpatric.mp3agic.InvalidDataException;
import mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class PanelManager extends ListenerManager implements PlayerListener {

    private DataBase dataBase;
    private Player player;

    public PanelManager(DataBase dataBase, Player player) {
        this.dataBase = dataBase;
        this.player = player;
    }

    public PanelManager(DataBase dataBase) {
        this.dataBase = dataBase;
        this.player = new Player(this);
    }


    // MenuPanelListener implementation
    @Override
    public void home() {

    }

    @Override
    public void songs() {
        getGUI().getMainPanel().removeAll();
        getGUI().getMainPanel().addPanels(dataBase.getMusicsArray());
        getGUI().getMainPanel().setMainPanelState(MainPanelState.SONGS);
    }

    @Override
    public void addSong(File file) {
        try {
            Music music = new Music(file);

            if (dataBase.addSong(music) == 0)
                JOptionPane.showMessageDialog(getGUI().getMainPanel(),
                        "File is already exist in your library");
            else {

                if (getGUI().getMainPanel().getMainPanelState() == MainPanelState.SONGS)
                    getGUI().getMainPanel().addPanel(music);

                if (getGUI().getMainPanel().getMainPanelState() == MainPanelState.ALBUMS)
                    this.albums();

                JOptionPane.showMessageDialog(getGUI().getMainPanel(),
                        music.getTitle() + " added to your Library");
            }
        } //for Testing #Test
        catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (NoTagFoundException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getGUI().getMainPanel(),
                    "Can't Add file",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println(e.getCause() + e.getMessage());
        }

    }

    @Override
    public void albums() {
        getGUI().getMainPanel().removeAll();
        getGUI().getMainPanel().addPanels(dataBase.getAlbumsArray());
        getGUI().getMainPanel().setMainPanelState(MainPanelState.ALBUMS);
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
        player.changePositionRelative(newPosition);
    }

    // MainPanelListener implementation
    @Override
    public void panelClicked(String id) {
        //#Test
        switch (getGUI().getMainPanel().getMainPanelState()) {
            case SONGS:
                Music music = dataBase.getMusicById(id);
                player.updateMusic(music);
                music.updateLastPlayedTime();
                player.playMusic();
                songs();
                break;
            case ALBUMS:
                getGUI().getMainPanel().removeAll();
                getGUI().getMainPanel().addPanels(dataBase.getMusicByAlbumTitle(id));
                getGUI().getMainPanel().setMainPanelState(MainPanelState.SONGS);
                break;
            case PLAYLIST:
                break;
            default:
        }

    }

    @Override
    public void closingProgram() {
        dataBase.saveDataBase();
    }

    @Override
    public void updatePosition(int position) {
        getGUI().getPlayerPanel().setSliderCurrentPosition(position);
    }

    @Override
    public void musicFinished() {

    }
}
