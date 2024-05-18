package it.unibo.grubclash.controller;

import java.util.ArrayList;

import javax.swing.JPanel;

import it.unibo.grubclash.controller.Implementation.Entity;
import it.unibo.grubclash.controller.Weapon.weapons;
import it.unibo.grubclash.view.Implementation.EnumEntity.entities;

public class Projectile {

    //serve dare una traiettoria al proiettile, il suo comportamento varia a seconda del tipo di proiettile creato
    //il proiettile smette di esistere se canMoveThere() restituisce false
    //il tipo di arma dipende lo mettiamo in una classe diversa? idk

    private static int ROWS;
    public static int getROWS() {
        return ROWS;
    }
    public static void setROWS(int ROWS) {
        Projectile.ROWS = ROWS;
    }

    private static int COLS;
    public static int getCOLS() {
        return COLS;
    }
    public static void setCOLS(int COLS) {
        Projectile.COLS = COLS;
    }

    static int WIDTHROKET = 20;
    static int HEIGHTROKET = 20;
    private static Entity projectile;
    public static Entity getProjectile() {
        return projectile;
    }
    public static void setProjectile(Entity projectile) {
        Projectile.projectile = projectile;
    }

    private static int xClicked;
    private static int yClicked;
    public static int getxClicked() {
        return xClicked;
    }
    public static int getyClicked() {
        return yClicked;
    }

    private static int xCurrentPos;
    public static int getxCurrentPos() {
        return xCurrentPos;
    }
    public static void setxCurrentPos(int xCurrentPos) {
        Projectile.xCurrentPos = xCurrentPos;
    }

    private static int yCurrentPos;
    public static int getyCurrentPos() {
        return yCurrentPos;
    }
    public static void setyCurrentPos(int yCurrentPos) {
        Projectile.yCurrentPos = yCurrentPos;
    }

    private static Weapon weapon;
    public static Weapon getWeapon() {
        return weapon;
    }
    public static void setWeapon(Weapon weapon) {
        Projectile.weapon = weapon;
    }
    
    // la traiettoria del proiettile viene calcolata subito dopo aver costruito l'oggetto proiettile
    public Projectile (Weapon weapon, int xClicked, int yClicked, int ROWS, int COLS) {
        setWeapon(weapon);
        Projectile.xClicked = xClicked;
        Projectile.yClicked = yClicked;
        setROWS(ROWS);
        setCOLS(COLS);
        trajectory();
        setProjectile(new Entity(getxClicked(), getyClicked(), WIDTHROKET, HEIGHTROKET, entities.PROJECTILE)); //a seconda del tipo di proiettile varieranno le dimensioni di width e height, qui ipotizzo di lanciare un razzo
    }

    public static void trajectory () {
        /*  TODO da fare
            Qui si gestisce la fisica del proiettile a seconda del tipo di arma
            vorrei aggiungere uno switch case per le armi diverse, potenziali armi possono essere:
                - hitscan
                - lanciagranate a parabola
                - lanciarazzi a traiettoria fissa
            uno tra il lanciagranate e il lanciarazzi vanno implementate per prime

            per il calcolo della traiettoria, una volta compreso come agisce la fisica e il salto (che no trovo lol) basta che mi calcolo il vettore direzione
            double dirX = getxClicked() - ( getWeapon().getOwner().getX() + (getWeapon().getOwner().getWidth() / 2) );
            double dirY = getyClicked() - ( getWeapon().getOwner().getY() + (getWeapon().getOwner().getHeight() / 2) );

        */

        // TODO applicare la fisica di christian qui
        if (Projectile.weapon.getType() == weapons.GRANADE) {
            
        }
        if (Projectile.weapon.getType() == weapons.HITSCAN) {
            
        }
        if (Projectile.weapon.getType() == weapons.ROKET) {
            
        }

        /*
            TODO da capire come gestire parallelamente il proiettile nel panel con la sua hitbox
            E questa new Entity si gestisce in modo parallelo all'immagine del proiettile, che si può direttamente gestire qua dentro 
        */
    }

    private static Entity whatIsFacing(Entity[][] lvlData, ArrayList<Entity> dynamicEntity, float x, float y) {
        //controlla, di quei punti dell'angolo che vengono passati, se è dentro un oggetto
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (x >= lvlData[i][j].getX() && x < (lvlData[i][j].getWidth() + lvlData[i][j].getX()) && y >= lvlData[i][j].getX() && y < (lvlData[i][j].getY() + lvlData[i][j].getHeight()) ) {
                    return lvlData[i][j]; //se le coordinate degli angoli si intersecano, l'entità
                }
            }
        }
        for (Entity entity : dynamicEntity) {
            if (x >= entity.getX() && x < (entity.getWidth() + entity.getX()) && y >= entity.getX() && y < (entity.getY() + entity.getHeight()) ) {
                return entity;
            }
        }
        return null;
    }

    /*
    TODO MODO PER ELIMINARE LA MAPPA DA METTERE DENTRO GRUBPANEL
        if (collideWithSmth == true) {
            addDynamicEntity( damage() ); //mettendola a entità si possono includere immagini o animazioni o boh
            for (canMoveThere( dynamicEntity.find(entities.EXPLOSION) == false)) {
                // scorro eliminando tutto quello che c'è dentro l'area
                mapDestroyer(mapBase, whatWallIsIncluded (x, y, width, height));

                if (whatIsFacing( dynamicEntity.find(entities.EXPLOSION).getX, dynamicEntity.find(entities.EXPLOSION).getY ))
                
            }
            //faccio questa pulizia per eliminare l'entità dell'esplosione
            dynamicEntity.find(entities.EXPLOSION).remove;
        }
    */

    private boolean collidesWithSmth (Entity[][] lvlData, ArrayList<Entity> dynamicEntity, JPanel[][] mapBase, int x, int y) { // controlla l'esplosione del proiettile
        // dynamicEntity potrebbe essere l'insieme dielle entità non fisse che girano per la mappa, come proiettili, player e in caso item speciali
        // do per scontato che ogni angolo appartiene a una sola entità alla volta (o player o muro, non entrambe)
        if (whatIsFacing(lvlData, dynamicEntity, x, y).getEntity() == entities.SKY || whatIsFacing(lvlData, dynamicEntity, x, y).getEntity() != entities.ITEM ) {
            if(whatIsFacing(lvlData, dynamicEntity, x + WIDTHROKET, y + HEIGHTROKET).getEntity() == entities.SKY || whatIsFacing(lvlData, dynamicEntity, x + WIDTHROKET, y + HEIGHTROKET).getEntity() == entities.ITEM) {
                if(whatIsFacing(lvlData, dynamicEntity, x + WIDTHROKET, y).getEntity() == entities.SKY || whatIsFacing(lvlData, dynamicEntity, x + WIDTHROKET, y).getEntity() == entities.ITEM) {
                    if(whatIsFacing(lvlData, dynamicEntity, x, y + HEIGHTROKET).getEntity() == entities.SKY || whatIsFacing(lvlData, dynamicEntity, x, y + HEIGHTROKET).getEntity() == entities.ITEM)
                        return false;
                }
            }
        }
        return true;
    }

    //WIDTHROKET deve essere cambiato
    private static int getRadius () { // restituisce il raggio a seconda dell'arma
        switch (getWeapon().getType()) {
            case GRANADE:
                return WIDTHROKET + 20;
            case ROKET:
                return WIDTHROKET + 40;
            default: //hitscan
                return WIDTHROKET;
        }
    }

    /*
        TODO restituisce una Entity, da aggiungere alla dynamicEntity. 
        Ogni cosa dentro la damageArea() deve volare, infatti chiamata mapdestroyer() per ogni entità che sta dentro
        finché canMoveThere() da falso, si scorrono le entità all'interno dell'area e viene applicato l'effetto del danno
        ^naturalmente da mettere dentro GrubPanel :) ^ 
    */
    private static Entity damage () { 
        int x = (int) ((getProjectile().getX() + getProjectile().getWidth()) / 2);
        int y = (int) ((getProjectile().getY() + getProjectile().getHeight()) / 2);
        Entity explosionRadius = new Entity(x - getRadius() , y - getRadius(), getRadius() * 2, getRadius() * 2, entities.EXPLOSION); //elimino ogni explosion dopo
        return explosionRadius;

    }

}
