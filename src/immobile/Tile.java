package immobile;

public interface Tile {

    //TODO: complete method isWalkable()
    default boolean isWalkable(){
        return this instanceof FloorTile;
    }

    //TODO: complete method onEnter()
    default void onEnter(){

    }


}