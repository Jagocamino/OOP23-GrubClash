package it.unibo.grubclash.model.Implementation;

import it.unibo.grubclash.controller.Implementation.GrubPanel;
import it.unibo.grubclash.model.Implementation.EnumEntity.status;

public class Physic {
  
    GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    public void checkTerrain(Entity entity){
        if(Allowed.CanMoveThere(entity.x, entity.y+entity.gravityAcceleration, entity.width, entity.height)){
            entity.gravityCounter++;
            entity.y+=entity.gravityAcceleration;
            if(entity.gravityCounter > 15){
                entity.gravityAcceleration++;
                entity.gravityCounter = 0;
            }
            entity.isFalling = true;
        }else{
            entity.isFalling = false;
            entity.gravityAcceleration = 2;
            entity.gravityCounter = 0;
            if(entity.y >= grubPanel.frameManager.getWindowHeight().get()-40){
                entity.working = status.DEAD;
            }
        }
    }
}
