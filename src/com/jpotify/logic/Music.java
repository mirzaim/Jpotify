package com.jpotify.logic;

import com.jpotify.logic.exceptions.NoTagFoundException;
import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.ImagePanel;
import com.jpotify.view.helper.MTextArea;
import mpatric.mp3agic.ID3v2;
import mpatric.mp3agic.InvalidDataException;
import mpatric.mp3agic.Mp3File;
import mpatric.mp3agic.UnsupportedTagException;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

public class Music implements Comparable<Music>, DrawableItem {

    private final int imageWidth = 250;
    private final int imageHeight = 250;
    private Mp3File mp3File;
    private ID3v2 id3v2Tag;
    private String title;
    private String filePath;
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
        this.filePath = file.getAbsolutePath();
        this.mp3File = new Mp3File(file.getAbsolutePath());

        if (mp3File.hasId3v2Tag()) {
            this.id3v2Tag = mp3File.getId3v2Tag();

            this.genre = id3v2Tag.getGenre();
            byte[] imageData = id3v2Tag.getAlbumImage();

            if (imageData != null) {
                InputStream in = new ByteArrayInputStream(imageData);
                this.albumImage = ImageIO.read(in);
            }

            if (mp3File.hasId3v1Tag()) {
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
                    }
                }
            } else {
                this.title = id3v2Tag.getTitle();
                this.artist = id3v2Tag.getArtist();
                this.album = id3v2Tag.getAlbum();
                this.year = id3v2Tag.getYear();
            }
            if (title != null)
                title = title.trim();


            // resizing image (from ? size to 200*200 - MainPanel elements size -)
//            BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, this.albumImage.getType());
//            Graphics2D g2d = outputImage.createGraphics();
//            g2d.drawImage(this.albumImage, 0, 0, imageWidth, imageHeight, null);
//            g2d.dispose();
//            this.albumImage = outputImage;

            this.albumImage = Thumbnails.of(this.albumImage).size(imageWidth,imageHeight).asBufferedImage();

        } else {
            throw new NoTagFoundException("There is No ID3v2 Tag");
        }

    }

    public String getAlbum() {
        return album;
    }

    public String getFilePath() {
        return filePath;
    }

    public BufferedImage getAlbumImage() {
        return albumImage;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Music music = (Music) o;
        return Objects.equals(filePath, music.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
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

    @Override
    public JPanel draw(int width, int height) {

        JPanel jPanel = new JPanel();

        jPanel.setPreferredSize(new Dimension(width, height));
        jPanel.setLayout(new BorderLayout());

        ImagePanel imagePanel = new ImagePanel(this.albumImage, width, height - 50);
        jPanel.add(imagePanel,BorderLayout.PAGE_START);

        MTextArea titleLabel = new MTextArea(this.title);
        MTextArea artistLabel = new MTextArea(this.artist);

        titleLabel.setFont(new Font(Font.DIALOG,Font.BOLD,13));
        titleLabel.setForeground(Color.WHITE);

        artistLabel.setFont(new Font(Font.DIALOG,Font.BOLD,12));
        artistLabel.setForeground(Color.LIGHT_GRAY);


        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setOpaque(false);

        jPanel.add(bottom,BorderLayout.PAGE_END);
        bottom.add(titleLabel);
        bottom.add(artistLabel);

        // Only for debugging
//        jPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLUE));
//        artistLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.green));
//        titleLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.cyan));
//        imagePanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));



        return jPanel;
    }

    @Override
    public String getId() {
        return filePath;
    }
}
