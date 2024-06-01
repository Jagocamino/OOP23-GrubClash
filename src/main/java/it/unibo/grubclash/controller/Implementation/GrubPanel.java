package it.unibo.grubclash.controller.Implementation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.grubclash.controller.Application_Programming_Interface.GrubPanelInter;
import it.unibo.grubclash.model.Application_Programming_Interface.EntityInterface;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.ItemSpawner;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.Physic;
import it.unibo.grubclash.model.Implementation.Sound;
import it.unibo.grubclash.model.Implementation.EnumEntity.entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.orientation;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;
import it.unibo.grubclash.view.Implementation.FrameManagerImpl;
import it.unibo.grubclash.view.Implementation.Menu;
import it.unibo.grubclash.view.Implementation.UI;

// Pannello di gioco
public class GrubPanel extends JPanel implements Runnable, GrubPanelInter {

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

    //UI
    UI ui = new UI(this);

    //PHYSIC
    public Physic physic = new Physic(this);


    //COLLISION CHECKER
    public Allowed allowed = new Allowed(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get(), MapBuilder.ROWS, MapBuilder.COLS);

    //VARIABLES
    public boolean turnBegin = false;

    //MUSIC AND SFX
    Sound music = new Sound();
    Sound soundEffect = new Sound();


    public GrubPanel(int playerCount) {

        this.playerCount = playerCount;
        this.idOfTheWinner = 99;
        this.numAlivePlayers = 0;
        keyHandelers = new ArrayList<>();
        Allowed.setMapBase(MapBuilder.getMapBase());

        ItemSpawner itSpawn = new ItemSpawner();
        itSpawn.generateSpawnLocation(Allowed.getROWS(), Allowed.getCOLS(), MapBuilder.getItemNum(), MapBuilder.getEntityMatrix());

        Allowed.addMapBase(MapBuilder.getEntityMatrix()); //creo la matrice delle entità (20x20)
        

        players = new ArrayList<>();
        for(int i = 0; i < playerCount; i++) {
            keyHandelers.add(new KeyHandler(this));
            players.add(new Player(this, i, keyHandelers.get(i)));
        } 


        this.setSize(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    //parte il gameThread principale
    @Override
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
                updateProj();
                updateDynamicEntities();
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

    private void updateProj() {
        ArrayList<Entity> toDelete = new ArrayList<Entity>();
        for (Entity entity : Allowed.getDynamicEntities()) {
            if(entity.getEntity() == entities.PROJECTILE){
                if (entity.isAlive()) {
                    entity.update();
                } else {
                    toDelete.add(entity);
                }
            }
        }
        deleteDynamicEntity(toDelete);
    }

    private void updateDynamicEntities(){
        ArrayList<Entity> toDelete = new ArrayList<Entity>();
        for (Entity entity : Allowed.getDynamicEntities()) {
            if(entity.getEntity() == entities.TRAP){
                if (!entity.isAlive()) {
                    toDelete.add(entity);
                }
            }
        }
        deleteDynamicEntity(toDelete);
    }

    private void deleteDynamicEntity (ArrayList<Entity> toDelete) {
        for (EntityInterface entity : toDelete) {
            Allowed.removeDynamicEntity(entity);
        }
        toDelete.removeAll(toDelete);
    }

    private void update() {
        new Thread(() -> {
            while(gameThread != null) {
                for (Player player : players) {
                    if(player.isAlive()){
                        numAlivePlayers++;
                        idOfTheWinner = player.getId();
                    }
                }
                if(numAlivePlayers == 1){
                    gameFinished(true);
                }else if(numAlivePlayers == 0){
                    gameFinished(false);
                }else{
                    for(Player p : players) {
                        if(p.isAlive()){
                            numPlayerTurn = p.getId();
                            int numCicles = 0;   //6 cicli da 2 secondi => 12 secondi di round
                            long wait = System.nanoTime();
                            while(System.nanoTime() - wait <= 2000000000) {
                                turnBegin = true;
                            } //due secondi di attesa prima che inizi il turno
                            if(p.isAlive())
                                MapBuilder.getCh();
                            turnBegin = false;
                            this.addKeyListener(p.getKeyH());
                            int counter = 0;
                            while(numCicles <= 5 && p.isAlive()){
                                long start = System.nanoTime();
                                while(System.nanoTime() - start <= 2000000000) {
                                    counter++;
                                    p.update();
                                    Allowed.touchDynamicEntity(p);
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
                            resetVariables(p);
                        }
                    }

                    numAlivePlayers = 0;
                }
            }
        }).start();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }

    private void gameFinished(boolean win) {
        gameThread = null;
        Object[] options = {"Esci", "Ricomincia"};
        int choice;
        if(win){
            choice = JOptionPane.showOptionDialog(null, "Congratulazioni!\n Il giocatore numero " + (idOfTheWinner + 1) + " ha vinto!",
            "VITTORIA", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            new ImageIcon("src" + FrameManagerImpl.FS + "main" + FrameManagerImpl.FS + "resources" + FrameManagerImpl.FS + "players" + FrameManagerImpl.FS + "player0" + FrameManagerImpl.FS + "Grub_pl_0_stand_1.png"),
            options, options[0]);
        }
        else{
            choice = JOptionPane.showOptionDialog(null, "NESSUN GIOCATORE HA VINTO LA PARTITA! ",
            "ENDGAME", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            new ImageIcon("src" + FrameManagerImpl.FS + "main" + FrameManagerImpl.FS + "resources" + FrameManagerImpl.FS + "players" + FrameManagerImpl.FS + "player0" + FrameManagerImpl.FS + "Grub_pl_0_stand_1.png"),
            options, options[0]);
        }
        if(choice == 0){
            System.exit(0);
        }else if(choice == 1){
            MapBuilder.getMapContainer().dispose();
            Thread.currentThread().interrupt();
            new Menu();
        }
    }

    private void resetVariables(Player p){

        secondsTurn = 0;
        p.getKeyH().leftPressed = false;
        p.getKeyH().rightPressed = false;
        p.getKeyH().spacePressed = false;
        p.getKeyH().shootPressed = false;
        this.removeKeyListener(p.getKeyH());
        p.setDirection(orientation.DOWN);
        p.jump1Counter = 0;
        p.jump2Counter = 0;
        p.gravity=true;
        p.canMove = true;
        p.alreadyShot = false;
        p.alreadyDug = false;
        p.shovelAnimation = false;
        p.shovelCounter = 0;
        p.cooldownDig = false;
    }

    private void updatePhysic() {
        for(Entity p : Allowed.getDynamicEntities()) {
            if(p.gravity){
                physic.checkTerrain(p);
            }
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        for (EntityInterface entity : Allowed.getDynamicEntities()) {
            if (!Allowed.isPlayer(entity)) {
                entity.draw(g2d);   
            }
        }

        for(Player p : players) {
            p.draw(g2d);
            p.life.draw(g2d);
            p.getWeapon().get().draw(g2d);
            if(!p.getWeapon().get().getRocket().isEmpty()){
                p.getWeapon().get().getRocket().get().draw(g2d);
            }
        }

        ui.draw(g2d);

        g2d.dispose();

    }

    
}
