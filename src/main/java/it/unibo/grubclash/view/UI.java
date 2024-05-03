package it.unibo.grubclash.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import it.unibo.grubclash.model.GrubPanel;

public class UI {

    GrubPanel grubPanel;
    Graphics2D g2d;
    public Font snapITCFont;

    public UI(GrubPanel grubPanel){

        this.grubPanel = grubPanel;

        //Font
        snapITCFont = new Font("Snap ITC", Font.PLAIN, 24);


    }

    public void draw(Graphics2D g2d){

        this.g2d = g2d;

        g2d.setFont(snapITCFont);
        g2d.setColor(Color.ORANGE);

        drawPlayerTurn();

    }

    public void drawPlayerTurn() {
        
        g2d.drawString("Turno del giocatore:" + grubPanel.numPlayerTurn, 600, 50);
        g2d.drawString("" + grubPanel.secondsTurn, 600, 100);
    }

}
