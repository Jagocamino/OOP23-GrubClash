package it.unibo.grubclash.view.Implementation;

import java.io.File;
import java.awt.Toolkit;

import javax.swing.*;

// Classe per gestire le impostazioni dei frame
public class FrameManagerImpl implements it.unibo.grubclash.view.Application_Programming_Interface.FrameManager {

    final static char FS = File.separatorChar;

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = 800;

    public void setTitle(JFrame frame) {
        frame.setTitle("GrubClash");
    }

    public void setIcon(JFrame frame) {
        ImageIcon icon = new ImageIcon("src" + FrameManagerImpl.FS + "main" + FrameManagerImpl.FS + "resources" + FrameManagerImpl.FS + "players" + FrameManagerImpl.FS + "player0" + FrameManagerImpl.FS + "Grub_pl_0_stand_1.png");
        frame.setIconImage(icon.getImage());
    }

    public void showMessageBox(String title, String content, int messageType) {
        JOptionPane.showMessageDialog(null, content, title, messageType);
    }

    public int getWindowWidth() {
        return FrameManagerImpl.WINDOW_WIDTH;
    }

    public int getWindowHeight() {
        return FrameManagerImpl.WINDOW_HEIGHT;
    }
}
