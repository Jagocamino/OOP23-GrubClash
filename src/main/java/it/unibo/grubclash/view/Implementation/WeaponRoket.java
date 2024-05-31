package it.unibo.grubclash.view.Implementation;
import java.io.File;
import java.util.Optional;

import it.unibo.grubclash.controller.Implementation.Player;
import it.unibo.grubclash.model.Implementation.Entity;
import it.unibo.grubclash.model.Implementation.ProjectileRoket;
import it.unibo.grubclash.model.Implementation.Weapon;
import it.unibo.grubclash.view.Application_Programming_Interface.WeaponType;

public class WeaponRoket extends Weapon implements WeaponType {
    final char FS = File.separatorChar;

    private final static int defaultAmmo = 5;

    public WeaponRoket(Player owner) {
        super(owner, defaultAmmo);
        getImage();
    }

    private void getImage(){ //parametro con numero del giocatore es : if(player == 1) => getImage del player1

        left = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_left.png", 40, 40);
        right = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_right.png", 40, 40);
        up = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_up.png", 40, 40);
        down = Entity.setup("src" + FS + "main" + FS + "resources" + FS + "weapons" + FS + "rocketweapon" + FS + "rocketweapon_down.png", 40, 40);
    }

    @Override
    public Optional<ProjectileRoket> shoot() {

        if(getAmmo() == 0){
            rocket = Optional.empty();
            return rocket;
        }
        setShootingDir(getOwner().getDirection());
        reduceAmmo();
        rocket = Optional.of(new ProjectileRoket(getOwner().getX(), getOwner().getY(), getOwner()));
        return rocket;
        
    }

    @Override
    public void refillAmmo() {
        setAmmo(defaultAmmo);
    }
    
}
