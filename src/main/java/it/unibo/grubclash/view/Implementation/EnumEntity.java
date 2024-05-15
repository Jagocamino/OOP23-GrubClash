package it.unibo.grubclash.view.Implementation;

import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;

public class EnumEntity {

    //FM creo il FrameManager visto che creando l'interfaccia non posso avere più i metodi statici
    private static final FrameManager frameManager = new FrameManagerImpl();

    public enum entities {
        PLAYER1,
        PLAYER2,
        PLAYER3,
        PLAYER4,
        PLAYER5,
        WALL,
        SKY,
        ITEM,
        PROJECTILE;   
    }

    public static entities idToEntitiesConverter(int id){

        return switch (id) {
            case 0 -> entities.PLAYER1;
            case 1 -> entities.PLAYER2;
            case 2 -> entities.PLAYER3;
            case 3 -> entities.PLAYER4;
            case 4 -> entities.PLAYER5;
            default -> null;
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
