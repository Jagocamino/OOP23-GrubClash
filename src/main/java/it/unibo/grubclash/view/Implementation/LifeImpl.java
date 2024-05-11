package it.unibo.grubclash.view.Implementation;

import it.unibo.grubclash.controller.Implementation.Entity;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.GrubPanel;
import it.unibo.grubclash.view.Implementation.LifeImpl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LifeImpl implements it.unibo.grubclash.view.Application_Programming_Interface.Life {

    int playerCount;
    GrubPanel grubPanel;
    Graphics2D g2d;
    String color_game = "#EF7B10";
    private final Font snapITCFont;
    private final Player player;

    //vita di ogni giocatore che va da 10 a 0
    int life;
    private final int value=2;

    public LifeImpl (GrubPanel grubPanel,Player player){
        this.playerCount= grubPanel.playerCount;
        this.grubPanel=grubPanel;
        this.life=10;
        this.player=player;

        //Font
        snapITCFont = new Font("Snap ITC", Font.BOLD, 24);


    }

    @Override public void draw(Graphics2D g2d){

        this.g2d = g2d;

        g2d.setFont(snapITCFont);
        g2d.setColor(Color.decode(color_game));

        drawLifePlayer();

    }

    private void drawLifePlayer() {
        //mi salvo tutte le varie immagini della vita
        final char FS = File.separatorChar;
        BufferedImage life100 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_100.png", 100, 15);
        BufferedImage life80 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_80.png", 100, 15);
        BufferedImage life60 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_60.png", 100, 15);
        BufferedImage life40 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_40.png", 100, 15);
        BufferedImage life20 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_20.png", 100, 15);
        BufferedImage life0 = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "Life" + FS + "life_10.png", 100, 15);

        //disegno la vita per ogni giocatore
        for(int i=1; i<=playerCount;i++){
            switch (life){
                case 10:
                    g2d.drawImage(life100, player.x-25, player.y-15,null);
                    break;
                case 8:
                    g2d.drawImage(life80,player.x-25, player.y-15,null);
                    break;
                case 6:
                    g2d.drawImage(life60, player.x-25, player.y-15,null);
                    break;
                case 4:
                    g2d.drawImage(life40,player.x-25, player.y-15,null);
                    break;
                case 2:
                    g2d.drawImage(life20,player.x-25, player.y-15,null);
                    break;
                case 0:
                    g2d.drawImage(life0,player.x-25, player.y-15,null);
                    break;
            }
        }
    }

    @Override public void setLife (int life) {
        this.life = life;
    }

    @Override public int getLife(){
        return this.life;
    }

    @Override public void damage(){
        this.life -= value;
    }

    @Override public void plusLife(){
        this.life += value;
    }
}
