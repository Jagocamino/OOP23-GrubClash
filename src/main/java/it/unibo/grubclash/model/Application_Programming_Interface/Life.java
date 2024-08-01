package it.unibo.grubclash.model.Application_Programming_Interface;

import java.awt.*;
import java.util.Optional;

/**
 * @author Patera Giorgia
 */
public interface Life {

    /**
     * Draw life on top of the entity
     * @param g2d
     */
    void draw(Graphics2D g2d);

     /**
     * Sets the entity's life to a certain value
     * @param life
     */
    void setLifeValue(int life);

    /**
     * @return the value of the life
     */
    Optional<Integer> getLifeValue();

    /**
     * Decreases the player's life by 2
     */
    void damage();

    /**
     * Increases the player's life by 2
     */
    void plusLife();
}
