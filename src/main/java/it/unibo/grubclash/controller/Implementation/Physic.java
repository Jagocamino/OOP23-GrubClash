package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.model.Implementation.GrubPanel;

public class Physic {
  
    GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    public void checkTerrain(Entity entity){
        //TODO caduta accelerata
        if(Allowed.CanMoveThere(entity.x, entity.y+entity.gravityAcceleration, entity.width, entity.height)){
            entity.gravityCounter++;
            entity.y+=entity.gravityAcceleration;
            if(entity.gravityCounter > 15){
                entity.gravityAcceleration++;
                entity.gravityCounter = 0;
            }
            entity.canJump = false;
        }else{
            entity.canJump = true;
            entity.gravityAcceleration = 0;
            entity.gravityCounter = 0;
        }
    }
}
