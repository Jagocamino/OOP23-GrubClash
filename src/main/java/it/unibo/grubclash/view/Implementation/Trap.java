package it.unibo.grubclash.view.Implementation;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.EnumEntity.status;

public class Trap extends Entity{

    private final char FS = File.separatorChar;

    private BufferedImage stand1;
    private static final int widthTrap=20;
    private static final int heightTrap=20;

    public Trap(int x, int y) {

        super(x, y, widthTrap, heightTrap, EnumEntity.entities.TRAP);

        Allowed.addDynamicEntity(Optional.of(this));
        getImage();
    }

    private void getImage() {

        //Pixel removal Insert the Trap in the right place
        final int removePixelsWidth = 23;
        final int removePixelsHeigth = 23;

        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "trap.png", this.width+removePixelsWidth, this.height+removePixelsHeigth);
    }

    @Override
    public void draw(Graphics2D g2d){
        if(working == status.ALIVE){
            g2d.drawImage(stand1, x, y,null);
        }
    }
    
}
