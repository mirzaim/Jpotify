package com.jpotify.view.Listeners;

import com.jpotify.view.GUI;
import com.jpotify.view.TopPanel;

public abstract class ListenerManager implements MainPanelListener, MenuPanelListener,
        NetworkPanelListener, PlayerPanelListener, TopPanelListener {
    private GUI gui;

    public GUI getGUI() {
        return gui;
    }

    public void setGui(GUI gui) {
        if (this.gui == null)
            this.gui = gui;
    }

    public abstract void closingProgram();
}
