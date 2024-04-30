package it.unibo.grubclash.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.grubclash.model.GrubPanel;
import it.unibo.grubclash.model.KeyHandler;

//   LA POSIZIONE DEL GIOCATORE VIENE PASSATA DOPO IL CONTROLLO NEL MAP BUILDER

public class Player extends Entity{

    KeyHandler keyH;

    public BufferedImage stand1, stand2, left1, left2, right1, right2;

    private int id;
    private int x, y;
    private int speed;
    private String direction;

    public Player(GrubPanel grubPanel, int id, KeyHandler keyH) {

        super(grubPanel);

        this.keyH = keyH;

        this.id = id;
        this.x = 100;
        this.y = 100;
        this.speed = 2;
        this.direction = "right";

        getImage();
    }

    public void getImage(){

        stand1 = setup("src\\main\\resources\\menu\\Grub.png", 48, 48);
        stand2 = setup("src\\main\\resources\\menu\\Grub.png", 48, 48);
        left1 = setup("src\\main\\resources\\menu\\Grub.png", 48, 48);
        left2 = setup("src\\main\\resources\\menu\\Grub.png", 48, 48);
        right1 = setup("src\\main\\resources\\menu\\Grub.png", 48, 48);
        right2 = setup("src\\main\\resources\\menu\\Grub.png", 48, 48);
    }


    public void update() {
        /*
        updatePos();
        updateHitbox();
        updateAnimationTick();
        setAnimation();
        */

        if(keyH.leftPressed){
            direction = "left";
            x-=speed;
            System.out.println(x);
        } else if(keyH.rightPressed){
            direction = "right";
            x+=speed;
            System.out.println(x);
        }
    }

    public void draw(Graphics2D g2d){
        
        BufferedImage image = null;

        switch(direction){
        case "left":
            image = left1;
        break;
        case "right":
            image = right1;
        break;
        }
        
        g2d.drawImage(image, x, y,null);

    }

    public void render() {
        //future implementation
        //costruisco l'hitbox sopra il player
        /*
        drawHitbox(g);
        */
    }

    public int getId() {
        return id;
    }

    public float getPosX() {
        return x;
    }

    public float getPosY() {
        return y;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void newPos(int x, int y) { //ogni volta che viene chiamata si passa una nuova posizione
        this.x = x;
        this.y = y;
    }


    //da settare le animazioni (o l'immagine)
    //initClasses prima del game loop

    private void initPlayers(int numerogiocatori) { //per ogni giocatore del menu a tendina, si crea un oggetto player
        Player[] playersarray = new Player[numerogiocatori];
        for (int i=0 ; i<numerogiocatori ; i++) { //per ogni giocatore segno la posizione iniziale
            playersarray[i].setId(i+1);
        }
    }        
}
