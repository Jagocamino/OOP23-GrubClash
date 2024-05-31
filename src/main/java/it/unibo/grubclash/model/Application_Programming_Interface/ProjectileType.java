package it.unibo.grubclash.model.Application_Programming_Interface;

import it.unibo.grubclash.controller.Implementation.Player;

public interface ProjectileType {

    Player getOwner();

    void update();

}