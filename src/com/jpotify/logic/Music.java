package com.jpotify.logic;

import com.jpotify.logic.exceptions.NoTagFoundException;
import com.jpotify.view.helper.ImagePanel;
import mpatric.mp3agic.ID3v2;
import mpatric.mp3agic.InvalidDataException;
import mpatric.mp3agic.Mp3File;
import mpatric.mp3agic.UnsupportedTagException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Music implements Comparable<Music> {

    private final int imageWidth = 200;
    private final int imageHeight = 200;
    private Mp3File mp3File;
    private ID3v2 id3v2Tag;
    private String title;
    private String artist;
    private String album;
    private String year;
    private int genre;
    private BufferedImage albumImage;
    private int size;
    private int lastPlayedTime;

    // for sorting music in playlist
    private int addingToListTime;

    public Music(File file) throws IOException, UnsupportedTagException, InvalidDataException, NoTagFoundException {

        // using mp3agic for getting metadata
        this.mp3File = new Mp3File(file.getAbsolutePath());
        if (mp3File.hasId3v2Tag()) {

            this.id3v2Tag = mp3File.getId3v2Tag();
            //this filed must be get from id3v1 way if there is no id3v1 then we use id3v2
//            this.artist = id3v2Tag.getArtist();
//            this.album = id3v2Tag.getAlbum();
//            this.year = id3v2Tag.getYear();
            this.genre = id3v2Tag.getGenre();
            byte[] imageData = id3v2Tag.getAlbumImage();
            if (imageData != null) {
                InputStream in = new ByteArrayInputStream(imageData);
                this.albumImage = ImageIO.read(in);
            }

            //
            try (FileInputStream fis = new FileInputStream(file)) {

                this.size = (int) file.length();
                fis.skip(this.size - 128);
                byte[] last128 = new byte[128];
                fis.read(last128);

                String id3v1 = new String(last128);
                String tag = id3v1.substring(0, 3);

                if (tag.equals("TAG")) {
                    this.title = id3v1.substring(3, 32);
                    this.artist = id3v1.substring(33, 62);
                    this.album = id3v1.substring(63, 91);
                    this.year = id3v1.substring(93, 97);
                } else {
//                    throw new NoTagFoundException("There is No id3v1v1 Tag");
                    this.artist = id3v2Tag.getArtist();
                    this.album = id3v2Tag.getAlbum();
                    this.year = id3v2Tag.getYear();
                }
            }


            // resizing image (from ? size to 200*200 - MainPanel elements size -)
            BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, this.albumImage.getType());
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(this.albumImage, 0, 0, imageWidth, imageHeight, null);
            g2d.dispose();
            this.albumImage = outputImage;

        } else {
            throw new NoTagFoundException("There is No ID3v2 Tag");
        }
    }

    @Override
    public int compareTo(Music otherMusic) {
        if (this.addingToListTime > otherMusic.addingToListTime)
            return 1;
        else if (this.addingToListTime < otherMusic.addingToListTime)
            return -1;
        else
            return 0;
    }

    public JPanel panelize() {

        JPanel jPanel = new JPanel();

        ImagePanel imagePanel = new ImagePanel(this.albumImage, imageWidth, imageHeight);
        jPanel.add(imagePanel);

        return jPanel;
    }
}
