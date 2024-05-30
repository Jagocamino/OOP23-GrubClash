package it.unibo.grubclash.controller.Implementation;
import it.unibo.grubclash.controller.Projectile;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;

public class ProjectileRoket extends Projectile {

    private int dmgRadius = 50;
    private static int widthRoket = 10;
    private static int heightRoket = 10;
    private Player owner;

    public ProjectileRoket(int x, int y, Player owner) {
        super(x, y, getWidthRoket(), getHeightRoket(), entities.PROJECTILE); //qui c'era entity al posto di null ma entity static non va bene rompe tutto TODO
        this.owner = owner;
        Allowed.addDynamicEntity(this);
    }   

    public Player getOwner() {
        return owner;
    }

    public static int getWidthRoket() {
        return widthRoket;
    }
    public static int getHeightRoket() {
        return heightRoket;
    }

    public void update () {

        orientation dir = owner.getWeapon().get().getShootingDir();
        
        if(this.working == EnumEntity.status.ALIVE){
            if (dir == orientation.LEFT) {
                if(Allowed.gonnaExplode(updatedDir (dir), getY(), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setX(updatedDir (dir)); 
                }  
            }
            else if (dir == orientation.RIGHT) {
                if(Allowed.gonnaExplode(updatedDir (dir), getY(), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setX(updatedDir (dir)); 
                } 
            }
            else if (dir == orientation.UP || dir == orientation.UP2) {
                if(Allowed.gonnaExplode(getX(), updatedDir (dir), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setY(updatedDir (dir)); 
                }  
            }
            else if (dir == orientation.DOWN) {
                if(Allowed.gonnaExplode(getX(), updatedDir (dir), getWidthRoket(), getHeightRoket(), owner)){
                    explosionHappening();
                }else{
                    setY(updatedDir (dir)); 
                }  
            }
        }
    }

    private void explosionHappening () {
        Entity damage = damage(dmgRadius);
        Allowed.applyDamage(Allowed.dealDamage(damage.getX(), damage.getY(), damage.getWidth(), damage.getHeight()), 1); //da risolvere TODO
        this.working = EnumEntity.status.DEAD;
    }

    // personalizzata per ogni proiettile
    private int updatedDir (orientation dir) {
        switch (dir) {
            case UP:
                return getY() - 5;
            case UP2:
                return getY() - 5;
            case DOWN:
                return getY() + 5;
            case RIGHT:
                return getX() + 5;
            case LEFT:
                return getX() - 5;
            default:
                return 0;
        }
    }
    

}
