package it.unibo.grubclash.model.Implementation;

import java.util.Optional;

import it.unibo.grubclash.controller.Implementation.MapBuilder;

public class EnumEntity {

    public enum entities {
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
        MOBGENERATOR;   
    }

    public enum weapons {
        GRANADE,
        ROKET,
        HITSCAN;
    }
    
    public enum orientation {
        UP,
        UP2,
        DOWN,
        LEFT,
        RIGHT;
    }

    public enum status {
        ALIVE,
        DEAD;
    }

    public static Optional<entities> idToEntitiesConverter(int id){

        return switch (id) {
            case 0 -> Optional.ofNullable(entities.PLAYER1);
            case 1 -> Optional.ofNullable(entities.PLAYER2);
            case 2 -> Optional.ofNullable(entities.PLAYER3);
            case 3 -> Optional.ofNullable(entities.PLAYER4);
            case 4 -> Optional.ofNullable(entities.PLAYER5);
            default -> Optional.empty();
        };

    }

    public static int buttonToCoordsXConverter(entities[][] entities, entities player){
        int x = 0;
        for(int i = 0; i < MapBuilder.COLS; i++){
            for(int j = 0; j < MapBuilder.ROWS; j++){
                if(entities[i][j].equals(player)){
                    x = MapBuilder.getXMapBase(i, j);
                }
            }
        }
        return x;
    }

    public static int buttonToCoordsYConverter(entities[][] entities, entities player){
        int y = 0;
        for(int i = 0; i < MapBuilder.COLS; i++){
            for(int j = 0; j < MapBuilder.ROWS; j++){
                if(entities[i][j].equals(player)){
                    y = MapBuilder.getYMapBase(i, j);
                }
            }
        }
        return y;
    }
}
