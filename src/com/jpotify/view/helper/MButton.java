package com.jpotify.view.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MButton extends JButton {
    private Color defaultColor;
    private Icon defaultIcon;

    public MButton(String text, Icon defaultIcon, Color defaultColor, boolean enableDefaultHover) {
        super(text, defaultIcon);
        this.defaultColor = defaultColor;
        this.defaultIcon = defaultIcon;

        firstSetup();

        if (enableDefaultHover)
            setDefaultHoverEffect();
    }

    public MButton(String text, Icon defaultIcon, Color defaultColor) {
        this(text, defaultIcon, defaultColor, false);
    }

    public MButton(String text, Icon icon) {
        this(text, icon, Color.LIGHT_GRAY);
    }

    public MButton(String text, Icon icon, boolean enableDefaultHover) {
        this(text, icon, Color.LIGHT_GRAY, enableDefaultHover);
    }

    public MButton(String text) {
        this(text, null);
    }

    public MButton(String text, boolean enableDefaultHover) {
        this(text, null, Color.LIGHT_GRAY, enableDefaultHover);
    }

    public MButton(Icon defaultIcon) {
        this(null, defaultIcon, false);
    }

    public MButton(Icon defaultIcon, Icon performedIcon) {
        this(null, defaultIcon, false);
        setHoverEffect(null, performedIcon);
    }

    private void firstSetup() {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFont(new Font("Arial", Font.BOLD, 15));
        setForeground(defaultColor);
        setFocusPainted(false);
    }

    public void setDefaultHoverEffect() {
        setTextHoverEffect(Color.WHITE);
    }

    public void setTextHoverEffect(Color performedColor) {
        setHoverEffect(performedColor, defaultIcon);
    }

    public void setHoverEffect(Color PerformedColor, Icon performedIcon) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(PerformedColor);
                setIcon(performedIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(defaultColor);
                setIcon(defaultIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setForeground(defaultColor);
                setIcon(defaultIcon);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setForeground(PerformedColor);
                setIcon(performedIcon);
            }
        });
    }

}
