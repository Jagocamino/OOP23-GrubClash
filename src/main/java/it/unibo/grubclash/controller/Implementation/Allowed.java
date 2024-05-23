package it.unibo.grubclash.controller.Implementation;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
import javax.swing.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

public class Allowed { 

    /*
    public static Insets giveInsetsBorder(JFrame map) { //da passare ad ALLOWED per quanto riguarda IsSolid --> ALLOWED viene chiamato qua dentro
        return map.getInsets();
    }
    da finire guida sulle collisioni, una volta risolto il problema dei player sul map builder
    */
    private static int borderX;
    private static int borderY;
    private static JPanel[][] mapBase;

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



    //TODO mapBase serve dentro allowed, ma allowed è chiamato dentro GrubPanel che non usa mapBase
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

    public static void setMapBase (JPanel[][] mapBase) {
            Allowed.mapBase = mapBase;
    }

    public static void addEntity (int x, int y, int width, int height, entities entity, int i, int j) {
        if (lvlData[i][j] != null && (lvlData[i][j].getEntity() == entities.WALL || lvlData[i][j].getEntity() == entities.ITEM1 || lvlData[i][j].getEntity() == entities.ITEM2
        || lvlData[i][j].getEntity() == entities.ITEM3 || lvlData[i][j].getEntity() == entities.ITEM4 || lvlData[i][j].getEntity() == entities.ITEM5)) {
            System.out.println("This box is already taken, overwriting floor or item on the map..");
        }
        Allowed.lvlData[i][j] = new Entity(x, y, width, height, entity);
    }

    //regards player
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

    /*
            TODO le due funzioni sotto scorrono rispettivamente lvlData e dynamicEntity e ogni volta che vengono chiamate restituiscono una entità in range
            vorrei fare un metodo per il calcolo del danno, magari chiamato alla fine dei due metodi sotto, per eliminare entità o altro    
    */

    //se non funziona distruggo tutto, MA PROBABILMENTE NEANCHE SERVE!!!
    //è un whatisfacing che restituisce un entità e non controlla i bordi
    /* public static Optional<Entity> whatWallIsIncluded (int x, int y, int width, int height) { //qui ci metto le dimensioni dell'esplosione, ritorna la prima entità Wall nell'area
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if(  Allowed.lvlData[i][j].getEntity() == entities.WALL && (
                    ( x >= Allowed.lvlData[i][j].getX() && x < (Allowed.lvlData[i][j].getX() + Allowed.lvlData[i][j].getWidth())) || // se è sporgente a sx
                    ( x + width >= Allowed.lvlData[i][j].getX() && x + width < (Allowed.lvlData[i][j].getX() + Allowed.lvlData[i][j].getWidth())) || // se è sporgente a dx
                    ( x < Allowed.lvlData[i][j].getX() && x + width >= (Allowed.lvlData[i][j].getX() + Allowed.lvlData[i][j].getWidth())) // se è dentro
                    ) && (
                    ( y >= Allowed.lvlData[i][j].getY() && y < (Allowed.lvlData[i][j].getY() + Allowed.lvlData[i][j].getHeight())) || // se è sporgente sopra
                    ( y + height >= Allowed.lvlData[i][j].getY() && y + height < (Allowed.lvlData[i][j].getY() + Allowed.lvlData[i][j].getHeight())) || // se è sporgente sotto
                    ( y < Allowed.lvlData[i][j].getY() && y + height >= (Allowed.lvlData[i][j].getY() + Allowed.lvlData[i][j].getHeight())) // se è dentro
                    ) ){
                    return Optional.ofNullable(Allowed.lvlData[i][j]);
                }
            }
        }
        return Optional.empty();
    } */

    


    public static void addMapBase (EnumEntity.entities[][] entityMatrix) {
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                addEntity(mapBase[i][j].getX(), mapBase[i][j].getY(), (int)mapBase[i][j].getBounds().getWidth(), (int)mapBase[i][j].getBounds().getHeight(), entityMatrix[i][j], i, j);
            }
        }
    }

    //entityMatrix serve SOLO per inizializzare lvlData, poi NON DEVE più essere usato
    public void mapDestroyer (int i, int j) {
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

    public void mapDestroyer (Entity wall) {
        int i = getI(wall).get().intValue();
        int j = getJ(wall).get().intValue();
        mapDestroyer(i, j);
    }

    // METTERE METODO CHE RITORNA i DATA l'entità del muro
    public Optional<Integer> getI (Entity entity) {

        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (Allowed.lvlData[i][j] == entity ) {
                    return Optional.ofNullable(i);
                }
            }
        }
        return Optional.empty();
    }

    // METTERE METODO CHE RITORNA j DATA l'entità del muro
    public Optional<Integer> getJ (Entity entity) {

        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (Allowed.lvlData[i][j] == entity ) {
                    return Optional.ofNullable(j);
                }
            }
        }
        return Optional.empty();
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


    //                                      TUTTO QUELLO CHE RIGUARDA IL PROIETTILE

 

    /*
        TODO restituisce una Entity, da aggiungere alla dynamicEntity. 
        Ogni cosa dentro la damageArea() deve volare, infatti chiamata mapdestroyer() per ogni entità che sta dentro
        finché canMoveThere() da falso, si scorrono le entità all'interno dell'area e viene applicato l'effetto del danno
        ^naturalmente da mettere dentro GrubPanel :) ^ 
    */

        
    //TODO MODO PER ELIMINARE LA MAPPA DA METTERE DENTRO GRUBPANEL

    /* if (gonnaExplode == true) {
            addDynamicEntity( damage() ); //mettendola a entità si possono includere immagini o animazioni o boh
            for (canMoveThere( dynamicEntity.find(entities.EXPLOSION) == false)) {
                // scorro eliminando tutto quello che c'è dentro l'area
                mapDestroyer(whatWallIsIncluded (x, y, width, height));                
            }
            //faccio questa pulizia per eliminare l'entità dell'esplosione
            dynamicEntity.find(entities.EXPLOSION).remove();
    } */

    private static boolean gonnaExplodeHere(float x, float y) {
        //scorre tutte le entità in lvlData per controllare le collisioni
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if ( (
                        x >= Allowed.lvlData[i][j].getX() &&
                        x < (Allowed.lvlData[i][j].getWidth() + Allowed.lvlData[i][j].getX()) &&
                        y >= Allowed.lvlData[i][j].getY() &&
                        y < (Allowed.lvlData[i][j].getY() + lvlData[i][j].getHeight()) 
                        ) && (
                        damageable(lvlData[i][j])
                        )) {
                    return true;
                }
            }
        }

        //scorre tutte le entità in dynamicEntity per controllare le collisioni
        for (Entity entity : Allowed.dynamicEntities) {
            if ( (      x >= entity.getX() &&
                        x < (entity.getWidth() + entity.getX()) &&
                        y >= entity.getX() && 
                        y < (entity.getY() + entity.getHeight())
                    ) && (
                        damageable(entity)
                    )
                ) {
                return true;
            }
        }
        return false;
    }
   
    public boolean gonnaExplode ( int x, int y, int widthProjectile, int heightProjectile) { // controlla l'esplosione del proiettile
        if (gonnaExplodeHere( x, y )) {
            if(gonnaExplodeHere( x + widthProjectile, y + heightProjectile)) {
                if(gonnaExplodeHere( x + widthProjectile, y)) {
                    if(gonnaExplodeHere( x, y + heightProjectile))
                        return false;
                }
            }
        }
        return true;
    }

    //check if entity is hittable
    public static boolean hittable(Entity entity) {
        return (
            entity.getEntity() == entities.PLAYER1 ||
            entity.getEntity() == entities.PLAYER2 ||
            entity.getEntity() == entities.PLAYER3 ||
            entity.getEntity() == entities.PLAYER4 ||
            entity.getEntity() == entities.PLAYER5 ||
            entity.getEntity() == entities.WALL ||
            entity.getEntity() == entities.PROJECTILE
        );
    }

    public static boolean damageable (Entity entity) {
        return (
            entity.getEntity() == entities.PLAYER1 ||
            entity.getEntity() == entities.PLAYER2 ||
            entity.getEntity() == entities.PLAYER3 ||
            entity.getEntity() == entities.PLAYER4 ||
            entity.getEntity() == entities.PLAYER5 ||
            entity.getEntity() == entities.WALL
        );
    }

    public static boolean isPlayer (Entity entity) {
        return (
            entity.getEntity() == entities.PLAYER1 ||
            entity.getEntity() == entities.PLAYER2 ||
            entity.getEntity() == entities.PLAYER3 ||
            entity.getEntity() == entities.PLAYER4 ||
            entity.getEntity() == entities.PLAYER5
        );
    }

    // restituisce una lista di entità che subiranno il danno
    // chiede in input le dimensioni dell'esplosione
    public static ArrayList<Entity> dealDamage (int explosionX, int explosionY, int explosionWidth, int explosionHeight) { 
        ArrayList<Entity> damageToWhichDynamicEntities = new ArrayList<>();
        for (Entity dynamicEntity : Allowed.dynamicEntities) {
            int entityX = dynamicEntity.getX();
            int entityY = dynamicEntity.getY();
            int entityWidth = dynamicEntity.getWidth();
            int entityHeight = dynamicEntity.getHeight();
            if( hittable(dynamicEntity) && (
                ( explosionX >= entityX && explosionX < (entityX + entityWidth)) || // se è sporgente a sx
                ( explosionX + explosionWidth>= entityX && explosionX + explosionWidth< (entityX + entityWidth)) || // se è sporgente a dx
                ( explosionX < entityX && explosionX + explosionWidth>= (entityX + entityWidth)) // se è dentro
                ) && (
                ( explosionY >= entityY && explosionY < (entityY + entityHeight)) || // se è sporgente sopra
                ( explosionY + explosionHeight >= entityY && explosionY + explosionHeight < (entityY + entityHeight)) || // se è sporgente sotto
                ( explosionY < entityY && explosionY + explosionHeight >= (entityY + entityHeight)) // se è dentro
                ) ){
                    damageToWhichDynamicEntities.add(dynamicEntity);
                }
        }
        return damageToWhichDynamicEntities;
    }

    // infligge il danno alle strutture e ai giocatori nella ArrayList buttata fuori da dealDamage (ovvero tutte le entità colpite dall'esplosione)
    // TODO da gestire cosa succede al danneggiamento dell'entità
    public static void applyDamage (int x, int y, int width, int height) {
        for (Entity entity : dealDamage(x, y, width, height)) {
            if (
                    isPlayer(entity)
                ) { // cosa succede se il giocatore prende danno?
            }
            if (
                    entity.getEntity() == entities.WALL
            ) {
                // da valutare se il muoro si cancella direttamente oppure ha una healthbar
            }

        }
    }


}
