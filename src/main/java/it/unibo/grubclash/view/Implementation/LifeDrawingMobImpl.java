package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import it.unibo.grubclash.model.Implementation.EntityImpl;
import it.unibo.grubclash.view.Application_Programming_Interface.LifeDrawing;

/**
 * Class implementing the LifeDrawing methods.
 */
public class LifeDrawingMobImpl implements LifeDrawing {

    private BufferedImage life20;
    private BufferedImage life60;

    //pixel removal to draw the image of life on top of the character
    private static final int REMOVING_X = 25;
    private static final int REMOVING_Y = 15;

    private static final int WIDTH_LIFE_MOB = 100;
    private static final int HEIGHT_LIFE_MOB = 15;

    public LifeDrawingMobImpl() {
        final char FS = File.separatorChar;
        life20 = EntityImpl.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_mob.png", WIDTH_LIFE_MOB, HEIGHT_LIFE_MOB);
        life60 = EntityImpl.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_mob_6.png", WIDTH_LIFE_MOB, HEIGHT_LIFE_MOB);
    }   

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawLife(Graphics2D g2d, EntityImpl entity, int life) {

        switch (life) {
            case 2:
            g2d.drawImage(life20, entity.getX() - REMOVING_X, entity.getY() - REMOVING_Y, null);
            break;
            case 6:
            g2d.drawImage(life60, entity.getX() - REMOVING_X, entity.getY() - REMOVING_Y, null);
            break;
            default:
            break;
        }
    }
    
}
