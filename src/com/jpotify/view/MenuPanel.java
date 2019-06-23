package com.jpotify.view;

import com.jpotify.logic.PlayList;
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
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;

public class MenuPanel extends JPanel implements ActionListener {
    private final int WIDTH = 200;

    private MiniMenu playList;
    private MenuPanelListener listener;

    private ImagePanel imagePanel;

    public MenuPanel(MenuPanelListener listener) {
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
        playList.addButton(new MButton("Favourites", true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.playListClicked("Favourites");
            }
        }));

        playList.addButton(new MButton("Shared PlayList", true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.playListClicked("Shared PlayList");
            }
        }));
        top.add(playList);


        JPanel bottom = new JPanel();
        bottom.setBackground(this.getBackground());
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.PAGE_AXIS));
        add(bottom, BorderLayout.PAGE_END);

        MiniMenu newPlayList = new MiniMenu(null);
        newPlayList.addButton(new MButton("New Playlist",
                AssetManager.getImageIconByName("add.png"), true, this));
        bottom.add(newPlayList);

        imagePanel = new ImagePanel(AssetManager.getBufferedImageByName("abc.jpg"), WIDTH, -1);
        bottom.add(imagePanel);
    }

    public void addPlayList(String name) {
        playList.addButton(new MButton(name, true));
    }

    public void setArtwork(BufferedImage image) {
        imagePanel.setImage(image, WIDTH, -1);
    }

    public MiniMenu getPlayList() {
        return playList;
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
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    listener.addSongButton(file);
//                    File[] files = fileChooser.getSelectedFiles();
//                    for (File file: files) {
//                        listener.addSong(file);
//                    }
                }

                break;
            case "Albums":
                listener.albums();
                break;
            case "New Playlist":
                String name = JOptionPane.showInputDialog(
                        this,
                        "Name that you want :)"
                );
                    listener.newPlayList(name);
                break;
            default:

        }


    }

}
