package it.controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.grubclash.view.ImageScaler;
import it.unibo.grubclash.model.GrubPanel;

import java.awt.Graphics;

public abstract class Entity {

    GrubPanel grubPanel;

    protected float x,y;  //qui metterei int e non float tanto viene sempre messo (int) e come valore float non viene mai effettivamente usato
    protected int width, height;
    protected Rectangle hitbox;

    public int spriteSeq = 1;

    /* public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //con hitBox. esce una sfilza di cose utili
        initHitbox();
    } */

    public Entity(GrubPanel grubPanel){
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

    public Rectangle getHitbox() { //è questa classe che fa l'update dell'hitbox
        return hitbox;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
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
