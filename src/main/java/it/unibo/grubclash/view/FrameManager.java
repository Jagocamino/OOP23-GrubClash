package it.unibo.grubclash.view;

import java.io.File;
import java.awt.Toolkit;

import javax.swing.*;

// Classe per gestire le impostazioni dei frame
public class FrameManager {

    final static char FS = File.separatorChar;
    public static final double PROP = 0.9;

    public static final int WINDOW_WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * PROP);
    public static final int WINDOW_HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * PROP);

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

    public static int getPercentage(int total, int percentage) {
        return (int)(total - (total * ((double)percentage / 100)));
    }

}
