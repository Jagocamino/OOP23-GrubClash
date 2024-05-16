package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.model.Implementation.GrubPanel;

public class Physic {
  
    GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    public void checkTerrain(Player player){
        //TODO caduta accelerata
        if(Allowed.CanMoveThere(player.x, player.y+player.gravityAcceleration, player.width, player.height)){
            player.gravityCounter++;
            player.y+=player.gravityAcceleration;
            if(player.gravityCounter > 15){
                player.gravityAcceleration++;
                player.gravityCounter = 0;
            }
            player.canJump = false;
        }else{
            player.canJump = true;
            player.gravityAcceleration = 0;
            player.gravityCounter = 0;
        }
    }
}
