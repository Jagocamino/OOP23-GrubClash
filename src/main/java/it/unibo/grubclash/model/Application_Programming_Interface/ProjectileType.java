package it.unibo.grubclash.model.Application_Programming_Interface;

import it.unibo.grubclash.controller.Application_Programming_Interface.Player;
/**
 * @author Camoni Jago
 */
public interface ProjectileType {

    /**
     * 
     * @return The owner of the bullet
     */
    Player getOwner();

    /**
     * updates the position of the bullet
     */
    void update();

}