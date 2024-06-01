package it.unibo.grubclash.model.Application_Programming_Interface;

import it.unibo.grubclash.controller.Application_Programming_Interface.PlayerInterface;

public interface ProjectileType {

    PlayerInterface getOwner();

    void update();

}