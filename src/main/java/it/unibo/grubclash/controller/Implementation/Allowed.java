package it.unibo.grubclash.controller.Implementation;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
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
    
    public static Entity[][] lvlData; // da cambiare in private
    public static Entity[][] getLvlData() {
        return Allowed.lvlData;
    }
    public static Entity getLvlData( int i, int j) {
        return Allowed.lvlData[i][j];
    }
    public static void setLvlData(Entity[][] lvlData) {
        Allowed.lvlData = lvlData;
    }
    public static void switchBehaviourLvlData (int i, int j,entities entity) {
        Allowed.lvlData[i][j].setEntity(entity);
    }

    private static ArrayList<Entity> dynamicEntities;
    public static void addDynamicEntity (Entity dynamicEntity) {
        Allowed.dynamicEntities.add(dynamicEntity);
    }
    public static void removeDynamicEntity (Entity dynamicEntity) {
        Allowed.dynamicEntities.remove(dynamicEntity);
    }


    public static void delateSpawnpoint () { //prima di entrare qui è già esploso
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (Allowed.lvlData[i][j].getEntity() == entities.PLAYER1 || Allowed.lvlData[i][j].getEntity() != entities.PLAYER2 || Allowed.lvlData[i][j].getEntity() != entities.PLAYER3 || Allowed.lvlData[i][j].getEntity() != entities.PLAYER4 || Allowed.lvlData[i][j].getEntity() != entities.PLAYER5 ) {
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
        return Allowed.ROWS;
    }

    private static int COLS;
    public static int getCOLS() {
        return Allowed.COLS;
    }

    /*
    addEntity mette nella matrice di entità una nuova entità, da fare controlli necessari affinché nuove entità non vadano in conflitto
    quando il costruttore di allowed viene chiamato, devo passargli anche le const relative all numero di celle, per rendere più flessibile il codice a future implementazioni
    */
    public static entities getEntity (int i, int j) {
        return Allowed.lvlData[i][j].getEntity();
    }

    public Allowed(int borderX, int borderY, int ROWS, int COLS) { //quando creo Allowed DENTRO MAP BUILDER chiamo il costruttore e gli passo i bordi del JPanel che contiene il giochino e le righe e colonne totali
        Allowed.borderX = borderX;
        Allowed.borderY = borderY;
        Allowed.ROWS = ROWS;
        Allowed.COLS = COLS;
        Allowed.lvlData = new Entity[ROWS][COLS];
        Allowed.dynamicEntities = new ArrayList<Entity>();
    }

    public static void addEntity (int x, int y, int width, int height, entities entity, int i, int j) {
        if (lvlData[i][j] != null && (lvlData[i][j].getEntity() == entities.WALL || lvlData[i][j].getEntity() == entities.ITEM)) {
            System.out.println("This box is already taken, overwriting floor or item on the map..");
        }
        Allowed.lvlData[i][j] = new Entity(x, y, width, height, entity);
    }

    public static boolean CanMoveThere(int x, int y, int width, int height) {
        //controlla ogni angolo del rettangolo, se gli angoli non sono contenuti nel WALL (cioè la piattaforma o i bordi), allora restituisce true
        if (whatIsFacing(x+5, y+5) != entities.WALL) {
            if(whatIsFacing(x + width, y + height+2) != entities.WALL) {
                if(whatIsFacing(x + width, y+5) != entities.WALL) {
                    if(whatIsFacing(x+5, y + height+2) != entities.WALL)
                        return true;
                }
            }
        }
        return false;
    }

    //TODO DA FARE ASSOLUTAMENTE APPENA TI SVEGLI, COGLIONE ritorna il primo muro che si trova dentro l'hitbox messa da parametro nel metodo, da completare
    public static Entity whatLvlDataWallInside (int x, int y, int width, int height) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if(x >= Allowed.lvlData[i][j].getX() && x <= (Allowed.lvlData[i][j].getX() + Allowed.lvlData[i][j].getWidth()) && 
                        y >= Allowed.lvlData[i][j].getY() && y <= (Allowed.lvlData[i][j].getY() + Allowed.lvlData[i][j].getHeight())){
                    return Allowed.lvlData[i][j];
                }
            }
        }
        return null; // TODO da aggiungere il return dell'optional, NON null
    }

    public static void addMapBase (JPanel[][] mapBase, EnumEntity.entities[][] entityMatrix) { //sano dopo essere uscito da qui
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                addEntity(mapBase[i][j].getX(), mapBase[i][j].getY(), (int)mapBase[i][j].getBounds().getWidth(), (int)mapBase[i][j].getBounds().getHeight(), entityMatrix[i][j], i, j);
            }    //TODO addEntity popola lvlData di copie delle ultime entità che sono state aggiunte, ecco perché lvlData printa sempre gli stessi valori 
        }
    }

    // TODO entityMatrix serve SOLO per inizializzare lvlData, poi NON DEVE più essere usato
    public void mapDestroyer (JButton[][] mapBase, int i, int j) {
        if (getLvlData(i, j).getEntity() == entities.WALL) {     
            mapBase[i][j].removeAll();
            mapBase[i][j].setBackground(Color.CYAN);
            mapBase[i][j].revalidate();
            mapBase[i][j].repaint();
            Allowed.lvlData[i][j].setEntity(entities.SKY);
        }else{
            System.out.println("Entity not wall didnt switch behaviour");
        }
    }

    // TODO mapdestroyer DEVE spaccare l'entità da finire
    public void mapDestroyer (JButton[][] mapBase, Entity wall) {
        int i = getI (wall);
        int j = getJ (wall);
        mapDestroyer(mapBase, i, j);
    }

    // METTERE METODO CHE RITORNA i, j DATA l'entità del muro
    public int getI (Entity entity) {

        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (Allowed.lvlData[i][j] == entity ) {
                    return i;
                }
            }
        }
        return 0; // ritorna sempre 0 TODO dovremmo aggiuntere il return di Optional
    }

    public int getJ (Entity entity) {

        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (Allowed.lvlData[i][j] == entity ) {
                    return j;
                }
            }
        }
        return 0; // ritorna sempre 0 TODO dovremmo aggiuntere il return di Optional
    }
    
    //controlleremo di volta in volta i quattro angoli di questo "rettangolo"
    //x & y sono la posizione dei giocatori
    public static entities whatIsFacing(int x, int y) { // metti private
        //controlla, di quei punti dell'angolo che vengono passati, se è dentro un oggetto
        
        if (x < 0 || x >= borderX) {
            return entities.WALL;
        } 
        if (y < 0 || y >= borderY) {
            return entities.WALL;
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if(x >= Allowed.lvlData[i][j].getX() && x <= (Allowed.lvlData[i][j].getX() + Allowed.lvlData[i][j].getWidth()) && 
                        y >= Allowed.lvlData[i][j].getY() && y <= (Allowed.lvlData[i][j].getY() + Allowed.lvlData[i][j].getHeight())){
                    return Allowed.lvlData[i][j].getEntity();
                }
            }

        }
        return entities.SKY;
    }

}
