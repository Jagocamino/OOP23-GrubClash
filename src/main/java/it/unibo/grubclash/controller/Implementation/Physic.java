package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.model.Implementation.GrubPanel;

public class Physic {
  
    GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    public void checkTerrain(Player player){
        //TODO caduta accelerata
        if(Allowed.CanMoveThere(player.x, player.y+1, player.width, player.height)){
            player.y++;
            player.canJump = false;
        }else{
            player.canJump = true;
        }
    }
}
