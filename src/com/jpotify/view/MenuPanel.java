package com.jpotify.view;

import com.jpotify.view.Listeners.MenuPanelListener;
import com.jpotify.view.assets.AssetManager;
import com.jpotify.view.helper.ImagePanel;
import com.jpotify.view.helper.MButton;
import com.jpotify.view.helper.MiniMenu;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

class MenuPanel extends JPanel implements ActionListener {
    private final int WIDTH = 200;

    private MiniMenu playList;
    private MenuPanelListener listener;

    MenuPanel(MenuPanelListener listener) {
        this.listener = listener;
        setupPanel();

    }

    private void setupPanel() {
        setBackground(Color.DARK_GRAY);
        setBorder(new EmptyBorder(20, 0, 0, 0));
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        add(top, BorderLayout.PAGE_START);

        MiniMenu mainMenu = new MiniMenu(null);
        mainMenu.addButton(new MButton("Home",
                AssetManager.getImageIconByName("home.png"), true, this));
        mainMenu.addButton(new MButton("Browse",
                AssetManager.getImageIconByName("folder.png"), true, this));
        mainMenu.addButton(new MButton("Radio",
                AssetManager.getImageIconByName("radio.png"), true, this));
        top.add(mainMenu);

        MiniMenu library = new MiniMenu("YOUR LIBRARY");
        library.addButton(new MButton("Songs", true, this));
        library.addButton(new MButton("Add Song",
                AssetManager.getImageIconByName("add.png"), true, this));
        library.addButton(new MButton("Albums", true, this));
        top.add(library);

        playList = new MiniMenu("PLAYLISTS");
        playList.addButton(new MButton("Test1", true));
        playList.addButton(new MButton("Test2", true));
        playList.addButton(new MButton("Test3", true));
        top.add(playList);


        JPanel bottom = new JPanel();
        bottom.setBackground(this.getBackground());
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.PAGE_AXIS));
        add(bottom, BorderLayout.PAGE_END);

        MiniMenu newPlayList = new MiniMenu(null);
        newPlayList.addButton(new MButton("New Playlist",
                AssetManager.getImageIconByName("add.png"), true, this));
        bottom.add(newPlayList);

        ImagePanel imagePanel = new ImagePanel(AssetManager.getBufferedImageByName("abc.jpg"), WIDTH, -1);
        bottom.add(imagePanel);
    }

    public void addPlayList(String name) {
        playList.addButton(new MButton(name, true));
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("PLAYLISTS")) {
            listener.playListClicked(((MButton) e.getSource()).getText());
        }

        switch (((MButton) e.getSource()).getId()) {
            case "Home":
                listener.home();
                break;
            case "Songs":
                listener.songs();
                break;
            case "Add Song":
                System.out.println("ADD");
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    listener.addSong(file);
                }
                break;
            case "Albums":
                listener.albums();
                break;
            case "New Playlist":
                break;
            default:

        }


    }

}
