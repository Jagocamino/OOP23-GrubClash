package it.unibo.grubclash.model.Implementation;

import it.unibo.grubclash.controller.Implementation.GrubPanel;
import it.unibo.grubclash.model.Application_Programming_Interface.PhysicInterface;
import it.unibo.grubclash.model.Implementation.EnumEntity.status;

public class Physic implements PhysicInterface {
  
    GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    @Override
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

    @Override
    public void checkDeath(Entity entity) {
        if(Allowed.isPlayer(entity)){
            if(entity.life.getLife().get() <= 0){
                entity.working = status.DEAD;
            }
        }
    }
}
