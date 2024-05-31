package it.unibo.grubclash.controller.Implementation;

import java.util.Random;
import java.util.ArrayList;

import it.unibo.grubclash.view.Implementation.EnumEntity.entities;

public class ItemSpawner {

    public static void generateSpawnLocation (int ROWS, int COLS, int numOfItems, entities[][] entityMatrix) { // mette dentro entityMatrix[][] ITEM, dentro grubpanel verranno cambiati
        Random randomNum = new Random();
        int randX;
        int randY;
        int saturation; // se cicla troppo, spawna anche nella stessa colonna
        ArrayList<Integer> list = new ArrayList<>();
        while (numOfItems > 0) {
            randY = randomNum.nextInt(ROWS);
            saturation = 0;
            do {
                randX = randomNum.nextInt(COLS);
            } while ((alreadyInCol(randX, list) == true) && (saturation >= numOfItems*2));
            if (entityMatrix[randY][randX] == entities.SKY) {
                entityMatrix[randY][randX] = entities.ITEM;
                list.add(randX);
                numOfItems--;
            }
        }
    }

    private static boolean alreadyInCol (int randX, ArrayList<Integer> list) {
        for (Integer element : list) {
            return (Integer.valueOf(element) == randX);
        } 
        return false;
    }
}
