package com.jpotify.view;

import com.jpotify.view.assets.AssetManager;
import com.jpotify.view.helper.ImagePanel;
import com.jpotify.view.helper.MButton;
import com.jpotify.view.menu_panel.MiniMenu;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class MenuPanel extends JPanel {
    private final int WIDTH = 200;

    MenuPanel() {
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
        mainMenu.addButton(new MButton("Home", AssetManager.getImageIconByName("home.png"), true));
        mainMenu.addButton(new MButton("Browse", AssetManager.getImageIconByName("folder.png"), true));
        mainMenu.addButton(new MButton("Radio", AssetManager.getImageIconByName("radio.png"), true));
        top.add(mainMenu);

        MiniMenu library = new MiniMenu("YOUR LIBRARY");
        library.addButton(new MButton("Songs", true));

        MButton addSongButton = new MButton("Add Song", AssetManager.getImageIconByName("add.png"), true);
        addSongButton.addActionListener(new playSong());
        library.addButton(addSongButton);
        library.addButton(new MButton("Albums", true));
        top.add(library);

        MiniMenu playList = new MiniMenu("PLAYLISTS");
        playList.addButton(new MButton("Test1", true));
        playList.addButton(new MButton("Test2", true));
        playList.addButton(new MButton("Test3", true));
        top.add(playList);

        JPanel bottom = new JPanel();
        bottom.setBackground(this.getBackground());
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.PAGE_AXIS));
        add(bottom, BorderLayout.PAGE_END);

        MiniMenu newPlayList = new MiniMenu(null);
        newPlayList.addButton(new MButton("New Playlist", AssetManager.getImageIconByName("add.png"), true));
        bottom.add(newPlayList);

        ImagePanel imagePanel = new ImagePanel(AssetManager.getBufferedImageByName("abc.jpg"), WIDTH, -1);
        bottom.add(imagePanel);
    }

    class playSong implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if(result == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
                    javazoom.jl.player.Player player = new javazoom.jl.player.Player(bis);
                    System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                    player.play();
                }
                catch (javazoom.jl.decoder.JavaLayerException e2){
                    System.out.println("JavaLayerException");
                } catch (IOException e1){
                    System.out.println("Cant open file");
                }
            }

        }
    }

}
