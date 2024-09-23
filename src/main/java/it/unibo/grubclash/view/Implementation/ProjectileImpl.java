package it.unibo.grubclash.view.Implementation;
import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.grubclash.model.Application_Programming_Interface.Entity;
import it.unibo.grubclash.model.Implementation.EntityImpl;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.view.Application_Programming_Interface.Projectile;

public abstract class ProjectileImpl extends EntityImpl implements Projectile{

    public ProjectileImpl(int x, int y, int width, int height, Entities entity) {
        super(x, y, width, height, entity);
    }

    @Override
    public Entity damage (int dmgRadius) { 
        int x = getX() + (getWidth() / 2);
        int y = getY() + (getHeight() / 2);
        return new EntityImpl(x - dmgRadius, y - dmgRadius, dmgRadius*2, dmgRadius*2, Entities.EXPLOSION); 
    }

    @Override
    public void draw(Graphics2D g2d){

        if(isAlive()){
            g2d.setColor(Color.BLACK);
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

}
