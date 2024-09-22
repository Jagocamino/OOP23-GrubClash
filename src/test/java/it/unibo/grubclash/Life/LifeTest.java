package it.unibo.grubclash.Life;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class LifeTest {
    
    ArrayList<Optional<EntityImpl>> dynamicEntities = new ArrayList<>(); 
    GrubPanelImpl gp;
    PlayerImpl pl;
    EnumEntity.Entities[][] entities = new EnumEntity.Entities[20][20];

    @Test
    void TestLife(){

        for(int i = 0; i< 20; i++){
            for(int j = 0; j<20;j++){
                entities[i][j] = EnumEntity.Entities.SKY;
            }
        }
        new Allowed(0,0,0,0);
        MapBuilderImpl.entityMatrix = entities;
        pl = new PlayerImpl(0, new KeyHandler());
        assertEquals(pl.getLife().getLifeValue().get(), 10);
        pl.getLife().damage();
        assertEquals(pl.getLife().getLifeValue().get(), 8);
        pl.getLife().plusLife();
        assertEquals(pl.getLife().getLifeValue().get(), 10);
        pl.getLife().setLifeValue(0);
        assertEquals(pl.getLife().getLifeValue().get(), 0);
    
        

    }


}
