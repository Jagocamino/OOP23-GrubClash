package it.unibo.grubclash.controller.Implementation;
import it.unibo.grubclash.controller.ProjectileType;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;

public class ProjectileRoket extends Entity implements ProjectileType {

    final private int dmgRadius = 40;
    final private static int damage = 50; // non devo mettere 50, il danno ha valori fissi
    final private static int widthRoket = 10;
    final private static int heightRoket = 10;
    private Player owner;
    
    public ProjectileRoket(int x, int y, Player owner) {
        super(x, y, getWidthRoket(), getHeightRoket(), null); //qu ic'era entity al posto di null ma entity static non va bene rompe tutto TODO
        this.owner = owner;
    }   

    public static int getWidthRoket() {
        return ProjectileRoket.widthRoket;
    }
    public static int getHeightRoket() {
        return ProjectileRoket.heightRoket;
    }
    
    public void update () { // come faccio a passare la direizone di movimento?
        if (owner.getDirection() == orientation.LEFT) {
            setX(x--);
        }
        if (owner.getDirection() == orientation.RIGHT) {
            setX(x++);
        }
        if (owner.getDirection() == orientation.UP) {
            setY(y--);
        }
        if (owner.getDirection() == orientation.DOWN) {
            setY(y++);
        }

    }

    @Override
    public void trajectory(int x, int y) { // passa la x e y dell'owner, poi passa la x e y del mouse
        
    }

    @Override
    public Entity damage () { 
        int x = (int) ((getX() + getWidth()) / 2);
        int y = (int) ((getY() + getHeight()) / 2);
        return new Entity(x - dmgRadius , y - dmgRadius, dmgRadius * 2, dmgRadius * 2, entities.EXPLOSION); //elimino ogni explosion dopo
    }
    

}
