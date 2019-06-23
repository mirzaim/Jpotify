package com.jpotify.view;

import com.jpotify.view.Listeners.MainPanelListener;
import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.WrapLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainPanel extends JPanel {
    private final int ITEM_WIDTH = 250, ITEM_HEIGHT = 300;
    private int currentDisplayingPanels; // 0 -> songs | 1 -> Albums | 2 -> playlist

    private MainPanelListener listener;

    public MainPanel(MainPanelListener listener) {
        this.listener = listener;
        setLayout(new WrapLayout(0, 30, 30));

    }

    public void setCurrentDisplayingPanels(int currentDisplayingPanels) {
        this.currentDisplayingPanels = currentDisplayingPanels;
    }

    public int getCurrentDisplayingPanels() {
        return currentDisplayingPanels;
    }

    public void addPanel(DrawableItem item) {
        JPanel panel = item.draw(ITEM_WIDTH, ITEM_HEIGHT);
        panel.setBackground(Color.DARK_GRAY);
        add(panel);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                listener.panelClicked(item.getId());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2,2,2,2),
                        new EmptyBorder(2,2,2,2)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panel.setBorder(new EmptyBorder(0,0,0,0));
            }
        });

        repaint();
        revalidate();
    }

    public void addPanels(DrawableItem[] items) {
        for (DrawableItem item : items)
            addPanel(item);
    }
}
