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

    final char FS = File.separatorChar;

    public BufferedImage stand1;

    public Trap(int x, int y) {

        super(x, y, 20, 20, EnumEntity.entities.TRAP);

        Allowed.addDynamicEntity(Optional.of(this));
        getImage();
    }

    private void getImage() {
        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "items" + FS + "trap.png", this.width+23, this.height+23);
    }

    @Override
    public void draw(Graphics2D g2d){
        if(working == status.ALIVE){
            g2d.drawImage(stand1, x, y,null);
        }
    }
    
}
