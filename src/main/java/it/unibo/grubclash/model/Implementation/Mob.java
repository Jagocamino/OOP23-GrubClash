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

    private static final int speed = 3;
    private Orientation direction;

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
        canMove=true;
        this.life = new LifeImpl(this, new LifeDrawingMobImpl());
        this.life.setLife(lifeValues);

        this.direction = Orientation.DOWN;
        getImage();

    }

    private void getImage() {
        stand1 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_down_1.png" , this.width+13, this.height+13);
        stand2 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_down_2.png" , this.width+13, this.height+13);
        left1 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_left_1.png" , this.width+13, this.height+13);
        left2 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_left_2.png" , this.width+13, this.height+13);
        right1 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_right_1.png" , this.width+13, this.height+13);
        right2 = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_right_2.png" , this.width+13, this.height+13);
        death = setup("src" + FS + "main" + FS + "resources" + FS + "mob" + FS + "Mob_death.png", this.width+13, this.height+13);
    }

    public void update(){

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
            if(Allowed.CanMoveThere(x - speed, y, width, height) && !canMove){
                direction = Orientation.LEFT;
                x-=speed;
                
            }
            else{
                direction = Orientation.DOWN;
                canMove=true;
            }
            if(Allowed.CanMoveThere(x + speed, y, width, height) && canMove){
                direction = Orientation.RIGHT;
                x+=speed;
            }else {
                direction = Orientation.LEFT;
                canMove=false;
            }

            if(this.life.getLife().get() <= 0){
                working=Status.DEAD;
            }
        }
    }

    public void draw(Graphics2D g2d){
        
        BufferedImage image = death;

        if(this.isAlive()){

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
                default:
                    break;
            }
            g2d.drawImage(image, x, y,null);

        }
        else{
            this.life.setLife(0);
            g2d.drawImage(image, x, y,null);

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
