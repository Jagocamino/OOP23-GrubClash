package it.unibo.grubclash.model.Application_Programming_Interface;

import java.awt.Graphics2D;

import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;

/**
 * @author Patera Giorgia
 */
public interface Monster {

    /**
     * Updates the monster's position and constantly changes the animation
     */
    void update();

    /**
     * Draw the various animations of the monster
     * @param g2d
     */
    void draw(Graphics2D g2d);

    /**
     * @return The direction of the monster
     */
    Orientation getDirection();

    /**
     * Sets the direction of the monster
     * @param direction
     */
    void setDirection(Orientation direction);

}