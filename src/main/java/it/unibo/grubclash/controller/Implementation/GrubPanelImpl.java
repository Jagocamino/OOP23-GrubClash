package it.unibo.grubclash.controller.Implementation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.grubclash.controller.Application_Programming_Interface.GrubPanel;
import it.unibo.grubclash.model.Application_Programming_Interface.Physic;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.EntityImpl;
import it.unibo.grubclash.model.Implementation.ItemSpawnerImpl;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.model.Implementation.PhysicImpl;
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
public class GrubPanelImpl extends JPanel implements Runnable, GrubPanel {

    private static final int FPS = 60;
    private static final int TIME_LIMIT = 1000000000;
    private static final int RESET = 0;
    private static final int NUMBER_OF_CICLES = 5;
    private static final int SECONDS_CONVERTER = 33;
    private static final int TIME_SLEEP = 20;

    private FrameManager frameManager = FrameManagerImpl.getInstance();
    private Thread gameThread; 
    private ArrayList<KeyHandler> keyHandlers;
    private ArrayList<PlayerImpl> players;
    private int playerCount;
    private int numPlayerTurn;
    private int secondsTurn = RESET;
    private int numAlivePlayers = RESET;
    private int idOfTheWinner;
    private UIInterface ui = new UI(this);
    private Physic physic = new PhysicImpl(this);
    private boolean turnBegin = false;
    
    /**
    * Need to initialize allowed, even if we are not going to use it as a variable
    */
    @SuppressWarnings("unused")
    private Allowed allowed = new Allowed(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get(), MapBuilderImpl.ROWS, MapBuilderImpl.COLS);

    /**
    * Constructor for GrubPanel
    * @param playerCount number of players
    */
    public GrubPanelImpl(int playerCount) {

        this.playerCount = playerCount;
        this.idOfTheWinner = 99;
        this.numAlivePlayers = RESET;
        keyHandlers = new ArrayList<>();
        Allowed.setMapBase(MapBuilderImpl.getMapBase());

        ItemSpawnerImpl itSpawn = new ItemSpawnerImpl();
        itSpawn.generateSpawnLocation(Allowed.getROWS(), Allowed.getCOLS(), MapBuilderImpl.getItemNum(), MapBuilderImpl.getEntityMatrix());

        Allowed.addMapBase(MapBuilderImpl.getEntityMatrix()); 
        

        players = new ArrayList<>();
        for(int i = 0; i < playerCount; i++) {
            keyHandlers.add(new KeyHandler());
            players.add(new PlayerImpl(i, keyHandlers.get(i)));
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
        for (Optional<EntityImpl> skeleton : Allowed.getSkeleton()) {
            if (skeleton.isPresent()) {
                skeleton.get().update(); 
            }
        }
        for (Optional<EntityImpl> zombie : Allowed.getZombie()) {
            if (zombie.isPresent()) {
                zombie.get().update(); 
            }
        }
    }

    /**
     * Checks how many players are alive, then ends the game or not, choosing the winner
     */
    private void updateGameState() {
        
        for (PlayerImpl player : players) {
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
        ArrayList<EntityImpl> toDelete = new ArrayList<EntityImpl>();
        for (Optional<EntityImpl> entity : Allowed.getDynamicEntities()) {
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
        ArrayList<EntityImpl> toDelete = new ArrayList<EntityImpl>();
        for (Optional<EntityImpl> entity : Allowed.getDynamicEntities()) {
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
    private void deleteDynamicEntity (ArrayList<EntityImpl> toDelete) {
        for (EntityImpl entity : toDelete) {
            
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
                for(PlayerImpl p : players) {
                    if(p.isAlive()){
                        numPlayerTurn = p.getId();
                        int numCicles = RESET;   
                        long wait = System.nanoTime();
                            
                        while(System.nanoTime() - wait <= 2 * TIME_LIMIT) {
                            turnBegin = true;
                        } 

                        MapBuilderImpl.getCh();
                            
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
            MapBuilderImpl.getMapContainer().dispose();
            Thread.currentThread().interrupt();
            new Menu();
        }
    }

    /**
     * resets the variables 
     */
    private void resetVariables(PlayerImpl p){

        secondsTurn = RESET;
        p.getKeyHandler().setLeftPressed(false);
        p.getKeyHandler().setRightPressed(false);
        p.getKeyHandler().setSpacePressed(false);
        p.getKeyHandler().setShootPressed(false);
        this.removeKeyListener(p.getKeyHandler());
        p.setDirection(Orientation.DOWN);
        p.setJump1Counter(RESET);
        p.setJump2Counter(RESET);
        p.setGravity(true);
        p.setCanMove(true);
        p.setAlreadyShot(false);
        p.setAlreadyDug(false);
        p.setShovelAnimation(false);
        p.setShovelCounter(RESET);
        p.setCooldownDig(false);
    }

    /**
     * Updates the physic of the dynamic entities, players and mobs
     */
    private void updatePhysic() {
        for(Optional<EntityImpl> entity : Allowed.getDynamicEntities()) {
            if(entity.isPresent() && entity.get().isThereGravity()){
                physic.checkTerrain(entity.get());
            }
        }
        for(PlayerImpl p : players){
            physic.checkDeath(p);
        }

        for (Optional<EntityImpl> skeleton : Allowed.getSkeleton()) {
            if (skeleton.isPresent()) {
                physic.checkTerrain(skeleton.get()); 
            }
        }
        for (Optional<EntityImpl> zombie : Allowed.getZombie()) {
            if (zombie.isPresent()) {
                physic.checkTerrain(zombie.get()); 
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
        for(PlayerImpl p : players) {
            p.draw(g2d);
            p.getLife().draw(g2d);
            p.getWeapon().get().draw(g2d);
            if(!p.getWeapon().get().getRocket().isEmpty()){
                p.getWeapon().get().getRocket().get().draw(g2d);
            }
        }

        //DRAW OBJECTS
        for (Optional<EntityImpl> entity : Allowed.getDynamicEntities()) {
            if (entity.isPresent() && !Allowed.isPlayer(entity.get()) && entity.get().getEntity() != Entities.PROJECTILE) {
                entity.get().draw(g2d);   
            }
        }

        //DRAW MOBS
        for (Optional<EntityImpl> mob : Allowed.getSkeleton()) {
            if (mob.isPresent()) {
                mob.get().draw(g2d);
                mob.get().getLife().draw(g2d);   
            }
        }
        for (Optional<EntityImpl> zombie : Allowed.getZombie()) {
            if (zombie.isPresent()) {
                zombie.get().draw(g2d);
                zombie.get().getLife().draw(g2d);   
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
    public ArrayList<PlayerImpl> getPlayers() {
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
