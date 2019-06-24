package com.jpotify.view;

import com.jpotify.view.Listeners.ListenerManager;
import org.jsoup.Jsoup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public final class GUI extends JFrame {
    private final String TITLE = "Jpotify";
    private final int WIDTH = 1280, HEIGHT = 800;

    private MenuPanel menuPanel;
    private PlayerPanel playerPanel;
    private MainPanel mainPanel;
    private TopPanel topPanel;
    private NetworkPanel networkPanel;

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

    public TopPanel getTopPanel() {
        return topPanel;
    }

    public void setMusicData(String title, String singer, BufferedImage image) {
        playerPanel.setDataMusicData(title, singer);
        menuPanel.setArtwork(image);
    }

    private void setupGUI() {

        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                listenerManager.closingProgram();
            }
        });

        JScrollPane menuPanelScroll = new JScrollPane(menuPanel = new MenuPanel(listenerManager));
        menuPanelScroll.setBorder(new EmptyBorder(0,0,0,0));

        add(menuPanelScroll, BorderLayout.LINE_START);
        add(playerPanel = new PlayerPanel(listenerManager), BorderLayout.PAGE_END);
        add(networkPanel = new NetworkPanel(listenerManager), BorderLayout.LINE_END);


        JPanel temp = new JPanel(new BorderLayout());
        add(temp, BorderLayout.CENTER);

        temp.add(topPanel = new TopPanel(), BorderLayout.PAGE_START);

        JScrollPane jScrollPaneCenter = new JScrollPane(mainPanel = new MainPanel(listenerManager));
        jScrollPaneCenter.setBorder(new EmptyBorder(0, 0, 0, 0));
        temp.add(jScrollPaneCenter, BorderLayout.CENTER);

        setVisible(true);
    }

}
