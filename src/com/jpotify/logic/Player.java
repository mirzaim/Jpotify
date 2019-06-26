package com.jpotify.logic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.*;
import java.io.*;

/**
 * This class is for playing music and it uses jLayer library.
 *
 * @author Morteza Mirzai
 * @see PlayerListener
 */

public class Player extends Thread {
    private AdvancedPlayer player;
    private Music music;
    private PlayerListener listener;
    private static final int POSITION_CONS = 1000;
    private double FRAME_RATE = 0.0260;

    private int currentFrame = 0;
    private long totalFrame;


    private PlayerState currState;
    private PlayerState prevState;


    public Player() {
    }

    /**
     * @param listener PlayListListener
     */
    public Player(PlayerListener listener) {
        this.listener = listener;
    }

    /**
     * This method is used when we want to set music for player
     * after this we can play music
     *
     * @param music music instance
     */

    public void updateMusic(Music music) {
        procPause();
        currentFrame = 0;
        this.music = music;
        pauseMusic();
    }

    /**
     * @return returns music that set by updateMusic().
     */
    public Music getMusic() {
        return music;
    }

    /**
     * for changing music sound volume
     *
     * @param volume an integer between 0 to 100
     */
    public void updateSoundVolume(int volume) {
        double lineVolume = ((double) volume) / 100;

        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineInfos = mixer.getTargetLineInfo(); // target, not source
            for (Line.Info lineInfo : lineInfos) {
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }
                    if (line.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl volCtrl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                        volCtrl.setValue((float) lineVolume);
                    }

                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException iaEx) {
                    System.out.println("    " + iaEx);
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }

    }

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

    /**
     * pause music
     */
    public void pauseMusic() {
        setCurrState(PlayerState.PAUSED);
    }

    /**
     * go to next music in playList
     */
    public void nextMusic() {

    }

    /**
     * go to previous music in playList
     */
    public void previousMusic() {

    }

    /**
     * change position relatively with an integer between 1 to 1000
     *
     * @param position an integer between 1 to 1000
     */
    public void changePositionRelative(int position) {
        if (this.isAlive()) {
            int frame = (int) ((double) position / POSITION_CONS * totalFrame);
            changeFramePosition(frame);
        }
    }

    // for player
    private void changeFramePosition(int frame) {
        procPause();
        this.currentFrame = frame;
        resetPlayer(frame);
        procBack();
    }

    private void updatePosition() {
        if (listener != null) {
            int position = (int) ((double) currentFrame / totalFrame * POSITION_CONS);
            listener.updatePosition(position, (int) (totalFrame * FRAME_RATE),
                    (int) (currentFrame * FRAME_RATE));
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
                if (!player.play(1)) {
                    setCurrState(PlayerState.FINISHED);
                    listener.musicFinished();
                }

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

    /**
     * it doesn't do any thing!
     * don't try this method to run thread ;)
     */
    @Override
    public synchronized void start() {
    }

}
