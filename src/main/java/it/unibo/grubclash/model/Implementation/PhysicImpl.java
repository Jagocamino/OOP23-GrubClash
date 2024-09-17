package it.unibo.grubclash.model.Implementation;

import it.unibo.grubclash.controller.Implementation.GrubPanelImpl;
import it.unibo.grubclash.model.Application_Programming_Interface.Entity;
import it.unibo.grubclash.model.Application_Programming_Interface.Physic;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;

/**
 * Class implementing the Physic methods.
 */
public class PhysicImpl implements Physic {

    private static final int BORDER_OFFSET = 40;
    private static final int FRAMES_TO_INCREASE_GRAVITY = 15;
    private static final int RESET = 0;
  
    private GrubPanelImpl grubPanel;

    /**
     * Constructor for physic
     * @param grubPanel
     */
    public PhysicImpl(GrubPanelImpl grubPanel){
        this.grubPanel = grubPanel;
    }

    /**
     * {@inheritDoc}
     */
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
            entity.setGravityAcceleration(EntityImpl.INITIAL_GRAVITY_ACCELERATION);
            entity.setGravityCounter(RESET);
            if(entity.getY() >= grubPanel.getFrameManager().getWindowHeight().get() - BORDER_OFFSET){
                entity.setWorking(Status.DEAD);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkDeath(Entity entity) {
        if(Allowed.isPlayer(entity)){
            if(entity.getLife().getLifeValue().get() <= RESET){
                entity.setWorking(Status.DEAD);
            }
        }
    }
}
