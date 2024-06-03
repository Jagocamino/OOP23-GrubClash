package it.unibo.grubclash.Life;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class LifeTest {
    
    ArrayList<Optional<Entity>> dynamicEntities = new ArrayList<>(); 
    GrubPanel gp;
    Player pl;
    EnumEntity.entities[][] entities = new EnumEntity.entities[20][20];

    @Test
    void TestLife(){

        for(int i = 0; i< 20; i++){
            for(int j = 0; j<20;j++){
                entities[i][j] = EnumEntity.entities.SKY;
            }
        }
        new Allowed(0,0,0,0);
        MapBuilder.entityMatrix = entities;
        pl = new Player(0, new KeyHandler());
        assertEquals(pl.life.getLife().get(), 10);
        pl.life.damage();
        assertEquals(pl.life.getLife().get(), 8);
        pl.life.plusLife();
        assertEquals(pl.life.getLife().get(), 10);
        pl.life.setLife(0);
        assertEquals(pl.life.getLife().get(), 0);
    
        

    }


}
