package it.unibo.grubclash.controller.Implementation;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.grubclash.view.Implementation.ImageScalar;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
import it.unibo.grubclash.model.Implementation.GrubPanel;

import java.awt.Graphics;

public class Entity { //ogni entity, comprese le strutture, ha questa classe

    GrubPanel grubPanel;

    protected float x;  //qui metterei int e non float tanto viene sempre messo (int) e come valore float non viene mai effettivamente usato
    protected float y;
    protected double width, height;
    protected Rectangle hitbox;
    protected entities entity;
    protected int spriteNum = 1;
    public boolean canMove = true;
    public boolean gravity = true;

    //COUNTERS
    protected static int spriteCounter = 0;
    public int jump1Counter = 0;
    public int jump2Counter = 0;

    public Entity(float x, float y, double width, double height, entities entity) { //passed
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entity = entity;
        /* initHitbox(); */
    }

    public Entity (GrubPanel grubPanel) {
        this.grubPanel = grubPanel;
    }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }

    public void setX (float x) {
        this.x = x;
    }

    public void setY (float y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    protected void drawHitbox(Graphics g) {
        //just for Debugging purpose
        g.setColor(Color.PINK);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    /* private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, (int) width, (int) height); //width e height sono double, non ho capito perché mi forza a fare il cast a int
    } */

    public void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public entities getEntity () {
        return this.entity;
    }
    
    public void setEntity (entities entity) {
        this.entity = entity;
    }

    public static BufferedImage setup(String imagePath, int width, int height) { //da spolpare per bene
        ImageScalar uTool = new ImageScalar();
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

}
