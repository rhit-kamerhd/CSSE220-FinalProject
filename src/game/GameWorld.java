package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;

import java.util.ArrayList;

public class GameWorld {
    private Tile[][] grid;
    private Player player;
    public ArrayList<Zombie> zombies;
    private ArrayList<Gem> gems;
    private int gemsRemaining;
    private GameStatus status;

    public GameWorld(Tile[][] g, Player p, ArrayList<Zombie> z, ArrayList<Gem> c){
        grid = g; player = p; zombies = z; gems = c; gemsRemaining = c.size();
        status = GameStatus.ON_START;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Gem gemAt(Position pos){
        for (Gem gem : gems) {
            Position position = gem.getPosition();
            if (position.equals(pos)) return gem;
        }
        return null;
    }

    public void removeGem(Gem c){
        gems.remove(c); gemsRemaining--;
    }

    public void setStatus(GameStatus s) {
        status = s;
    }

    public int getGemsRemaining(){
        return gemsRemaining;
    }

    public Tile[][] getMap(){
        return grid;
    }

    public ArrayList<Gem> getGems(){
        return gems;
    }

    public ArrayList<Zombie> getZombies(){
        return zombies;
    }

    public Position getPlayerPosition(){
        return player.getPosition();
    }

    public Zombie getZombieAt(Position p) {
        for (Zombie z : zombies) {
            Position zp = z.getPosition();
            if (zp.row == p.row && zp.col == p.col) return z;
        }
        return null;
    }

    public boolean isWall(Position p) {
        int xPos = p.getPosition()[0]; int yPos = p.getPosition()[1];
        return (grid[xPos][yPos] instanceof Wall);
    }

    public Position getExitTilePosition(){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] instanceof ExitTile) return new Position(i, j);
            }
        }
        return null;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getScore(){
        return getPlayer().score;
    }

    public ExitTile getExitTile(){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] instanceof ExitTile) return (ExitTile) grid[i][j];
            }
        }
        return null;
    }

    public void setGemsRemaining(int i){
        this.gemsRemaining = i;
    }

    public void setAllToNull(){
        this.grid = null; this.player = null; this.zombies = null; this.gems = null;
    }
}
