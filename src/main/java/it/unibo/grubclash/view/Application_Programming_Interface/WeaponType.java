package it.unibo.grubclash.view.Application_Programming_Interface;

import java.util.Optional;

import it.unibo.grubclash.model.Implementation.ProjectileRoket;

public interface WeaponType {

    Optional<ProjectileRoket> shoot();

    void refillAmmo();

}