package immobile;

import game.GameStatus;
import game.GameWorld;

public class ExitTile implements Tile{


    public static void onEnter(GameWorld world){
        if (world.getGemsRemaining() == 0) world.setStatus(GameStatus.WON);
        System.out.println("You won!");
    }

}
