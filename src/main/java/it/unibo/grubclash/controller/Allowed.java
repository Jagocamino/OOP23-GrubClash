package it.unibo.grubclash.controller;
import it.unibo.grubclash.view.EnumEntity;
import it.unibo.grubclash.view.EnumEntity.entities;
import javax.swing.*;

import java.awt.Color;
import java.util.ArrayList;

public class Allowed { 

    /*
    public static Insets giveInsetsBorder(JFrame map) { //da passare ad ALLOWED per quanto riguarda IsSolid --> ALLOWED viene chiamato qua dentro
        return map.getInsets();
    }
    da finire guida sulle collisioni, una volta risolto il problema dei player sul map builder
    */
    private static int borderX;
    private static int borderY;
    
    private static Entity[][] lvlData;
    public static Entity[][] getLvlData() {
        return lvlData;
    }
    public static void setLvlData(Entity[][] lvlData) {
        Allowed.lvlData = lvlData;
    }
    public static void switchBehaviourLvlData (int i, int j, entities entity) {
        Allowed.lvlData[i][j].setEntity(entity);
    }

    public static void delateSpawnpoint () {
        Entity[][] lvlData = getLvlData();
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (lvlData[i][j].getEntity() != entities.WALL && lvlData[i][j].getEntity() != entities.SKY && lvlData[i][j].getEntity() != entities.ITEM) {
                    switchBehaviourLvlData(i, j, entities.SKY);
                }
            }
        }
    }

    private static ArrayList<ArrayList<Object>> a = new ArrayList<ArrayList<Object>>();
    public static ArrayList<ArrayList<Object>> getA () {
        return a;
    }

    private static int ROWS;
    public static int getROWS() {
        return ROWS;
    }

    private static int COLS;
    public static int getCOLS() {
        return COLS;
    }

    /*
    addEntity mette nella matrice di entità una nuova entità, da fare controlli necessari affinché nuove entità non vadano in conflitto
    quando il costruttore di allowed viene chiamato, devo passargli anche le const relative all numero di celle, per rendere più flessibile il codice a future implementazioni
    */
    public entities getEntity (int i, int j) {
        return Allowed.lvlData[i][j].getEntity();
    }

    public Allowed(int borderX, int borderY, int ROWS, int COLS) { //quando creo Allowed DENTRO MAP BUILDER chiamo il costruttore e gli passo i bordi del JPanel che contiene il giochino e le righe e colonne totali
        Allowed.borderX = borderX;
        Allowed.borderY = borderY;
        Allowed.ROWS = ROWS;
        Allowed.COLS = COLS;
        Allowed.lvlData = new Entity[ROWS][COLS];
    }

    public static void addEntity (int x, int y, double height, double width, entities entity, int i, int j) {
        if (Allowed.lvlData[i][j]!=null && (Allowed.lvlData[i][j].getEntity() == entities.WALL || Allowed.lvlData[i][j].getEntity() == entities.ITEM)) {
            System.out.println("This box is already taken, overwriting floor or item on the map..");
        }
        System.out.println("prova");
        Allowed.lvlData[i][j] = new Entity((float) x, (float) y, width, height, entity);

    }    

    public static boolean CanMoveThere(int x, int y, int width, int height, entities[][] entityMatrix) {
        //controlla ogni angolo del rettangolo, se gli angoli non sono contenuti nel WALL (cioè la piattaforma o i bordi), allora restituisce true
        if (whatIsFacing(x, y) != entities.WALL) {
            if(whatIsFacing(x + width, y + width) != entities.WALL) {
                if(whatIsFacing(x + width, y) != entities.WALL) {
                    if(whatIsFacing(x, y + height) != entities.WALL)
                        return true;
                }
            }
        }
        return false;
    }

    public void addMapBase (JPanel[][] mapBase, EnumEntity.entities[][] entityMatrix) {
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                System.out.println(" x "+mapBase[i][j].getX()+ "y " + mapBase[i][j].getY()+ "Height" +mapBase[i][j].getBounds().getHeight()+ "Width" +mapBase[i][j].getBounds().getWidth()+ "entity" +entityMatrix[i][j]+ "i" +i+ "j" +j);
                addEntity(mapBase[i][j].getX(), mapBase[i][j].getY(), mapBase[i][j].getBounds().getHeight(), mapBase[i][j].getBounds().getWidth(), entityMatrix[i][j], i, j);
            }    
        }

        System.out.println("ciao");
    }

    public void mapDestroyer (JButton[][] mapBase, EnumEntity.entities[][] entityMatrix, int i, int j) {
        if (entityMatrix[i][j] == entities.WALL) {     
            mapBase[i][j].removeAll();
            mapBase[i][j].setBackground(Color.CYAN);
            mapBase[i][j].revalidate();
            mapBase[i][j].repaint();
            entityMatrix[i][j] = entities.SKY;
            lvlData[i][j].setEntity(entities.SKY);
        }else{
            System.out.println("Entity not wall didnt switch behaviour");
        }
    }
    
    //controlleremo di volta in volta i quattro angoli di questo "rettangolo"
    //x & y sono la posizione dei giocatori
    private static entities whatIsFacing(float x, float y) {
        //controlla, di quei punti dell'angolo che vengono passati, se è dentro un oggetto
        Entity[][] lvlData = getLvlData();
        if (x < 0 || x >= borderX) {
            return entities.WALL;
        } 
        if (y < 0 || y >= borderY) {
            return entities.WALL;
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (x >= lvlData[i][j].getX() && x < (lvlData[i][j].getWidth() + lvlData[i][j].getX()) && y >= lvlData[i][j].getX() && y < (lvlData[i][j].getY() + lvlData[i][j].getHeight()) ) {
                    return lvlData[i][j].getEntity(); //se le coordinate degli angoli si intersecano, ritorna il tipo di entità
                } 
            }

        }
        return entities.SKY;
    }
}
