package it.unibo.grubclash.model.Implementation;

import java.util.Random;

import it.unibo.grubclash.model.Implementation.EnumEntity.entities;

import java.util.ArrayList;

public class ItemSpawner {

    public static void generateSpawnLocation (int ROWS, int COLS, int numOfItems, entities[][] entityMatrix) { // mette dentro entityMatrix[][] ITEM, dentro grubpanel verranno cambiati
        
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

            if (entityMatrix[randY][randX] == entities.SKY) {
                entityMatrix[randY][randX] = entities.ITEM;
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
