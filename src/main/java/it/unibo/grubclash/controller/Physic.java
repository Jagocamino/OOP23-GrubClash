package it.unibo.grubclash.controller;

import it.unibo.grubclash.model.GrubPanel;
import it.unibo.grubclash.view.MapBuilder;
import it.unibo.grubclash.view.EnumEntity.entities;

public class Physic {
  
    GrubPanel grubPanel;

    public Physic(GrubPanel grubPanel){
        this.grubPanel = grubPanel;
    }

    public boolean checkTerrain(Player player){

        for ( int i =0; i<MapBuilder.ROWS; i++){
            for (int j=0; j<MapBuilder.COLS;j++){
                if(MapBuilder.entityMatrix[i][j].equals(entities.WALL)){
                    if(MapBuilder.getYMapBase(i, j)==player.y-50){
                        return true ;
                    }
                }
            }
        }
        return false;
    }
}
