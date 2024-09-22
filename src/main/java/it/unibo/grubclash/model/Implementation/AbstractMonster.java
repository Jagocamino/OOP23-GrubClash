package it.unibo.grubclash.model.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import it.unibo.grubclash.model.Application_Programming_Interface.Monster;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;
import it.unibo.grubclash.view.Implementation.LifeDrawingMobImpl;

/**
 * Class Abstract implementing the Moster methods.
 */
public abstract class AbstractMonster extends EntityImpl implements Monster {

    private Orientation direction;
    private int speed;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;
    protected BufferedImage stand1;
    protected BufferedImage stand2;
    protected BufferedImage death;
    protected static final int lifeValues = 0;
    
    public AbstractMonster(int x, int y, int width, int height, Entities entity, int lifeValues, int speed) {

        super(x, y, width, height, entity);

        setCanMove(true);
        this.setLife(new LifeImpl(this, new LifeDrawingMobImpl()));
        this.getLife().setLifeValue(lifeValues);
        this.direction = Orientation.DOWN;
        this.speed = speed;
        loadImages();
    }

    protected abstract void loadImages();



    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        if (this.isAlive()) {
            animateSprite();
            move();
            if (this.getLife().getLifeValue().get() <= RESET) {
                setWorking(Status.DEAD);
            }
        }
    }

    /**
     * Makes the monster animation
     */
    private void animateSprite() {
        setSpriteCounter(getSpriteCounter() + 1);
        if (getSpriteCounter() > ANIMATION_INTERVAL) {
            if (getSpriteNum() == FIRST_ANIMATION) {
                setSpriteNum(SECOND_ANIMATION);
            } else if (getSpriteNum() == SECOND_ANIMATION) {
                setSpriteNum(FIRST_ANIMATION);
            }
            setSpriteCounter(RESET);
        }
    }

    /**
     * Performs the movements of the various monsters
     */
    protected void move() {
        if (Allowed.CanMoveThere(getX() - speed, getY(), getWidth(), getHeight()) && !canMove()) {
            direction = Orientation.LEFT;
            setX(getX() - speed);
        } else {
            direction = Orientation.DOWN;
            setCanMove(true);
        }
        if (Allowed.CanMoveThere(getX() + speed, getY(), getWidth(), getHeight()) && canMove()) {
            direction = Orientation.RIGHT;
            setX(getX() + speed);
        } else {
            direction = Orientation.LEFT;
            setCanMove(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage image = death;
        if (this.isAlive()) {
            switch (direction) {
                case LEFT:
                    image = (getSpriteNum() == FIRST_ANIMATION) ? left1 : left2;
                    break;
                case RIGHT:
                    image = (getSpriteNum() == FIRST_ANIMATION) ? right1 : right2;
                    break;
                case DOWN:
                    image = (getSpriteNum() == FIRST_ANIMATION) ? stand1 : stand2;
                    break;
                default:
                    break;
            }
            g2d.drawImage(image, getX(), getY(), null);
        } else {
            this.getLife().setLifeValue(RESET);
            g2d.drawImage(image, getX(), getY(), null);
            if (Allowed.getDynamicEntities().contains(Optional.of(this))) {
                Allowed.removeDynamicEntity(this);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Orientation getDirection() {
        return direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection(Orientation direction) {
        this.direction = direction;
    }
}
