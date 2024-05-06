package it.unibo.grubclash.controller;

import it.unibo.grubclash.view.EnumEntity.entities;

import java.util.Random;

/*
    fa nascere casualmente sul lvlData[][] degli item, implementati in altre funzioni
*/

public class ItemSpawner{

    
    protected static int ROWS;
    protected static int COLS;
    protected static int numOfItems;
    protected static Entity lvlData[][];

    public ItemSpawner(int ROWS, int COLS, int numOfItems, Entity lvlData[][]) {
        ItemSpawner.ROWS = ROWS;
        ItemSpawner.COLS = COLS;
        ItemSpawner.numOfItems = numOfItems;
        ItemSpawner.lvlData = lvlData;
        generateSpawnLocation();
    }

    public static void generateSpawnLocation () { //voglio generare casualmente più item sul top e meno sul bottom
        Random randomnum = new Random();
        int randX;
        int randY;
        while (numOfItems != 0) {
            randX = randomnum.nextInt(ROWS);
            randY = randomnum.nextInt(COLS);
            while (lvlData[randX][randY].getEntity() == entities.SKY) { // se la cella che genero casualmente è cielo
                if ( randX >= (int)(ROWS / 3) && randomnum.nextInt(8) == 1) {
                    lvlData[randX][randY].setEntity(entities.ITEM);
                }
                if ( (randX > (int)(ROWS / 3) || randX < (int)(ROWS - ROWS / 3) ) && randomnum.nextInt(7) == 1) {
                    lvlData[randX][randY].setEntity(entities.ITEM);
                }
                if ( randX <= (int)(ROWS / 3) && randomnum.nextInt(5) == 1) {
                    lvlData[randX][randY].setEntity(entities.ITEM);
                }
                numOfItems--;
            }
        }
        
    }
  
}
