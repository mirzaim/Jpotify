package com.jpotify.view.Listeners;

import com.jpotify.view.GUI;
import com.jpotify.view.TopPanel;
import com.jpotify.view.helper.MainPanelState;

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

    public MainPanelState getMainPanelState() {
        return gui.getMainPanel().getMainPanelState();
    }

    public void setMainPanelState(MainPanelState state) {
        gui.getMainPanel().setMainPanelState(state);
    }

    public abstract void closingProgram();
}
