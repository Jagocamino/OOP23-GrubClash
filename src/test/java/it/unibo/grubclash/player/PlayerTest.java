package it.unibo.grubclash.player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.grubclash.controller.Implementation.GrubPanel;
import it.unibo.grubclash.controller.Implementation.MapBuilder;
import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.KeyHandler;
import it.unibo.grubclash.view.Implementation.Trap;

public class PlayerTest {

    ArrayList<Optional<Entity>> dynamicEntities = new ArrayList<>(); 
    int x = 0;
    int y = 10;
    Allowed allowed;
    GrubPanel gp;
    Player pl;
    EnumEntity.entities[][] entities = new EnumEntity.entities[20][20];
    
    @Test
    void testSpawn(){
        allowed = new Allowed(x, y, y, x);
        Allowed.addDynamicEntity(Optional.of(new Trap(x,y)));
        assertEquals(Allowed.getDynamicEntities().get(0).get().getX(), x);
        assertEquals(Allowed.getDynamicEntities().get(0).get().getY(), y);
        assertTrue(Allowed.getDynamicEntities().get(0).get().isAlive());
    }
    @Test
    void testSpawnPlayer(){
        for(int i = 0; i< 20; i++){
            for(int j = 0; j<20;j++){
                entities[i][j] = EnumEntity.entities.SKY;
            }
        }
        new Allowed(0,0,0,0);
        MapBuilder.entityMatrix = entities;
        pl = new Player(0, new KeyHandler());
        assertEquals(pl.getEntity(), EnumEntity.entities.PLAYER1);
        assertTrue(pl.isAlive());
        assertEquals(pl.getId(), 0);
    }
}
