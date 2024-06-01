package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;

import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.Entity;

import java.awt.image.BufferedImage;
import java.io.File;

import it.unibo.grubclash.view.Application_Programming_Interface.LifeDrawing;

public class LifeDrawingImpl implements LifeDrawing {

    private BufferedImage life20,life40,life60,life80,life100;

    public LifeDrawingImpl() {
        final char FS = File.separatorChar;
        life100 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_100.png", 100, 15);
        life80 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_80.png", 100, 15);
        life60 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_60.png", 100, 15);
        life40 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_40.png", 100, 15);
        life20 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_20.png", 100, 15);
    }

    @Override
    public void drawLife(Graphics2D g2d, Player player, int life) {
        switch (life) {
            case 10:
                g2d.drawImage(life100, player.x - 25, player.y - 15, null);
                break;
            case 8:
                g2d.drawImage(life80, player.x - 25, player.y - 15, null);
                break;
            case 6:
                g2d.drawImage(life60, player.x - 25, player.y - 15, null);
                break;
            case 4:
                g2d.drawImage(life40, player.x - 25, player.y - 15, null);
                break;
            case 2:
                g2d.drawImage(life20, player.x - 25, player.y - 15, null);
                break;
            default:
                break;
        }
    }

}