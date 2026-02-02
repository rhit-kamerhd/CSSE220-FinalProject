package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;

import java.awt.*;
import java.util.ArrayList;

public class GameWorld {
    private static Tile[][] grid;
    private final Player player;
    public ArrayList<Zombie> zombies;
    private final ArrayList<Collectible> collectibles;
    private static int gemsRemaining;
    private GameStatus status;

    public GameWorld(Tile[][] g, Player p, ArrayList<Zombie> z, ArrayList<Collectible> c){
        grid = g; player = p; zombies = z; collectibles = c; gemsRemaining = c.size();
        status = GameStatus.RUNNING;
    }

    public Player getPlayer(){
        return this.player;
    }

    public boolean isWalkable(Position pos){
        int[] coords = pos.getPosition(); Tile tile = grid[coords[0]][coords[1]];
        if (tile instanceof FloorTile){
            for (Zombie z : zombies){
                if (z.getPosition() != pos){
                    return true;
        }}}
        return false;
    }

    public Tile tileAt(Position pos) {
        int[] position = pos.getPosition();
        return grid[position[0]][position[1]];
    }

    public Collectible collectibleAt(Position pos){
        for (Collectible collectible : collectibles) {
            Position position = (Position) collectible.getPosition();
            if (position.equals(pos)) return collectible;
        }
        return null;
    }

    public void decrementGems(){
        gemsRemaining--;
    }

    public void removeCollectible(Collectible c){
        collectibles.remove(c);
    }

    public void setStatus(GameStatus s) {
        status = s;
    }

    public static int getGemsRemaining(){
        return gemsRemaining;
    }
    public static Tile[][] getMap(){
        return grid;
    }
    public ArrayList<Collectible> getCollectibles(){
        return collectibles;
    }
    public ArrayList<Zombie> getZombies(){
        return zombies;
    }
    public Position getPlayerPosition(){
        return player.getPosition();
    }

    public Zombie getZombieAt(Position p) {
        for (Zombie z : zombies) {
            if (z.getPosition() == p) return z;
        }
        return null;
    }

    public boolean isWall(Position p) {
        int xPos = p.getPosition()[0]; int yPos = p.getPosition()[1];
        return (grid[xPos][yPos] instanceof Wall);
    }

    public GameStatus getStatus() {
        return status;
    }
}
