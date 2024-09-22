package it.unibo.grubclash.model.Implementation;

import it.unibo.grubclash.model.Implementation.EnumEntity.Entities;

/**
 * Concrete class extending abstract class, AbstractMonster.
 */
public class Skeleton extends AbstractMonster {

    private static final int SKELETON_SPEED = 3;
    private static final int SKELETON_LIFE = 2;

    public Skeleton(int x, int y, int width, int height, Entities entity) {
        super(x, y, width, height, entity, SKELETON_LIFE, SKELETON_SPEED);
    }

    @Override
    protected void loadImages() {
        left1 = setup("src/main/resources/mob/Mob_left_1.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        left2 = setup("src/main/resources/mob/Mob_left_2.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        right1 = setup("src/main/resources/mob/Mob_right_1.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        right2 = setup("src/main/resources/mob/Mob_right_2.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        stand1 = setup("src/main/resources/mob/Mob_down_1.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        stand2 = setup("src/main/resources/mob/Mob_down_2.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
        death = setup("src/main/resources/mob/Mob_death.png", getWidth()+ADDITIONAL_PIXELS, getHeight()+ADDITIONAL_PIXELS);
    }
}
