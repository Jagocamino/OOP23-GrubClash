package it.unibo.grubclash.controller.Implementation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.grubclash.controller.Application_Programming_Interface.GrubPanelInter;
import it.unibo.grubclash.model.Application_Programming_Interface.PhysicInterface;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.ItemSpawner;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.Physic;
import it.unibo.grubclash.model.Implementation.Sound;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;
import it.unibo.grubclash.model.Implementation.EnumEntity.Orientation;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;
import it.unibo.grubclash.view.Application_Programming_Interface.UIInterface;
import it.unibo.grubclash.view.Implementation.FrameManagerImpl;
import it.unibo.grubclash.view.Implementation.Menu;
import it.unibo.grubclash.view.Implementation.UI;

/**
 * Class implementing the GrubPanel methods.
 */
public class GrubPanel extends JPanel implements Runnable, GrubPanelInter {

    private static final int FPS = 60;
    private static final int TIME_LIMIT = 1000000000;
    private static final int RESET = 0;
    private static final int NUMBER_OF_CICLES = 5;
    private static final int SECONDS_CONVERTER = 33;
    private static final int TIME_SLEEP = 20;

    private FrameManager frameManager = FrameManagerImpl.getInstance();
    private Thread gameThread; 
    private ArrayList<KeyHandler> keyHandlers;
    private ArrayList<Player> players;
    private int playerCount;
    private int numPlayerTurn;
    private int secondsTurn = RESET;
    private int numAlivePlayers = RESET;
    private int idOfTheWinner;
    private UIInterface ui = new UI(this);
    private PhysicInterface physic = new Physic(this);
    private boolean turnBegin = false;
    
    /**
    * Need to initialize allowed, even if we are not going to use it as a variable
    */
    @SuppressWarnings("unused")
    private Allowed allowed = new Allowed(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get(), MapBuilder.ROWS, MapBuilder.COLS);

    /**
    * Constructor for GrubPanel
    * @param playerCount number of players
    */
    public GrubPanel(int playerCount) {

        this.playerCount = playerCount;
        this.idOfTheWinner = 99;
        this.numAlivePlayers = RESET;
        keyHandlers = new ArrayList<>();
        Allowed.setMapBase(MapBuilder.getMapBase());

        ItemSpawner itSpawn = new ItemSpawner();
        itSpawn.generateSpawnLocation(Allowed.getROWS(), Allowed.getCOLS(), MapBuilder.getItemNum(), MapBuilder.getEntityMatrix());

        Allowed.addMapBase(MapBuilder.getEntityMatrix()); 
        

        players = new ArrayList<>();
        for(int i = 0; i < playerCount; i++) {
            keyHandlers.add(new KeyHandler(this));
            players.add(new Player(i, keyHandlers.get(i)));
        } 


        this.setSize(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGameThread () {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Delta method to create a game loop
     */
    @Override
    public void run() {
        double drawInterval = (double) TIME_LIMIT / FPS;
        double delta = RESET;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = RESET;

        update();

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                repaint();  
                updatePhysic();
                updateProj();
                updateDynamicEntities();
                updateGameState();
                updateMob();
                delta--;
            }

            if(timer >= TIME_LIMIT){
                timer = RESET;
            }
        }
    }

    /**
     * Updates the mob's movement and interaction
     */
    private void updateMob() {
        for (Optional<Entity> mob : Allowed.getMob()) {
            if (mob.isPresent()) {
                mob.get().update(); 
            }
        }
    }

    /**
     * Checks how many players are alive, then ends the game or not, choosing the winner
     */
    private void updateGameState() {
        
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
        }
        numAlivePlayers = RESET;
    }

    /**
     * Updates the projectile's movement and interaction
     */
    private void updateProj() {
        ArrayList<Entity> toDelete = new ArrayList<Entity>();
        for (Optional<Entity> entity : Allowed.getDynamicEntities()) {
            if(entity.isPresent() && entity.get().getEntity() == Entities.PROJECTILE){
                if (entity.get().isAlive()) {
                    entity.get().update();
                } else {
                    toDelete.add(entity.get());
                }
            }
        }
        deleteDynamicEntity(toDelete);
    }

    /**
     * Removes the dynamic entities whenever their state is set to dead
     */
    private void updateDynamicEntities(){
        ArrayList<Entity> toDelete = new ArrayList<Entity>();
        for (Optional<Entity> entity : Allowed.getDynamicEntities()) {
            if(entity.isPresent() && entity.get().getEntity() == Entities.TRAP){
                if (!entity.get().isAlive()) {
                    toDelete.add(entity.get());
                }
            }
        }
        deleteDynamicEntity(toDelete);
    }

    /**
     * Supports the previous method
     */
    private void deleteDynamicEntity (ArrayList<Entity> toDelete) {
        for (Entity entity : toDelete) {
            
            Allowed.removeDynamicEntity(entity);
        }
        toDelete.removeAll(toDelete);
    }

    /**
     * Updates the players based on their turn
     */
    private void update() {
        new Thread(() -> {
            while(gameThread != null) {
                for(Player p : players) {
                    if(p.isAlive()){
                        numPlayerTurn = p.getId();
                        int numCicles = RESET;   
                        long wait = System.nanoTime();
                            
                        while(System.nanoTime() - wait <= 2 * TIME_LIMIT) {
                            turnBegin = true;
                        } 

                        MapBuilder.getCh();
                            
                        turnBegin = false;
                        this.addKeyListener(p.getKeyHandler());
                        int counter = RESET;
                        while(numCicles <= NUMBER_OF_CICLES && p.isAlive()){
                            long start = System.nanoTime();
                            while(System.nanoTime() - start <= 2 * TIME_LIMIT) {
                                counter++;
                                p.update();
                                Allowed.touchDynamicEntity(p);
                                if(counter % SECONDS_CONVERTER == 0){  
                                    secondsTurn++;
                                }
                                try {
                                    Thread.sleep(TIME_SLEEP);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            numCicles++;
                        }
                        resetVariables(p);
                    }
                }
                Allowed.clearDynamicEntities();
            }
        }).start();
    }

    /**
     * Manages the option pane at the end of the game and his behavior
     */
    private void gameFinished(boolean win) {
        Sound.setFile(6);
        Sound.play();
        gameThread = null;
        Object[] options = {"Esci", "Ricomincia"};
        int choice;
        final char FS = File.separatorChar;
        if(win){
            choice = JOptionPane.showOptionDialog(null, "Congratulazioni!\n Il giocatore numero " + (idOfTheWinner + 1) + " ha vinto!",
            "VITTORIA", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player0" + FS + "Grub_pl_0_stand_1.png"),
            options, options[0]);
        }
        else{
            choice = JOptionPane.showOptionDialog(null, "NESSUN GIOCATORE HA VINTO LA PARTITA! ",
            "ENDGAME", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player0" + FS + "Grub_pl_0_stand_1.png"),
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

    /**
     * resets the variables 
     */
    private void resetVariables(Player p){

        secondsTurn = RESET;
        p.getKeyHandler().leftPressed = false;
        p.getKeyHandler().rightPressed = false;
        p.getKeyHandler().spacePressed = false;
        p.getKeyHandler().shootPressed = false;
        this.removeKeyListener(p.getKeyHandler());
        p.setDirection(Orientation.DOWN);
        p.jump1Counter = RESET;
        p.jump2Counter = RESET;
        p.gravity=true;
        p.canMove = true;
        p.alreadyShot = false;
        p.alreadyDug = false;
        p.shovelAnimation = false;
        p.shovelCounter = RESET;
        p.cooldownDig = false;
    }

    /**
     * Updates the physic of the dynamic entities, players and mobs
     */
    private void updatePhysic() {
        for(Optional<Entity> entity : Allowed.getDynamicEntities()) {
            if(entity.isPresent() && entity.get().gravity){
                physic.checkTerrain(entity.get());
            }
        }
        for(Player p : players){
            physic.checkDeath(p);
        }

        for (Optional<Entity> mob : Allowed.getMob()) {
            if (mob.isPresent()) {
                physic.checkTerrain(mob.get()); 
            }
        }
    }

    /**
     * Draws players, objects, mobs and the UI on the screen
     */
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        //DRAW PLAYER
        for(Player p : players) {
            p.draw(g2d);
            p.life.draw(g2d);
            p.getWeapon().get().draw(g2d);
            if(!p.getWeapon().get().getRocket().isEmpty()){
                p.getWeapon().get().getRocket().get().draw(g2d);
            }
        }

        //DRAW OBJECTS
        for (Optional<Entity> entity : Allowed.getDynamicEntities()) {
            if (entity.isPresent() && !Allowed.isPlayer(entity.get()) && entity.get().getEntity() != Entities.PROJECTILE) {
                entity.get().draw(g2d);   
            }
        }

        //DRAW MOBS
        for (Optional<Entity> mob : Allowed.getMob()) {
            if (mob.isPresent()) {
                mob.get().draw(g2d);
                mob.get().life.draw(g2d);   
            }
        }

        //DRAW UI
        ui.draw(g2d);

        g2d.dispose();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FrameManager getFrameManager() {
        return frameManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumPlayerTurn() {
        return numPlayerTurn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSecondsTurn() {
        return secondsTurn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTurnBegin() {
        return turnBegin;
    }
}
