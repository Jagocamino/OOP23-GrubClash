package it.unibo.grubclash.controller.Application_Programming_Interface;

import java.awt.Color;
/**
 * @author Camoni Jago
 */
public interface MapBuilder {

    /**
     * This method switches the behavior of the background, each background depends on the state of the map
     */
    Color switchBackground(int i, int j, Color color);

    /**
     * This is the first method called when the map is created 
     */
    void p1Map();

}