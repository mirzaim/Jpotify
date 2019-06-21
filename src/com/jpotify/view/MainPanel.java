package com.jpotify.view;

import com.jpotify.view.helper.WrapLayout;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JPanel {
    public MainPanel () {

        setLayout(new WrapLayout(0,30,30));

        for (int i = 0; i < 10; i++) {
            JPanel b = new JPanel();
            b.setPreferredSize(new Dimension(200,250));
            b.setBackground(Color.DARK_GRAY);
            this.add(b);
        }



    }
}
