package it.unibo.grubclash.model.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import it.unibo.grubclash.controller.Implementation.PlayerImpl;
import it.unibo.grubclash.model.Application_Programming_Interface.Weapon;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;

public abstract class WeaponImpl implements Weapon {

    private PlayerImpl owner;
    private int ammo;
    private Orientation shootingDir;

    private Orientation weaponDir;

    protected Optional<ProjectileRoket> rocket;

    protected BufferedImage left;
    protected BufferedImage right;
    protected BufferedImage up;
    protected BufferedImage down;

    public WeaponImpl(PlayerImpl owner, int ammo){

        setOwner(owner);
        setAmmo(ammo);
        this.rocket = Optional.empty();
        this.weaponDir = owner.getDirection();
    }

    @Override
    public void setWeaponDir(Orientation weaponDir) {
        this.weaponDir = weaponDir;
    }

    @Override
    public Orientation getShootingDir() {
        return shootingDir;
    }
    @Override
    public void setShootingDir(Orientation shootingDir) {
        this.shootingDir = shootingDir;
    }

    @Override
    public PlayerImpl getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(PlayerImpl owner) {
        this.owner = owner;
    }

    @Override
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @Override
    public int getAmmo() {
        return this.ammo;
    }

    @Override
    public void reduceAmmo() {
        this.ammo--;
    }

    @Override
    public void refillAmmo(){}

    @Override
    public void draw(Graphics2D g2d){
        
        BufferedImage image = null;

        if(getOwner().isAlive()){

            switch(weaponDir){
                case LEFT:
                    image = left;
                    g2d.drawImage(image, getOwner().getX() - 5, getOwner().getY() + 5,null);
                break;
                case RIGHT:
                    image = right;
                    g2d.drawImage(image, getOwner().getX() + getOwner().getWidth() + 5, getOwner().getY() + 5,null);
                break;
                case DOWN:
                    image = down;
                    g2d.drawImage(image, getOwner().getX() + getOwner().getWidth()/2, getOwner().getY() + getOwner().getHeight()/2,null);
                break;
                case UP:
                    image = up;
                    g2d.drawImage(image, getOwner().getX() + getOwner().getWidth()/2, getOwner().getY() - getOwner().getHeight()/2,null);
                break;
                case UP2:
                    image = up;
                    g2d.drawImage(image, getOwner().getX() + getOwner().getWidth()/2, getOwner().getY() - getOwner().getHeight()/2,null);
                break;
            }
        }
    } 

    @Override
    public Optional<ProjectileRoket> shoot(){return Optional.empty();}

    @Override
    public Optional<ProjectileRoket> getRocket() {
        return rocket;
    }
    
}
