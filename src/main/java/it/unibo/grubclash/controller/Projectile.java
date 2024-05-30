package it.unibo.grubclash.controller;
import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.grubclash.controller.Implementation.Entity;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;

public abstract class Projectile extends Entity{

    public Projectile(int x, int y, int width, int height, entities entity) {
        super(x, y, width, height, entity);
    }

    public Entity damage (int dmgRadius) { 
        int x = getX() + (getWidth() / 2);
        int y = getY() + (getHeight() / 2);
        return new Entity(x - dmgRadius, y - dmgRadius, dmgRadius*2, dmgRadius*2, entities.EXPLOSION); //elimino ogni explosion dopo
    }

    @Override
    public void draw(Graphics2D g2d){

        if(this.working == EnumEntity.status.ALIVE){
            g2d.setColor(Color.BLACK);
            g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

}
