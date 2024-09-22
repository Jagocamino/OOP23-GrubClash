package it.unibo.grubclash.model.Implementation;
import it.unibo.grubclash.model.Application_Programming_Interface.Life;
import it.unibo.grubclash.model.Implementation.LifeImpl;
import it.unibo.grubclash.view.Application_Programming_Interface.LifeDrawing;

import java.awt.*;
import java.util.Optional;

/**
 * Class implementing the Life methods.
 */
public class LifeImpl implements Life {

    private static final String color_game = "#EF7B10";
    private final Font snapITCFont;
    private final EntityImpl entity;

    //life of each player ranging from 10 to 0
    private int life;
    private static final int maxLife=10;
    private static final int minLife=0;
    private final int valueDamage=2;
    private LifeDrawing drawingStrategy;

    private static final int sizeFont = 24;

    public LifeImpl (EntityImpl entity,LifeDrawing drawingStrategy){

        this.life=10;
        this.entity=entity;
        this.drawingStrategy = drawingStrategy;
        //Font
        snapITCFont = new Font("Snap ITC", Font.BOLD, sizeFont);
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public void draw(Graphics2D g2d){
        
        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawingStrategy.drawLife(g2d, entity, life);
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public void setLifeValue (int life) {
        this.life = life;
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public Optional<Integer> getLifeValue(){
        return Optional.ofNullable(this.life);
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public void damage(){
        if(this.life > minLife){
            this.life -= valueDamage;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public void plusLife(){
        if(this.life < maxLife){
            this.life += valueDamage;
        }
    }
}
