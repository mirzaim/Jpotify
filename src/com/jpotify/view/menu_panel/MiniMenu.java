package com.jpotify.view.menu_panel;

import com.jpotify.view.helper.MButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MiniMenu extends JPanel {

    public MiniMenu(String title) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));

        MButton titleText = new MButton(title);
        titleText.setFont(new Font("Arial", Font.PLAIN, 12));
        add(titleText);
    }

    public void addButton(JButton button) {
        button.setBorder(new EmptyBorder(5, 5, 0, 5));
        add(button);
    }
}
