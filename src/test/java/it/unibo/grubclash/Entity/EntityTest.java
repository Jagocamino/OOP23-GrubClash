package it.unibo.grubclash.Entity;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.grubclash.model.Implementation.Allowed;
import it.unibo.grubclash.model.Implementation.EnumEntity;
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
        assertEquals(Allowed.getDynamicEntities().get(0).get().getEntity(), EnumEntity.Entities.TRAP);
    }

    @Test
    void testHeal(){
        allowed = new Allowed(x, y, y, x);
        Allowed.addDynamicEntity(Optional.of(new Heal(x,y)));
        assertEquals(Allowed.getDynamicEntities().get(0).get().getX(), x);
        assertEquals(Allowed.getDynamicEntities().get(0).get().getY(), y);
        assertTrue(Allowed.getDynamicEntities().get(0).get().isAlive());
        assertEquals(Allowed.getDynamicEntities().get(0).get().getEntity(), EnumEntity.Entities.HEAL);
    }

    @Test
    void testAmmo_Box(){
        allowed = new Allowed(x, y, y, x);
        Allowed.addDynamicEntity(Optional.of(new Ammo_Box(x,y)));
        assertEquals(Allowed.getDynamicEntities().get(0).get().getX(), x);
        assertEquals(Allowed.getDynamicEntities().get(0).get().getY(), y);
        assertTrue(Allowed.getDynamicEntities().get(0).get().isAlive());
        assertEquals(Allowed.getDynamicEntities().get(0).get().getEntity(), EnumEntity.Entities.AMMO_BOX);
    }
    
}
