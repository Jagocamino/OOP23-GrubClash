package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.MapBuilder;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;

import java.util.ArrayList;
import java.util.Random;

/*
    fa nascere casualmente sul lvlData[][] degli item, implementati in altre funzioni
*/

public class ItemSpawner{

    
    protected int ROWS;
    protected int COLS;
    protected int numOfItems;
    protected Entity[][] lvlData;
    private ArrayList<Integer> list = new ArrayList<>();

    public ItemSpawner(int ROWS, int COLS, int numOfItems, Entity[][] lvlData) {
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.numOfItems = numOfItems;
        this.lvlData = lvlData;
    }

    public void generateSpawnLocation (boolean isTrap) { //voglio generare casualmente più item sul top e meno sul bottom
        Random randomNum = new Random();
        int randX;
        int randY;
        int isHere = 0;
        while (numOfItems > 0) {
            randX = randomNum.nextInt(COLS);
            randY = randomNum.nextInt(ROWS);

            for (Integer element : list) {
                if(element != randX){
                    isHere++;
                }
            }
            if(isHere == list.size()){   //non funziona spawnano uno sopra l'altro TODO
                list.add(randX);
                System.out.print("[" + randX + "] ");
                while (lvlData[randY][randX].getEntity() == entities.SKY) { // se la cella che genero casualmente è cielo
                    if(isTrap){
                        MapBuilder.setEntityInMatrix(randX, randY, EnumEntity.idToTrapConverter(numOfItems).get());
                        lvlData[randY][randX].setEntity(EnumEntity.idToTrapConverter(numOfItems).get());
                        numOfItems--;
                    }else{
                        MapBuilder.setEntityInMatrix(randX, randY, EnumEntity.idToHealConverter(numOfItems).get());
                        lvlData[randY][randX].setEntity(EnumEntity.idToHealConverter(numOfItems).get());
                        numOfItems--;
                    }
                }
            }
            isHere = 0;
        }
        
    }
  
}
