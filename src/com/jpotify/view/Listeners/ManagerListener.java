package com.jpotify.view.Listeners;

import com.jpotify.view.GUI;

public abstract class ManagerListener implements MainPanelListener, MenuPanelListener, NetworkPanelListener, PlayerPanelListener {
    private GUI gui;

    public GUI getGUI() {
        return gui;
    }

    public void setGui(GUI gui) {
        if (this.gui == null)
            this.gui = gui;
    }
}
