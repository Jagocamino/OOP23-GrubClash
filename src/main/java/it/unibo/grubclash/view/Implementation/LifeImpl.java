package it.unibo.grubclash.view.Implementation;
import it.unibo.grubclash.controller.Implementation.GrubPanel;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.view.Application_Programming_Interface.Life;
import it.unibo.grubclash.view.Implementation.LifeImpl;

import java.awt.*;
import java.util.Optional;

public class LifeImpl implements Life {

    private String color_game = "#EF7B10";
    private final Font snapITCFont;
    private final Player player;

    //vita di ogni giocatore che va da 10 a 0
    private int life;
    private final int value=2;
    private LifeDrawingImpl drawingStrategy;

    public LifeImpl (GrubPanel grubPanel,Player player,LifeDrawingImpl drawingStrategy){

        this.life=10;
        this.player=player;
        this.drawingStrategy = drawingStrategy;
        //Font
        snapITCFont = new Font("Snap ITC", Font.BOLD, 24);
    }

    @Override 
    public void draw(Graphics2D g2d){
        
        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawingStrategy.drawLife(g2d, player, life);
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
        if(this.life > 0){
            this.life -= value;
        }
    }

    @Override 
    public void plusLife(){
        if(this.life < 10){
            this.life += value;
        }
    }
}
