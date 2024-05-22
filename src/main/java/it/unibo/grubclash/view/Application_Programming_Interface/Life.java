package it.unibo.grubclash.view.Application_Programming_Interface;

import java.awt.*;
import java.util.Optional;

public interface Life {
    void draw(Graphics2D g2d);

    void setLife(int life);

    Optional<Integer> getLife();

    void damage();

    void plusLife();
}
