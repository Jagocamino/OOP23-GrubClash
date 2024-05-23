package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.controller.Weapon;

public class WeaponRoket implements Weapon {

    //TODO gestire ora che estende entity, OGNI ARMA  lancia un proiettile
    //il proiettile smette di esistere se canMoveThere() restituisce false

    private final int defaultAmmo = 5;
    private Player owner;
    private int ammo;

    @Override
    public Entity getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @Override
    public void refillAmmo() {
        setAmmo(defaultAmmo);
    }

    @Override
    public void reduceAmmo() {
        this.ammo = this.ammo - 1;
    }

    @Override
    public Entity shoot() {
        reduceAmmo();
        return new ProjectileRoket(owner.getX(), owner.getY(), owner);
    }
    
}
