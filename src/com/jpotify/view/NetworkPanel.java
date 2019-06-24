package com.jpotify.view;

import com.jpotify.view.Listeners.NetworkPanelListener;
import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.MButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public void addActivity(DrawableItem item) {
        activityPanel.add(item.draw(ITEM_WIDTH, ITEM_HEIGHT), 0);

        activityPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                listener.friendPanelClicked(item.getId());
            }
        });

        repaint();
        revalidate();
    }
}
