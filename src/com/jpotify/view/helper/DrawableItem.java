package com.jpotify.view.helper;

import javax.swing.*;

public interface DrawableItem {

    JPanel draw(int width, int height);

    String getId();
}
