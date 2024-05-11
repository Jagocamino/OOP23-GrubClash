package it.unibo.grubclash.view;

public class EnumEntity {

    public enum entities {
        PLAYER1,
        PLAYER2,
        PLAYER3,
        PLAYER4,
        PLAYER5,
        WALL,
        SKY,
        ITEM        
    }

    public static entities idToEntitiesConverter(int id){

        switch(id){
            case 0:return entities.PLAYER1;
            case 1:return entities.PLAYER2;
            case 2:return entities.PLAYER3;
            case 3:return entities.PLAYER4;
            case 4:return entities.PLAYER5;
        }

        return null;
    }

    public static int buttonToCoordsXConverter(entities[][] entities, entities player){ //da sistemare TODO
        int x = 0;
        for(int i = 0; i < MapBuilder.COLS; i++){
            for(int j = 0; j < MapBuilder.ROWS; j++){
                if(entities[i][j].equals(player)){
                    x = MapBuilder.getXMapBase(i, j);
                    System.out.println(FrameManager.WINDOW_WIDTH + " " + i);
                }
            }
        }
        return x;
    }

    public static int buttonToCoordsYConverter(entities[][] entities, entities player){ //da sistemare TODO
        int y = 0;
        for(int i = 0; i < MapBuilder.COLS; i++){
            for(int j = 0; j < MapBuilder.ROWS; j++){
                if(entities[i][j].equals(player)){
                    y = MapBuilder.getYMapBase(i, j);
                }
            }
        }
        return y-7;
    }
}
