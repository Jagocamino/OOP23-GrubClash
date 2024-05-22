package it.unibo.grubclash.controller.Implementation;

import it.unibo.grubclash.controller.ProjectileType;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;

public class ProjectileRoket extends Entity implements ProjectileType {

    final private int dmgRadius = 40;
    final private static int damage = 50; // non devo mettere 50, il danno ha valori fissi
    final private static int widthRoket = 10;
    final private static int heightRoket = 10;

    public static int getWidthRoket() {
        return ProjectileRoket.widthRoket;
    }
    public static int getHeightRoket() {
        return ProjectileRoket.heightRoket;
    }
    

    public ProjectileRoket(int x, int y) {
        super(x, y, getWidthRoket(), getHeightRoket(), null); //qu ic'era entity al posto di null ma entity static non va bene rompe tutto TODO
    }   

    @Override
    public void trajectory(int x, int y) { //passa la x e y dell'owner, poi passa la x e y del mouse
        
    }

    @Override
    public Entity damage () { 
        int x = (int) ((getX() + getWidth()) / 2);
        int y = (int) ((getY() + getHeight()) / 2);
        return new Entity(x - dmgRadius , y - dmgRadius, dmgRadius * 2, dmgRadius * 2, entities.EXPLOSION); //elimino ogni explosion dopo
    }
    
}
