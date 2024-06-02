package it.unibo.grubclash.view.Implementation;

import java.io.File;
import java.util.Optional;

import javax.swing.*;

import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;

// Classe per gestire le impostazioni dei frame
public class FrameManagerImpl implements FrameManager {

    public final static char FS = File.separatorChar;

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = 800;

     // Variabile statica per l'istanza Singleton
    private static FrameManagerImpl instance;

    // Costruttore privato per evitare istanziazione esterna
    private FrameManagerImpl() {
    }

    // Metodo statico per ottenere l'istanza Singleton
    public static FrameManagerImpl getInstance() {
        if (instance == null) {
            instance = new FrameManagerImpl();
        }
        return instance;
    }

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

    public Optional<Integer> getWindowWidth() {
        return Optional.of(FrameManagerImpl.WINDOW_WIDTH);
    }

    public Optional<Integer> getWindowHeight() {
        return Optional.of(FrameManagerImpl.WINDOW_HEIGHT);
    }
}
