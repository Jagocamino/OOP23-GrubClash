package it.unibo.grubclash.player;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.grubclash.controller.Implementation.GrubPanelImpl;
import it.unibo.grubclash.controller.Implementation.MapBuilderImpl;
import it.unibo.grubclash.controller.Implementation.PlayerImpl;
import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.EntityImpl;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.model.Implementation.KeyHandler;

public class PlayerTest {

    ArrayList<Optional<EntityImpl>> dynamicEntities = new ArrayList<>(); 
    GrubPanelImpl gp;
    PlayerImpl pl;
    EnumEntity.Entities[][] entities = new EnumEntity.Entities[20][20];

    @Test
    void testSpawnPlayer(){
        for(int i = 0; i< 20; i++){
            for(int j = 0; j<20;j++){
                entities[i][j] = EnumEntity.Entities.SKY;
            }
        }
        new Allowed(0,0,0,0);
        MapBuilderImpl.entityMatrix = entities;
        pl = new PlayerImpl(0, new KeyHandler());
        assertEquals(pl.getEntity(), EnumEntity.Entities.PLAYER1);
        assertTrue(pl.isAlive());
        assertEquals(pl.getId(), 0);
    }
}
