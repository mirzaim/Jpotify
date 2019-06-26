package com.jpotify.logic;

public interface PlayerListener {

    void updatePosition(int position, int totalTime, int CurrentTime);

    void musicFinished();

}
