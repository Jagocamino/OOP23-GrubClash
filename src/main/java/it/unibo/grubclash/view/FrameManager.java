package it.unibo.grubclash.view;

import java.io.File;
import java.awt.Toolkit;

import javax.swing.*;

// Classe per gestire le impostazioni dei frame
public class FrameManager {

    final static char FS = File.separatorChar;

    public static final int WINDOW_WIDTH = 1500;
    public static final int WINDOW_HEIGHT = 800;

    public static void setTitle(JFrame frame) {
        frame.setTitle("GrubClash");
    }

    // Icona della applicazione
    public static void setIcon(JFrame frame) {
        ImageIcon icon = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "players" + FS + "player0" + FS + "Grub_pl_0_stand_1.png");
        frame.setIconImage(icon.getImage());
    }

    public static void showMessageBox(String title, String content, int messageType) {
        JOptionPane.showMessageDialog(null, content, title, messageType);
    }

}
