package com.jpotify.logic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.PlayerApplet;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class Player extends Thread {
    private AdvancedPlayer player;
    private Music music;
    private PlayerListener listener;
//    private boolean repeat;
//    private boolean shuffle;

    private int currentFrame = 0;
    private long totalFrame;

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


    public Player() {
    }

    public Player(PlayerListener listener) {
        this.listener = listener;
    }

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

    private void updatePosition() {
        if (listener != null) {
            int position = (int) ((double) currentFrame / totalFrame * 1000);
            listener.updatePosition(position);
            System.out.println(currentFrame + " " + totalFrame + " " + position);
        }
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

    //not efficient but good.
    private void setTotalFrame() throws IOException, JavaLayerException {
        totalFrame = 0;
        AdvancedPlayer player = new AdvancedPlayer(new FileInputStream(music.getFilePath()));
        while (player.skipFrame())
            totalFrame++;
    }

    private void resetPlayer(int startFrame) {
        if (player != null)
            player.close();

        try {
            player = new AdvancedPlayer(new FileInputStream(music.getFilePath()));
            player.play(startFrame, 0);
            setTotalFrame();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                updatePosition();

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
