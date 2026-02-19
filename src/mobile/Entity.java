package mobile;

import game.GameWorld;
import game.Position;
import java.awt.Rectangle;
/**
 * abstract class for all movable objects in the game world.
 * Stores position and defines enity behavior
 * Implements collidable interface
 * @author Daniel Vermilya
 */
public abstract class Entity implements Collidable{
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
     * Attempts to move the entity one tile.
     * @param d direction to move
     * @param world game world for collision checks
     */
    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) setPosition(next);
    }

    /**
    * Gets the sprite size for entity
    */
    protected abstract int getSpriteSize();

    @Override
    public abstract void onCollide(Collidable other);

    @Override
    public Rectangle getBounds() {
        int size = getSpriteSize();
        int x = pos.col * size;
        int y = pos.row * size;
        return new Rectangle(x, y, size, size);
    }

    /**
     * Updates the entity each game tick.
     * @param world the game world 
     */
    public abstract void update(GameWorld world);
    
}

