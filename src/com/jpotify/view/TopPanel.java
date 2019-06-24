package com.jpotify.view;

import com.jpotify.view.Listeners.TopPanelListener;
import com.jpotify.view.helper.MButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TopPanel extends JPanel {
    private MButton username;
    private TopPanelListener listener;

    public TopPanel(TopPanelListener listener) {
        this.listener = listener;
        setup();
    }

    private void setup() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        setBorder(new EmptyBorder(15, 20, 15, 20));

        add(username = new MButton(listener.getUsername(), true), BorderLayout.LINE_END);
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }
}
