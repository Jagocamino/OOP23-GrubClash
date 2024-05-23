package it.unibo.grubclash.controller;
import it.unibo.grubclash.controller.Implementation.Entity;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;

public interface Weapon {

    /* 
        TODO l'owner naturalmente è un player
        TODO a seconda dell'arma che il player ha in mano (una alla volta) il player spara
        TODO è dentro weapon che metto il trigger del proiettile
        Weapon non deve appartenere a dynamicEntity ma il proiettile lanciato sì.
    */ 
    
    public enum weapons {
        GRANADE,
        ROKET,
        HITSCAN;
    }

    Entity getOwner();
    void setOwner(Player owner);

    public void setAmmo (int ammo);
    public void refillAmmo ();
    public void reduceAmmo ();
    public Entity shoot ();

    //serve aggiungere una funzione per lo
    
}
