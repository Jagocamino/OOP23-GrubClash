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

    private final static int defaultAmmo = 5;

    private static final int whidthRocket=20;
    private static final int heigthRocket=20;

    //Adding pixels to the rocket
    private static final int addingPixel=20;

    public WeaponRoket(Player owner) {
        super(owner, defaultAmmo);
        getImage();
    }

    private void getImage(){ 

        left = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_left.png", whidthRocket + addingPixel, heigthRocket + addingPixel);
        right = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_right.png", whidthRocket + addingPixel, heigthRocket + addingPixel);
        up = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_up.png", whidthRocket + addingPixel, heigthRocket + addingPixel);
        down = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_down.png", whidthRocket + addingPixel, heigthRocket + addingPixel);
    }

    @Override
    public Optional<ProjectileRoket> shoot() {

        if(getAmmo() == 0){
            rocket = Optional.empty();
            return rocket;
        }
        Sound.setFile(defaultAmmo);
        Sound.play();
        setShootingDir(getOwner().getDirection());
        reduceAmmo();
        rocket = Optional.of(new ProjectileRoket(getOwner().getX() + addingPixel, getOwner().getY() + addingPixel, getOwner()));
        return rocket;
        
    }

    @Override
    public void refillAmmo() {
        setAmmo(defaultAmmo);
    }
    
}
