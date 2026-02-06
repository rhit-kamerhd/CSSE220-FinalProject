package immobile;

import game.Position;

public interface Collectible {
    Position pos = null;


    default Position getPosition(){
        return pos;
    }

}
