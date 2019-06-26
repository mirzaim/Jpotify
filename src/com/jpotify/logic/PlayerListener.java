package com.jpotify.logic;

/**
 * this Listener is for alert you about player events
 *
 * @author Morteza Mirzai
 * @see Player
 */

public interface PlayerListener {

    /**
     * this method runs when music position changed.
     *
     * @param position    new position an integer between 0 to 1000
     * @param totalTime   total time of music in seconds
     * @param CurrentTime current time of music in seconds
     */
    void updatePosition(int position, int totalTime, int CurrentTime);

    /**
     * this method runs when playing music finished
     */
    void musicFinished();

}
