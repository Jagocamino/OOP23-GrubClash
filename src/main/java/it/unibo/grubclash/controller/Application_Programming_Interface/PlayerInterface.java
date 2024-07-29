package it.unibo.grubclash.controller.Application_Programming_Interface;

import java.awt.Graphics2D;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.Weapon;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;

/**
 * @author Remschi Christian
 */
public interface PlayerInterface {

    /**
     * If alive, it allows the player to move and act
     */
    void update();

    /**
     * @return the player's weapon
     */
    Optional<Weapon> getWeapon();

    /**
     * Sets the player's weapon
     * @param weapon the object to set
     */
    void setWeapon(Optional<Weapon> weapon);

    /**
     * @return the player's direction
     */
    Orientation getDirection();

    /**
     * Sets the player's direction
     * @param direction the direction to set
     */
    void setDirection(Orientation direction);

    /**
     * Draw player's movement animations
     * @param g2d the graphics object
     */
    void draw(Graphics2D g2d);

    /**
     * @return the player's Id
     */
    int getId();

    /**
     * @return the player's KeyHandler
     */
    KeyHandler getKeyHandler();

}