package it.unibo.grubclash.view.Implementation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.grubclash.controller.Implementation.GrubPanelImpl;
import it.unibo.grubclash.controller.Implementation.MapBuilderImpl;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;
import it.unibo.grubclash.view.Application_Programming_Interface.UIInterface;

public class UI implements UIInterface {

    private static final FrameManager frameManager = FrameManagerImpl.getInstance();

    private String color_game = "#EF7B10";

    private GrubPanelImpl grubPanel;
    private Graphics2D g2d;
    private Font snapITCFont;

    //Font
    private static final int FONT_SIZE = 24;

    //where the number of ammo is shown on the screen
    private static final int AMMO_X=50;
    private static final int AMMO_Y=50;

    public UI(GrubPanelImpl grubPanel2){

        this.grubPanel = grubPanel2;
        snapITCFont = new Font("Snap ITC", Font.BOLD, FONT_SIZE);


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2d){

        this.g2d = g2d;

        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawPlayerTurn();
        drawTurnBegin();
        drawAmmos();

    }


    /**
     * Draw the player's turn and the seconds of the turn
     */
    private void drawPlayerTurn() {
        
        g2d.drawString("TURNO DEL GIOCATORE N\u00B0: " + (grubPanel.getNumPlayerTurn() + 1) , frameManager.getWindowWidth().get()/3, 45);
        g2d.drawRect(MapBuilderImpl.getXMapBase(0, 19), MapBuilderImpl.getYMapBase(0, 19), MapBuilderImpl.getWidthMapBase(0,19), MapBuilderImpl.getHeightMapBase(0,19));
        g2d.setFont(snapITCFont.deriveFont(30f));
        if(grubPanel.getSecondsTurn() < 10){
            g2d.drawString("0" + grubPanel.getSecondsTurn(), MapBuilderImpl.getXMapBase(0, 19)+MapBuilderImpl.getWidthMapBase(0,19)*2/7, MapBuilderImpl.getYMapBase(0, 19)+MapBuilderImpl.getHeightMapBase(0,19)*2/3);
        }else{
            g2d.drawString("" + grubPanel.getSecondsTurn(), MapBuilderImpl.getXMapBase(0, 19)+MapBuilderImpl.getWidthMapBase(0,19)*2/7, MapBuilderImpl.getYMapBase(0, 19)+MapBuilderImpl.getHeightMapBase(0,19)*2/3);
        }
    }
    
    /**
     * He says that shift is about to start
     */
    private void drawTurnBegin(){

        if(grubPanel.isTurnBegin()){
            g2d.setFont(snapITCFont.deriveFont(40f));
            g2d.drawString("STA PER INIZIARE IL TURNO DI: " + (grubPanel.getNumPlayerTurn()+1), frameManager.getWindowWidth().get()/4, frameManager.getWindowHeight().get()/4);
        }
    }

    /**
     * Tells the number of ammo of the player on that turn
     */
    private void drawAmmos(){

        g2d.setFont(snapITCFont.deriveFont(25f));
        g2d.drawString("MUNIZIONI: " + grubPanel.getPlayers().get(grubPanel.getNumPlayerTurn()).getWeapon().get().getAmmo(), AMMO_X, AMMO_Y);
    }

}
