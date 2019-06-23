package com.jpotify.logic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Player extends Thread {
    private AdvancedPlayer player;
    private Music music;
//    private boolean repeat;
//    private boolean shuffle;

    private int currentFrame = 0;

    private PlayerState currState;
    private PlayerState prevState;

//    public Player(PlayList playList, boolean repeat, boolean shuffle) {
//        this.playList = playList;
//        this.repeat = repeat;
//        this.shuffle = shuffle;
//    }
//
//    public Player(PlayList playList) {
//        this(playList, false, false);
//    }
//
//    public Player(Media media) {
//        this(new PlayList(media));
//    }

//    public void setRepeat(boolean repeat) {
//        this.repeat = repeat;
//    }
//
//    public void setShuffle(boolean shuffle) {
//        this.shuffle = shuffle;
//    }


    public void updateMusic(Music music) {
        procPause();
        currentFrame = 0;
        this.music = music;
        pauseMusic();
    }

//    public void updatePlayList(PlayList playList) {
//        procPause();
//        this.playList = playList;
//        pauseMusic();
//    }

    /**
     * Play music after pause or first run
     * this method also do resume
     */
    public void playMusic() {
        procPause();

        resetPlayer(currentFrame);
        if (this.getState() == State.NEW)
            super.start();

        setCurrState(PlayerState.PLAYING);
    }

    /**
     * stop player completely.
     * After that player can't do any things
     */
    public void stopMusic() {
        procPause();
        player.close();
        interrupt();
        setCurrState(PlayerState.STOPPED);
    }

    public void pauseMusic() {
        setCurrState(PlayerState.PAUSED);
    }

//    public void resumeMedia() {
//        setCurrState(PlayerState.PLAYING);
//    }

    public void changeFramePosition(int frame) {
        procPause();
        this.currentFrame = frame;
        resetPlayer(frame);
        procBack();
    }

    public void nextMusic() {

    }

    public void previousMusic() {

    }

    private void setCurrState(PlayerState state) {
        prevState = currState;
        currState = state;

        if (currState == PlayerState.PLAYING)
            synchronized (this) {
                notifyAll();
            }
    }

    private void procPause() {
        setCurrState(PlayerState.PROCESS);
    }

    private void procBack() {
        setCurrState(prevState);
    }


    private void resetPlayer(int startFrame) {
        if (player != null)
            player.close();

        try {
            player = new AdvancedPlayer(new FileInputStream(music.getFilePath()));
            player.play(startFrame, 0);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        currentFrame = 0;
        try {
            while (currState != PlayerState.STOPPED) {
                if (!player.play(1))
                    setCurrState(PlayerState.FINISHED);
                currentFrame++;

                synchronized (this) {
                    if (currState != PlayerState.PLAYING)
                        wait();
                }
            }
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (InterruptedException ignored) {
        }

        System.out.println(getName() + ": Done!");
    }

    //overriding this method is to that none outSide of this class couldn't start thread.
    //and if we want start Thread use super.start()
    @Override
    public synchronized void start() {
    }
}
