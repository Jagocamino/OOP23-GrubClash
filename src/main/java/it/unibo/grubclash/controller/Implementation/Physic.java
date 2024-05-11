package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.GrubPanel;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.MapBuilder;

public class Physic {
  
    GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    public boolean checkTerrain(Player player){

        for (int i =0; i<MapBuilder.ROWS; i++){
            for (int j=0; j<MapBuilder.COLS;j++){
                if(MapBuilder.entityMatrix[i][j].equals(EnumEntity.idToEntitiesConverter(player.getId()))){
                    if(MapBuilder.entityMatrix[i+1][j] == EnumEntity.entities.WALL){
                        return true ;
                    }
                }
            }
        }
        return false;
    }
}
