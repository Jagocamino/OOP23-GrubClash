package it.unibo.grubclash.model.Application_Programming_Interface;

import java.awt.*;
import java.util.Optional;

public interface Life {
    void draw(Graphics2D g2d);

    void setLifeValue(int life);

    Optional<Integer> getLifeValue();

    void damage();

    void plusLife();
}
