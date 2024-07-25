package it.unibo.grubclash.view.Application_Programming_Interface;

import it.unibo.grubclash.model.Implementation.Entity;

import java.awt.Graphics2D;

public interface LifeDrawing {

    void drawLife(Graphics2D g2d, Entity entity, int life);
}
