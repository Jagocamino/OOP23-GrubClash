package it.unibo.grubclash.model.Application_Programming_Interface;

import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;

public interface ItemSpawnerInterface {

    void generateSpawnLocation (int ROWS, int COLS, int numOfItems, Entities[][] entityMatrix);

}