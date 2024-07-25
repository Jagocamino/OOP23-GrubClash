package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;

import it.unibo.grubclash.model.Implementation.Entity;

import java.awt.image.BufferedImage;
import java.io.File;

import it.unibo.grubclash.view.Application_Programming_Interface.LifeDrawing;

public class LifeDrawingImpl implements LifeDrawing {

    private BufferedImage life20;
    private BufferedImage life40;
    private BufferedImage life60;
    private BufferedImage life80;
    private BufferedImage life100;

    //pixel removal to draw the image of life on top of the character
    private static final int removingX = 25;
    private static final int removingY = 15;

    private static final int whidthLife = 100;
    private static final int heigthLife = 15;


    public LifeDrawingImpl() {
        final char FS = File.separatorChar;
        life100 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_100.png", whidthLife, heigthLife);
        life80 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_80.png", whidthLife, heigthLife);
        life60 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_60.png", whidthLife, heigthLife);
        life40 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_40.png", whidthLife, heigthLife);
        life20 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_20.png", whidthLife, heigthLife);
    }

    @Override
    public void drawLife(Graphics2D g2d, Entity entity, int life) {
        switch (life) {
            case 10:
                g2d.drawImage(life100, entity.x - removingX, entity.y - removingY, null);
                break;
            case 8:
                g2d.drawImage(life80, entity.x - removingX, entity.y - removingY, null);
                break;
            case 6:
                g2d.drawImage(life60, entity.x - removingX, entity.y - removingY, null);
                break;
            case 4:
                g2d.drawImage(life40, entity.x - removingX, entity.y - removingY, null);
                break;
            case 2:
                g2d.drawImage(life20, entity.x - removingX, entity.y - removingY, null);
                break;
            default:
                break;
        }
    }

}