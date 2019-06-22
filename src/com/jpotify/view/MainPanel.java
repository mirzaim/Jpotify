package com.jpotify.view;

import com.jpotify.view.Listeners.MainPanelListener;
import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.WrapLayout;

import javax.swing.*;
import java.awt.*;


class MainPanel extends JPanel {
    private final int ITEM_WIDTH = 200, ITEM_HEIGHT = 250;

    private MainPanelListener listener;

    MainPanel(MainPanelListener listener) {
        this.listener = listener;
        setLayout(new WrapLayout(0, 30, 30));

    }

    void addPanel(DrawableItem item) {
        JPanel panel = item.draw(ITEM_WIDTH, ITEM_HEIGHT);
        panel.setBackground(Color.DARK_GRAY);
        add(panel);
    }

    void addPanels(DrawableItem[] items) {
        for (DrawableItem item : items)
            addPanel(item);
    }
}
