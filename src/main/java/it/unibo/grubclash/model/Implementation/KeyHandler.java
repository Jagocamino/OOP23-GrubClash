package it.unibo.grubclash.model.Implementation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.grubclash.controller.Implementation.GrubPanelImpl;

public class KeyHandler implements KeyListener {

    GrubPanelImpl grubPanel;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean spacePressed;
    public boolean shootPressed;
    public boolean shovelPressed; 

    public KeyHandler(GrubPanelImpl grubPanel){
        this.grubPanel = grubPanel;
    }

    public KeyHandler(){}

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
