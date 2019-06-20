package com.jpotify.view.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JLabel {
    private BufferedImage image;

    public ImagePanel(BufferedImage image, int width, int height) {
        this.image = image;

        setIcon(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
    }

    public ImagePanel(BufferedImage image) {
        this(image, -1, -1);
    }


    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
