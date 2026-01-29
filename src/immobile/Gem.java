package immobile;
import game.GameWorld;
import mobile.Player;

import game.Position;

public class Gem implements Collectible{
    private Position pos;
    private boolean collected;

    public Gem(Position p){
        pos = p; collected = false;
    }

    public Position getPosition(){
        return pos;
    }


}
