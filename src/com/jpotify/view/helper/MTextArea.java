package com.jpotify.view.helper;

import javax.swing.*;
import java.awt.*;

public class MTextArea extends JTextArea {
    private boolean brief = false;

    public MTextArea(String text) {
        super();
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setOpaque(false);
        this.setEditable(false);
        setText(text);
    }

    public MTextArea(String text, Color color) {
        this(text);
        if (color == null)
            color = Color.LIGHT_GRAY;
        setForeground(color);
    }

    public MTextArea(String text, Color color, boolean brief) {
        this(text, color);
        this.brief = brief;
    }

    public void setBrief(boolean brief) {
        this.brief = brief;
    }

    @Override
    public void setText(String t) {
        super.setText(brief(t));
    }

    private String brief(String s) {
        if (s == null)
            return "";
        if (s.length() > 30)
            return s.substring(0, 30) + "...";
        return s;
    }
}
