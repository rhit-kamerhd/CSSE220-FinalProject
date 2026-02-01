package immobile.immobile;

import game.GameStatus;
import game.GameWorld;
import immobile.Tile;
import mobile.Entity;

public class ExitTile implements Tile {
    //TODO: complete method onEnter()
    public void onEnter(Entity e, GameWorld world){
        if (GameWorld.getGemsRemaining() == 0) world.setStatus(GameStatus.WON);

    }

}
