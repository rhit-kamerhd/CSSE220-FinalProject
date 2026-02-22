package immobile;
import game.Position;

public class Gem {
    public Position pos;

    public Gem(Position p){
        pos = p;
    }

    public Position getPosition(){
        return this.pos;
    }

}
