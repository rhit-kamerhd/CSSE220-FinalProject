package mobile;

import game.GameWorld;
import game.Position;

/**
 * Base class for all movable objects in the game world.
 * Stores position and defines behavior.
 */
public abstract class Entity {

    /** Current position of the entity in the world grid */
    private Position pos;

    /**
     * Gets the entity's current position.
     * @return current Position
     */
    public Position getPosition() {
        return pos;
    }

    /**
     * Sets the entity's position.
     * @param p new Position
     */
    public void setPosition(Position p) {
        pos = p;
    }

    /**
     * Updates the entity each game tick.
     * @param world the game world the entity exists in
     */
    public abstract void update(GameWorld world);
}

