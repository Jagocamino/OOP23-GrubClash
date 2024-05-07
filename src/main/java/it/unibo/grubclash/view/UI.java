package it.unibo.grubclash.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.grubclash.model.GrubPanel;

public class UI {

    String color_game = "#EF7B10";

    GrubPanel grubPanel;
    Graphics2D g2d;
    public Font snapITCFont;

    public UI(GrubPanel grubPanel){

        this.grubPanel = grubPanel;

        //Font
        snapITCFont = new Font("Snap ITC", Font.BOLD, 24);


    }

    public void draw(Graphics2D g2d){

        this.g2d = g2d;

        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawPlayerTurn();

    }

    public void drawPlayerTurn() {
        
        g2d.drawString("TURNO DEL GIOCATORE N°: " + grubPanel.numPlayerTurn, FrameManager.WINDOW_WIDTH/3, 45);
        g2d.drawRect(MapBuilder.getXMapBase(0, 19), MapBuilder.getYMapBase(0, 19), MapBuilder.getWidthMapBase(0,19), MapBuilder.getHeightMapBase(0,19));
        g2d.setFont(snapITCFont.deriveFont(30f));
        if(grubPanel.secondsTurn < 10){
            g2d.drawString("0" + grubPanel.secondsTurn, MapBuilder.getXMapBase(0, 19)+20, MapBuilder.getYMapBase(0, 19)+35);
        }else{
            g2d.drawString("" + grubPanel.secondsTurn, MapBuilder.getXMapBase(0, 19)+28, MapBuilder.getYMapBase(0, 19)+35);
        }
    }

}
