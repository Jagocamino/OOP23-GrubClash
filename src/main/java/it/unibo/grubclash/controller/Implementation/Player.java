package it.unibo.grubclash.controller.Implementation;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.controller.Application_Programming_Interface.PlayerInterface;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.Sound;
import it.unibo.grubclash.model.Implementation.Weapon;
import it.unibo.grubclash.model.Implementation.EnumEntity.orientation;
import it.unibo.grubclash.model.Implementation.EnumEntity.status;
import it.unibo.grubclash.view.Implementation.LifeImpl;
import it.unibo.grubclash.view.Implementation.WeaponRoket;

public class Player extends Entity implements PlayerInterface{

    private final char FS = File.separatorChar;

    private KeyHandler keyH;

    private BufferedImage stand1, stand2, left1, left2, right1, right2, jump1, jump2, jump3, death;
    private BufferedImage shovelUp, shovelDown, shovelLeft, shovelRight;

    private int id;
    private int speed;
    private orientation direction;
    private Optional<Weapon> weapon;
    public boolean alreadyShot = false;
    public boolean alreadyDug = false;
    public boolean shovelAnimation = false;
    public int shovelCounter = 0;
    public boolean cooldownDig = false;

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
        weapon = Optional.of(new WeaponRoket(this));
        setEntity(EnumEntity.idToEntitiesConverter(id).get());
        Allowed.addDynamicEntity(this);
        getImage(id);
        getShovelImage(id);
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

    private void getShovelImage(int playerId){

        shovelDown = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_stand_1_shovel.png", this.width+13, 2 * (this.height+13));
        shovelUp = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_jump_shovel.png", this.width+13, this.height*2 + 2);
        shovelLeft = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_left_1_shovel.png", this.width + 25, this.height+13);
        shovelRight = setup("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player" + id + FS + "Grub_pl_" + id + "_right_1_shovel.png", 2 * (this.width+13), this.height+13);
    }

    @Override
    public void update() {

        if(this.isAlive()){
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
            
            if(keyH.spacePressed){  
                canMove = false;
                gravity=false;
                
                jump1Counter++;

                if(jump1Counter < 15){
                    direction = orientation.UP;
                }

                if(jump1Counter == 10){
                    Sound.setFile(4);
                    Sound.play();
                }
                
                if(jump1Counter > 15){
                    canMove = true;
                    if(jump1Counter == 16){
                        direction = orientation.UP2;
                    }
                    jump2Counter++;
                    if(jump2Counter < 15 && Allowed.CanMoveThere(x, y-30/jump2Counter, width, height)){
                        y-=30 / jump2Counter;
                    }
                    if(jump2Counter > 15 && jump2Counter < 30){ //  && Allowed.CanMoveThere(x, y+gravityAcceleration, width, height) 
                        if(jump2Counter == 16){
                            direction = orientation.DOWN;
                        } 
                        gravity = true;
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
            if(keyH.shootPressed && !alreadyShot){
                getWeapon().get().shoot();
                keyH.shootPressed = false;
                alreadyShot = true;
            }
            if(keyH.shovelPressed && !alreadyDug && !cooldownDig){
                shovelAnimation = true;
                cooldownDig = true;
                Sound.setFile(1);
                Sound.play();
                switch(this.direction){
                    case DOWN: 
                        if( !Allowed.meleeAttack(x, y + height, width, height, this).equals(Optional.empty()) || 
                            !Allowed.CanMoveThere(x, y + height, width, height)){
                            Allowed.applyDamage(Allowed.dealDamage(x, y + height, width, height), 1);
                            alreadyDug = true;
                        }
                        break;
                    case LEFT:
                        if( !Allowed.meleeAttack(x - width - 5, y, width, height, this).equals(Optional.empty()) || 
                            !Allowed.CanMoveThere(x - width - 5, y, width, height)){
                            Allowed.applyDamage(Allowed.dealDamage(x - width - 5, y, width, height), 1);
                            alreadyDug = true;
                        }
                        break;
                    case RIGHT:
                        if( !Allowed.meleeAttack(x + width + 5, y, width, height, this).equals(Optional.empty()) || 
                            !Allowed.CanMoveThere(x + width + 5, y, width, height)){
                            Allowed.applyDamage(Allowed.dealDamage(x + width + 5, y, width, height), 1);
                            alreadyDug = true;
                        }
                        break;
                    case UP:
                        if( !Allowed.meleeAttack(x, y - 25, width, height - 15, this).equals(Optional.empty()) || 
                            !Allowed.CanMoveThere(x, y - 25, width, height - 15)){
                            Allowed.applyDamage(Allowed.dealDamage(x, y - 25, width, height - 15), 1);
                            alreadyDug = true;
                        }
                        break;
                    case UP2:
                        if( !Allowed.meleeAttack(x, y - 25, width, height - 15, this).equals(Optional.empty()) || 
                            !Allowed.CanMoveThere(x, y - 25, width, height - 15)){
                            Allowed.applyDamage(Allowed.dealDamage(x, y - 25, width, height - 15), 1);
                            alreadyDug = true;
                        }
                        break;
                    default:
                        break;
                }
            }
            if(!keyH.leftPressed && !keyH.rightPressed && !keyH.spacePressed){
                direction = orientation.DOWN;
            }
            if(this.life.getLife().get() <= 0){
                this.working = status.DEAD;
            }
            if(shovelAnimation){
                shovelCounter++;
                if(shovelCounter > 10){
                    shovelCounter = 0;
                    shovelAnimation = false;
                    cooldownDig = false;
                }
            }
            weapon.get().setWeaponDir(direction);
        }
    }

    @Override
    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(Optional<Weapon> weapon) {
        this.weapon = weapon;
    }

    @Override
    public orientation getDirection() {
        return direction;
    }

    @Override
    public void setDirection(orientation direction) {
        this.direction = direction;
    }
    
    @Override
    public void draw(Graphics2D g2d){
        
        BufferedImage image = death;

        if(this.isAlive()){

            switch(direction){
                case LEFT:
                if(shovelAnimation){
                    image = shovelLeft;
                }else{
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                break;
                case RIGHT:
                if(shovelAnimation){
                    image = shovelRight;
                }else{
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                }
                break;
                case DOWN:
                if(shovelAnimation){
                    image = shovelDown;
                }else{
                    if(!isFalling){
                        if(spriteNum == 1){image = stand1;}
                        if(spriteNum == 2){image = stand2;}
                    }else{
                        image = jump3;
                    }
                }
                break;
                case UP:
                if(shovelAnimation){
                    image = shovelUp;
                }else{
                    image = jump1;
                }
                break;
                case UP2:
                if(shovelAnimation){
                    image = shovelUp;
                }else{
                    image = jump2;
                }
                break;
                }
                
                g2d.drawImage(image, x, y,null);

        }else{

            if(Allowed.getDynamicEntities().contains(this)){
                Allowed.removeDynamicEntity(this);
            }
            g2d.drawImage(image, x, y,null);
        }

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public KeyHandler getKeyH() {
        return keyH;
    }
}
