package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.EnumEntity.status;

public class Ammo_Box extends Entity{
    
    private final char FS = File.separatorChar;

    private BufferedImage stand1;
    private BufferedImage stand2;

    private static final int newWidth = 20;
    private static final int newHeigth = 20;
    //Adding pixels for the image
    private static final int addiction = 23;

    public Ammo_Box(int x, int y) {

        super(x, y, newWidth, newHeigth, EnumEntity.entities.AMMO_BOX);

        Allowed.addDynamicEntity(Optional.of(this));
        getImage();
    }

    private void getImage() {

        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "ammo_box_1.png", this.width+addiction, this.height+addiction);
        stand2 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "ammo_box_2.png", this.width+addiction, this.height+addiction);    
    }

    @Override
    public void draw(Graphics2D g2d){
        if(working == status.ALIVE){
            g2d.drawImage(stand1, x, y,null);
        }else{
            g2d.drawImage(stand2, x, y,null);
        }
    }
}
