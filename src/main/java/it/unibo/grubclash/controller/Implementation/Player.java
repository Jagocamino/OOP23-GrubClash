package it.unibo.grubclash.controller.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import it.unibo.grubclash.model.Implementation.GrubPanel;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.LifeImpl;
import it.unibo.grubclash.view.Implementation.MapBuilder;

//   LA POSIZIONE DEL GIOCATORE VIENE PASSATA DOPO IL CONTROLLO NEL MAP BUILDER

public class Player extends Entity{

    final char FS = File.separatorChar;
    public LifeImpl life;

    KeyHandler keyH;

    public BufferedImage stand1, stand2, left1, left2, right1, right2, jump1, jump2, jump3;

    private int id;
    public int x, y;
    public int width, height;
    public int speed;
    public String direction;
    public boolean canJump = true;

    public Player(GrubPanel grubPanel, int id, KeyHandler keyH) {

        super(grubPanel);

        this.life= new LifeImpl(grubPanel,this);

        this.keyH = keyH;

        this.id = id;
        this.x = EnumEntity.buttonToCoordsXConverter(MapBuilder.entityMatrix, EnumEntity.idToEntitiesConverter(id).get());
        this.y = EnumEntity.buttonToCoordsYConverter(MapBuilder.entityMatrix, EnumEntity.idToEntitiesConverter(id).get());
        this.width = 35;
        this.height = 35;
        this.speed = 3;
        this.direction = "down";
        setEntity(EnumEntity.idToEntitiesConverter(id).get());

        getImage(id);
    }

    private void getImage(int playerId){ //parametro con numero del giocatore es : if(player == 1) => getImage del player1

        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_stand_1.png", this.width+13, this.height+13);
        stand2 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_stand_2.png", this.width+13, this.height+13);
        left1 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_left_1.png", this.width+13, this.height+13);
        left2 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_left_2.png", this.width+13, this.height+13);
        right1 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_right_1.png", this.width+13, this.height+13);
        right2 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_right_2.png", this.width+13, this.height+13);
        jump1 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player0" + FS + "Grub_pl_0" + "_jump_1.png", this.width+13, this.height+13);
        jump2 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_stand_1.png", this.width+13, this.height+13);
        jump3 = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player0" + FS + "Grub_pl_0" + "_jump_2.png", this.width+13, this.height+13);
    }


    public void update() {
        
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

        // TODO @notnoted ziopera stiamo muovendo il player NON entity, dobbiamo muovere anche l'Entity del giocatore
        /*
            Non ci metto le mani altrimenti Pianini mi uccide, aggiorna con i setter appena messi su Entity, se non si muovono insieme naturalmente non sono registrate le collisioni 
        */
        if(keyH.leftPressed && canMove){
            if(Allowed.CanMoveThere(x - speed, y, width, height)){
                direction = "left";
                x-=speed;
            }
        }
        else if(keyH.rightPressed && canMove){
            if(Allowed.CanMoveThere(x + speed, y, width, height)){
                direction = "right";
                x+=speed;
            }
        }
        //TODO togliere la possibilità di saltare mentre si è in aria (bugga anche la fisica(accelerazione di gravità continua ad aumentare))
        if(keyH.spacePressed){  //aggiungi "&& canJump" per effetto palleggio
            direction = "up";
            canMove = false;
            gravity=false;
            
            jump1Counter++;
            
            if(jump1Counter > 15){
                canMove = true;
                direction = "up2";
                jump2Counter++;
                if(jump2Counter < 15 && Allowed.CanMoveThere(x, y-30/jump2Counter, width, height)){
                    y-=30 / jump2Counter;
                }
                if(jump2Counter > 15 && jump2Counter < 30){ //  && Allowed.CanMoveThere(x, y+gravityAcceleration, width, height)  
                    direction = "down";
                    gravity = true;
                    //y+=jump2Counter/8;
                }
                if(jump2Counter > 30){
                    jump2Counter = 0;
                    jump1Counter = 0;
                    keyH.spacePressed = false;
                }
            }
        }else{
            gravity=true;
        }
        if(!keyH.leftPressed && !keyH.rightPressed && !keyH.spacePressed){
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
        case "up":
            image = jump1;
        break;
        case "up2":
            if(keyH.leftPressed){image = jump3;}
            else {image = jump2;}
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
}
