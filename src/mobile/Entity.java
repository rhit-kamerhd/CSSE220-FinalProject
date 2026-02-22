package mobile;

import game.GameWorld;
import game.Position;

/**
 * Abstract base class for all movable entities in the game world.
 *
 * <p>An Entity maintains a {@link Position} within the grid and provides
 * shared movement logic via {@link #tryMove(Direction, GameWorld)}.
 * Concrete subclasses such as Player and Zombie must implement
 * {@link #update(GameWorld)} to define per-tick behavior.
 */
public abstract class Entity {

    /** Current grid position of the entity. */
    private Position pos;

    /**
     * Returns the current position of this entity.
     *
     * @return the entity's grid position
     */
    public Position getPosition() {
        return pos;
    }

    /**
     * Sets the position of this entity.
     *
     * @param p the new grid position
     */
    public void setPosition(Position p) {
        pos = p;
    }

    /**
     * Updates this entity for a single game tick.
     *
     * <p>This method is called by the game loop and must be implemented
     * by subclasses to define movement, interaction, or AI behavior.
     *
     * @param world the current game world context
     */
    public abstract void update(GameWorld world);

    /**
     * Attempts to move the entity one tile in the specified direction.
     *
     * <p>If the direction is null, no movement occurs. If the translated
     * position is not a wall tile according to {@link GameWorld#isWall(Position)},
     * the entity's position is updated. Otherwise, movement is blocked.
     *
     * @param d the direction of movement
     * @param world the game world used to validate movement constraints
     */
    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;

        Position next = getPosition().translate(d);

        if (!world.isWall(next)) {
            setPosition(next);
        }
    }
}