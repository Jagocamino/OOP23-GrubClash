package it.unibo.grubclash.view.Application_Programming_Interface;

import java.util.Optional;

import it.unibo.grubclash.model.Implementation.ProjectileRoket;

/**
 * @author Camoni Jago
 */
public interface Weapon {

    /**
     * Shoots the bullet/granade
     * @return The projectile shot
     */
    Optional<ProjectileRoket> shoot();

    /**
     * Resets the ammunitions of the clip to its original size
     */
    void refillAmmo();

}