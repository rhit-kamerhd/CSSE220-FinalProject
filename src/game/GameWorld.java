package game;

import immobile.Collectible;
import immobile.Tile;
import mobile.Player;
import mobile.Zombie;
import javax.swing.text.Position;
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

    //TODO: complete method isWalkable(game.Position pos)
    public boolean isWalkable(Position pos){

    }

    //TODO: complete method tileAt(game.Position pos)
    public Tile tileAt(Position pos) {

    }

    //TODO: complete method collectibleAt(game.Position pos)
    public Collectible collectibleAt(Position pos){

    }

    //TODO: complete method getStatus(GameStatus s)
    public void getStatus(GameStatus s){

    }

    //TODO: complete method decrementGems()
    public void decrementGems(){

    }

    //TODO: complete method removeCollectible(Collectible c)
    public void removeCollectible(Collectible c){

    }

    //TODO: complete method setStatus(GameStatus s)
    public void setStatus(GameStatus s) {

    }


}
