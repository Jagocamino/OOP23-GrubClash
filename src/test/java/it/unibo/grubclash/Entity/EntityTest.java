package it.unibo.grubclash.Entity;

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
import it.unibo.grubclash.view.Implementation.Ammo_Box;
import it.unibo.grubclash.view.Implementation.Heal;
import it.unibo.grubclash.view.Implementation.Trap;

public class EntityTest {

    Allowed allowed;
    int x = 0;
    int y = 10;

    @Test
    void testTrap(){
        allowed = new Allowed(x, y, y, x);
        Allowed.addDynamicEntity(Optional.of(new Trap(x,y)));
        assertEquals(Allowed.getDynamicEntities().get(0).get().getX(), x);
        assertEquals(Allowed.getDynamicEntities().get(0).get().getY(), y);
        assertTrue(Allowed.getDynamicEntities().get(0).get().isAlive());
        assertEquals(Allowed.getDynamicEntities().get(0).get().getEntity(), EnumEntity.entities.TRAP);
    }

    @Test
    void testHeal(){
        allowed = new Allowed(x, y, y, x);
        Allowed.addDynamicEntity(Optional.of(new Heal(x,y)));
        assertEquals(Allowed.getDynamicEntities().get(0).get().getX(), x);
        assertEquals(Allowed.getDynamicEntities().get(0).get().getY(), y);
        assertTrue(Allowed.getDynamicEntities().get(0).get().isAlive());
        assertEquals(Allowed.getDynamicEntities().get(0).get().getEntity(), EnumEntity.entities.HEAL);
    }

    @Test
    void testAmmo_Box(){
        allowed = new Allowed(x, y, y, x);
        Allowed.addDynamicEntity(Optional.of(new Ammo_Box(x,y)));
        assertEquals(Allowed.getDynamicEntities().get(0).get().getX(), x);
        assertEquals(Allowed.getDynamicEntities().get(0).get().getY(), y);
        assertTrue(Allowed.getDynamicEntities().get(0).get().isAlive());
        assertEquals(Allowed.getDynamicEntities().get(0).get().getEntity(), EnumEntity.entities.AMMO_BOX);
    }

    ArrayList<Optional<Entity>> dynamicEntities = new ArrayList<>(); 
    GrubPanel gp;
    Player pl;
    EnumEntity.entities[][] entities = new EnumEntity.entities[20][20];

    @Test
    void testProjectile(){
        for(int i = 0; i< 20; i++){
            for(int j = 0; j<20;j++){
                entities[i][j] = EnumEntity.entities.SKY;
            }
        }
        new Allowed(0,0,0,0);
        MapBuilder.entityMatrix = entities;
        pl = new Player(0, new KeyHandler());
        assertTrue(pl.getWeapon().isPresent());
        

    }
    
}
