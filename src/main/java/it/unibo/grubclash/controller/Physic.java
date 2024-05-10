package it.unibo.grubclash.controller;

import it.unibo.grubclash.model.GrubPanel;
import it.unibo.grubclash.view.EnumEntity;
import it.unibo.grubclash.view.MapBuilder;

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
