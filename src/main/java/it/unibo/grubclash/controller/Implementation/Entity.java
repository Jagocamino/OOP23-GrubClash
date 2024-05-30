package it.unibo.grubclash.controller.Implementation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import it.unibo.grubclash.view.Implementation.ImageScalar;
import it.unibo.grubclash.view.Implementation.LifeImpl;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
import it.unibo.grubclash.view.Implementation.EnumEntity.status;
import it.unibo.grubclash.model.Implementation.GrubPanel;

public class Entity { //ogni entity, comprese le strutture, ha questa classe

    GrubPanel grubPanel;

    public int x;  //qui metterei int e non float tanto viene sempre messo (int) e come valore float non viene mai effettivamente usato
    public int y;
    public int width, height;
    protected entities entity;
    protected int spriteNum = 1;
    public boolean canMove = true;
    public boolean gravity = true;
    public int gravityAcceleration = 2;
    public int gravityCounter = 0;
    public boolean canJump = true;
    public status working = status.ALIVE;


    public LifeImpl life; //da rivedere TODO

    //COUNTERS
    protected static int spriteCounter = 0;
    public int jump1Counter = 0;
    public int jump2Counter = 0;

    // TODO components COMPONENTS components COMPONESNT  COMPONENTRS pordcoednibwqeanbdoaliusj
    // hp dell'entità gestita in player

    public Entity(int x, int y, int width, int height, entities entity) { //passed
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entity = entity;
        //initHitbox(); 
    } 

    public Entity (GrubPanel grubPanel) {
        this.grubPanel = grubPanel;
    }

    public void update(){}

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /* private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, (int) width, (int) height); //width e height sono double, non ho capito perché mi forza a fare il cast a int
    } */

    public entities getEntity () {
        return this.entity;
    }
    
    public void setEntity (entities entity) {
        this.entity = entity;
    }

    public boolean isAlive() {
        if (working == status.ALIVE) {
            return true;
        }
        return false;
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

    public void draw(Graphics2D g2d) {
        
    }

}
