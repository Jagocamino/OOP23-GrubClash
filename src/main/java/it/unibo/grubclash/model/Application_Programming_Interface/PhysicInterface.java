package it.unibo.grubclash.model.Application_Programming_Interface;

import it.unibo.grubclash.model.Implementation.Entity;

/**
 * @author Remschi Christian
 */
public interface PhysicInterface {

    /**
     * Checks if there is terrain under the entity, if so the entity stops, otherwise it will fall
     * @param entity
     */
    void checkTerrain(Entity entity);

    /**
     * Checks the life of the entity, if it is equal or below 0, it will die
     * @param entity
     */
    void checkDeath(Entity entity);

}