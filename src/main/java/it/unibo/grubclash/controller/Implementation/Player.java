package it.unibo.grubclash.controller.Implementation;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.controller.Application_Programming_Interface.PlayerInterface;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.LifeImpl;
import it.unibo.grubclash.model.Implementation.Sound;
import it.unibo.grubclash.model.Implementation.Weapon;
import it.unibo.grubclash.view.Implementation.LifeDrawingImpl;
import it.unibo.grubclash.view.Implementation.WeaponRoket;

/**
 * Class implementing the Player methods.
 */
public class Player extends Entity implements PlayerInterface{

    private static final char FS = File.separatorChar;
    private static final int RESET = 0;
    private static final int DEFAULT_SPEED = 3;
    private static final int ADDITIONAL_PIXELS = 13;
    private static final int ANIMATION_INTERVAL = 12;
    private static final int FIRST_ANIMATION = 1;
    private static final int SECOND_ANIMATION = 2;
    private static final int CHANGE_STAGE_JUMP = 15;
    private static final int SHOVEL_INTERVAL = 10;
    private static final int OFFSET = 5;
    private static final String ROOT = "src" + FS + "main" + FS + "resources" + FS + "players" + FS;

    private KeyHandler keyH;

    private BufferedImage stand1;
    private BufferedImage stand2;
    private BufferedImage left1;
    private BufferedImage left2;
    private BufferedImage right1;
    private BufferedImage right2;
    private BufferedImage jump1;
    private BufferedImage jump2;
    private BufferedImage jump3;
    private BufferedImage death;
    private BufferedImage shovelUp;
    private BufferedImage shovelDown;
    private BufferedImage shovelLeft;
    private BufferedImage shovelRight;

    private int id;
    private int speed;
    private Orientation direction;
    private Optional<Weapon> weapon;
    private boolean alreadyShot = false;
    private boolean alreadyDug = false;
    private boolean shovelAnimation = false;
    private int shovelCounter = RESET;
    private boolean cooldownDig = false;

    /**
    * Constructor for Player
    * @param id id of the player
    * @param keyH KeyHandler assigned to the player
    */
    public Player(int id, KeyHandler keyH) {
        super(EnumEntity.buttonToCoordsXConverter(MapBuilder.entityMatrix, EnumEntity.idToEntitiesConverter(id).get()), EnumEntity.buttonToCoordsYConverter(MapBuilder.entityMatrix, EnumEntity.idToEntitiesConverter(id).get()),
        35, 35,EnumEntity.idToEntitiesConverter(id).get());

        this.life= new LifeImpl(this,new LifeDrawingImpl());

        this.keyH = keyH;

        this.id = id;
        this.speed = DEFAULT_SPEED;
        this.direction = Orientation.DOWN;
        weapon = Optional.of(new WeaponRoket(this));
        Allowed.addDynamicEntity(Optional.of(this));
        getImage(id);
        getShovelImage(id);
    }

    /**
     * Links up BufferedImage variables with the correct images of the player, with all its movement animations
     * @param playerId
     */
    private void getImage(int playerId){ 

        stand1 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_1.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        stand2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_2.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        left1 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_left_1.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        left2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_left_2.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        right1 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_right_1.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        right2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_right_2.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        jump1 = setup(ROOT + "player0" + FS + "Grub_pl_0" + "_jump_1.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        jump2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_1.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        jump3 = setup(ROOT + "player0" + FS + "Grub_pl_0" + "_jump_2.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        death = setup(ROOT + "death.png", this.width+ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
    }

    /**
     * Links up BufferedImage variables with the correct images of the player, with all its digging animations
     * @param playerId
     */
    private void getShovelImage(int playerId){

        shovelDown = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_1_shovel.png", this.width+ADDITIONAL_PIXELS, 2 * (this.height+ADDITIONAL_PIXELS));
        shovelUp = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_jump_shovel.png", this.width+ADDITIONAL_PIXELS, this.height*2 + 2);
        shovelLeft = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_left_1_shovel.png", this.width + 2 * ADDITIONAL_PIXELS, this.height+ADDITIONAL_PIXELS);
        shovelRight = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_right_1_shovel.png", 2 * (this.width+ADDITIONAL_PIXELS), this.height+ADDITIONAL_PIXELS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {

        if(this.isAlive()){
            spriteCounter++;
            if(spriteCounter > ANIMATION_INTERVAL){
                if(spriteNum == FIRST_ANIMATION){
                    spriteNum = SECOND_ANIMATION;
                }
                else if(spriteNum == SECOND_ANIMATION){
                    spriteNum = FIRST_ANIMATION;
                }
                spriteCounter = RESET;
            }
            if(keyH.leftPressed && canMove){
                if(Allowed.CanMoveThere(x - speed, y, width, height)){
                    direction = Orientation.LEFT;
                    x-=speed;
                }
            }
            else if(keyH.rightPressed && canMove){
                if(Allowed.CanMoveThere(x + speed, y, width, height)){
                    direction = Orientation.RIGHT;
                    x+=speed;
                }
            }
            if(keyH.spacePressed){  
                jumpAnimation();
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
                shovelAttack(direction);
            }
            if(!keyH.leftPressed && !keyH.rightPressed && !keyH.spacePressed){
                direction = Orientation.DOWN;
            }
            if(shovelAnimation){
                shovelCounter++;
                if(shovelCounter > SHOVEL_INTERVAL){
                    shovelCounter = RESET;
                    shovelAnimation = false;
                    cooldownDig = false;
                }
            }
            if(Allowed.damageFromMob(this)){
                this.life.damage();
            }
            weapon.get().setWeaponDir(direction);
        }
    }

    /**
     * Manages the jump of the player with all animations and collision checker
     */
    private void jumpAnimation() {
        canMove = false;
        gravity=false;
                
        jump1Counter++;

        if(jump1Counter < CHANGE_STAGE_JUMP){
            direction = Orientation.UP;
        }

        if(jump1Counter == 10){
            Sound.setFile(4);
            Sound.play();
        }
                
        if(jump1Counter > CHANGE_STAGE_JUMP){
            canMove = true;
            if(jump1Counter == CHANGE_STAGE_JUMP + 1){
                direction = Orientation.UP2;
            }
            jump2Counter++;
            if(jump2Counter < CHANGE_STAGE_JUMP && Allowed.CanMoveThere(x, y-30/jump2Counter, width, height)){
                y -= 30 / jump2Counter;
            }
            if(jump2Counter > CHANGE_STAGE_JUMP && jump2Counter < CHANGE_STAGE_JUMP * 2){  
                if(jump2Counter == CHANGE_STAGE_JUMP + 1){
                    direction = Orientation.DOWN;
                } 
                gravity = true;
            }
            if(jump2Counter > CHANGE_STAGE_JUMP * 2){
                jump2Counter = RESET;
                jump1Counter = RESET;
                keyH.spacePressed = false;
            }
        }
    }

    /**
     * Checks if the shovel hits something then manages the interaction
     * @param direction orientation of the player
     */
    private void shovelAttack(Orientation direction){
        switch(direction){
            case DOWN: 
                if( !Allowed.meleeAttack(x, y + height, width, height, this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(x, y + height, width, height)){
                    Allowed.applyDamage(Allowed.dealDamage(x, y + height, width, height), 1);
                    alreadyDug = true;
                }
                break;
            case LEFT:
                if( !Allowed.meleeAttack(x - width - OFFSET, y, width, height, this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(x - width - OFFSET, y, width, height)){
                    Allowed.applyDamage(Allowed.dealDamage(x - width - OFFSET, y, width, height), 1);
                    alreadyDug = true;
                }
                break;
            case RIGHT:
                if( !Allowed.meleeAttack(x + width + OFFSET, y, width, height, this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(x + width + OFFSET, y, width, height)){
                    Allowed.applyDamage(Allowed.dealDamage(x + width + OFFSET, y, width, height), 1);
                    alreadyDug = true;
                }
                break;
            case UP:
                if( !Allowed.meleeAttack(x, y - OFFSET * OFFSET, width, height - OFFSET * 3, this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(x, y - OFFSET * OFFSET, width, height - OFFSET * 3)){
                    Allowed.applyDamage(Allowed.dealDamage(x, y - OFFSET * OFFSET, width, height - OFFSET * 3), 1);
                    alreadyDug = true;
                }
                break;
            case UP2:
                if( !Allowed.meleeAttack(x, y - OFFSET * OFFSET, width, height - OFFSET * 3, this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(x, y - OFFSET * OFFSET, width, height - OFFSET * 3)){
                    Allowed.applyDamage(Allowed.dealDamage(x, y - OFFSET * OFFSET, width, height - OFFSET * 3), 1);
                    alreadyDug = true;
                }
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWeapon(Optional<Weapon> weapon) {
        this.weapon = weapon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Orientation getDirection() {
        return direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection(Orientation direction) {
        this.direction = direction;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2d){
        
        BufferedImage image = death;

        if(this.isAlive()){

            switch(direction){
                case LEFT:
                if(shovelAnimation){
                    image = shovelLeft;
                }else{
                    if(spriteNum == FIRST_ANIMATION){
                        image = left1;
                    }
                    if(spriteNum == SECOND_ANIMATION){
                        image = left2;
                    }
                }
                break;
                case RIGHT:
                if(shovelAnimation){
                    image = shovelRight;
                }else{
                    if(spriteNum == FIRST_ANIMATION){
                        image = right1;
                    }
                    if(spriteNum == SECOND_ANIMATION){
                        image = right2;
                    }
                }
                break;
                case DOWN:
                if(shovelAnimation){
                    image = shovelDown;
                }else{
                    if(!isFalling){
                        if(spriteNum == FIRST_ANIMATION){
                            image = stand1;
                        }
                        if(spriteNum == SECOND_ANIMATION){
                            image = stand2;
                        }
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
            this.life.setLife(RESET);
            g2d.drawImage(image, x, y,null);

            if(Allowed.getDynamicEntities().contains(Optional.of(this))){
                Allowed.removeDynamicEntity(this);
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KeyHandler getKeyHandler() {
        return keyH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlreadyShot(boolean alreadyShot) {
        this.alreadyShot = alreadyShot;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlreadyDug(boolean alreadyDug) {
        this.alreadyDug = alreadyDug;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShovelAnimation(boolean shovelAnimation) {
        this.shovelAnimation = shovelAnimation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShovelCounter(int shovelCounter) {
        this.shovelCounter = shovelCounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCooldownDig(boolean cooldownDig) {
        this.cooldownDig = cooldownDig;
    }
}
