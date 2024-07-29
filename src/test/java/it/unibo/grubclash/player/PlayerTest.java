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

public class PlayerTest {

    ArrayList<Optional<Entity>> dynamicEntities = new ArrayList<>(); 
    GrubPanel gp;
    Player pl;
    EnumEntity.Entities[][] entities = new EnumEntity.Entities[20][20];

    @Test
    void testSpawnPlayer(){
        for(int i = 0; i< 20; i++){
            for(int j = 0; j<20;j++){
                entities[i][j] = EnumEntity.Entities.SKY;
            }
        }
        new Allowed(0,0,0,0);
        MapBuilder.entityMatrix = entities;
        pl = new Player(0, new KeyHandler());
        assertEquals(pl.getEntity(), EnumEntity.Entities.PLAYER1);
        assertTrue(pl.isAlive());
        assertEquals(pl.getId(), 0);
    }
}
