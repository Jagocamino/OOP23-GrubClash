package it.unibo.grubclash.model.Implementation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import it.unibo.grubclash.controller.Implementation.Allowed;
import it.unibo.grubclash.controller.Implementation.ItemSpawner;
import it.unibo.grubclash.controller.Implementation.Physic;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.controller.Implementation.Trap;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;
import it.unibo.grubclash.view.Implementation.FrameManagerImpl;
import it.unibo.grubclash.view.Implementation.MapBuilder;
import it.unibo.grubclash.view.Implementation.UI;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;

// Pannello di gioco
public class GrubPanel extends JPanel implements Runnable {

    //FM creo il FrameManager visto che creando l'interfaccia non posso avere più i metodi statici
    FrameManager frameManager = new FrameManagerImpl();

    // FPS
    static final int FPS = 60;

    // Thread principale
    Thread gameThread; 

    // Stato del gioco
    public int gameState;
    public final int initialState = 0;
    public final int playState = 1;

    // Schermo
    BufferedImage screen;
    Graphics2D g2d;

    //Key Handler
    ArrayList<KeyHandler> keyHandelers;

    //Players
    public ArrayList<Player> players;
    public int playerCount;
    public int numPlayerTurn;
    public int secondsTurn = 0;

    //Traps
    public ArrayList<Trap> traps;

    //UI
    UI ui = new UI(this);

    //PHYSIC
    public Physic physic = new Physic(this);


    //COLLISION CHECKER
    public Allowed allowed = new Allowed(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get(), MapBuilder.ROWS, MapBuilder.COLS);

    //VARIABLES
    public boolean turnBegin = false;
    private int numItems = 5;

    public GrubPanel(int playerCount) {

        this.playerCount = playerCount;
        keyHandelers = new ArrayList<>();
        Allowed.setMapBase(MapBuilder.getMapBase());
        Allowed.addMapBase(MapBuilder.getEntityMatrix()); //creo la matrice delle entità (20x20)
        

        //ITEMSPAWNER
        ItemSpawner itemSpawner = new ItemSpawner(MapBuilder.ROWS, MapBuilder.COLS, numItems, Allowed.getLvlData());
        
        itemSpawner.generateSpawnLocation();
        

        //Allowed.delateSpawnpoint(); //sostituisco i player con il cielo nella matrice 20x20, non so se metterlo TODO

        players = new ArrayList<>();
        for(int i = 0; i < playerCount; i++) {
            keyHandelers.add(new KeyHandler(this));
            players.add(new Player(this, i, keyHandelers.get(i)));
        } 
        traps = new ArrayList<>();
        
        for(int i = 0; i < numItems; i++){
            
            traps.add(new Trap(this, i+1));
        }


        this.setSize(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void startGameThread () {

        gameThread = new Thread(this);
        gameThread.start();
    } 

    //metodo "delta" per creare un game loop
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        update();

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                repaint();  // disegna gli aggiornamenti
                updatePhysic();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        // TODO: RIVEDI
        new Thread(() -> {
            while(gameThread != null) {
                for(Player p : players) {
                    numPlayerTurn = p.getId();
                    int numCicles = 0;   //5 cicli da 2 secondi => 10 secondi di round
                    long wait = System.nanoTime();
                    while(System.nanoTime() - wait <= 2000000000) {
                        turnBegin = true;
                    } //due secondi di attesa prima che inizi il turno
                    //TODO aggiungere scritta che dice "sta per iniziare il turno ..."
                    turnBegin = false;
                    this.addKeyListener(p.getKeyH());
                    int counter = 0;

                    while(numCicles <= 5){   //algoritmo da rivedere ma la sostanza c'è TODO 
                        long start = System.nanoTime();
                        while(System.nanoTime() - start <= 2000000000) {
                            counter++;
                            p.update();
                            if(counter % 33 == 0){   //forse c'è un modo migliore per farlo 
                                secondsTurn++;
                            }
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        numCicles++;
                    }

                    secondsTurn = 0;
                    p.getKeyH().leftPressed = false;
                    p.getKeyH().rightPressed = false;
                    p.getKeyH().spacePressed = false;
                    this.removeKeyListener(p.getKeyH());
                    p.direction = orientation.DOWN;
                    p.jump1Counter = 0;
                    p.jump2Counter = 0;
                    p.gravity=true;
                    p.canMove = true;
                }
            }
        }).start();
    }

    private void updatePhysic() {
        for(Player p : players) {
            if(p.gravity){
                physic.checkTerrain(p);
            }
        }
        for(Trap t : traps) {
            if(t.gravity){
                physic.checkTerrain(t);
            }
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        for(Player p : players) {
            p.draw(g2d);
            p.life.draw(g2d);
        }
        for(Trap t : traps) {
            t.draw(g2d);
        }

        ui.draw(g2d);

        g2d.dispose();

    }
}
