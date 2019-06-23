package com.jpotify;

import com.jpotify.controller.PanelManager;
import com.jpotify.logic.DataBase;
import com.jpotify.logic.Player;
import com.jpotify.view.GUI;

public class Main {
    public static void main(String[] args) {

        DataBase dataBase;
        if ((dataBase = DataBase.loadDataBase()) == null)
            dataBase = new DataBase();

        PanelManager panelManager = new PanelManager(dataBase, new Player());
        GUI.initGUI(panelManager);
        GUI gui = GUI.getGUI();

        //click songs button to load songs.
        panelManager.songs();
    }
}
