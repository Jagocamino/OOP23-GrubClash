package it.unibo.grubclash.view;

import javax.swing.*;

// Classe per gestire le impostazioni dei frame
public class FrameManager {

    public static void setTitle(JFrame frame) {
        frame.setTitle("GrubClash");
    }

    // Icona della applicazione
    public static void setIcon(JFrame frame) {
        ImageIcon icon = new ImageIcon("src\\main\\resources\\menu\\Grub.png");
        frame.setIconImage(icon.getImage());
    }

    public static void showMessageBox(String title, String content, int messageType) {
        JOptionPane.showMessageDialog(null, content, title, messageType);
    }

}
