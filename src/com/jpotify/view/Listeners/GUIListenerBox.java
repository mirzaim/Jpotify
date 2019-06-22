package com.jpotify.view.Listeners;

public class GUIListenerBox {
    private MainPanelListener mainPanelListener;
    private MenuPanelListener menuPanelListener;
    private PlayerPanelListener playerPanelListener;
    private NetworkPanelListener networkPanelListener;

    public GUIListenerBox() {

    }

    public GUIListenerBox(ManagerListener managerListener) {
        this(managerListener, managerListener, managerListener, managerListener);
    }

    public GUIListenerBox(MainPanelListener mainPanelListener,
                          MenuPanelListener menuPanelListener,
                          PlayerPanelListener playerPanelListener,
                          NetworkPanelListener networkPanelListener) {
        this.mainPanelListener = mainPanelListener;
        this.menuPanelListener = menuPanelListener;
        this.playerPanelListener = playerPanelListener;
        this.networkPanelListener = networkPanelListener;
    }

    public MainPanelListener getMainPanelListener() {
        return mainPanelListener;
    }

    public MenuPanelListener getMenuPanelListener() {
        return menuPanelListener;
    }

    public PlayerPanelListener getPlayerPanelListener() {
        return playerPanelListener;
    }

    public NetworkPanelListener getNetworkPanelListener() {
        return networkPanelListener;
    }

    public void setMainPanelListener(MainPanelListener mainPanelListener) {
        if (this.mainPanelListener != null)
            this.mainPanelListener = mainPanelListener;
    }

    public void setMenuPanelListener(MenuPanelListener menuPanelListener) {
        if (this.menuPanelListener != null)
            this.menuPanelListener = menuPanelListener;
    }

    public void setPlayerPanelListener(PlayerPanelListener playerPanelListener) {
        if (this.playerPanelListener != null)
            this.playerPanelListener = playerPanelListener;
    }

    public void setNetworkPanelListener(NetworkPanelListener networkPanelListener) {
        if (this.networkPanelListener != null)
            this.networkPanelListener = networkPanelListener;
    }

    public boolean isReady() {
        return this.mainPanelListener != null &&
                this.menuPanelListener != null &&
                this.playerPanelListener != null &&
                this.networkPanelListener != null;

    }
}
