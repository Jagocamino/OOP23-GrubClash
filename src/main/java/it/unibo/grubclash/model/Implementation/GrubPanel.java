package it.unibo.grubclash.model.Implementation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.grubclash.controller.Implementation.Allowed;
import it.unibo.grubclash.controller.Implementation.Heal;
import it.unibo.grubclash.controller.Implementation.ItemSpawner;
import it.unibo.grubclash.controller.Implementation.Physic;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.controller.Implementation.Trap;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;
import it.unibo.grubclash.view.Implementation.FrameManagerImpl;
import it.unibo.grubclash.view.Implementation.MapBuilder;
import it.unibo.grubclash.view.Implementation.UI;
import it.unibo.grubclash.view.Implementation.EnumEntity.orientation;
import it.unibo.grubclash.view.Implementation.EnumEntity.status;

// Pannello di gioco
public class GrubPanel extends JPanel implements Runnable {

    //FM creo il FrameManager visto che creando l'interfaccia non posso avere più i metodi statici
    public FrameManager frameManager = new FrameManagerImpl();

    // FPS
    static final int FPS = 60;

    // Thread principale
    Thread gameThread; 

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
    private int numAlivePlayers = 0;
    private int idOfTheWinner;

    //Traps
    public ArrayList<Trap> traps;

    //Heals
    public ArrayList<Heal> heals;

    //UI
    UI ui = new UI(this);

    //PHYSIC
    public Physic physic = new Physic(this);


    //COLLISION CHECKER
    public Allowed allowed = new Allowed(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get(), MapBuilder.ROWS, MapBuilder.COLS);

    //VARIABLES
    public boolean turnBegin = false;
    private int numTraps = 5;
    private int numHeals = 3;

    public GrubPanel(int playerCount) {

        this.playerCount = playerCount;
        this.idOfTheWinner = 99;
        this.numAlivePlayers = 0;
        keyHandelers = new ArrayList<>();
        Allowed.setMapBase(MapBuilder.getMapBase());
        Allowed.addMapBase(MapBuilder.getEntityMatrix()); //creo la matrice delle entità (20x20)
        

        //ITEMSPAWNER
        ItemSpawner itemSpawner = new ItemSpawner(MapBuilder.ROWS, MapBuilder.COLS, numTraps, Allowed.getLvlData());
        ItemSpawner itemSpawner2 = new ItemSpawner(MapBuilder.ROWS, MapBuilder.COLS, numHeals, Allowed.getLvlData());
        
        itemSpawner.generateSpawnLocation(true);
        itemSpawner2.generateSpawnLocation(false);
        

        //Allowed.delateSpawnpoint(); //sostituisco i player con il cielo nella matrice 20x20, non so se metterlo TODO

        players = new ArrayList<>();
        for(int i = 0; i < playerCount; i++) {
            keyHandelers.add(new KeyHandler(this));
            players.add(new Player(this, i, keyHandelers.get(i)));
        } 
        traps = new ArrayList<>();
        
        for(int i = 0; i < numTraps; i++){
            
            traps.add(new Trap(this, i+1));
        }

        heals = new ArrayList<>();

        for(int i = 0; i < numHeals; i++){
            
            heals.add(new Heal(this, i+1));
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
                for (Player player : players) {
                    if(player.working == status.ALIVE){
                        numAlivePlayers++;
                        idOfTheWinner = player.getId();
                    }
                }
                if(numAlivePlayers == 1){
                    gameFinished();
                }else{
                    for(Player p : players) {
                        if(p.working == status.ALIVE){
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

                            while(numCicles <= 5 && p.working == status.ALIVE){   //algoritmo da rivedere ma la sostanza c'è TODO 
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
                            p.setDirection(orientation.DOWN);
                            p.jump1Counter = 0;
                            p.jump2Counter = 0;
                            p.gravity=true;
                            p.canMove = true;
                        }
                        
                    }
                    numAlivePlayers = 0;
                }
            }
        }).start();
    }

    private void gameFinished() {
        /* Object[] options = {"Esci", "Ricomincia"};
        int choice = JOptionPane.showOptionDialog(null, "Congratulazioni!\n Il giocatore numero " + idOfTheWinner + " ha vinto!",
            "VITTORIA", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            new ImageIcon("src" + FrameManagerImpl.FS + "main" + FrameManagerImpl.FS + "resources" + FrameManagerImpl.FS + "players" + FrameManagerImpl.FS + "player0" + FrameManagerImpl.FS + "Grub_pl_0_stand_1.png"),
            options, options[0]);
        if(choice == 0){
            System.exit(0);
        }else if(choice == 1){
            MapBuilder.getMapContainer().dispose();
            Thread.currentThread().interrupt();
            System.out.println(gameThread.isAlive());
            new Menu();
        } */
        frameManager.showMessageBox("VITTORIA", "Congratulazioni!\n Il giocatore numero " + idOfTheWinner + " ha vinto!", JOptionPane.OK_OPTION);
        System.exit(0);
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
        for(Heal h : heals) {
            if(h.gravity){
                physic.checkTerrain(h);
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
        for(Heal h : heals) {
            h.draw(g2d);
        }

        ui.draw(g2d);

        g2d.dispose();

    }
}
