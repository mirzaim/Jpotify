package com.jpotify.view;

import com.jpotify.view.Listeners.GUIListenerBox;
import com.jpotify.view.Listeners.ManagerListener;
import com.jpotify.view.helper.DrawableItem;

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

    private GUIListenerBox guiListenerBox;

    private GUI(GUIListenerBox guiListenerBox) throws HeadlessException {
        super();
        this.guiListenerBox = guiListenerBox;

        setupGUI();
    }

    public static void initGUI(GUIListenerBox guiListenerBox) {
        if (gui == null && guiListenerBox.isReady())
            gui = new GUI(guiListenerBox);
    }

    public static void initGUI(ManagerListener managerListener) {
        if (gui == null) {
            gui = new GUI(new GUIListenerBox(managerListener));
            managerListener.setGui(gui);
        }
    }

    public static GUI getGUI() {
        return gui;
    }

    public void addPanel(DrawableItem item) {
        mainPanel.addPanel(item);
    }

    public void addPanels(DrawableItem[] items) {
        mainPanel.addPanels(items);
    }

    public void setMediaMaxFrame(int frame) {
        playerPanel.setMediaMaxFrame(frame);
    }

    public void setSliderCurrentPosition(int frame) {
        playerPanel.setSliderCurrentPosition(frame);
    }

    public void updateSliderAFrame() {
        playerPanel.updateSliderAFrame();
    }

    private void setupGUI() {
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);

        add(menuPanel = new MenuPanel(guiListenerBox.getMenuPanelListener()), BorderLayout.LINE_START);

        add(playerPanel = new PlayerPanel(guiListenerBox.getPlayerPanelListener()), BorderLayout.PAGE_END);

        JScrollPane jScrollPaneCenter = new JScrollPane(mainPanel = new MainPanel(guiListenerBox.getMainPanelListener()));
        jScrollPaneCenter.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(jScrollPaneCenter, BorderLayout.CENTER);

        setVisible(true);
    }

}
