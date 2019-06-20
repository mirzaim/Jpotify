package com.jpotify.view;

import com.jpotify.view.assets.AssetManager;
import com.jpotify.view.helper.MButton;
import com.jpotify.view.menu_panel.MiniMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class MenuPanel extends JPanel {

    MenuPanel() {
        setupPanel();

    }

    private void setupPanel() {
        setBackground(Color.BLACK);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        add(top, BorderLayout.PAGE_START);

        MiniMenu mainMenu = new MiniMenu(null);
        mainMenu.addButton(new MButton("Home", AssetManager.getImageIconByName("home.png"), true));
        mainMenu.addButton(new MButton("Browse", AssetManager.getImageIconByName("folder.png"), true));
        mainMenu.addButton(new MButton("Radio", AssetManager.getImageIconByName("radio.png"), true));
        top.add(mainMenu);

        MiniMenu library = new MiniMenu("YOUR LIBRARY");
        library.addButton(new MButton("Songs", true));
        library.addButton(new MButton("Albums", true));
        top.add(library);

        MiniMenu playList = new MiniMenu("PLAYLISTS");
        playList.addButton(new MButton("Test1", true));
        playList.addButton(new MButton("Test2", true));
        playList.addButton(new MButton("Test3", true));
        top.add(playList);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        add(bottom, BorderLayout.PAGE_END);

        MiniMenu newPlayList = new MiniMenu(null);
        newPlayList.addButton(new MButton("New Playlist", AssetManager.getImageIconByName("add.png"),true));
        bottom.add(newPlayList);

        
    }

}
