package immobile;

import game.GameStatus;
import game.GameWorld;

public class ExitTile implements Tile{

    public static boolean winOnEnter(GameWorld world){
        if (world.getGemsRemaining() == 0) {
            world.setStatus(GameStatus.WON);
            System.out.println("You beat the level!");
            return true;
        }
        else return false;
    }


}
