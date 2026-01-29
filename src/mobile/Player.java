package mobile;

import game.GameWorld;

import game.Position;

public class Player extends Entity{
    private int lives;
    private int score;
    private Position pos;

    public Player(Position p){
        lives = 3; score = 0; pos = p;
    }

    //TODO: complete method tryMove(mobile.Direction d, GameWorld world)
    public void tryMove(Direction d, GameWorld world){

    }

    //TODO: complete method loseLife()
    public void loseLife(){

    }

    //TODO: complete Method pushAttempt(mobile.Direction d, GameWorld world)
    public void pushAttempt(Direction d, GameWorld world){

    }




}
