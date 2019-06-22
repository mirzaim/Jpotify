package com.jpotify.view;

import com.jpotify.view.Listeners.ListenerManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class GUI extends JFrame {
    private final String TITLE = "Jpotify";
    private final int WIDTH = 1280, HEIGHT = 800;

    private MenuPanel menuPanel;
    private PlayerPanel playerPanel;
    private MainPanel mainPanel;

    private static GUI gui;

    private ListenerManager listenerManager;

    private GUI(ListenerManager guiListenerBox) throws HeadlessException {
        super();
        this.listenerManager = guiListenerBox;

        setupGUI();
    }

    public static void initGUI(ListenerManager listenerManager) {
        if (gui == null) {
            gui = new GUI(listenerManager);
            listenerManager.setGui(gui);
        }
    }

    public static GUI getGUI() {
        return gui;
    }

//    public void addPanel(DrawableItem item) {
//        mainPanel.addPanel(item);
//    }
//
//    public void addPanels(DrawableItem[] items) {
//        mainPanel.addPanels(items);
//    }
//
//    public void setMediaMaxFrame(int frame) {
//        playerPanel.setMediaMaxFrame(frame);
//    }
//
//    public void setSliderCurrentPosition(int frame) {
//        playerPanel.setSliderCurrentPosition(frame);
//    }
//
//    public void updateSliderAFrame() {
//        playerPanel.updateSliderAFrame();
//    }


    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    private void setupGUI() {

        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);

        add(menuPanel = new MenuPanel(listenerManager), BorderLayout.LINE_START);
        add(playerPanel = new PlayerPanel(listenerManager), BorderLayout.PAGE_END);

        JScrollPane jScrollPaneCenter = new JScrollPane(mainPanel = new MainPanel(listenerManager));
        jScrollPaneCenter.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(jScrollPaneCenter, BorderLayout.CENTER);

        setVisible(true);
    }

}
