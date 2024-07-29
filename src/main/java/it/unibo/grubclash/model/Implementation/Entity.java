package it.unibo.grubclash.model.Implementation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import it.unibo.grubclash.controller.Application_Programming_Interface.GrubPanelInter;
import it.unibo.grubclash.model.Application_Programming_Interface.EntityInterface;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;
import it.unibo.grubclash.view.Application_Programming_Interface.ImageScalarInterface;
import it.unibo.grubclash.view.Implementation.ImageScalar;

public class Entity implements EntityInterface { //ogni entity, comprese le strutture, ha questa classe

    GrubPanelInter grubPanel;  

    public int x;  
    public int y;
    public int width, height;
    protected Entities entity;
    protected int spriteNum = 1;
    public boolean canMove = true;
    public boolean gravity = true;
    public int gravityAcceleration = 2;
    public int gravityCounter = 0;
    public boolean isFalling = true;

    public Status working = Status.ALIVE;

    public LifeImpl life; 

    //COUNTERS
    protected static int spriteCounter = 0;
    public int jump1Counter = 0;
    public int jump2Counter = 0;

    public Entity(int x, int y, int width, int height, Entities entity) { 
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entity = entity;
    } 

    @Override
    public void update(){}

    @Override
    public int getX () {
        return x;
    }

    @Override
    public int getY () {
        return y;
    }

    @Override
    public void setX (int x) {
        this.x = x;
    }

    @Override
    public void setY (int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Entities getEntity () {
        return this.entity;
    }
    
    @Override
    public void setEntity (Entities entity) {
        this.entity = entity;
    }

    @Override
    public boolean isAlive() {
        if (working == Status.ALIVE) {
            return true;
        }
        return false;
    }

    public static BufferedImage setup(String imagePath, int width, int height) { 
        ImageScalarInterface uTool = new ImageScalar();
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

    @Override
    public void draw(Graphics2D g2d) {
        
    }

}
