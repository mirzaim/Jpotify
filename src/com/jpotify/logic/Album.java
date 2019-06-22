package com.jpotify.logic;

import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.ImagePanel;
import com.jpotify.view.helper.MTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Album extends MusicList implements DrawableItem {

    private String albumTitle;
    private BufferedImage albumImage;

    public Album(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumImage(BufferedImage albumImage) {
        this.albumImage = albumImage;
    }

    @Override
    public JPanel draw(int width, int height) {
        JPanel jPanel = new JPanel();

        jPanel.setPreferredSize(new Dimension(width, height));
        jPanel.setLayout(new BorderLayout());

        ImagePanel imagePanel = new ImagePanel(this.albumImage, width, height - 50);
        jPanel.add(imagePanel,BorderLayout.PAGE_START);

        MTextArea AlbumName = new MTextArea(this.albumTitle);
        MTextArea tracksNumber = new MTextArea("" + this.size());

        AlbumName.setFont(new Font(Font.DIALOG,Font.BOLD,13));
        AlbumName.setForeground(Color.WHITE);

        tracksNumber.setFont(new Font(Font.DIALOG,Font.BOLD,12));
        tracksNumber.setForeground(Color.LIGHT_GRAY);


        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setOpaque(false);

        jPanel.add(bottom,BorderLayout.PAGE_END);
        bottom.add(AlbumName);
        bottom.add(tracksNumber);

        // Only for debugging
//        jPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLUE));
//        artistLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.green));
//        titleLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.cyan));
//        imagePanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));

        return jPanel;
    }

    @Override
    public String getId() {
        return albumTitle;
    }
}
