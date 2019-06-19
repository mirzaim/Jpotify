package com.jpotify.view.assets;

import javax.swing.*;

public class AssetManager {

    private static final String PATH = "src/com/jpotify/view/assets/";

    public static ImageIcon getImageIconByName(String filename) {
        return new ImageIcon(PATH + filename);
    }
}
