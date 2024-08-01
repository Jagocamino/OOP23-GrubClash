package it.unibo.grubclash.view.Application_Programming_Interface;

import java.util.Optional;

import javax.swing.*;

/**
 * @author Patera Giorgia
 */
public interface FrameManager {

    /**
     * Given a frame, he sets the title
     * @param frame
     */
    void setTitle(JFrame frame);

    /**
     * Given a frame, he sets the icon
     * @param frame
     */
    void setIcon(JFrame frame);

    /**
     * show message 
     * @param title
     * @param content 
     * @param massageType
     */
    void showMessageBox(String title, String content, int messageType);

    /**
     * @return The Window Width
     */
    Optional<Integer> getWindowWidth();

    /**
     * @return The Window Height
     */
    Optional<Integer> getWindowHeight();
}
