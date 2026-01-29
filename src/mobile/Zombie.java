package mobile;

import game.GameWorld;
import game.Position;

public class Zombie extends Entity{
    Position pos = null;

    public Zombie(Position p){
        pos = p;
    }

    public Position getPosition(){
        return this.pos;
    }

    //TODO: complete method chooseMove(GameWorld world)
    public Direction chooseMove(GameWorld world){

    }

    //TODO: complete method tryMove(Direction d, GameWorld world)
    public void tryMove(Direction d, GameWorld world){

    }

}
