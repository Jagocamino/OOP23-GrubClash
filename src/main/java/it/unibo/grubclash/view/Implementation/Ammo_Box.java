package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;

public class Ammo_Box extends Entity{
    
    private final char FS = File.separatorChar;

    private BufferedImage stand1;
    private BufferedImage stand2;

    private static final int NEW_WIDTH = 20;
    private static final int NEW_HEIGHT = 20;
    //Adding pixels for the image
    private static final int ADDITION = 23;

    public Ammo_Box(int x, int y) {

        super(x, y, NEW_WIDTH, NEW_HEIGHT, EnumEntity.Entities.AMMO_BOX);

        Allowed.addDynamicEntity(Optional.of(this));
        getImage();
    }

    private void getImage() {

        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "ammo_box_1.png", this.getWidth()+ADDITION, this.getHeight()+ADDITION);
        stand2 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "ammo_box_2.png", this.getWidth()+ADDITION, this.getHeight()+ADDITION);    
    }

    @Override
    public void draw(Graphics2D g2d){
        if(isAlive()){
            g2d.drawImage(stand1, getX(), getY(),null);
        }else{
            g2d.drawImage(stand2, getX(), getY(),null);
        }
    }
}
