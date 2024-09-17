package it.unibo.grubclash.model.Application_Programming_Interface;

import java.awt.Graphics2D;

import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;
import it.unibo.grubclash.model.Implementation.LifeImpl;

/**
 * @author Camoni Jago
 */
public interface Entity {

    /**
     * If alive, the entity is updated
     */
    void update();

    /**
     * @return the entity's X coordinate
     */
    int getX();

    /**
     * @return the entity's Y coordinate
     */
    int getY();

    /**
     * Sets the entity's X coordinate
     * @param x
     */
    void setX(int x);

    /**
     * Sets the entity's Y coordinate
     * @param y
     */
    void setY(int y);

    /**
     * 
     * @return the entity's width
     */
    int getWidth();

    /**
     * 
     * @return the entity's height
     */
    int getHeight();

    /**
     * 
     * @return the type of the entity
     */
    Entities getEntity();

    /**
     * Sets the type of the entity
     * @param entity
     */
    void setEntity(Entities entity);

    /**
     * 
     * @return true if the entity is alive
     */
    boolean isAlive();

    /**
     * Draws the entity on the screen
     * @param g2d
     */
    void draw(Graphics2D g2d);

    /**
     * 
     * @return true if the entity can move
     */
    boolean canMove();

    /**
     * Sets if the entity can move
     * @param canMove
     */
    void setCanMove(boolean canMove);

    /**
     * 
     * @return true if the entity is subject to gravity
     */
    boolean isThereGravity();

    /**
     * Sets if the entity is subject to gravity or not
     * @param gravity
     */
    void setGravity(boolean gravity);

    /**
     * 
     * @return the number of the sprite shown on the screen
     */
    int getSpriteNum();

    /**
     * Sets the number of the sprite 
     * @param spriteNum
     */
    void setSpriteNum(int spriteNum);

    /**
     * 
     * @return true if the entity is falling
     */
    boolean isFalling();

    /**
     * Sets if the entity is falling or not
     * @param isFalling
     */
    void setFalling(boolean isFalling);

    /**
     * 
     * @return the gravitational acceleration to which the entity is subjected
     */
    int getGravityAcceleration();

    /**
     * Sets the gravitational acceleration to which the entity is subjected
     * @param gravityAcceleration
     */
    void setGravityAcceleration(int gravityAcceleration);

    /**
     * 
     * @return the gravity counter
     */
    int getGravityCounter();

    /**
     * Sets the gravity counter
     * @param gravityCounter
     */
    void setGravityCounter(int gravityCounter);

    /**
     * 
     * @return the object life of the entity
     */
    LifeImpl getLife();

    /**
     * Sets the object life of the entity
     * @param life
     */
    void setLife(LifeImpl life);

    /**
     * Sets the state of the entity
     * @param working
     */
    void setWorking(Status working);

    /**
     * 
     * @return the state of the entity
     */
    Status getWorking();

    /**
     * 
     * @return the sprite counter
     */
    int getSpriteCounter();

    /**
     * Sets the sprite counter
     * @param spriteCounter
     */
    void setSpriteCounter(int spriteCounter);

}