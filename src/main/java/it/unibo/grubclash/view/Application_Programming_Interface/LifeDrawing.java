package it.unibo.grubclash.view.Application_Programming_Interface;

import it.unibo.grubclash.controller.Implementation.Player;
import java.awt.Graphics2D;

public interface LifeDrawing {

    void drawLife(Graphics2D g2d, Player player, int life);
}
