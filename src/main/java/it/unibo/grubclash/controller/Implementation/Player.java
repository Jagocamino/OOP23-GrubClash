package it.unibo.grubclash.controller.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.controller.Weapon;
import it.unibo.grubclash.model.Implementation.GrubPanel;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.LifeImpl;
import it.unibo.grubclash.view.Implementation.MapBuilder;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;

//   LA POSIZIONE DEL GIOCATORE VIENE PASSATA DOPO IL CONTROLLO NEL MAP BUILDER

public class Player extends Entity{

    final char FS = File.separatorChar;
    public LifeImpl life;

    KeyHandler keyH;

    public BufferedImage stand1, stand2, left1, left2, right1, right2, jump1, jump2, jump3, death;

    private int id;
    public int speed;
    private orientation direction;
    private Optional<Weapon> weapon;

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
        this.direction = orientation.DOWN;
        setEntity(EnumEntity.idToEntitiesConverter(id).get());
        Allowed.addDynamicEntity(this);
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
        death = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "death.png", this.width+13, this.height+13);
    }


    public void update() {

        if(this.alive){
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

            //controlla se danneggiato da una trappola
            touchTrap();

            //controlla se si cura con un heal
            touchHeal();

            // TODO @notnoted ziopera stiamo muovendo il player NON entity, dobbiamo muovere anche l'Entity del giocatore
            /*
                Non ci metto le mani altrimenti Pianini mi uccide, aggiorna con i setter appena messi su Entity, se non si muovono insieme naturalmente non sono registrate le collisioni 
            */
            if(keyH.leftPressed && canMove){
                if(Allowed.CanMoveThere(x - speed, y, width, height)){
                    direction = orientation.LEFT;
                    x-=speed;
                }
            }
            else if(keyH.rightPressed && canMove){
                if(Allowed.CanMoveThere(x + speed, y, width, height)){
                    direction = orientation.RIGHT;
                    x+=speed;
                }
            }



            //TODO togliere la possibilità di saltare mentre si è in aria (bugga anche la fisica(accelerazione di gravità continua ad aumentare))
            if(keyH.spacePressed){  //aggiungi "&& canJump" per effetto palleggio
                direction = orientation.UP;
                canMove = false;
                gravity=false;
                
                jump1Counter++;
                
                if(jump1Counter > 15){
                    canMove = true;
                    direction = orientation.UP2;
                    jump2Counter++;
                    if(jump2Counter < 15 && Allowed.CanMoveThere(x, y-30/jump2Counter, width, height)){
                        y-=30 / jump2Counter;
                    }
                    if(jump2Counter > 15 && jump2Counter < 30){ //  && Allowed.CanMoveThere(x, y+gravityAcceleration, width, height)  
                        direction = orientation.DOWN;
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
                direction = orientation.DOWN;
            }
            if(this.life.getLife().get() == 0){
                this.alive = false;
            }
        }else{
            Allowed.removeDynamicEntity(this);
        }
    }

    public void touchTrap(){

        for (Trap t : grubPanel.traps) {
            if(t.alive && this.x + this.width/2 > t.getX() && this.x + this.width/2 < t.getX() + t.getWidth()
            && this.y + this.height/2 > t.getY() && this.y + this.height/2 < t.getY() + t.getHeight()){
                this.life.damage();
                t.alive = false;
            }
        }
    }
    public void touchHeal(){

        for (Heal h : grubPanel.heals) {
            if(h.alive && this.x + this.width/2 > h.getX() && this.x + this.width/2 < h.getX() + h.getWidth()
            && this.y + this.height/2 > h.getY() && this.y + this.height/2 < h.getY() + h.getHeight()){
                this.life.plusLife();
                h.alive = false;
            }
        }
    }

    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    public void setWeapon(Optional<Weapon> weapon) {
        this.weapon = weapon;
    }

    public orientation getDirection() {
        return direction;
    }

    public void setDirection(orientation direction) {
        this.direction = direction;
    }

    public void draw(Graphics2D g2d){
        
        BufferedImage image = death;

        if(this.alive){

            switch(direction){
                case LEFT:
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                break;
                case RIGHT:
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                break;
                case DOWN:
                    if(spriteNum == 1){image = stand1;}
                    if(spriteNum == 2){image = stand2;}
                break;
                case UP:
                    image = jump1;
                break;
                case UP2:
                    if(keyH.leftPressed){image = jump3;}
                    else {image = jump2;}
                break;
                }
                
                g2d.drawImage(image, x, y,null);

        }else{

            g2d.drawImage(image, x, y,null);
        }

    }

    public int getId() {
        return id;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public void setId( int id ) {
        this.id = id;
    }




}
