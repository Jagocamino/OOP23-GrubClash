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

    protected float x,y;  //qui metterei int e non float tanto viene sempre messo (int) e come valore float non viene mai effettivamente usato
    protected int width, height;
    protected Rectangle hitbox;
    protected entities entity;

    public Entity(float x, float y, int width, int height, entities entity) { //passed
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entity = entity;

        initHitbox();
    }

    public Entity(GrubPanel grubPanel){ //non mi viene in mente perché dovrei passare questo costruttore
        this.grubPanel = grubPanel;
    }

    protected void drawHitbox(Graphics g) {
        //just for Debugging purpose
        g.setColor(Color.PINK);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, width, height);
    }

    public void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() {
        return hitbox;
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
