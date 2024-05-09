package it.unibo.grubclash.controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.grubclash.view.ImageScaler;
import it.unibo.grubclash.view.EnumEntity.entities;
import it.unibo.grubclash.model.GrubPanel;

import java.awt.Graphics;

public class Entity { //ogni entity, comprese le strutture, ha questa classe

    GrubPanel grubPanel;

    protected static float x;  //qui metterei int e non float tanto viene sempre messo (int) e come valore float non viene mai effettivamente usato
    protected static float y;
    protected static double width, height;
    protected static Rectangle hitbox;
    protected static entities entity;
    protected static int spriteNum = 1;
    protected static boolean canMove = true;
    protected static boolean justJumped = false;
    public boolean gravity = true;

    //COUNTERS
    protected static int spriteCounter = 0;
    public int jump1Counter = 0;
    public int jump2Counter = 0;

    public Entity(float x, float y, double width, double height, entities entity) { //passed
        Entity.x = x;
        Entity.y = y;
        Entity.width = width;
        Entity.height = height;
        Entity.entity = entity;
        initHitbox();
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

    private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, (int) width, (int) height); //width e height sono double, non ho capito perché mi forza a fare il cast a int
    }

    public void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public entities getEntity () {
        return Entity.entity;
    }
    
    public void setEntity (entities entity) {
        Entity.entity = entity;
    }

    public BufferedImage setup(String imagePath, int width, int height) { //da spolpare per bene
        ImageScaler uTool = new ImageScaler();
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
