package it.unibo.grubclash.controller;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;

public interface ProjectileType {
    // custom methods for each different type of projectile
    int updatedDir ();
    Player getOwner ();
    int getWidthRoket ();
    int getHeightRoket ();
    void update ();
    void explosionHappening ();
    int updatedDir (orientation dir);
    
}
