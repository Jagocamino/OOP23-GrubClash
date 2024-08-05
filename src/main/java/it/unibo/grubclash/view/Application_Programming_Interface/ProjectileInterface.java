package it.unibo.grubclash.view.Application_Programming_Interface;

import java.awt.Graphics2D;

import it.unibo.grubclash.model.Application_Programming_Interface.EntityInterface;
/**
 * @author Camoni Jago
 */
public interface ProjectileInterface {
    
    /**
     * Applies the damage of the explosion when a collision is detected
     * @param dmgRadius
     */
    EntityInterface damage(int dmgRadius);

    /**
     * Draws the damage
     */	
    void draw(Graphics2D g2d);

}