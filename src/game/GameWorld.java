package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;

import java.util.ArrayList;

/**
 * Represents the mutable state of a single game level.
 *
 * <p>A GameWorld owns the tile grid, player, enemies, collectables, and the current
 * {@link GameStatus}. It also provides convenience queries for locating entities and
 * tiles (for example, checking walls, finding the exit, and looking up a zombie or gem
 * at a position).
 *
 * <p>This class functions as the primary model object used by gameplay and rendering
 * code (for example, {@link Game} and {@link HUD}).
 */
public class GameWorld {

    /** 2D tile grid representing the level layout. */
    private Tile[][] grid;

    /** The player entity for this world. */
    private Player player;

    /**
     * All zombie enemies currently active in the world.
     * Exposed publicly by the original design; use {@link #getZombies()} when possible.
     */
    public ArrayList<Zombie> zombies;

    /** All gems currently present in the world (collected gems should be removed). */
    private ArrayList<Gem> gems;

    /** Cached count of remaining gems to collect. */
    private int gemsRemaining;

    /** Current status of the world (start, running, lost, won, etc.). */
    private GameStatus status;

    /**
     * Constructs a new GameWorld with the provided state.
     *
     * <p>The initial gems remaining count is set to the size of the provided gem list,
     * and the initial status is set to {@link GameStatus#ON_START}.
     *
     * @param g the tile grid for the level
     * @param p the player entity
     * @param z the initial list of zombies
     * @param c the initial list of gems
     */
    public GameWorld(Tile[][] g, Player p, ArrayList<Zombie> z, ArrayList<Gem> c) {
        grid = g;
        player = p;
        zombies = z;
        gems = c;
        gemsRemaining = c.size();
        status = GameStatus.ON_START;
    }

    /**
     * Returns the player entity.
     *
     * @return the player for this world
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the gem located at the given position, if any.
     *
     * @param pos the position to check
     * @return the gem at {@code pos}, or null if none exists there
     */
    public Gem gemAt(Position pos) {
        for (Gem gem : gems) {
            Position position = gem.getPosition();
            if (position.equals(pos)) return gem;
        }
        return null;
    }

    /**
     * Removes a gem from the world and decrements the remaining gem count.
     *
     * @param c the gem to remove
     */
    public void removeGem(Gem c) {
        gems.remove(c);
        gemsRemaining--;
    }

    /**
     * Sets the current world status.
     *
     * @param s the new status
     */
    public void setStatus(GameStatus s) {
        status = s;
    }

    /**
     * Returns the number of gems remaining to be collected.
     *
     * @return remaining gem count
     */
    public int getGemsRemaining() {
        return gemsRemaining;
    }

    /**
     * Returns the tile grid backing this world.
     *
     * @return the 2D array of tiles
     */
    public Tile[][] getMap() {
        return grid;
    }

    /**
     * Returns the list of gems currently in the world.
     *
     * @return gems list
     */
    public ArrayList<Gem> getGems() {
        return gems;
    }

    /**
     * Returns the list of zombies currently in the world.
     *
     * @return zombies list
     */
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    /**
     * Returns the player's current position.
     *
     * @return player position
     */
    public Position getPlayerPosition() {
        return player.getPosition();
    }

    /**
     * Returns the zombie located at a given position, if any.
     *
     * @param p the position to check
     * @return the zombie at {@code p}, or null if no zombie occupies that position
     */
    public Zombie getZombieAt(Position p) {
        for (Zombie z : zombies) {
            Position zp = z.getPosition();
            if (zp.row == p.row && zp.col == p.col) return z;
        }
        return null;
    }

    /**
     * Checks whether the given position corresponds to a wall tile.
     *
     * @param p the position to test
     * @return true if the tile at {@code p} is a {@link Wall}, false otherwise
     */
    public boolean isWall(Position p) {
        int xPos = p.getPosition()[0];
        int yPos = p.getPosition()[1];
        return (grid[xPos][yPos] instanceof Wall);
    }

    /**
     * Finds and returns the position of the exit tile in the grid.
     *
     * @return the exit tile position, or null if no {@link ExitTile} exists in the grid
     */
    public Position getExitTilePosition() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] instanceof ExitTile) return new Position(i, j);
            }
        }
        return null;
    }

    /**
     * Returns the current status of the world.
     *
     * @return current {@link GameStatus}
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Returns the current player score.
     *
     * <p>Note: this delegates to the player's score field as currently implemented.
     *
     * @return current score
     */
    public int getScore() {
        return getPlayer().score;
    }

    /**
     * Returns the {@link ExitTile} instance from the grid, if present.
     *
     * @return the exit tile, or null if none exists
     */
    public ExitTile getExitTile() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] instanceof ExitTile) return (ExitTile) grid[i][j];
            }
        }
        return null;
    }

    /**
     * Sets the cached remaining gem count.
     *
     * <p>This does not add or remove gems from {@link #gems}; it only updates the counter.
     *
     * @param i the new remaining count
     */
    public void setGemsRemaining(int i) {
        this.gemsRemaining = i;
    }
}