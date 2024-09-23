package it.unibo.grubclash.model.Implementation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Christian Remschi 
 */
public class KeyHandler implements KeyListener {

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean shootPressed;
    private boolean shovelPressed; 

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

    /**
     * 
     * @return true if the key A is pressed
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Sets the variable to the param value
     * @param leftPressed
     */
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }
    
    /**
     * 
     * @return true if the key D is pressed
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    /**
     * Sets the variable to the param value
     * @param rightPressed
     */
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    /**
     * 
     * @return true if the key SPACE is pressed
     */
    public boolean isSpacePressed() {
        return spacePressed;
    }

    /**
     * Sets the variable to the param value
     * @param spacePressed
     */
    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    /**
     * 
     * @return true if the key ENTER is pressed
     */
    public boolean isShootPressed() {
        return shootPressed;
    }

    /**
     * Sets the variable to the param value
     * @param shootPressed
     */
    public void setShootPressed(boolean shootPressed) {
        this.shootPressed = shootPressed;
    }

    /**
     * 
     * @return true if the key F is pressed
     */
    public boolean isShovelPressed() {
        return shovelPressed;
    }

    /**
     * Sets the variable to the param value
     * @param shovelPressed
     */
    public void setShovelPressed(boolean shovelPressed) {
        this.shovelPressed = shovelPressed;
    }
}
