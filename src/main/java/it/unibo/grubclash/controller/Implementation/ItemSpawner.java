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

    public void generateSpawnLocation (boolean isTrap) { // TODO ci credo che gli oggetti vanno uno sopra l'altro, ci sono 2 ItemSpawner in grubPanel ziopera
        Random randomNum = new Random();
        int randX;
        int randY;
        while (numOfItems > 0) {
            
            randY = randomNum.nextInt(ROWS);

            do {
                randX = randomNum.nextInt(COLS);
                System.out.println("alskjijallògajlk" + randX);
            } while (alreadyInCol(randX, list) == true); // I dont want duplicates of randX
            
            //non funziona spawnano uno sopra l'altro TODO
            list.add(randX);
            System.out.print("[" + randX + "] ");

            /*
                TODO la entityMatrix è una matrice di enum entities, è questa che deve essere popolata di ITEM che poi diventeranno entità in lvlData.
                noi stiamo settando lo SKY di lvlData in altro. NO. 
            */

            if (lvlData[randY][randX].getEntity() == entities.SKY) {
                lvlData[randY][randX].setEntity(entities.ITEM);
                numOfItems--;
            }



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
        
    }

    private boolean alreadyInCol (int randX, ArrayList<Integer> list) {
        for (Integer element : list) {
            return (Integer.valueOf(element) == randX);
        } 
        return false;
    }


  
}
