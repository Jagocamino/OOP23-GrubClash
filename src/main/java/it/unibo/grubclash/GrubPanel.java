package it.unibo.grubclash;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

//pannello di gioco
public class GrubPanel extends JPanel implements Runnable{

    //dimensioni schermo
    static final int width_game = 1702;
    static final int height_game = 956;

    //FPS
    int FPS = 60;

    //Thread principale
    Thread gameThread; 

    //Stato del gioco
    public int gameState;
    public final int initialState = 0;
    public final int playState = 1;

    //schermo
    BufferedImage screen;
    Graphics2D g2d;

    public GrubPanel (){
        this.setSize(width_game, height_game);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    //appena avviata l'applicazione si va sulla schermata iniziale
    public void setupGame(){

        gameState = initialState;

        screen = new BufferedImage(width_game, height_game, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D)screen.getGraphics();
    }

    public void startGameThread () {

        gameThread = new Thread(this);
        gameThread.start();
    } 

    //metodo "delta" per creare un game loop
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread!=null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update(); //aggiorna eventi e posizioni
                draw();  // disegna gli aggiornamenti
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

    private void draw() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawToScreen'");
    }

    private void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
