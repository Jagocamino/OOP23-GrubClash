package it.unibo.grubclash.controller;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.view.Implementation.EnumEntity.status;

import java.util.Optional;

public abstract class Weapon {

    /* 
        TODO l'owner naturalmente è un player
        TODO a seconda dell'arma che il player ha in mano (una alla volta) il player spara
        TODO è dentro weapon che metto il trigger del proiettile
        Weapon non deve appartenere a dynamicEntity ma il proiettile lanciato sì.
    */ 

    private final int defaultAmmo = 5;
    private Player owner;
    private int ammo;
    private status working;
    

    public status getWorking() {
        return working;
    }

    public void setWorking(status working) {
        this.working = working;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void refillAmmo() {
        setAmmo(defaultAmmo);
    }

    public void reduceAmmo() {
        this.ammo = this.ammo - 1;
    }
    
}
