package com.jpotify.view;

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

    private GUIListener guiListener;

    private GUI(GUIListener guiListener) throws HeadlessException {
        super();
        this.guiListener = guiListener;

        setupGUI();
    }

    public static void initGUI(GUIListener guiListener) {
        if (gui == null)
            gui = new GUI(guiListener);
    }

    public static GUI getGUI() {
        return gui;
    }

    private void setupGUI() {
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);

        add(menuPanel = new MenuPanel(), BorderLayout.LINE_START);
        add(playerPanel = new PlayerPanel(), BorderLayout.PAGE_END);

        JScrollPane jScrollPaneCenter = new JScrollPane(mainPanel = new MainPanel());
        jScrollPaneCenter.setBorder(new EmptyBorder(0,0,0,0));
        add(jScrollPaneCenter,BorderLayout.CENTER);

        setVisible(true);
    }
}
