package it.unibo.grubclash.model.Application_Programming_Interface;

import java.awt.Graphics2D;
import java.util.Optional;

import it.unibo.grubclash.controller.Application_Programming_Interface.PlayerInterface;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.ProjectileRoket;
import it.unibo.grubclash.model.Implementation.EnumEntity.orientation;

/**
 * @author Camoni Jago
 */
public interface WeaponInterface {

    /**
     * Set the direction of the weapon
     * @param weaponDir
     */
    void setWeaponDir(orientation weaponDir);

    /**
     * @return The direction of the projectile
     */
    orientation getShootingDir();

    /**
     * Set the direction of the projectile
     * @param shootingDir
     */
    void setShootingDir(orientation shootingDir);

    /**
     * @return The owner
     */
    PlayerInterface getOwner();

    /**
     * Sets the owner
     */
    void setOwner(Player owner);

    /**
     * Sets the ammunitions
     * @param ammo
     */
    void setAmmo(int ammo);

    /**
     * @return The remaining ammunitions
     */
    int getAmmo();

    /**
     * Reduces the total number of munitions by 1
     */
    void reduceAmmo();

    /**
     * Reset the number of ammunitions
     */
    void refillAmmo();

    /**
     * Draws the weapon
     */
    void draw(Graphics2D g2d);

    /**
     * What happens when the owner shoots
     * @return The projectile it shoots
     */
    Optional<ProjectileRoket> shoot();

    /**
     * @return The projectile at its current state
     */
    Optional<ProjectileRoket> getRocket();

}