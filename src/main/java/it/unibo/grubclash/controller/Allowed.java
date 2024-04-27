package it.unibo.grubclash.controller;

import javax.swing.JFrame;
import it.unibo.grubclash.view.MapBuilder;
import java.awt.Insets;
import it.unibo.grubclash.view.MapBuilder;



public class Allowed { 

    /*
    public static Insets giveInsetsBorder(JFrame map) { //da passare ad ALLOWED per quanto riguarda IsSolid --> ALLOWED viene chiamato qua dentro
        return map.getInsets();
    }
    da finire guida sulle collisioni, una volta risolto il problema dei player sul map builder
    */
    static int borderX;
    static int borderY;

    public Allowed(int borderX, int borderY) { //quando creo Allowed DENTRO MAP BUILDER chiamo il costruttore e gli passo i bordi del JFrame
        this.borderX = borderX;
        this.borderY = borderY;
    }

    public static boolean CanMoveThere(float x, float y, int width, int height, int[][] lvlData) {
        //se tutte le condizioni sono rispettate (se non ci sono quadranti occupati, mi posso muovere (c'è cielo, azzurro))
        if (!IsSolid(x, y, lvlData)) {
            if(!IsSolid(x + width, y + width, lvlData)) {
                if(!IsSolid(x + width, y, lvlData)) {
                    if(!IsSolid(x, y + height, lvlData))
                        return true;
                }
            }
        }
        return false;
    }
    
    //controlleremo di volta in volta i quattro angoli di questo "rettangolo"
    //x & y sono la posizione dei giocatori
    private static boolean IsSolid(float x, float y, int[][]  lvlData) { //da passare tramite giveInsetsBorder().bottom (per x) e right (per y) dentro MapBuilder
        
        //if violates borders
        if (x < 0 || x >= borderX) {
            return true;
        } 
        if (y < 0 || y >= borderY) {
            return true;
        }
            
        return false;
    }
}
