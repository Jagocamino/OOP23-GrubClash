package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.view.Application_Programming_Interface.LifeDrawing;

public class LifeDrawingMobImpl implements LifeDrawing {

    private BufferedImage life20;

    //pixel removal to draw the image of life on top of the character
    private static final int REMOVING_X = 25;
    private static final int REMOVING_Y = 15;

    private static final int WIDTH_LIFE_MOB = 100;
    private static final int HEIGHT_LIFE_MOB = 15;

    public LifeDrawingMobImpl() {
        final char FS = File.separatorChar;
        life20 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_mob.png", WIDTH_LIFE_MOB, HEIGHT_LIFE_MOB);
    }

    @Override
    public void drawLife(Graphics2D g2d, Entity entity, int life) {

        switch (life) {
            case 2:
            g2d.drawImage(life20, entity.getX() - REMOVING_X, entity.getY() - REMOVING_Y, null);
            break;
            default:
            break;
        }
    }
    
}
