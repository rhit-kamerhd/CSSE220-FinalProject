package immobile;

import game.GameWorld;
import mobile.Player;

import game.Position;

public interface Collectible {
    //TODO: complete method getPosition()
    default Position getPosition(){

    }

    //TODO: complete method onCollect(Player player, GameWorld world)
    default void onCollect(Player player, GameWorld world){

    }

    //TODO: complete method isCollected()
    default boolean isCollected(){

    }

}
