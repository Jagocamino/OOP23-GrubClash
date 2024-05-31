package it.unibo.grubclash.view.Application_Programming_Interface;

import java.awt.Graphics2D;

import it.unibo.grubclash.model.Application_Programming_Interface.EntityInterface;

public interface ProjectileInterface {

    EntityInterface damage(int dmgRadius);

    void draw(Graphics2D g2d);

}