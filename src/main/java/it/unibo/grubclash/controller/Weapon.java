package it.unibo.grubclash.controller;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.view.Implementation.EnumEntity.status;

import java.util.Optional;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Weapon {

    /* 
        TODO Weapon deve matierializzare l'immagine, anche i proiettili
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

    /* public void draw(Graphics2D g2d){
        
        BufferedImage image = imageWeapon();

        if(getOwner().alive){
            g2d.drawImage(image, x, y,null);
            

        }else{
            // TODO semplicemente si toglie
        }

    } */
    
}
