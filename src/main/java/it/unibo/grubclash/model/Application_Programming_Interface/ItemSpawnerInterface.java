package it.unibo.grubclash.model.Application_Programming_Interface;

import it.unibo.grubclash.model.Implementation.EnumEntity.entities;

public interface ItemSpawnerInterface {

    void generateSpawnLocation (int ROWS, int COLS, int numOfItems, entities[][] entityMatrix);

}