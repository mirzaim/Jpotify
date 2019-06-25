package com.jpotify.view.helper;

import com.jpotify.view.helper.MButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniMenu extends JPanel implements ActionListener {
    private ActionListener listener;
    private String title;

    public MiniMenu(String title) {
        this.title = title;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(new EmptyBorder(5, 20, 5, 20));

        MButton titleText = new MButton(title);
        titleText.setFont(new Font("Arial", Font.PLAIN, 12));
        add(titleText);
    }

    public MiniMenu(String title, ActionListener listener) {
        this(title);
        this.listener = listener;
    }

    public void addButton(JButton button) {
        button.setBorder(new EmptyBorder(5, 5, 0, 5));
        button.addActionListener(this);
        add(button);
    }

//    public void addButton(JButton button) {
//        button.setBorder(new EmptyBorder(5, 5, 0, 5));
//        button.addActionListener(this);
//        add(button);
//    }

    public void removeButton(String id) {
        Component[] components = getComponents();
        for (Component component : components)
            if (component instanceof MButton) {
                MButton button = (MButton) component;
                if (button.getId().equals(id))
                    remove(component);
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null)
            listener.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_FIRST, title));
    }
}
