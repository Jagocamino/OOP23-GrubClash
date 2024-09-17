package it.unibo.grubclash.model.Application_Programming_Interface;

import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
/**
 * @author Jago Camoni
 */
public interface ItemSpawner {

    /**
     * Fills some blank spots on the map with the position of new items
     * @param ROWS
     * @param COLS
     * @param numOfItems
     * @param entityMatrix
     */
    void generateSpawnLocation (int ROWS, int COLS, int numOfItems, Entities[][] entityMatrix);

}