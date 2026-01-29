package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;
import game.Position;
import java.util.ArrayList;

public class GameWorld {
    private Tile[][] grid;
    private Player player;
    private ArrayList<Zombie> zombies;
    private ArrayList<Collectible> collectibles;
    private int gemsRemaining;
    private GameStatus status;

    //constructor for GameWorld
    public GameWorld(Tile[][] g, Player p, ArrayList<Zombie> z, ArrayList<Collectible> c){
        grid = g; player = p; zombies = z; collectibles = c; gemsRemaining = c.size();
        status = GameStatus.RUNNING;
    }

    //DONE: complete method isWalkable(game.Position pos)
    public boolean isWalkable(Position pos){
        int[] coords = pos.getPosition(); Tile tile = grid[coords[0]][coords[1]];
        if (tile instanceof FloorTile){
            for (Zombie z : zombies){
                if (z.getPosition() != pos){
                    return true;
                }
            }
        }
        return false;
    }

    //DONE: complete method tileAt(game.Position pos)
    public Tile tileAt(Position pos) {
        int[] position = pos.getPosition();
        return grid[position[0]][position[1]];
    }

    //DONE: complete method collectibleAt(game.Position pos)
    public Collectible collectibleAt(Position pos){
        for (Collectible collectible : collectibles) {
            Position position = (Position) collectible.getPosition();
            if (position.equals(pos)) return collectible;
        }
        return null;
    }

    //DONE: complete method getStatus(GameStatus s)
    public GameStatus getStatus(){
        return this.status;
    }

    //DONE: complete method decrementGems()
    public void decrementGems(){
        gemsRemaining--;
    }

    //DONE: complete method removeCollectible(Collectible c)
    public void removeCollectible(Collectible c){
        collectibles.remove(c);
    }

    //DONE: complete method setStatus(GameStatus s)
    public void setStatus(GameStatus s) {
        status = s;
    }

    public int getGemsRemaining(){
        return this.gemsRemaining;
    }

}
