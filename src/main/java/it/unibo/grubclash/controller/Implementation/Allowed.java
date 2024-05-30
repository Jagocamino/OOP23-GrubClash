package it.unibo.grubclash.controller.Implementation;
import it.unibo.grubclash.view.Implementation.EnumEntity;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;
import it.unibo.grubclash.view.Implementation.EnumEntity.status;

import javax.swing.*;

import java.util.Random;
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
    public static ArrayList<Entity> getDynamicEntities() {
        return dynamicEntities;
    }
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

    public static void setMapBase (JPanel[][] mapBase) {
        Allowed.mapBase = mapBase;
    }

    public static void addEntity (int x, int y, int width, int height, entities entity, int i, int j) {
        if (lvlData[i][j] != null ) {
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

    public static void touchDynamicEntity(Player player){
        for (Entity t : getDynamicEntities()) {
            if( t.working == status.ALIVE && player.x + player.width/2 > t.getX() && player.x + player.width/2 < t.getX() + t.getWidth() && 
                player.y + player.height/2 > t.getY() && player.y + player.height/2 < t.getY() + t.getHeight()){
                switch(t.getEntity()){
                    case TRAP: 
                        player.life.damage(); 
                        t.working = status.DEAD; 
                        break;
                    case HEAL:
                        player.life.plusLife(); 
                        t.working = status.DEAD; 
                        break;
                    case AMMO_BOX:
                        player.getWeapon().get().refillAmmo();
                        t.working = status.DEAD; 
                        break;
                    default: break;
                }
            }
        }
    }

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

    // TODO evitare che il proiettile esca dallo schermo, gestire la collisione dei proiettili con il giocatore, fare in modo che i proiettili colpiscano in tutte le direzioni

    // TODO devo riuscire a lavorare le heal e trap senza dover scomodare GrubPanel

    public static void addMapBase (EnumEntity.entities[][] entityMatrix) {
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                // TODO QUI si gestisce l'assegnazione random degli item ATTRAVERSO UN ALTRO METODO (tipo, boh giveRandomItem() )
                // addEntity( giveRandomItem() ), dove il randomItem è mina, healthpack o arma i guess
                addEntity(mapBase[i][j].getX(), mapBase[i][j].getY(), (int)mapBase[i][j].getBounds().getWidth(), (int)mapBase[i][j].getBounds().getHeight(), entityMatrix[i][j], i, j);
                if (entityMatrix[i][j] == entities.ITEM) {
                    addDynamicEntity(giveRandomItem(mapBase[i][j].getX(), mapBase[i][j].getY())); // TODO giverandomitem ci restituisce un item tra granata, heal e trap
                    lvlData[i][j].setEntity(entities.SKY);
                }
            }
        }
    }

    
    private static Entity giveRandomItem (int x, int y) {
        Random randomNum = new Random();    
        switch (randomNum.nextInt(3)) {
            case 0:
                return new Trap(x, y);
            case 1:
                return new Heal(x, y);
            case 2:
                return new Ammo_Box(x, y);
            default:
                return null;
        }
    }

    // TODO @notnoted @notnoted @notnoted @notnoted @notnoted @notnoted @notnoted @notnoted @notnoted @notnoted 
    public static Optional<ArrayList<Entity>> meleeAttack(int xRange, int yRange, int widthRange, int heightRange, Player owner) { // restituisce una arraylist optional dei player colpiti dal melee
        ArrayList<Entity> arrayList = new ArrayList<>();
        for (Entity entity : getDynamicEntities()) {
            if (entity != owner) {
                int entityX = entity.getX();
                int entityY = entity.getY();
                int entityWidth = entity.getWidth();
                int entityHeight = entity.getHeight();
                if( isPlayer(entity) && (
                    ( xRange >= entityX && xRange < (entityX + entityWidth)) || // se è sporgente a sx
                    ( xRange + widthRange >= entityX && xRange + widthRange < (entityX + entityWidth)) || // se è sporgente a dx
                    ( xRange < entityX && xRange + widthRange >= (entityX + entityWidth)) // se è dentro
                    ) && (
                    ( yRange >= entityY && yRange < (entityY + entityHeight)) || // se è sporgente sopra
                    ( yRange + heightRange >= entityY && yRange + heightRange < (entityY + entityHeight)) || // se è sporgente sotto
                    ( yRange < entityY && yRange + heightRange >= (entityY + entityHeight)) // se è dentro
                    ) 
                ){
                        arrayList.add(entity);
                }   
            }
        }
        return Optional.ofNullable(arrayList);
    }

    //entityMatrix serve SOLO per inizializzare lvlData, poi NON DEVE più essere usato
    public static void mapDestroyer (int i, int j) {
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

    public static void mapDestroyer (Entity wall) {
        int i = getI(wall).get().intValue();
        int j = getJ(wall).get().intValue();
        mapDestroyer(i, j);
    }

    // METTERE METODO CHE RITORNA i DATA l'entità del muro
    public static Optional<Integer> getI (Entity entity) {

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
    public static Optional<Integer> getJ (Entity entity) {

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

    
    private static boolean gonnaExplodeHere(int x, int y, Player owner ) {
        //scorre tutte le entità in lvlData per controllare le collisioni
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (x < 0 || x >= borderX) {
                    return true;
                } 
                if (y < 0 || y >= borderY) {
                    return true;
                }
                if (    lvlData[i][j].getEntity() == entities.WALL &&
                        x >= Allowed.lvlData[i][j].getX() &&
                        x < (Allowed.lvlData[i][j].getWidth() + Allowed.lvlData[i][j].getX()) &&
                        y >= Allowed.lvlData[i][j].getY() &&
                        y < (Allowed.lvlData[i][j].getY() + lvlData[i][j].getHeight()) 
                    ) {
                        System.out.println("ent: " + Allowed.lvlData[i][j].getEntity() + " x: " + Allowed.lvlData[i][j].getX() + " y: " + Allowed.lvlData[i][j].getY());
                    return true;
                }
            }
        }

        //scorre tutte le entità in dynamicEntity per controllare le collisioni
        for (Entity entity : Allowed.dynamicEntities) {
            if (    (
                        entity != owner && entity != owner.getWeapon().get().getRocket().get()
                    ) && (   
                        x >= entity.getX() &&
                        x < (entity.getWidth() + entity.getX()) &&
                        y >= entity.getY() && 
                        y < (entity.getY() + entity.getHeight())
                    ) && (
                        hittable(entity)
                    )
                ) {
                    System.out.println("ent: " + entity.getEntity() + " x: " + entity.getX() + " y: " + entity.getY());
                return true;
            }
        }
        return false;
    }
   
    public static boolean gonnaExplode ( int x, int y, int widthProjectile, int heightProjectile, Player playerException) { // controlla l'esplosione del proiettile
        if (!gonnaExplodeHere( x, y, playerException )) {
            if(!gonnaExplodeHere( x + widthProjectile, y + heightProjectile, playerException)) {
                if(!gonnaExplodeHere( x + widthProjectile, y, playerException)) {
                    if(!gonnaExplodeHere( x, y + heightProjectile, playerException))
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
            entity.getEntity() == entities.PROJECTILE  //boh 
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
            if( damageable(dynamicEntity) && (
                ( explosionX >= entityX && explosionX < (entityX + entityWidth)) || // se è sporgente a sx
                ( explosionX + explosionWidth >= entityX && explosionX + explosionWidth < (entityX + entityWidth)) || // se è sporgente a dx
                ( explosionX < entityX && explosionX + explosionWidth >= (entityX + entityWidth)) // se è dentro
                ) && (
                ( explosionY >= entityY && explosionY < (entityY + entityHeight)) || // se è sporgente sopra
                ( explosionY + explosionHeight >= entityY && explosionY + explosionHeight < (entityY + entityHeight)) || // se è sporgente sotto
                ( explosionY < entityY && explosionY + explosionHeight >= (entityY + entityHeight)) // se è dentro
                ) 
            ){
                    damageToWhichDynamicEntities.add(dynamicEntity);
            }
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int entityX = lvlData[i][j].getX();
                int entityY = lvlData[i][j].getY();
                int entityWidth = lvlData[i][j].getWidth();
                int entityHeight = lvlData[i][j].getHeight();
                if( (lvlData[i][j].getEntity() == entities.WALL) && (
                    ( explosionX >= entityX && explosionX < (entityX + entityWidth)) || // se è sporgente a sx
                    ( explosionX + explosionWidth >= entityX && explosionX + explosionWidth < (entityX + entityWidth)) || // se è sporgente a dx
                    ( explosionX < entityX && explosionX + explosionWidth >= (entityX + entityWidth)) // se è dentro
                    ) && (
                    ( explosionY >= entityY && explosionY < (entityY + entityHeight)) || // se è sporgente sopra
                    ( explosionY + explosionHeight >= entityY && explosionY + explosionHeight < (entityY + entityHeight)) || // se è sporgente sotto
                    ( explosionY < entityY && explosionY + explosionHeight >= (entityY + entityHeight)) // se è dentro
                    ) 
                ){
                        damageToWhichDynamicEntities.add(lvlData[i][j]);
                }
            }
        }
        return damageToWhichDynamicEntities;
    }

    // infligge il danno alle strutture e ai giocatori nella ArrayList buttata fuori da dealDamage (ovvero tutte le entità colpite dall'esplosione)
    // TODO da gestire cosa succede al danneggiamento dell'entità
    // il return di dealDamage() va come argomento di applyDamage()
    public static void applyDamage (ArrayList<Entity> dealDamage, int i) {
        for (Entity entity : dealDamage) {
            if ( isPlayer(entity) ) { // TODO ci sono due player
                for(int j = 0; j < i; j++){
                    entity.life.damage();
                }
            }
            if ( entity.getEntity() == entities.WALL ) {// da valutare se il muoro si cancella direttamente oppure ha una healthbar
                mapDestroyer(entity);
            }
        }
    }


}
