package com.jpotify;

import com.jpotify.logic.DataBase;
import com.jpotify.view.GUI;
import com.jpotify.view.GUIListener;
import com.jpotify.view.assets.AssetManager;

public class Main {
    public static void main(String[] args) {

        DataBase dataBase = new DataBase();

        GUI.initGUI(new GUIListener() {
        });
        GUI gui = GUI.getGUI();
    }
}
