package com.jpotify.view;

import com.jpotify.view.Listeners.PlayerPanelListener;
import com.jpotify.view.assets.AssetManager;
import com.jpotify.view.helper.MButton;
import com.jpotify.view.helper.MSlider;
import com.jpotify.view.helper.MTextArea;
import com.jpotify.view.helper.MToggleButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel implements ActionListener, ChangeListener {
    private PlayerPanelListener listener;
    private JSlider slider;
    private JSlider soundVolume;
    private MTextArea musicName;
    private MTextArea singerName;
    private MToggleButton playPauseButton;
    private MButton currentTime;
    private MButton totalTime;

    PlayerPanel(PlayerPanelListener listener) {
        this.listener = listener;
        setup();
    }

    private void setup() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 5, 20));

        setupCenterSection();

        setupLeftSection();

        setupRightSection();

    }

    private void setupCenterSection() {
        JPanel centerBox = new JPanel();
        centerBox.setOpaque(false);
        centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));
        add(centerBox, BorderLayout.CENTER);

        JPanel controllers = new JPanel();
        controllers.setBorder(new EmptyBorder(0, 0, 10, 0));
        controllers.setOpaque(false);
        controllers.setLayout(new BoxLayout(controllers, BoxLayout.X_AXIS));
        centerBox.add(controllers);

        controllers.add(new MButton(AssetManager.getImageIconByName("shuffle.png"), this, "shuffle"));
        controllers.add(new MButton(AssetManager.getImageIconByName("previous.png"), this, "previous"));

        controllers.add(playPauseButton = new MToggleButton(AssetManager.getImageIconByName("play.png"),
                AssetManager.getImageIconByName("pause.png"), this, "play"));

        controllers.add(new MButton(AssetManager.getImageIconByName("next.png"), this, "next"));
        controllers.add(new MButton(AssetManager.getImageIconByName("replay.png"), this, "replay"));


        JPanel sliderBox = new JPanel(new BorderLayout());
        sliderBox.setOpaque(false);
        sliderBox.setBorder(new EmptyBorder(5, 100, 20, 100));
        currentTime = new MButton("00:00");
        sliderBox.add(currentTime, BorderLayout.LINE_START);
        totalTime = new MButton("00:00");
        sliderBox.add(totalTime, BorderLayout.LINE_END);

        slider = new MSlider(1000, this);
        slider.setBorder(new EmptyBorder(0, 100, 0, 100));
        sliderBox.add(slider, BorderLayout.CENTER);
        centerBox.add(sliderBox);
    }

    private void setupLeftSection() {
        JPanel leftBox = new JPanel();
        leftBox.setOpaque(false);
        leftBox.setLayout(new BoxLayout(leftBox, BoxLayout.Y_AXIS));
        leftBox.setBorder(new EmptyBorder(0, 0, 0, 20));
        add(leftBox, BorderLayout.LINE_START);


        musicName = new MTextArea("Music Name", Color.WHITE, true);
        musicName.setFont(new Font("Ubuntu", Font.BOLD, 13));
        leftBox.add(musicName);

        singerName = new MTextArea("Singer", null, true);
        singerName.setFont(new Font("Ubuntu", Font.PLAIN, 11));
        leftBox.add(singerName);
    }

    private void setupRightSection() {
        JPanel rightBox = new JPanel();
        rightBox.setOpaque(false);
        rightBox.setLayout(new BoxLayout(rightBox, BoxLayout.X_AXIS));
        rightBox.setBorder(new EmptyBorder(0, 20, 0, 0));
        add(rightBox, BorderLayout.LINE_END);

//        rightBox.add(new MButton(AssetManager.getImageIconByName("test.png"), this, "test"));
//        rightBox.add(new MButton(AssetManager.getImageIconByName("test.png"), this, "test"));
//        rightBox.add(new MButton(AssetManager.getImageIconByName("test.png"), this, "test"));
//        rightBox.add(new MButton(AssetManager.getImageIconByName("test.png"), this, "test"));
//        rightBox.add(new MButton(AssetManager.getImageIconByName("test.png"), this, "test"));

        soundVolume = new MSlider();
        soundVolume.setBorder(new EmptyBorder(0, 50, 0, 50));
        soundVolume.addChangeListener(this);
        rightBox.add(soundVolume);
    }

    public void setSliderCurrentPosition(int frame, int totalTime, int currentTime) {
        if (!slider.getValueIsAdjusting()) {
            slider.removeChangeListener(this);
            slider.setValue(frame);
            slider.addChangeListener(this);
        }
        this.currentTime.setText(String.format("%d:%02d", currentTime / 60, currentTime % 60));
        this.totalTime.setText(String.format("%d:%02d", totalTime / 60, totalTime % 60));
    }

    public void setDataMusicData(String title, String singer) {
        musicName.setText(title);
        singerName.setText(singer);
    }

    public void setToPauseToggleButton() {
        playPauseButton.changeToSecond();
    }

//    public void updateSliderAFrame() {
//        slider.setValue(slider.getValue() + 1);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (((MButton) e.getSource()).getId()) {
            case "play":
                if (e.getActionCommand().equals("true"))
                    listener.play();
                else
                    listener.pause();
                break;
            case "next":
                listener.next();
                break;
            case "previous":
                listener.previous();
                break;
            case "replay":
                listener.replay();
                break;
            case "shuffle":
                listener.shuffle();
                break;
            default:
        }
    }

    //for jSlider
    @Override
    public void stateChanged(ChangeEvent e) {
        if (slider.equals(e.getSource()) && !slider.getValueIsAdjusting()) {
            listener.sliderChanged(slider.getValue());
        } else if (soundVolume.equals(e.getSource()) && !soundVolume.getValueIsAdjusting()) {
            listener.soundVolumeChanged(soundVolume.getValue());
        }
    }
}
