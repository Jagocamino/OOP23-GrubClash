package it.unibo.grubclash.controller.Application_Programming_Interface;

import java.util.ArrayList;

import it.unibo.grubclash.controller.Implementation.PlayerImpl;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;

/**
 * @author Remschi Christian
 */
public interface GrubPanel {

    /**
     * Starts the main Thread
     */
    void startGameThread();

    /**
     * @return the frame manager
     */
    FrameManager getFrameManager();

    /**
     * @return the number of players
     */
    int getPlayerCount();

    /**
     * @return the id of the player whose turn it is going to
     */
    int getNumPlayerTurn();

    /**
     * @return the timer of the turn
     */
    int getSecondsTurn();

    /**
     * @return the list of players
     */
    ArrayList<PlayerImpl> getPlayers();

    /**
     * @return true if the turn already started, false otherwise
     */
    boolean isTurnBegin();
}