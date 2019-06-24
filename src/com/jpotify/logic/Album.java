package com.jpotify.logic;

import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.ImagePanel;
import com.jpotify.view.helper.MTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Album extends ArrayList<Music> implements DrawableItem, Serializable {

    private String albumTitle;
    private transient BufferedImage albumImage = null;
    private Music albumFirstMusic;

    private long lastPlayedTime;

    public Album(String albumTitle, Music albumFirstMusic) {
        this.albumTitle = albumTitle;
        this.albumFirstMusic = albumFirstMusic;
    }

    public void setAlbumImage(BufferedImage albumImage) {
        this.albumImage = albumImage;
    }

    public void updateLastPlayedTime(long time) {
        lastPlayedTime = time;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }


    public BufferedImage getAlbumImage() {
        if (albumImage == null)
            this.albumImage = albumFirstMusic.getAlbumImage();
        return albumImage;
    }

    public long getLastPlayedTime() {
        return lastPlayedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Album album = (Album) o;
        return Objects.equals(albumTitle, album.albumTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), albumTitle);
    }

    @Override
    public JPanel draw(int width, int height) {
        JPanel jPanel = new JPanel();

        jPanel.setPreferredSize(new Dimension(width, height));
        jPanel.setLayout(new BorderLayout());

        ImagePanel imagePanel = new ImagePanel(getAlbumImage(), width, height - 50);
        jPanel.add(imagePanel, BorderLayout.PAGE_START);

        MTextArea AlbumName = new MTextArea(this.albumTitle);
        MTextArea tracksNumber = new MTextArea("" + this.size());

        AlbumName.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
        AlbumName.setForeground(Color.WHITE);

        tracksNumber.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        tracksNumber.setForeground(Color.LIGHT_GRAY);


        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setOpaque(false);

        jPanel.add(bottom, BorderLayout.PAGE_END);
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
