package it.unibo.grubclash.controller.Implementation;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.controller.Application_Programming_Interface.Player;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.EntityImpl;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.LifeImpl;
import it.unibo.grubclash.model.Implementation.Sound;
import it.unibo.grubclash.model.Implementation.WeaponImpl;
import it.unibo.grubclash.view.Implementation.LifeDrawingImpl;
import it.unibo.grubclash.view.Implementation.WeaponRoket;

/**
 * Class implementing the Player methods.
 */
public class PlayerImpl extends EntityImpl implements Player{

    private static final char FS = File.separatorChar;
    private static final int DEFAULT_SPEED = 3;
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
    private Optional<WeaponImpl> weapon;
    private boolean alreadyShot = false;
    private boolean alreadyDug = false;
    private boolean shovelAnimation = false;
    private int shovelCounter = RESET;
    private boolean cooldownDig = false;
    private int jump1Counter = RESET;
    private int jump2Counter = RESET;

    /**
    * Constructor for Player
    * @param id id of the player
    * @param keyH KeyHandler assigned to the player
    */
    public PlayerImpl(int id, KeyHandler keyH) {
        super(EnumEntity.buttonToCoordsXConverter(MapBuilderImpl.entityMatrix, EnumEntity.idToEntitiesConverter(id).get()), EnumEntity.buttonToCoordsYConverter(MapBuilderImpl.entityMatrix, EnumEntity.idToEntitiesConverter(id).get()),
        35, 35,EnumEntity.idToEntitiesConverter(id).get());

        this.setLife(new LifeImpl(this,new LifeDrawingImpl()));

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

        stand1 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_1.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        stand2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_2.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        left1 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_left_1.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        left2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_left_2.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        right1 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_right_1.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        right2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_right_2.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        jump1 = setup(ROOT + "player0" + FS + "Grub_pl_0" + "_jump_1.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        jump2 = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_1.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        jump3 = setup(ROOT + "player0" + FS + "Grub_pl_0" + "_jump_2.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        death = setup(ROOT + "death.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
    }

    /**
     * Links up BufferedImage variables with the correct images of the player, with all its digging animations
     * @param playerId
     */
    private void getShovelImage(int playerId){

        shovelDown = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_stand_1_shovel.png", this.getWidth()+ADDITIONAL_PIXELS, 2 * (this.getHeight()+ADDITIONAL_PIXELS));
        shovelUp = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_jump_shovel.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()*2 + 2);
        shovelLeft = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_left_1_shovel.png", this.getWidth() + 2 * ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        shovelRight = setup(ROOT + "player" + id + FS + "Grub_pl_" + id + "_right_1_shovel.png", 2 * (this.getWidth()+ADDITIONAL_PIXELS), this.getHeight()+ADDITIONAL_PIXELS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {

        if(this.isAlive()){
            setSpriteCounter(getSpriteCounter() + 1);
            if(getSpriteCounter() > ANIMATION_INTERVAL){
                if(getSpriteNum() == FIRST_ANIMATION){
                    setSpriteNum(SECOND_ANIMATION);
                }
                else if(getSpriteNum() == SECOND_ANIMATION){
                    setSpriteNum(FIRST_ANIMATION);
                }
                setSpriteCounter(RESET);
            }
            if(keyH.isLeftPressed() && canMove()){
                if(Allowed.CanMoveThere(getX() - speed, getY(), getWidth(), getHeight())){
                    direction = Orientation.LEFT;
                    setX(getX() - speed);
                }
            }
            else if(keyH.isRightPressed() && canMove()){
                if(Allowed.CanMoveThere(getX() + speed, getY(), getWidth(), getHeight())){
                    direction = Orientation.RIGHT;
                    setX(getX() + speed);
                }
            }
            if(keyH.isSpacePressed()){  
                jumpAnimation();
            }else{
                setGravity(true);
            }
            if(keyH.isShootPressed() && !alreadyShot){
                getWeapon().get().shoot();
                keyH.setShootPressed(false);
                alreadyShot = true;
            }
            if(keyH.isShovelPressed() && !alreadyDug && !cooldownDig){
                shovelAnimation = true;
                cooldownDig = true;
                Sound.setFile(1);
                Sound.play();
                shovelAttack(direction);
            }
            if(!keyH.isLeftPressed() && !keyH.isRightPressed() && !keyH.isSpacePressed()){
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
            if(Allowed.damageFromSkeleton(this)){
                this.getLife().damage();
            }
            if(Allowed.damageFromZombie(this)){
                this.getLife().damage();
            }
            weapon.get().setWeaponDir(direction);
        }
    }

    /**
     * Manages the jump of the player with all animations and collision checker
     */
    private void jumpAnimation() {
        setCanMove(false);
        setGravity(false);
                
        jump1Counter++;

        if(jump1Counter < CHANGE_STAGE_JUMP){
            direction = Orientation.UP;
        }

        if(jump1Counter == 10){
            Sound.setFile(4);
            Sound.play();
        }
                
        if(jump1Counter > CHANGE_STAGE_JUMP){
            setCanMove(true);
            if(jump1Counter == CHANGE_STAGE_JUMP + 1){
                direction = Orientation.UP2;
            }
            jump2Counter++;
            if(jump2Counter < CHANGE_STAGE_JUMP && Allowed.CanMoveThere(getX(), getY()-30/jump2Counter, getWidth(), getHeight())){
                setY(getY() - 30 / jump2Counter);
            }
            if(jump2Counter > CHANGE_STAGE_JUMP && jump2Counter < CHANGE_STAGE_JUMP * 2){  
                if(jump2Counter == CHANGE_STAGE_JUMP + 1){
                    direction = Orientation.DOWN;
                } 
                setGravity(true);
            }
            if(jump2Counter > CHANGE_STAGE_JUMP * 2){
                jump2Counter = RESET;
                jump1Counter = RESET;
                keyH.setSpacePressed(false);
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
                if( !Allowed.meleeAttack(getX(), getY() + getHeight(), getWidth(), getHeight(), this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(getX(), getY() + getHeight(), getWidth(), getHeight())){
                    Allowed.applyDamage(Allowed.dealDamage(getX(), getY() + getHeight(), getWidth(), getHeight()), 1);
                    alreadyDug = true;
                }
                break;
            case LEFT:
                if( !Allowed.meleeAttack(getX() - getWidth() - OFFSET, getY(), getWidth(), getHeight(), this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(getX() - getWidth() - OFFSET, getY(), getWidth(), getHeight())){
                    Allowed.applyDamage(Allowed.dealDamage(getX() - getWidth() - OFFSET, getY(), getWidth(), getHeight()), 1);
                    alreadyDug = true;
                }
                break;
            case RIGHT:
                if( !Allowed.meleeAttack(getX() + getWidth() + OFFSET, getY(), getWidth(), getHeight(), this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(getX() + getWidth() + OFFSET, getY(), getWidth(), getHeight())){
                    Allowed.applyDamage(Allowed.dealDamage(getX() + getWidth() + OFFSET, getY(), getWidth(), getHeight()), 1);
                    alreadyDug = true;
                }
                break;
            case UP:
                if( !Allowed.meleeAttack(getX(), getY() - OFFSET * OFFSET, getWidth(), getHeight() - OFFSET * 3, this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(getX(), getY() - OFFSET * OFFSET, getWidth(), getHeight() - OFFSET * 3)){
                    Allowed.applyDamage(Allowed.dealDamage(getX(), getY() - OFFSET * OFFSET, getWidth(), getHeight() - OFFSET * 3), 1);
                    alreadyDug = true;
                }
                break;
            case UP2:
                if( !Allowed.meleeAttack(getX(), getY() - OFFSET * OFFSET, getWidth(), getHeight() - OFFSET * 3, this).equals(Optional.empty()) || 
                    !Allowed.CanMoveThere(getX(), getY() - OFFSET * OFFSET, getWidth(), getHeight() - OFFSET * 3)){
                    Allowed.applyDamage(Allowed.dealDamage(getX(), getY() - OFFSET * OFFSET, getWidth(), getHeight() - OFFSET * 3), 1);
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
    public Optional<WeaponImpl> getWeapon() {
        return weapon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWeapon(Optional<WeaponImpl> weapon) {
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
                    if(getSpriteNum() == FIRST_ANIMATION){
                        image = left1;
                    }
                    if(getSpriteNum() == SECOND_ANIMATION){
                        image = left2;
                    }
                }
                break;
                case RIGHT:
                if(shovelAnimation){
                    image = shovelRight;
                }else{
                    if(getSpriteNum() == FIRST_ANIMATION){
                        image = right1;
                    }
                    if(getSpriteNum() == SECOND_ANIMATION){
                        image = right2;
                    }
                }
                break;
                case DOWN:
                if(shovelAnimation){
                    image = shovelDown;
                }else{
                    if(!isFalling()){
                        if(getSpriteNum() == FIRST_ANIMATION){
                            image = stand1;
                        }
                        if(getSpriteNum() == SECOND_ANIMATION){
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
                
                g2d.drawImage(image, getX(), getY(),null);

        }else{
            this.getLife().setLifeValue(RESET);
            g2d.drawImage(image, getX(), getY(),null);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setJump1Counter(int jump1Counter) {
        this.jump1Counter = jump1Counter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setJump2Counter(int jump2Counter) {
        this.jump2Counter = jump2Counter;
    }
}
