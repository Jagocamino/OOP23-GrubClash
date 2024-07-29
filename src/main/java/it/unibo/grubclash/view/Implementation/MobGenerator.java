package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;

public class MobGenerator extends Entity {
    
    private final char FS = File.separatorChar;

    private BufferedImage stand1;
    private static final int widthMobGenerator=20;
    private static final int heighMobGenerator=20;

    //Pixel removal Insert the MobGenerator in the right place
    private final int removePixelsWidth = 23;
    private final int removePixelsHeigth = 23;

    public MobGenerator(int x, int y) {

        super(x, y, widthMobGenerator, heighMobGenerator, EnumEntity.Entities.MOBGENERATOR);

        Allowed.addDynamicEntity(Optional.of(this));
        
        getImage();
    }

    private void getImage() {

        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "MobGenerator.png", this.width+removePixelsWidth, this.height+removePixelsHeigth);
    }

    @Override
    public void draw(Graphics2D g2d){
        if(working == Status.ALIVE){
            g2d.drawImage(stand1, x, y,null);
        }
    }
}
