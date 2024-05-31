package it.unibo.grubclash.model.Implementation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.grubclash.controller.Application_Programming_Interface.GrubPanelInter;

public class KeyHandler implements KeyListener {

    GrubPanelInter grubPanel;
    public boolean leftPressed, rightPressed, spacePressed, shootPressed, shovelPressed;

    public KeyHandler(GrubPanelInter grubPanel){
        this.grubPanel = grubPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            shootPressed = true;
        }
        if(code == KeyEvent.VK_F){
            shovelPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            shootPressed = false;
        }
        if(code == KeyEvent.VK_F){
            shovelPressed = false;
        }
    }
}
