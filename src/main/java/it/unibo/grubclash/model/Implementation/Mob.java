package it.unibo.grubclash.model.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;
import it.unibo.grubclash.model.Implementation.EnumEntity.Status;
import it.unibo.grubclash.view.Implementation.LifeDrawingMobImpl;

public class Mob extends Entity {

    private final char FS = File.separatorChar;

    private static final int DEFAULT_SPEED = 3;
    
    private Orientation direction;
    private int speed = DEFAULT_SPEED;

    private BufferedImage left1;
    private BufferedImage left2;
    private BufferedImage right1;
    private BufferedImage right2;
    private BufferedImage stand1;
    private BufferedImage stand2;
    private BufferedImage death;

    private static final int lifeValues = 2;

    public Mob(int x, int y, int width, int height, Entities entity) {
        super(x, y, width, height, entity);
        setCanMove(true);
        this.setLife(new LifeImpl(this, new LifeDrawingMobImpl()));
        this.getLife().setLifeValue(lifeValues);

        this.direction = Orientation.DOWN;
        getImage();

    }

    private void getImage() {
        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_down_1.png" , this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        stand2 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_down_2.png" , this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        left1 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_left_1.png" , this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        left2 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_left_2.png" , this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        right1 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_right_1.png" , this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        right2 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_right_2.png" , this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
        death = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_death.png", this.getWidth()+ADDITIONAL_PIXELS, this.getHeight()+ADDITIONAL_PIXELS);
    }

    public void update(){

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
            if(Allowed.CanMoveThere(getX() - speed, getY(), getWidth(), getHeight()) && !canMove()){
                direction = Orientation.LEFT;
                setX(getX() - speed);
                
            }
            else{
                direction = Orientation.DOWN;
                setCanMove(true);
            }
            if(Allowed.CanMoveThere(getX() + speed, getY(), getWidth(), getHeight()) && canMove()){
                direction = Orientation.RIGHT;
                setX(getX() + speed);
            }else {
                direction = Orientation.LEFT;
                setCanMove(false);
            }

            if(this.getLife().getLifeValue().get() <= RESET){
                setWorking(Status.DEAD);
            }
        }
    }

    public void draw(Graphics2D g2d){
        
        BufferedImage image = death;

        if(this.isAlive()){

            switch(direction){
                case LEFT:
                    if(getSpriteNum() == FIRST_ANIMATION){
                        image = left1;
                    }
                    if(getSpriteNum() == SECOND_ANIMATION){
                        image = left2;
                    }
                break;
                case RIGHT:
                    if(getSpriteNum() == FIRST_ANIMATION){
                        image = right1;
                    }
                    if(getSpriteNum() == SECOND_ANIMATION){
                        image = right2;
                    }
                break;
                case DOWN:
                    if(getSpriteNum() == FIRST_ANIMATION){
                        image = stand1;
                    }
                    if(getSpriteNum() == SECOND_ANIMATION){
                        image = stand2;
                    }
                break;
                default:
                    break;
            }
            g2d.drawImage(image, getX(), getY(),null);

        }
        else{
            this.getLife().setLifeValue(RESET);
            g2d.drawImage(image, getX(), getY(),null);

            if(Allowed.getDynamicEntities().contains(Optional.of(this))){
                Allowed.removeDynamicEntity(this);
            }
        }
        
    }

    public Orientation getDirection() {
        return direction;
    }

    public void setDirection(Orientation direction) {
        this.direction = direction;
    }
    
}
