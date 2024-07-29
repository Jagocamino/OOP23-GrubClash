package it.unibo.grubclash.model.Implementation;

import it.unibo.grubclash.controller.Implementation.GrubPanel;
import it.unibo.grubclash.model.Application_Programming_Interface.PhysicInterface;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;

public class Physic implements PhysicInterface {

    private static final int BORDER_OFFSET = 40;
    private static final int FRAMES_TO_INCREASE_GRAVITY = 15;
    private static final int RESET = 0;
  
    private GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    @Override
    public void checkTerrain(Entity entity){
        if(Allowed.CanMoveThere(entity.getX(), entity.getY()+entity.getGravityAcceleration(), entity.getWidth(), entity.getHeight())){
            entity.setGravityCounter(entity.getGravityCounter() + 1);
            entity.setY(entity.getY() + entity.getGravityAcceleration());
            if(entity.getGravityCounter() > FRAMES_TO_INCREASE_GRAVITY){
                entity.setGravityAcceleration(entity.getGravityAcceleration() + 1);
                entity.setGravityCounter(RESET);
            }
            entity.setFalling(true);
        }else{
            entity.setFalling(false);
            entity.setGravityAcceleration(Entity.INITIAL_GRAVITY_ACCELERATION);
            entity.setGravityCounter(RESET);
            if(entity.getY() >= grubPanel.getFrameManager().getWindowHeight().get()-BORDER_OFFSET){
                entity.setWorking(Status.DEAD);
            }
        }
    }

    @Override
    public void checkDeath(Entity entity) {
        if(Allowed.isPlayer(entity)){
            if(entity.getLife().getLifeValue().get() <= RESET){
                entity.setWorking(Status.DEAD);
            }
        }
    }
}
