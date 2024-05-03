package it.unibo.grubclash.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import it.unibo.grubclash.controller.Player;
import it.unibo.grubclash.view.FrameManager;

// Pannello di gioco
public class GrubPanel extends JPanel implements Runnable {

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
    ArrayList<Player> players;
    int playerCount;

    public GrubPanel(int playerCount) {
        this.playerCount = playerCount;
        keyHandelers = new ArrayList<>();
        players = new ArrayList<>();
        for(int i = 0; i < playerCount; i++) {
            keyHandelers.add(new KeyHandler(this));
            players.add(new Player(this, i, keyHandelers.get(i)));
        }

        this.setSize(900, 900);
        this.setDoubleBuffered(true);
        //this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Appena avviata l'applicazione si va sulla schermata iniziale
    /* public void setupGame(){

        gameState = initialState;

        screen = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D)screen.getGraphics();
    } */

    public void startGameThread () {

        gameThread = new Thread(this);
        gameThread.start();
    } 

    //metodo "delta" per creare un game loop
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
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
                    long start = System.nanoTime();
                    this.addKeyListener(p.getKeyH());

                    while(System.nanoTime() - start <= 2000000000) {
                        p.update();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    p.getKeyH().leftPressed = false;
                    p.getKeyH().rightPressed = false;
                    this.removeKeyListener(p.getKeyH());
                }
            }
        }).start();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);


        /* g.setColor(Color.YELLOW);

        g.drawRect(100, 100, 200, 50); */

        Graphics2D g2d = (Graphics2D)g;

        for(Player p : players) {
            p.draw(g2d);
        }

        g2d.dispose();

    }
}
