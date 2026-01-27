package game;

import immobile.Collectible;
import immobile.Tile;
import mobile.Player;
import mobile.Zombie;
import javax.swing.text.Position;
import java.util.List;

public class GameWorld {
    private Tile[][] grid;
    private Player player;
    private List<Zombie> zombies;
    private List<Collectible> collectibles;
    private int gemsRemaining;
    private GameStatus status;

    //TODO: complete method isWalkable(Position pos)
    public boolean isWalkable(Position pos){

    }

    //TODO: complete method tileAt(Position pos)
    public Tile tileAt(Position pos) {

    }

    //TODO: complete method collectibleAt(Position pos)
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
