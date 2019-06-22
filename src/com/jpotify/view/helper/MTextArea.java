package com.jpotify.view.helper;

import javax.swing.*;
import java.awt.*;

public class MTextArea extends JTextArea {
    public MTextArea(String text) {
        super(text);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setOpaque(false);
        this.setEditable(false);
    }
}
