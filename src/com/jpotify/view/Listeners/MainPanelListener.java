package com.jpotify.view.Listeners;

public interface MainPanelListener {

    void panelClicked(String id);

    void buttonAdd(String id);  // button + add for preventing mismatch with add + button

    void buttonLike(String id);

    void buttonShare(String id);

}
