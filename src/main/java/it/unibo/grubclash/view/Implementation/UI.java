package it.unibo.grubclash.view.Implementation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.grubclash.model.Implementation.GrubPanel;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;

public class UI {

    //FM creo il FrameManager visto che creando l'interfaccia non posso avere più i metodi statici
    private static final FrameManager frameManager = new FrameManagerImpl();

    String color_game = "#EF7B10";

    GrubPanel grubPanel;
    Graphics2D g2d;
    public Font snapITCFont;

    public UI(it.unibo.grubclash.model.Implementation.GrubPanel grubPanel2){

        this.grubPanel = grubPanel2;

        //Font
        snapITCFont = new Font("Snap ITC", Font.BOLD, 24);


    }

    public void draw(Graphics2D g2d){

        this.g2d = g2d;

        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawPlayerTurn();

    }

    private void drawPlayerTurn() {
        
        g2d.drawString("TURNO DEL GIOCATORE N\u00B0: " + grubPanel.numPlayerTurn, frameManager.getWindowWidth()/3, 45);
        g2d.drawRect(MapBuilder.getXMapBase(0, 19), MapBuilder.getYMapBase(0, 19), MapBuilder.getWidthMapBase(0,19), MapBuilder.getHeightMapBase(0,19));
        g2d.setFont(snapITCFont.deriveFont(30f));
        if(grubPanel.secondsTurn < 10){
            g2d.drawString("0" + grubPanel.secondsTurn, MapBuilder.getXMapBase(0, 19)+MapBuilder.getWidthMapBase(0,19)*2/7, MapBuilder.getYMapBase(0, 19)+MapBuilder.getHeightMapBase(0,19)*2/3);
        }else{
            g2d.drawString("" + grubPanel.secondsTurn, MapBuilder.getXMapBase(0, 19)+MapBuilder.getWidthMapBase(0,19)*2/7, MapBuilder.getYMapBase(0, 19)+MapBuilder.getHeightMapBase(0,19)*2/3);
        }
    }

}
