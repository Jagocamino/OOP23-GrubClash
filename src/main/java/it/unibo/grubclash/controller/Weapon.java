package it.unibo.grubclash.controller;
import it.unibo.grubclash.view.EnumEntity.entities;

public class Weapon {

    enum weapons {
        GRANADE,
        ROKET,
        HITSCAN;    
    }
    
    private static Entity owner;
    public static Entity getOwner() {
        return owner;
    }
    public static void setOwner(Entity owner) {
        Weapon.owner = owner;
    }

    private static weapons type;
    public static weapons getType() {
        return type;
    }
    public static void setType(weapons type) {
        Weapon.type = type;
    }

    public Weapon (Entity owner, weapons type) {
        
    }
    
    
}
