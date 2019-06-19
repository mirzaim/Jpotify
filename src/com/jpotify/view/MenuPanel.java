package com.jpotify.view;

import com.jpotify.view.assets.AssetManager;
import com.jpotify.view.helper.MButton;

import javax.swing.*;
import java.awt.*;

class MenuPanel extends JPanel {

    MenuPanel() {
        setBackground(Color.BLACK);
        add(new MButton("Home", AssetManager.getImageIconByName("home_filled.png"), true));
    }
}
