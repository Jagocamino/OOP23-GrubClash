package it.unibo.grubclash.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.controller.Implementation.ProjectileRoket;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;
import it.unibo.grubclash.view.Implementation.EnumEntity.status;

public abstract class Weapon {

    private Player owner;
    private int ammo;
    private orientation shootingDir;

    private orientation weaponDir;

    protected Optional<ProjectileRoket> rocket;

    public BufferedImage left, right, up, down;

    public Weapon(Player owner, int ammo){

        setOwner(owner);
        setAmmo(ammo);
        this.rocket = Optional.empty();
        this.weaponDir = owner.getDirection();
    }

    public void setWeaponDir(orientation weaponDir) {
        this.weaponDir = weaponDir;
    }

    public orientation getShootingDir() {
        return shootingDir;
    }
    public void setShootingDir(orientation shootingDir) {
        this.shootingDir = shootingDir;
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

    public int getAmmo() {
        return this.ammo;
    }

    public void reduceAmmo() {
        this.ammo--;
    }

    public void refillAmmo(){}

    public void draw(Graphics2D g2d){
        
        BufferedImage image = null;

        if(getOwner().working == status.ALIVE){

            switch(weaponDir){
                case LEFT:
                    image = left;
                    g2d.drawImage(image, getOwner().x - 5, getOwner().y + 5,null);
                break;
                case RIGHT:
                    image = right;
                    g2d.drawImage(image, getOwner().x + getOwner().width + 5, getOwner().y + 5,null);
                break;
                case DOWN:
                    image = down;
                    g2d.drawImage(image, getOwner().x + getOwner().width/2, getOwner().y + getOwner().height/2,null);
                break;
                case UP:
                    image = up;
                    g2d.drawImage(image, getOwner().x + getOwner().width/2, getOwner().y - getOwner().height/2,null);
                break;
                case UP2:
                    image = up;
                    g2d.drawImage(image, getOwner().x + getOwner().width/2, getOwner().y - getOwner().height/2,null);
                break;
            }
        }
    } 

    public Optional<ProjectileRoket> shoot(){return Optional.empty();}

    public Optional<ProjectileRoket> getRocket() {
        return rocket;
    }
    
}
