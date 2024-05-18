package it.unibo.grubclash.controller;
import it.unibo.grubclash.controller.Implementation.Entity;

public class Weapon {

    /* 
        TODO sarebbe bello se dentro weapon potesse essere instanziato un oggetto Entity che si aggiunge alle dynamicEntity 
        TODO l'owner naturalmente è un player, sarebbe figo mettere dentro al player la possibilità di avere una weapon con il metodo addWeapon()
        questo metodo ^ aggiunge un arma al personaggio. verifica che se un'arma è già presente allora si butta (magari con una mezza animazione).
        L'arma non deve essere considerata entità
    */ 
    enum weapons {
        GRANADE,
        ROKET,
        HITSCAN;
    }
    
    private static Entity owner;
    public static Entity getOwner() {
        return owner;
    }
    private static void setOwner(Entity owner) {
        Weapon.owner = owner;
    }

    private static weapons type;
    public weapons getType() {
        return type;
    }
    private static void setType(weapons type) {
        Weapon.type = type;
    }

    public Weapon (Entity owner, weapons type) {
        setOwner(owner);
        setType(type);
    }
    
}
