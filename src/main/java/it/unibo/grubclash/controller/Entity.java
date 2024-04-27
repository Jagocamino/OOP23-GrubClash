package it.unibo.grubclash.controller;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //con hitBox. esce una sfilza di cose utili
        initHitbox();
    }

    protected void drawHitbox(Graphics g) {
        //just for Debugging purpose
        g.setColor(Color.PINK);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    private void initHitbox() {
        hitbox = new Rectangle((int) x, (int) y, width, height);
    }

    public void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() { //è questa classe che fa l'update dell'hitbox
        return hitbox;
    }

}
