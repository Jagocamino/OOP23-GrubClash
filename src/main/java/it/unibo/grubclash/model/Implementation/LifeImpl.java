package it.unibo.grubclash.model.Implementation;
import it.unibo.grubclash.model.Application_Programming_Interface.Life;
import it.unibo.grubclash.model.Implementation.LifeImpl;
import it.unibo.grubclash.view.Application_Programming_Interface.LifeDrawing;

import java.awt.*;
import java.util.Optional;

public class LifeImpl implements Life {

    private String color_game = "#EF7B10";
    private final Font snapITCFont;
    private final Entity entity;

    //vita di ogni giocatore che va da 10 a 0
    private int life;
    private static final int maxLife=10;
    private static final int minLife=0;
    private final int valueDamage=2;
    private LifeDrawing drawingStrategy;

    private static final int sizeFont = 24;

    public LifeImpl (Entity entity,LifeDrawing drawingStrategy){

        this.life=10;
        this.entity=entity;
        this.drawingStrategy = drawingStrategy;
        //Font
        snapITCFont = new Font("Snap ITC", Font.BOLD, sizeFont);
    }

    @Override 
    public void draw(Graphics2D g2d){
        
        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawingStrategy.drawLife(g2d, entity, life);
    }


    @Override 
    public void setLife (int life) {
        this.life = life;
    }

    @Override 
    public Optional<Integer> getLife(){
        return Optional.ofNullable(this.life);
    }

    @Override 
    public void damage(){
        if(this.life > minLife){
            this.life -= valueDamage;
        }
    }

    @Override 
    public void plusLife(){
        if(this.life < maxLife){
            this.life += valueDamage;
        }
    }
}
