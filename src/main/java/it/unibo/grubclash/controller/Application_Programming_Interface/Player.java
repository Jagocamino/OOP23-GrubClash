package it.unibo.grubclash.controller.Application_Programming_Interface;

import java.awt.Graphics2D;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.WeaponImpl;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;

/**
 * @author Remschi Christian
 */
public interface Player {

    /**
     * If alive, it allows the player to move and act
     */
    void update();

    /**
     * @return the player's weapon
     */
    Optional<WeaponImpl> getWeapon();

    /**
     * Sets the player's weapon
     * @param weapon the object to set
     */
    void setWeapon(Optional<WeaponImpl> weapon);

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

    /**
     * Sets the alreadyShot variable to true if the player shot during his turn
     * @param alreadyShot 
     */
    void setAlreadyShot(boolean alreadyShot);

    /**
     * Sets the alreadydug variable to true if the player dug successfully during his turn
     * @param alreadyDug 
     */
    void setAlreadyDug(boolean alreadyDug);

    /**
     * Sets the shovelAnimation variable to true if the player dug during his turn and starts the animation
     * @param shovelAnimation 
     */
    void setShovelAnimation(boolean shovelAnimation);

    /**
     * Sets the shovelCounter variable to manage the duration of the dug animation
     * @param shovelCounter 
     */
    void setShovelCounter(int shovelCounter);

    /**
     * Sets the cooldownDig variable to manage the count down between one dig and the next one
     * @param cooldownDig 
     */
    void setCooldownDig(boolean cooldownDig);

    /**
     * Sets the counter for the first part of the player's jump
     * @param jump1Counter
     */
    void setJump1Counter(int jump1Counter);

    /**
     * Sets the counter for the second part of the player's jump
     * @param jump2Counter
     */
    void setJump2Counter(int jump2Counter);
}