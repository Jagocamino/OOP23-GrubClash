package it.unibo.grubclash.model.Implementation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import it.unibo.grubclash.model.Application_Programming_Interface.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;
import it.unibo.grubclash.view.Application_Programming_Interface.ImageScalar;
import it.unibo.grubclash.view.Implementation.ImageScalarImpl;

/**
 * Class implementing the Entity methods.
 */
public class EntityImpl implements Entity { 

    public static final int RESET = 0;
    public static final int FIRST_ANIMATION = 1;
    public static final int SECOND_ANIMATION = 2;
    public static final int ADDITIONAL_PIXELS = 13;
    public static final int INITIAL_GRAVITY_ACCELERATION = 2;
    public static final int ANIMATION_INTERVAL = 12;

    private int x;  
    private int y;
    private int width;
    private int height;
    private Entities entity;
    private boolean canMove = true;
    private boolean gravity = true;
    private int spriteNum = FIRST_ANIMATION;
    private int gravityAcceleration = INITIAL_GRAVITY_ACCELERATION;
    private int gravityCounter = RESET;
    private boolean isFalling = true;
    private Status working = Status.ALIVE;
    private LifeImpl life; 
    private int spriteCounter = RESET;

    public EntityImpl(int x, int y, int width, int height, Entities entity) { 
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entity = entity;
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(){
        
    }   

    /**
     * {@inheritDoc}
     */
    @Override
    public int getX () {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getY () {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setX (int x) {
        this.x = x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setY (int y) {
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entities getEntity () {
        return this.entity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntity (Entities entity) {
        this.entity = entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        if (working == Status.ALIVE) {
            return true;
        }
        return false;
    }

    /**
     * Sets up images scaling them based on the parameters specified
     * @param imagePath 
     * @param width 
     * @param height
     * @return the image scaled 
     */
    public static BufferedImage setup(String imagePath, int width, int height) { 
        ImageScalar uTool = new ImageScalarImpl();
        BufferedImage image = null;
    
        try {
            File file = new File(imagePath);
            if (!file.exists()) {
                throw new IOException("File does not exist: " + file.getAbsolutePath());
            }
    
            image = ImageIO.read(file);
            if (image == null) {
                throw new IOException("Failed to read image: " + file.getAbsolutePath());
            }
    
            // Verifica il tipo di immagine
            int imageType = image.getType();
            if (imageType == BufferedImage.TYPE_CUSTOM) {
                throw new IOException("Unsupported image type: " + imageType);
            }
    
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2d) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove() {
        return canMove;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isThereGravity() {
        return gravity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSpriteNum() {
        return spriteNum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFalling() {
        return isFalling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGravityAcceleration() {
        return gravityAcceleration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGravityAcceleration(int gravityAcceleration) {
        this.gravityAcceleration = gravityAcceleration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGravityCounter() {
        return gravityCounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGravityCounter(int gravityCounter) {
        this.gravityCounter = gravityCounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public LifeImpl getLife(){
        return life;
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public void setLife(LifeImpl life){
        this.life = life;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWorking(Status working) {
        this.working = working;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status getWorking() {
        return working;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSpriteCounter() {
        return spriteCounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }
}
