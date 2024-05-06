package it.unibo.grubclash.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import it.unibo.grubclash.model.GrubPanel;
import it.unibo.grubclash.model.KeyHandler;
import it.unibo.grubclash.view.EnumEntity;
import it.unibo.grubclash.view.MapBuilder;

//   LA POSIZIONE DEL GIOCATORE VIENE PASSATA DOPO IL CONTROLLO NEL MAP BUILDER

public class Player extends Entity{

    final char FS = File.separatorChar;

    KeyHandler keyH;

    public BufferedImage stand1, stand2, left1, left2, right1, right2;

    private int id;
    private int x, y;
    private int speed;
    public String direction;

    public Player(GrubPanel grubPanel, int id, KeyHandler keyH) {

        super(grubPanel);

        this.keyH = keyH;

        this.id = id;
        this.x = EnumEntity.buttonToCoordsXConverter(MapBuilder.entityMatrix, EnumEntity.idToEntitiesConverter(id));
        System.out.println("coords playerX: " + x);
        this.y = EnumEntity.buttonToCoordsYConverter(MapBuilder.entityMatrix, EnumEntity.idToEntitiesConverter(id));
        System.out.println("coords playerY: " + y);
        this.speed = 2;
        this.direction = "down";

        getImage(id);
    }

    public void getImage(int playerId){ //parametro con numero del giocatore es : if(player == 1) => getImage del player1

        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_stand_1.png", 48, 48);
        stand2 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_stand_2.png", 48, 48);
        left1 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_left_1.png", 48, 48);
        left2 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_left_2.png", 48, 48);
        right1 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_right_1.png", 48, 48);
        right2 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_right_2.png", 48, 48);
    }


    public void update() {
        /*
        updatePos();
        updateHitbox();
        updateAnimationTick();
        setAnimation();
        */
        
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(keyH.leftPressed){
            direction = "left";
            x-=speed;
        } else if(keyH.rightPressed){
            direction = "right";
            x+=speed;
        } else{
            direction = "down";
        }
        
    }

    public void draw(Graphics2D g2d){
        
        BufferedImage image = null;

        switch(direction){
        case "left":
            if(spriteNum == 1){image = left1;}
            if(spriteNum == 2){image = left2;}
        break;
        case "right":
            if(spriteNum == 1){image = right1;}
            if(spriteNum == 2){image = right2;}
        break;
        case "down":
            if(spriteNum == 1){image = stand1;}
            if(spriteNum == 2){image = stand2;}
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
