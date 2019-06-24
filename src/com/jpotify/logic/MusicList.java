package com.jpotify.logic;

import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.ImagePanel;
import com.jpotify.view.helper.MTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class MusicList extends ArrayList<Music> implements DrawableItem, Serializable {
    private String title;
    private transient BufferedImage Image = null;
    private Music FirstMusic;

    public MusicList(String title) {
        this(title, null);
    }

    public MusicList(String title, Music FirstMusic) {
        this.title = title;
        this.FirstMusic = FirstMusic;
    }

    public String getTitle() {
        return title;
    }

    public String[] getSongsName(){
        ArrayList<String> names = new ArrayList<>();
        for(Music music : this)
            names.add(music.getTitle());

        return names.toArray(new String [0]);
    }

    public BufferedImage getImage() {
        if (Image == null)
            this.Image = FirstMusic.getAlbumImage();
        return Image;
    }

    public Music getMusicById(String id) {
        for (Music music : this)
            if (music.getId().equals(id))
                return music;
        return null;
    }

    public void setImage(BufferedImage Image) {
        this.Image = Image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MusicList musicList = (MusicList) o;
        return Objects.equals(title, musicList.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title);
    }

    @Override
    public JPanel draw(int width, int height) {
        JPanel jPanel = new JPanel();

        jPanel.setPreferredSize(new Dimension(width, height));
        jPanel.setLayout(new BorderLayout());

        ImagePanel imagePanel = new ImagePanel(getImage(), width, height - 50);
        jPanel.add(imagePanel, BorderLayout.PAGE_START);

        MTextArea Name = new MTextArea(this.title);
        MTextArea tracksNumber = new MTextArea("" + this.size());

        Name.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
        Name.setForeground(Color.WHITE);

        tracksNumber.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        tracksNumber.setForeground(Color.LIGHT_GRAY);


        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setOpaque(false);

        jPanel.add(bottom, BorderLayout.PAGE_END);
        bottom.add(Name);
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
        return title;
    }
}

