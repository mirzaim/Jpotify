package com.jpotify;

import com.jpotify.controller.PanelManager;
import com.jpotify.logic.DataBase;
import com.jpotify.logic.Player;
import com.jpotify.view.GUI;

public class Main {
    public static void main(String[] args) {

        DataBase dataBase;
        if ((dataBase = DataBase.loadDataBase()) == null)
            dataBase = new DataBase(GUI.askUserName());


        PanelManager panelManager = new PanelManager(dataBase);
        GUI.initGUI(panelManager);
        GUI gui = GUI.getGUI();

        // for starting program - if doesnt exist nothing will not show
        panelManager.loadPlaylists();
        panelManager.songs();
    }
}
