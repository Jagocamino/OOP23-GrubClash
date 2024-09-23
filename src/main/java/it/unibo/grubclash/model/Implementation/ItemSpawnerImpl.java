package it.unibo.grubclash.model.Implementation;

import java.util.Random;

import it.unibo.grubclash.model.Application_Programming_Interface.ItemSpawner;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;

import java.util.ArrayList;

public class ItemSpawnerImpl implements ItemSpawner {

    /**
     * {@inheritDoc}
     */
    public void generateSpawnLocation (int ROWS, int COLS, int numOfItems, Entities[][] entityMatrix) {
        
        Random randomNum = new Random();
        int randX;
        int randY;
        int saturation; 
        int itemsAdded = 0;
        ArrayList<Integer> list = new ArrayList<>();

        while (numOfItems > itemsAdded) {
            randY = randomNum.nextInt(ROWS);
            saturation = 0;

            do {
                saturation++;
                randX = randomNum.nextInt(COLS);
            } while ((alreadyInCol(randX, list) == true) && (saturation < numOfItems));

            if (entityMatrix[randY][randX] == Entities.SKY) {
                entityMatrix[randY][randX] = Entities.ITEM;
                list.add(randX);
                itemsAdded++;
            }
        }
    }

    private static boolean alreadyInCol (int randX, ArrayList<Integer> list) {

        for (Integer element : list) {
            if (Integer.valueOf(element) == randX) {
                return true;
            }
        }
        return false;
    }
}
