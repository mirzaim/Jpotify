package com.jpotify;

import com.jpotify.view.GUI;
import com.jpotify.view.GUIListener;
import com.jpotify.view.assets.AssetManager;

public class Main {
    public static void main(String[] args) {
        GUI.initGUI(new GUIListener() {
        });
        GUI gui = GUI.getGUI();
    }
}
