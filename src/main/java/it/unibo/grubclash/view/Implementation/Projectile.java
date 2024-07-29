package it.unibo.grubclash.view.Implementation;
import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.grubclash.model.Application_Programming_Interface.EntityInterface;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.view.Application_Programming_Interface.ProjectileInterface;

public abstract class Projectile extends Entity implements ProjectileInterface{

    public Projectile(int x, int y, int width, int height, Entities entity) {
        super(x, y, width, height, entity);
    }

    @Override
    public EntityInterface damage (int dmgRadius) { 
        int x = getX() + (getWidth() / 2);
        int y = getY() + (getHeight() / 2);
        return new Entity(x - dmgRadius, y - dmgRadius, dmgRadius*2, dmgRadius*2, Entities.EXPLOSION); //elimino ogni explosion dopo
    }

    @Override
    public void draw(Graphics2D g2d){

        if(this.working == EnumEntity.Status.ALIVE){
            g2d.setColor(Color.BLACK);
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

}
