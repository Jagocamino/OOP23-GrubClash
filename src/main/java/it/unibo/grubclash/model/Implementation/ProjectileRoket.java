package it.unibo.grubclash.model.Implementation;
import java.util.Optional;

import it.unibo.grubclash.controller.Application_Programming_Interface.Player;
import it.unibo.grubclash.controller.Implementation.PlayerImpl;
import it.unibo.grubclash.model.Application_Programming_Interface.Entity;
import it.unibo.grubclash.model.Application_Programming_Interface.ProjectileType;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;
import it.unibo.grubclash.view.Implementation.ProjectileImpl;

public class ProjectileRoket extends ProjectileImpl implements ProjectileType {

    private int dmgRadius = 50;
    private static int widthRoket = 10;
    private static int heightRoket = 10;
    private int speed = 10;
    private PlayerImpl owner;

    public ProjectileRoket(int x, int y, PlayerImpl owner) {
        super(x, y, getWidthRoket(), getHeightRoket(), Entities.PROJECTILE); 
        this.owner = owner;
        setGravity(false);
        Allowed.addDynamicEntity(Optional.of(this));
    }   

    @Override
    public Player getOwner() {
        return owner;
    }

    public static int getWidthRoket() {
        return widthRoket;
    }
    public static int getHeightRoket() {
        return heightRoket;
    }

    @Override
    public void update () {
        Orientation dir = owner.getWeapon().get().getShootingDir();
        
        if(this.isAlive()){
            if (dir == Orientation.LEFT) {
                if(Allowed.gonnaExplode(updatedDir (dir), getY(), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setX(updatedDir (dir)); 
                }  
            }
            else if (dir == Orientation.RIGHT) {
                if(Allowed.gonnaExplode(updatedDir (dir), getY(), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setX(updatedDir (dir)); 
                } 
            }
            else if (dir == Orientation.UP || dir == Orientation.UP2) {
                if(Allowed.gonnaExplode(getX(), updatedDir (dir), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setY(updatedDir (dir)); 
                }  
            }
            else if (dir == Orientation.DOWN) {
                if(Allowed.gonnaExplode(getX(), updatedDir (dir), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setY(updatedDir (dir)); 
                }  
            }
        }
    }

    private void explosionHappening () {
        Sound.setFile(0);
        Sound.play();
        Entity damage = damage(dmgRadius);
        Allowed.applyDamage(Allowed.dealDamage(damage.getX(), damage.getY(), damage.getWidth(), damage.getHeight()), 2);
        this.setWorking(Status.DEAD);
    }

    private int updatedDir (Orientation dir) {
        switch (dir) {
            case UP:
                return getY() - speed;
            case UP2:
                return getY() - speed;
            case DOWN:
                return getY() + speed;
            case RIGHT:
                return getX() + speed;
            case LEFT:
                return getX() - speed;
            default:
                return 0;
        }
    }

}
