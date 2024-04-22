package it.unibo.grubclash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;



 //creo hitbox del player
public class TestCollisioni {
    protected float x, y;
    protected int width, height;
    protected Rectangle hitBox; //l'hitbox è un rettangolo

    public void Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initHitbox(); //metodo per inizializzare gli hitbox
    }

    protected void drawHitbox(Graphics g) { //è qui solo per il debug degli hitbox
        g.setColor(Color.PINK);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    //    IMPORTANTE    nel game loop del player, sul metodo update() bisogna chiamare updateHitbox();

    private void initHitbox() {
        hitBox = new Rectangle((int) x, (int) y, width, height); //gli angoli del rettangolo
    }

    protected void updateHitbox() {
        hitBox.x = (int) x;
        hitBox.y = (int) y;
        //non serve fare l'update di width e height dato che rimangono uguali
    }

    public Rectangle getHitbox() {
        return hitBox;
    }


}
