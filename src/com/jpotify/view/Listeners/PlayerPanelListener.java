package com.jpotify.view.Listeners;

public interface PlayerPanelListener {

    void play();

    void pause();

    void next();

    void previous();

    void shuffle();

    void replay();

    void sliderChanged(int newPosition);

}
