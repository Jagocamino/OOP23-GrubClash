package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.MapBuilder;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;

import java.util.Random;

/*
    fa nascere casualmente sul lvlData[][] degli item, implementati in altre funzioni
*/

public class ItemSpawner{

    
    protected int ROWS;
    protected int COLS;
    protected int numOfItems;
    protected Entity[][] lvlData;

    public ItemSpawner(int ROWS, int COLS, int numOfItems, Entity[][] lvlData) {
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.numOfItems = numOfItems;
        this.lvlData = lvlData;
    }

    public void generateSpawnLocation () { //voglio generare casualmente più item sul top e meno sul bottom
        Random randomNum = new Random();
        int randX;
        int randY;
        while (numOfItems > 0) {
            randX = randomNum.nextInt(ROWS);
            randY = randomNum.nextInt(COLS);
            
            while (lvlData[randX][randY].getEntity() == entities.SKY) { // se la cella che genero casualmente è cielo
                /* if ( randX >= (int)(ROWS / 3) && randomNum.nextInt(8) == 1) {
                    lvlData[randX][randY].setEntity(entities.ITEM);
                }
                if ( (randX > (int)(ROWS / 3) || randX < (int)(ROWS - ROWS / 3) ) && randomNum.nextInt(7) == 1) {
                    lvlData[randX][randY].setEntity(entities.ITEM);
                }
                if ( randX <= (int)(ROWS / 3) && randomNum.nextInt(5) == 1) {
                    lvlData[randX][randY].setEntity(entities.ITEM);
                } */
                MapBuilder.setEntityInMatrix(randX, randY, EnumEntity.idToItemConverter(numOfItems).get());
                lvlData[randX][randY].setEntity(EnumEntity.idToItemConverter(numOfItems).get());
                numOfItems--;
            }
        }
        
    }
  
}
