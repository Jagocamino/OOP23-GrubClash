package it.controller;

//   LA POSIZIONE DEL GIOCATORE VIENE PASSATA DOPO IL CONTROLLO NEL MAP BUILDER

public class Player {

    private int id;
    private float x, y;

    public Player(int id) {
        this.id = id;
    }


    public void update() {
        /*
        updatePos();
        updateHitbox();
        updateAnimationTick();
        setAnimation();
        */
    }

    public void render() {
        //future implementation
        //costruisco l'hitbox sopra il player
        /*
        drawHitbox(g);
        */
    }

    public int getId() {
        return id;
    }

    public float getPosX() {
        return x;
    }

    public float getPosY() {
        return y;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void newPos(float x, float y) { //ogni volta che viene chiamata si passa una nuova posizione
        this.x = x;
        this.y = y;
    }


    //da settare le animazioni (o l'immagine)
    //initClasses prima del game loop

    private void initPlayers(int numerogiocatori) { //per ogni giocatore del menu a tendina, si crea un oggetto player
        Player[] playersarray = new Player[numerogiocatori];
        for (int i=0 ; i<numerogiocatori ; i++) { //per ogni giocatore segno la posizione iniziale
            playersarray[i].setId(i+1);
        }
    }

    //Il "level data", ovvero l'hitbox e altre conse non visibili a schermo, saranno dentro il player stesso
    private int[][] lvlData; //sarebbe più corretto chiamarlo "map data"

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;        
    }

    
}
