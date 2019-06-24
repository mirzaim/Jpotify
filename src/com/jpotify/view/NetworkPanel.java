package com.jpotify.view;

import com.jpotify.logic.Music;
import com.jpotify.view.Listeners.NetworkPanelListener;
import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.MButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NetworkPanel extends JPanel {

    private final static int ITEM_WIDTH = 200, ITEM_HEIGHT = 100;

    private JPanel activityPanel;

    private NetworkPanelListener listener;

    public NetworkPanel(NetworkPanelListener listener) {
        this.listener = listener;

        setup();
    }

    private void setup() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(new MButton("Friend Activity", null, Color.WHITE), BorderLayout.PAGE_START);

        activityPanel = new JPanel();
        activityPanel.setOpaque(false);
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        add(activityPanel, BorderLayout.CENTER);
    }

    public void addActivity(String username, Music music) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        MButton usernameButton = new MButton(username, null, Color.WHITE);
        usernameButton.addActionListener(e -> listener.friendPanelClicked(username));

        panel.add(usernameButton);
        panel.add(new MButton(music.getTitle()));
        panel.setOpaque(false);

        activityPanel.add(panel, 0);

        repaint();
        revalidate();
    }
}
