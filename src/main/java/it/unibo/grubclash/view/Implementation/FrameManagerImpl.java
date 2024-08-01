package it.unibo.grubclash.view.Implementation;

import java.io.File;
import java.util.Optional;

import javax.swing.*;

import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;

/**
 * Class implementing the FrameManager methods.
 */
public class FrameManagerImpl implements FrameManager {

    private final static char FS = File.separatorChar;

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = 800;

     // Static variable for the Singleton instance
    private static FrameManagerImpl instance;

    /**
     * Private builder to avoid external instantiation
     */
    private FrameManagerImpl() {
    }

    /**
     * Static method to get the Singleton instance
     */
    public static FrameManagerImpl getInstance() {
        if (instance == null) {
            instance = new FrameManagerImpl();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitle(JFrame frame) {
        frame.setTitle("GrubClash");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIcon(JFrame frame) {
        ImageIcon icon = new ImageIcon("src" + FrameManagerImpl.FS + "main" + FrameManagerImpl.FS + "resources" + FrameManagerImpl.FS + "players" + FrameManagerImpl.FS + "player0" + FrameManagerImpl.FS + "Grub_pl_0_stand_1.png");
        frame.setIconImage(icon.getImage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessageBox(String title, String content, int messageType) {
        JOptionPane.showMessageDialog(null, content, title, messageType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getWindowWidth() {
        return Optional.of(FrameManagerImpl.WINDOW_WIDTH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getWindowHeight() {
        return Optional.of(FrameManagerImpl.WINDOW_HEIGHT);
    }
}
