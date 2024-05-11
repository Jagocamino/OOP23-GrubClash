package it.unibo.grubclash.view.Application_Programming_Interface;

import java.awt.*;

public interface Life {
    void draw(Graphics2D g2d);

    void setLife(int life);

    int getLife();

    void damage();

    void plusLife();
}
