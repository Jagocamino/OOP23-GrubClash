package it.unibo.grubclash.view.Implementation;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.EntityImpl;
import it.unibo.grubclash.model.Implementation.EnumEntity;

public class Trap extends EntityImpl{

    private final char FS = File.separatorChar;

    private BufferedImage stand1;
    private static final int WIDTH_TRAP=20;
    private static final int HEIGHT_TRAP=20;

    //Pixel removal Insert the Trap in the right place
    private static final int REMOVE_PIXELS_WIDTH = 23;
    private static final int REMOVE_PIXELS_HEIGHT = 23;

    public Trap(int x, int y) {

        super(x, y, WIDTH_TRAP, HEIGHT_TRAP, EnumEntity.Entities.TRAP);

        Allowed.addDynamicEntity(Optional.of(this));
        getImage();
    }

    private void getImage() {

        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "trap.png", this.getWidth()+REMOVE_PIXELS_WIDTH, this.getHeight()+REMOVE_PIXELS_HEIGHT);
    }

    @Override
    public void draw(Graphics2D g2d){
        if(isAlive()){
            g2d.drawImage(stand1, getX(), getY(),null);
        }
    }
    
}
