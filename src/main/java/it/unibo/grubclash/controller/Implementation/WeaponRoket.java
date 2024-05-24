package it.unibo.grubclash.controller.Implementation;
import java.util.Optional;
import it.unibo.grubclash.controller.Weapon;
import it.unibo.grubclash.view.Implementation.EnumEntity.status;

public class WeaponRoket extends Weapon {

    //TODO gestire ora che estende entity, OGNI ARMA  lancia un proiettile
    //il proiettile smette di esistere se canMoveThere() restituisce false

    Optional<ProjectileRoket> projectileLaunched;

    public Optional<ProjectileRoket> shoot() {

        if (getWorking() == status.DEAD) { // if is not present, it is shot
            reduceAmmo();
            setWorking(status.ALIVE);
            projectileLaunched = Optional.ofNullable(new ProjectileRoket(getOwner().getX(), getOwner().getY(), getOwner()));
            return projectileLaunched;
        }
        return Optional.empty();
        
    }
    
}
