package it.unibo.grubclash.controller.Application_Programming_Interface;

import java.awt.Graphics2D;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.Weapon;
import it.unibo.grubclash.model.Implementation.EnumEntity.orientation;

public interface PlayerInterface {

    void update();

    Optional<Weapon> getWeapon();

    void setWeapon(Optional<Weapon> weapon);

    orientation getDirection();

    void setDirection(orientation direction);

    void draw(Graphics2D g2d);

    int getId();

    KeyHandler getKeyH();

}