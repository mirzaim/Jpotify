package com.jpotify.view;

import com.jpotify.logic.Music;
import com.jpotify.view.Listeners.MainPanelListener;
import com.jpotify.view.helper.DrawableItem;
import com.jpotify.view.helper.MButton;
import com.jpotify.view.helper.MainPanelState;
import com.jpotify.view.helper.WrapLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;


public class MainPanel extends JPanel {
    private final int ITEM_WIDTH = 250, ITEM_HEIGHT = 310;
    private int currentDisplayingPanels; // 0 -> songs | 1 -> Albums | 2 -> playlist
    private MainPanelState mainPanelState;

    private MainPanelListener listener;

    public MainPanel(MainPanelListener listener) {
        this.listener = listener;
        setLayout(new WrapLayout(0, 30, 30));
        setBackground(Color.DARK_GRAY);

    }

    public void setCurrentDisplayingPanels(int currentDisplayingPanels) {
        this.currentDisplayingPanels = currentDisplayingPanels;
    }

    public int getCurrentDisplayingPanels() {
        return currentDisplayingPanels;
    }

    public void setMainPanelState(MainPanelState mainPanelState) {
        this.mainPanelState = mainPanelState;
    }

    public MainPanelState getMainPanelState() {
        return mainPanelState;
    }

    public void addPanel(DrawableItem item) {
        JPanel panel = item.draw(ITEM_WIDTH, ITEM_HEIGHT);
        panel.add(getButtonPanel(item), BorderLayout.NORTH);
        panel.setBackground(Color.DARK_GRAY);


//        loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);



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
//                panel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2,2,2,2),
//                        new EmptyBorder(2,2,2,2)));
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
//                panel.setBorder(new EmptyBorder(0,0,0,0));
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        repaint();
        revalidate();
    }

    public void addPanels(DrawableItem[] items) {
        if (items == null || items.length == 0) {
            MButton empty = new MButton("Empty");
            empty.setFont((new Font("Ubuntu", Font.BOLD, 30)));
            addPanel(empty);
            return;
        }

        for (DrawableItem item : items)
            addPanel(item);

    }

    public JPanel getButtonPanel(DrawableItem item) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));


        MButton like = new MButton("Like", true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.buttonLike(item.getId());
            }
        });

        MButton plus = new MButton("Add", true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.buttonAdd(item.getId());
            }
        });

        MButton share = new MButton("Share", true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.buttonShare(item.getId());
            }
        });

        MButton lyric = new MButton("Lyric", true, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.buttonLyric(item.getId());
            }
        });

        buttonPanel.add(plus);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(like);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(share);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(lyric);
        buttonPanel.setBackground(Color.DARK_GRAY);
        changeFont(buttonPanel,new Font("Ubuntu",Font.BOLD,13));

        return buttonPanel;
    }

    public static void changeFont ( Component component, Font font )
    {
        component.setFont ( font );
        if ( component instanceof Container )
        {
            for ( Component child : ( ( Container ) component ).getComponents () )
            {
                changeFont ( child, font );
            }
        }
    }
}
