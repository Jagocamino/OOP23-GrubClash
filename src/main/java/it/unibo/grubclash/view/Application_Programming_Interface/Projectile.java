package it.unibo.grubclash.view.Application_Programming_Interface;

import java.awt.Graphics2D;

import it.unibo.grubclash.model.Application_Programming_Interface.Entity;
/**
 * @author Camoni Jago
 */
public interface Projectile {
    
    /**
     * Applies the damage of the explosion when a collision is detected
     * @param dmgRadius
     */
    Entity damage(int dmgRadius);

    /**
     * Draws the damage
     */	
    void draw(Graphics2D g2d);

}