package it.unibo.grubclash.model.Application_Programming_Interface;

import java.awt.Graphics2D;

import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;

public interface EntityInterface {

    void update();

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    int getWidth();

    int getHeight();

    Entities getEntity();

    void setEntity(Entities entity);

    boolean isAlive();

    void draw(Graphics2D g2d);

}