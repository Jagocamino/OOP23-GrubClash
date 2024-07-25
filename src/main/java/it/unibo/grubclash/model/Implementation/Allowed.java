package it.unibo.grubclash.model.Implementation;
import it.unibo.grubclash.controller.Application_Programming_Interface.PlayerInterface;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Application_Programming_Interface.EntityInterface;
import it.unibo.grubclash.model.Implementation.EnumEntity.entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.status;
import it.unibo.grubclash.view.Implementation.Ammo_Box;
import it.unibo.grubclash.view.Implementation.Heal;
import it.unibo.grubclash.view.Implementation.MobGenerator;
import it.unibo.grubclash.view.Implementation.Trap;

import javax.swing.*;

import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

public class Allowed { 

    private static int borderX;
    private static int borderY;
    private static JPanel[][] mapBase;
    private static ArrayList<Optional<Entity>> dynamicEntities;
    private static Entity[][] lvlData;
    private static int ROWS;
    private static int COLS;
    private static ArrayList<Optional<Entity>> mob;

    public Allowed(int borderX, int borderY, int ROWS, int COLS) { //quando creo Allowed DENTRO MAP BUILDER chiamo il costruttore e gli passo i bordi del JPanel che contiene il giochino e le righe e colonne totali
        Allowed.borderX = borderX;
        Allowed.borderY = borderY;
        Allowed.ROWS = ROWS;
        Allowed.COLS = COLS;
        Allowed.lvlData = new Entity[ROWS][COLS];
        Allowed.dynamicEntities = new ArrayList<Optional<Entity>>();
        mob = new ArrayList<>();
    }

    private static EntityInterface getLvlData( int i, int j) {
        return Allowed.lvlData[i][j];
    }

    public static void clearDynamicEntities(){
        ArrayList<Optional<Entity>> toClear = new ArrayList<>();
        for (Optional<Entity> optional : dynamicEntities) {
            if(optional.isEmpty()){
                toClear.add(optional);
            }
        }
        for (Optional<Entity> entity : toClear) {
            dynamicEntities.remove(entity);
        }
    }
    
    public static ArrayList<Optional<Entity>> getDynamicEntities() {
        return dynamicEntities;
    }
    public static void addDynamicEntity (Optional<Entity> dynamicEntity) {
        Allowed.dynamicEntities.add(dynamicEntity);
    }
    
    public static void removeDynamicEntity (EntityInterface dynamicEntity) {
        Allowed.dynamicEntities.set(Allowed.dynamicEntities.indexOf(Optional.of(dynamicEntity)), Optional.empty());
    }

    public static int getROWS() {
        return Allowed.ROWS;
    }
    
    public static int getCOLS() {
        return Allowed.COLS;
    }

    public static entities getEntity (int i, int j) {
        return Allowed.lvlData[i][j].getEntity();
    }

    
    public static void setMapBase (JPanel[][] mapBase) {
        Allowed.mapBase = mapBase;
    }

    private static void addEntity (int x, int y, int width, int height, entities entity, int i, int j) {
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
        for (Optional<Entity> t : getDynamicEntities()) {
            if( t.isPresent() && t.get().working == status.ALIVE && player.x + player.width/2 > t.get().getX() && player.x + player.width/2 < t.get().getX() + t.get().getWidth() && 
                player.y + player.height/2 > t.get().getY() && player.y + player.height/2 < t.get().getY() + t.get().getHeight()){
                switch(t.get().getEntity()){
                    case TRAP: 
                        Sound.setFile(0);
                        Sound.play();
                        player.life.damage(); 
                        player.life.damage();
                        t.get().working = status.DEAD; 
                        break;
                    case HEAL:
                        Sound.setFile(3);
                        Sound.play();
                        player.life.plusLife(); 
                        t.get().working = status.DEAD; 
                        break;
                    case AMMO_BOX:
                        Sound.setFile(2);
                        Sound.play();
                        player.getWeapon().get().refillAmmo();
                        t.get().working = status.DEAD; 
                        break;
                    case MOBGENERATOR:
                        t.get().working = status.DEAD;
                        getMob().add(Optional.of(new Mob(0,0,35,35,entities.MOB)));
                        break;
                    default: break;
                }
            }
        }
    }

    public static void addMapBase (EnumEntity.entities[][] entityMatrix) {
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                addEntity(mapBase[i][j].getX(), mapBase[i][j].getY(), (int)mapBase[i][j].getBounds().getWidth(), (int)mapBase[i][j].getBounds().getHeight(), entityMatrix[i][j], i, j);
                if (entityMatrix[i][j] == entities.ITEM) {
                    addDynamicEntity(Optional.of(giveRandomItem(mapBase[i][j].getX(), mapBase[i][j].getY())));
                    lvlData[i][j].setEntity(entities.SKY);
                }
            }
        }
    }
    
    private static Entity giveRandomItem (int x, int y) {
        Random randomNum = new Random();    
        switch (randomNum.nextInt(4)) {
            case 0:
                return new Trap(x, y);
            case 1:
                return new Heal(x, y);
            case 2:
                return new Ammo_Box(x, y);
            case 3:
                return new MobGenerator(x, y);
            default:
                return null;
        }
    }

    public static Optional<ArrayList<Entity>> meleeAttack(int xRange, int yRange, int widthRange, int heightRange, PlayerInterface owner) { // restituisce una arraylist optional dei player colpiti dal melee
        boolean empty = true;
        ArrayList<Entity> arrayList = new ArrayList<>();
        for (Optional<Entity> entity : getDynamicEntities()) {
            if (entity.isPresent() && entity.get() != owner) {
                int entityX = entity.get().getX();
                int entityY = entity.get().getY();
                int entityWidth = entity.get().getWidth();
                int entityHeight = entity.get().getHeight();
                if( isPlayer(entity.get()) && (
                    ( xRange >= entityX && xRange < (entityX + entityWidth)) || // se è sporgente a sx
                    ( xRange + widthRange >= entityX && xRange + widthRange < (entityX + entityWidth)) || // se è sporgente a dx
                    ( xRange < entityX && xRange + widthRange >= (entityX + entityWidth)) // se è dentro
                    ) && (
                    ( yRange >= entityY && yRange < (entityY + entityHeight)) || // se è sporgente sopra
                    ( yRange + heightRange >= entityY && yRange + heightRange < (entityY + entityHeight)) || // se è sporgente sotto
                    ( yRange < entityY && yRange + heightRange >= (entityY + entityHeight)) // se è dentro
                    ) 
                ){
                    empty = false;
                    arrayList.add(entity.get());
                }   
            }
        }
        if (empty) {
            return Optional.empty();
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

    public static void mapDestroyer (EntityInterface wall) {
        int i = getI(wall).get().intValue();
        int j = getJ(wall).get().intValue();
        mapDestroyer(i, j);
    }

    // METTERE METODO CHE RITORNA i DATA l'entità del muro
    public static Optional<Integer> getI (EntityInterface entity) {

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
    public static Optional<Integer> getJ (EntityInterface entity) {

        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (Allowed.lvlData[i][j] == entity ) {
                    return Optional.ofNullable(j);
                }
            }
        }
        return Optional.empty();
    }
    
    private static entities whatIsFacing(int x, int y) {
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
    
    private static boolean gonnaExplodeHere(int x, int y, PlayerInterface owner ) {
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
                    return true;
                }
            }
        }

        //scorre tutte le entità in dynamicEntity per controllare le collisioni
        for (Optional<Entity> entity : Allowed.dynamicEntities) {
            if (    (
                        entity.isPresent() && entity.get() != owner && entity.get() != owner.getWeapon().get().getRocket().get()
                    ) && (   
                        x >= entity.get().getX() &&
                        x < (entity.get().getWidth() + entity.get().getX()) &&
                        y >= entity.get().getY() && 
                        y < (entity.get().getY() + entity.get().getHeight())
                    ) && (
                        hittable(entity.get())
                    )
                ) {
                return true;
            }
        }
        for (Optional<Entity> entity : Allowed.mob) {
            if (    (
                        entity.isPresent() 
                    ) && (   
                        x >= entity.get().getX() &&
                        x < (entity.get().getWidth() + entity.get().getX()) &&
                        y >= entity.get().getY() && 
                        y < (entity.get().getY() + entity.get().getHeight())
                    ) && (
                        hittable(entity.get())
                    )
                ) {
                return true;
            }
        }
        return false;
    }
   
    public static boolean gonnaExplode ( int x, int y, int widthProjectile, int heightProjectile, PlayerInterface playerException) { // controlla l'esplosione del proiettile
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
    public static boolean hittable(EntityInterface entity) {
        return (
            entity.getEntity() == entities.PLAYER1 ||
            entity.getEntity() == entities.PLAYER2 ||
            entity.getEntity() == entities.PLAYER3 ||
            entity.getEntity() == entities.PLAYER4 ||
            entity.getEntity() == entities.PLAYER5 ||
            entity.getEntity() == entities.WALL ||
            entity.getEntity() == entities.PROJECTILE ||
            entity.getEntity() == entities.MOB
        );
    }

    public static boolean damageable (EntityInterface entity) {
        return (
            entity.getEntity() == entities.PLAYER1 ||
            entity.getEntity() == entities.PLAYER2 ||
            entity.getEntity() == entities.PLAYER3 ||
            entity.getEntity() == entities.PLAYER4 ||
            entity.getEntity() == entities.PLAYER5 ||
            entity.getEntity() == entities.WALL ||
            entity.getEntity() == entities.MOB
        );
    }

    public static boolean isPlayer (EntityInterface entity) {
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
        for (Optional<Entity> dynamicEntity : Allowed.dynamicEntities) {
            if(dynamicEntity.isPresent()){

                int entityX = dynamicEntity.get().getX();
                int entityY = dynamicEntity.get().getY();
                int entityWidth = dynamicEntity.get().getWidth();
                int entityHeight = dynamicEntity.get().getHeight();
                if( damageable(dynamicEntity.get()) && (
                    ( explosionX >= entityX && explosionX < (entityX + entityWidth)) || // se è sporgente a sx
                    ( explosionX + explosionWidth >= entityX && explosionX + explosionWidth < (entityX + entityWidth)) || // se è sporgente a dx
                    ( explosionX < entityX && explosionX + explosionWidth >= (entityX + entityWidth)) // se è dentro
                    ) && (
                    ( explosionY >= entityY && explosionY < (entityY + entityHeight)) || // se è sporgente sopra
                    ( explosionY + explosionHeight >= entityY && explosionY + explosionHeight < (entityY + entityHeight)) || // se è sporgente sotto
                    ( explosionY < entityY && explosionY + explosionHeight >= (entityY + entityHeight)) // se è dentro
                    ) 
                ){
                        damageToWhichDynamicEntities.add(dynamicEntity.get());
                }
            }

        }

        for (Optional<Entity> dynamicEntity : Allowed.mob) {
            if(dynamicEntity.isPresent()){

                int entityX = dynamicEntity.get().getX();
                int entityY = dynamicEntity.get().getY();
                int entityWidth = dynamicEntity.get().getWidth();
                int entityHeight = dynamicEntity.get().getHeight();
                if( damageable(dynamicEntity.get()) && (
                    ( explosionX >= entityX && explosionX < (entityX + entityWidth)) || // se è sporgente a sx
                    ( explosionX + explosionWidth >= entityX && explosionX + explosionWidth < (entityX + entityWidth)) || // se è sporgente a dx
                    ( explosionX < entityX && explosionX + explosionWidth >= (entityX + entityWidth)) // se è dentro
                    ) && (
                    ( explosionY >= entityY && explosionY < (entityY + entityHeight)) || // se è sporgente sopra
                    ( explosionY + explosionHeight >= entityY && explosionY + explosionHeight < (entityY + entityHeight)) || // se è sporgente sotto
                    ( explosionY < entityY && explosionY + explosionHeight >= (entityY + entityHeight)) // se è dentro
                    ) 
                ){
                        damageToWhichDynamicEntities.add(dynamicEntity.get());
                }
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
    public static void applyDamage (ArrayList<Entity> dealDamage, int i) {
        for (Entity entity : dealDamage) {
            if ( isPlayer(entity) || entity.getEntity().equals(entities.MOB) ) {
                for(int j = 0; j < i; j++){
                    entity.life.damage();
                }
            }
            if ( entity.getEntity() == entities.WALL ) {// da valutare se il muoro si cancella direttamente oppure ha una healthbar
                mapDestroyer(entity);
            }
        }
    }

    public static ArrayList<Optional<Entity>> getMob() {
        return mob;
    }


}
