package com.jpotify.view.helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MToggleButton extends MButton implements ActionListener {
    private Icon secondIcon;
    private ActionListener listener;
    private boolean on = true;

    public MToggleButton(Icon defaultIcon, Icon secondIcon, ActionListener listener, String id) {
        super(defaultIcon, id);
        this.secondIcon = secondIcon;
        this.listener = listener;
        addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (on)
            setIcon(secondIcon);
        else
            setIcon(getDefaultIcon());
        listener.actionPerformed(new ActionEvent(e.getSource(), e.getID(), Boolean.toString(on)));
        on = !on;
    }

    public void changeToDefault() {
        on = true;
        setIcon(getDefaultIcon());
    }

    public void changeToSecond() {
        on = false;
        setIcon(secondIcon);
    }
}
