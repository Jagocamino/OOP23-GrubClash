package it.unibo.grubclash.view.Application_Programming_Interface;

import it.unibo.grubclash.model.Implementation.Entity;

import java.awt.Graphics2D;

/**
 * @author Patera Giorgia
 */
public interface LifeDrawing {

    /**
     * Given the graphics the entity and life draws it on top of the entity
     */
    void drawLife(Graphics2D g2d, Entity entity, int life);
}
