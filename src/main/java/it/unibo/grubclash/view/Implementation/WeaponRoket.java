package it.unibo.grubclash.view.Implementation;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.ProjectileRoket;
import it.unibo.grubclash.model.Implementation.Sound;
import it.unibo.grubclash.model.Implementation.Weapon;
import it.unibo.grubclash.view.Application_Programming_Interface.WeaponType;

public class WeaponRoket extends Weapon implements WeaponType {
    private final char FS = File.separatorChar;

    private final static int DEFAULT_AMMO = 5;

    private static final int WIDTH_ROCKET=20;
    private static final int HEIGHT_ROCKET=20;

    //Adding pixels to the rocket
    private static final int ADDITION=20;

    public WeaponRoket(Player owner) {
        super(owner, DEFAULT_AMMO);
        getImage();
    }

    private void getImage(){ 

        left = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_left.png", WIDTH_ROCKET + ADDITION, HEIGHT_ROCKET + ADDITION);
        right = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_right.png", WIDTH_ROCKET + ADDITION, HEIGHT_ROCKET + ADDITION);
        up = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_up.png", WIDTH_ROCKET + ADDITION, HEIGHT_ROCKET + ADDITION);
        down = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_down.png", WIDTH_ROCKET + ADDITION, HEIGHT_ROCKET + ADDITION);
    }

    @Override
    public Optional<ProjectileRoket> shoot() {

        if(getAmmo() == 0){
            rocket = Optional.empty();
            return rocket;
        }
        Sound.setFile(DEFAULT_AMMO);
        Sound.play();
        setShootingDir(getOwner().getDirection());
        reduceAmmo();
        rocket = Optional.of(new ProjectileRoket(getOwner().getX() + ADDITION, getOwner().getY() + ADDITION, getOwner()));
        return rocket;
        
    }

    @Override
    public void refillAmmo() {
        setAmmo(DEFAULT_AMMO);
    }
    
}
