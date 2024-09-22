package it.unibo.grubclash.model.Implementation;
import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;

/**
 * Concrete class extending abstract class, AbstractMonster.
 */
public class Zombie extends AbstractMonster {

    private static final int ZOMBIE_SPEED = 1;
    private static final int ZOMBIE_LIFE = 6;

    public Zombie(int x, int y, int width, int height, Entities entity) {
        super(x, y, width, height, entity, ZOMBIE_LIFE, ZOMBIE_SPEED);
    }

    @Override
    protected void loadImages() {
        left1 = setup("src/main/resources/mob/Mob_green_left_1.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        left2 = setup("src/main/resources/mob/Mob_green_left_2.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        right1 = setup("src/main/resources/mob/Mob_green_right_1.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        right2 = setup("src/main/resources/mob/Mob_green_right_2.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        stand1 = setup("src/main/resources/mob/Mob_green_down_1.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        stand2 = setup("src/main/resources/mob/Mob_green_down_2.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        death = setup("src/main/resources/mob/Mob_green_death.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
    }
}
