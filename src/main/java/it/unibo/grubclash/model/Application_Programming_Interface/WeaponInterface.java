package it.unibo.grubclash.model.Application_Programming_Interface;

import java.awt.Graphics2D;
import java.util.Optional;

import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.ProjectileRoket;
import it.unibo.grubclash.model.Implementation.EnumEntity.orientation;

public interface WeaponInterface {

    void setWeaponDir(orientation weaponDir);

    orientation getShootingDir();

    void setShootingDir(orientation shootingDir);

    Player getOwner();

    void setOwner(Player owner);

    void setAmmo(int ammo);

    int getAmmo();

    void reduceAmmo();

    void refillAmmo();

    void draw(Graphics2D g2d);

    Optional<ProjectileRoket> shoot();

    Optional<ProjectileRoket> getRocket();

}