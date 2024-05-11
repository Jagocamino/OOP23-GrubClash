package it.unibo.grubclash.controller;

import javax.swing.JPanel;

import it.unibo.grubclash.view.EnumEntity.entities;

public class Projectile {

    //serve dare una traiettoria al proiettile, il suo comportamento varia a seconda del tipo di proiettile creato
    //il proiettile smette di esistere se canMoveThere() restituisce false
    //il tipo di arma dipende lo mettiamo in una classe diversa? idk
    private static Entity[] entitiesExploded;
    public static void initEntitiesExploded (int n) {
        Projectile.entitiesExploded = new Entity[n];
    }

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
    public Weapon getWeapon() {
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
        setProjectile(new Entity(getxClicked(), getyClicked(), WIDTHROKET, HEIGHTROKET, entities.PROJECTILE)); //a seconda del tipo di proiettile varieranno le dimensioni di width e height, qui ipotizzo di lanciare un razzo
        /*
            TODO da capire come gestire parallelamente il proiettile nel panel con la sua hitbox
            E questa new Entity si gestisce in modo parallelo all'immagine del proiettile, che si può direttamente gestire qua dentro 
        */
    }

    private static Entity whatIsFacing(Entity[][] lvlData, Entity[] dynamicEntity, float x, float y) {
        //controlla, di quei punti dell'angolo che vengono passati, se è dentro un oggetto
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (x >= lvlData[i][j].getX() && x < (lvlData[i][j].getWidth() + lvlData[i][j].getX()) && y >= lvlData[i][j].getX() && y < (lvlData[i][j].getY() + lvlData[i][j].getHeight()) ) {
                    return lvlData[i][j]; //se le coordinate degli angoli si intersecano, l'entità
                }
            }
        }
        int dynamicEntityDim = 8; // TODO dynamicEntityDim non serve a una ciola, perché è meglio mettere una ArrayList di dynamicEntity()
        for (int i = 0; i < dynamicEntityDim; i++) {
            if (x >= dynamicEntity[i].getX() && x < (dynamicEntity[i].getWidth() + dynamicEntity[i].getX()) && y >= dynamicEntity[i].getX() && y < (dynamicEntity[i].getY() + dynamicEntity[i].getHeight()) ) {
                return dynamicEntity[i];
            }
        }
        return null;
    }

    //cosa succede se il proiettile tocca una superfice?
    public boolean collidesWithSmth (Entity[][] lvlData, Entity[] dynamicEntity, JPanel[][] mapBase, int x, int y) { // controlla l'esplosione del proiettile
        // dynamicEntity potrebbe essere l'insieme dielle entità non fisse che girano per la mappa, come proiettili, player e in caso item speciali
        // do per scontato che ogni angolo appartiene a una sola entità alla volta (o player o muro, non entrambe)
        if (whatIsFacing(lvlData, dynamicEntity, x, y).getEntity() != entities.WALL) {
            if(whatIsFacing(lvlData, dynamicEntity, x + WIDTHROKET, y + HEIGHTROKET).getEntity() != entities.WALL) {
                if(whatIsFacing(lvlData, dynamicEntity, x + WIDTHROKET, y).getEntity() != entities.WALL) {
                    if(whatIsFacing(lvlData, dynamicEntity, x, y + HEIGHTROKET).getEntity() != entities.WALL)
                        return false;
                }
            }
        }
        destroyInRange();
        return true;
    }


    public void destroyInRange () {
        // TODO spacca tutto quello che è in range dell'esplosione

    }

}
