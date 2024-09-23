package it.unibo.grubclash.model.Implementation;

import java.util.Optional;

import it.unibo.grubclash.controller.Implementation.MapBuilderImpl;

public class EnumEntity {

    public enum Entities {
        PLAYER1,
        PLAYER2,
        PLAYER3,
        PLAYER4,
        PLAYER5,
        WALL,
        SKY,
        TRAP,
        HEAL,
        AMMO_BOX,
        EXPLOSION,
        GUN,
        ITEM,
        PROJECTILE,
        MOBGENERATOR,
        SKELETON,
        ZOMBIE;   
    }

    public enum Weapons {
        GRANADE, 
        HITSCAN;
    }
    
    public enum Orientation {
        UP,
        UP2,
        DOWN,
        LEFT,
        RIGHT;
    }

    public enum Status {
        ALIVE,
        DEAD;
    }

    /**
     * 
     * @param id of the player 
     * @return the player enum
     */
    public static Optional<Entities> idToEntitiesConverter(int id){

        return switch (id) {
            case 0 -> Optional.ofNullable(Entities.PLAYER1);
            case 1 -> Optional.ofNullable(Entities.PLAYER2);
            case 2 -> Optional.ofNullable(Entities.PLAYER3);
            case 3 -> Optional.ofNullable(Entities.PLAYER4);
            case 4 -> Optional.ofNullable(Entities.PLAYER5);
            default -> Optional.empty();
        };

    }

    /**
     * 
     * @param entities
     * @param player
     * @return the coord x of the player in the map
     */
    public static int buttonToCoordsXConverter(Entities[][] entities, Entities player){
        int x = 0;
        for(int i = 0; i < MapBuilderImpl.COLS; i++){
            for(int j = 0; j < MapBuilderImpl.ROWS; j++){
                if(entities[i][j].equals(player)){
                    x = MapBuilderImpl.getXMapBase(i, j);
                }
            }
        }
        return x;
    }

     /**
     * 
     * @param entities
     * @param player
     * @return the coord y of the player in the map
     */
    public static int buttonToCoordsYConverter(Entities[][] entities, Entities player){
        int y = 0;
        for(int i = 0; i < MapBuilderImpl.COLS; i++){
            for(int j = 0; j < MapBuilderImpl.ROWS; j++){
                if(entities[i][j].equals(player)){
                    y = MapBuilderImpl.getYMapBase(i, j);
                }
            }
        }
        return y;
    }
}
