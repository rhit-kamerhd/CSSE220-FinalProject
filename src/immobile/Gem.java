package immobile;
import game.Position;

public class Gem implements Collectible{
    public Position pos;
    private boolean collected;

    public Gem(Position p){
        pos = p; collected = false;
    }

}
