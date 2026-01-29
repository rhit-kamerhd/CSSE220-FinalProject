package mobile;

import game.GameWorld;

import game.Position;

public abstract class Entity {
    private Position pos;

    //DONE: complete method getPosition()
    public Position getPosition(){
        return this.pos;
    }

    //DONE: complete method setPosition(game.Position pos)
    public void setPosition(Position p){
        this.pos = p;
    }

    //TODO: complete method update(GameWorld world)
    public void update(GameWorld world){

    }
}
