package com.jpotify;

import com.jpotify.controller.PanelManager;
import com.jpotify.logic.DataBase;
import com.jpotify.view.GUI;

public class Main {
    public static void main(String[] args) {

        DataBase dataBase = new DataBase();

        GUI.initGUI(new PanelManager(dataBase));
        GUI gui = GUI.getGUI();
    }
}
