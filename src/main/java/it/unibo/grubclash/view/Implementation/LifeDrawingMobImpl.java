package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.view.Application_Programming_Interface.LifeDrawing;

public class LifeDrawingMobImpl implements LifeDrawing {

    private BufferedImage life20;

    //pixel removal to draw the image of life on top of the character
    private static final int removingX = 25;
    private static final int removingY = 15;

    private static final int whidthLifeMob = 100;
    private static final int heigthLifeMob = 15;

    public LifeDrawingMobImpl() {
        final char FS = File.separatorChar;
        life20 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_mob.png", whidthLifeMob, heigthLifeMob);
    }

    @Override
    public void drawLife(Graphics2D g2d, Entity entity, int life) {

        switch (life) {
            case 2:
            g2d.drawImage(life20, entity.x - removingX, entity.y - removingY, null);
            break;
            default:
            break;
        }
    }
    
}
