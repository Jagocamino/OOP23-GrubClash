package it.unibo.grubclash.controller.Implementation;
import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.grubclash.controller.ProjectileType;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;

public class ProjectileRoket extends Entity implements ProjectileType {

    final private int dmgRadius = 50;
    final private int damage = 50; // non devo mettere 50, il danno ha valori fissi
    final private static int widthRoket = 10;
    final private static int heightRoket = 10;
    private Player owner;
    
    public Player getOwner() {
        return owner;
    }

    public ProjectileRoket(int x, int y, Player owner) {
        super(x, y, getWidthRoket(), getHeightRoket(), entities.PROJECTILE); //qu ic'era entity al posto di null ma entity static non va bene rompe tutto TODO
        this.owner = owner; 
        Allowed.addDynamicEntity(this);
    }   

    public static int getWidthRoket() {
        return ProjectileRoket.widthRoket;
    }
    public static int getHeightRoket() {
        return ProjectileRoket.heightRoket;
    }

    public Entity damage (int dmgRadius) { 
        int x = getX() + (getWidth() / 2);
        int y = getY() + (getHeight() / 2);
        return new Entity(x - dmgRadius, y - dmgRadius, dmgRadius*2, dmgRadius*2, entities.EXPLOSION); //elimino ogni explosion dopo
    } 
    
    @Override
    public void update () { // come faccio a passare la direizone di movimento?

        orientation dir = owner.getWeapon().get().getShootingDir();
        
        if(this.working == EnumEntity.status.ALIVE){
            if (dir == orientation.LEFT) {
                if(Allowed.gonnaExplode(getX() - 5, getY(), getWidthRoket(), getHeightRoket(), owner)){

                    System.out.println("pos razzo x: " + getX() + "y: " + getY());

                    Entity damage = damage(dmgRadius);
                    Allowed.applyDamage(Allowed.dealDamage(damage.getX(), damage.getY(), damage.getWidth(), damage.getHeight()), 1); //da risolvere TODO
                    this.working = EnumEntity.status.DEAD;
                }else{
                    setX(getX() - 5); 
                }  
            }
            else if (dir == orientation.RIGHT) {
                setX(getX()+5);
            }
            else if (dir == orientation.UP || dir == orientation.UP2) {
                setY(getY()-5);
            }
            else if (dir == orientation.DOWN) {
                setY(getY()+5);
            }
        }
    }

    /* @Override
    public void trajectory(int x, int y) { // passa la x e y dell'owner, poi passa la x e y del mouse
        
    } */

    @Override //d
    public int getDamage() {
        return this.damage;
    }

    public void draw(Graphics2D g2d){

        if(this.working == EnumEntity.status.ALIVE){

            g2d.setColor(Color.BLACK);
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    } 
    

}
