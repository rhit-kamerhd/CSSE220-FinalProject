package mobile;


import game.GameWorld;
import game.Position;

/**
 * Base class for all movable objects in the game world.
 * Stores position and defines the update behavior.
 * @author Daniel Vermilya
 */
public abstract class Entity {
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
     * @param world the game world 
     */
    public abstract void update(GameWorld world);
    
    /**
     * Attempts to move the entity one tile.
     * @param d direction to move
     * @param world game world for collision checks
     */
    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) setPosition(next);
    }
}

