package it.unibo.grubclash.view.Application_Programming_Interface;

import java.util.Optional;

import javax.swing.*;

public interface FrameManager {
    void setTitle(JFrame frame);

    // Icona della applicazione
    void setIcon(JFrame frame);

    void showMessageBox(String title, String content, int messageType);

    Optional<Integer> getWindowWidth();

    Optional<Integer> getWindowHeight();
}
