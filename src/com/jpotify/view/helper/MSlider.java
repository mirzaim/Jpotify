package com.jpotify.view.helper;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;

public class MSlider extends JSlider {
    public MSlider() {

        setOpaque(false);

        setUI(new MetalSliderUI() {
            @Override
            protected void scrollDueToClickInTrack(int dir) {
                int value = this.valueForXPosition(slider.getMousePosition().x);
                slider.setValue(value);
            }
        });
    }

    public MSlider(int max) {
        this();
        setMaximum(max);
    }

    public MSlider(int max, ChangeListener listener) {
        this(max);
        addChangeListener(listener);
    }
}
