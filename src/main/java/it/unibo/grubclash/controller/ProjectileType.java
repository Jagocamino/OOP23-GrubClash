package it.unibo.grubclash.controller;
import it.unibo.grubclash.controller.Implementation.Entity;

public interface ProjectileType { //l'unica cosa che deve fare il proiettile è essere lanciato.

    /*
            Qui si gestisce la fisica del proiettile a seconda del tipo di arma
            vorrei aggiungere uno switch case per le armi diverse, potenziali armi possono essere:
                - hitscan
                - lanciagranate a parabola
                - lanciarazzi a traiettoria fissa
            uno tra il lanciagranate e il lanciarazzi vanno implementate per prime

            per il calcolo della traiettoria, una volta compreso come agisce la fisica e il salto (che no trovo lol) basta che mi calcolo il vettore direzione
            double dirX = getxClicked() - ( getWeapon().getOwner().getX() + (getWeapon().getOwner().getWidth() / 2) );
            double dirY = getyClicked() - ( getWeapon().getOwner().getY() + (getWeapon().getOwner().getHeight() / 2) );

    */

    /* void trajectory (int x, int y); //passati gli x e y di dove clicca il mouse */

    /* public Entity damage (int dmgRadius) { 
        int x = (int) ((getProjectile().getX() + getProjectile().getWidth()) / 2);
        int y = (int) ((getProjectile().getY() + getProjectile().getHeight()) / 2);
        return new Entity(x - dmgRadius , y - dmgRadius, dmgRadius * 2, dmgRadius * 2, entities.EXPLOSION); //elimino ogni explosion dopo
    } */

    Entity damage ();

    int getDamage ();
    
}
