package immobile;

import game.Position;

/**
 * Represents a collectible gem within the game world.
 *
 * <p>Gems are placed at fixed {@link Position} coordinates and are removed
 * from the {@link game.GameWorld} when collected by the player.
 */
public class Gem {

    /** Grid position of this gem. */
    public Position pos;

    /**
     * Constructs a gem at the specified position.
     *
     * @param p the grid position where the gem is located
     */
    public Gem(Position p) {
        pos = p;
    }

    /**
     * Returns the current position of this gem.
     *
     * @return the gem's grid position
     */
    public Position getPosition() {
        return this.pos;
    }
}