package com.jpotify.view.assets;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AssetManager {

    private static final String PATH = "src/com/jpotify/view/assets/";

    public static ImageIcon getImageIconByName(String filename) {
        return new ImageIcon(PATH + filename);
    }

    public static File getFileByName(String filename) {
        return new File(PATH + filename);
    }

    public static BufferedImage getBufferedImageByName(String filename) {
        try {
            return ImageIO.read(new File(PATH + filename));
        } catch (IOException e) {
            return null;
        }
    }
}
