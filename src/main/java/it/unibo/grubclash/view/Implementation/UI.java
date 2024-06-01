package it.unibo.grubclash.view.Implementation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.grubclash.controller.Implementation.GrubPanel;
import it.unibo.grubclash.controller.Implementation.MapBuilder;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;
import it.unibo.grubclash.view.Application_Programming_Interface.UIInterface;

public class UI implements UIInterface {

    private static final FrameManager frameManager = new FrameManagerImpl();

    String color_game = "#EF7B10";

    GrubPanel grubPanel;
    Graphics2D g2d;
    public Font snapITCFont;

    public UI(GrubPanel grubPanel2){

        this.grubPanel = grubPanel2;

        //Font
        snapITCFont = new Font("Snap ITC", Font.BOLD, 24);


    }

    @Override
    public void draw(Graphics2D g2d){

        this.g2d = g2d;

        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawPlayerTurn();
        drawTurnBegin();
        drawAmmos();

    }

    private void drawPlayerTurn() {
        
        g2d.drawString("TURNO DEL GIOCATORE N\u00B0: " + (grubPanel.numPlayerTurn + 1) , frameManager.getWindowWidth().get()/3, 45);
        g2d.drawRect(MapBuilder.getXMapBase(0, 19), MapBuilder.getYMapBase(0, 19), MapBuilder.getWidthMapBase(0,19), MapBuilder.getHeightMapBase(0,19));
        g2d.setFont(snapITCFont.deriveFont(30f));
        if(grubPanel.secondsTurn < 10){
            g2d.drawString("0" + grubPanel.secondsTurn, MapBuilder.getXMapBase(0, 19)+MapBuilder.getWidthMapBase(0,19)*2/7, MapBuilder.getYMapBase(0, 19)+MapBuilder.getHeightMapBase(0,19)*2/3);
        }else{
            g2d.drawString("" + grubPanel.secondsTurn, MapBuilder.getXMapBase(0, 19)+MapBuilder.getWidthMapBase(0,19)*2/7, MapBuilder.getYMapBase(0, 19)+MapBuilder.getHeightMapBase(0,19)*2/3);
        }
    }
    
    private void drawTurnBegin(){

        if(grubPanel.turnBegin){
            g2d.setFont(snapITCFont.deriveFont(40f));
            g2d.drawString("STA PER INIZIARE IL TURNO DI: " + (grubPanel.numPlayerTurn + 1), frameManager.getWindowWidth().get()/4, frameManager.getWindowHeight().get()/4);
        }
    }

    private void drawAmmos(){

        g2d.setFont(snapITCFont.deriveFont(25f));
        g2d.drawString("MUNIZIONI: " + grubPanel.players.get(grubPanel.numPlayerTurn).getWeapon().get().getAmmo(), 50, 50);
    }

}
