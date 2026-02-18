package mobile;

import java.util.Random;
import game.GameWorld;
import game.HUD;
import game.Position;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents an enemy zombie that moves randomly.
 * @author Daniel Vermilya
 */
public class Zombie extends Entity {

    private final Random rand = new Random();
    public final BufferedImage sprite;
    
    /**
     * Creates a zombie.
     * @param p starting position
     */
    public Zombie(Position p) throws IOException {
        setPosition(p);
        sprite = HUD.load("/zombie_sprite.png");
    }

    /**
     * Chooses a random valid movement direction.
     * @param world game world
     * @return Direction or null if none valid
     */
    public Direction chooseMove(GameWorld world) {
        Direction[] dirs = Direction.values();
        for (int i = 0; i < 10; i++) {
            Direction d = dirs[rand.nextInt(dirs.length)];
            Position next = getPosition().translate(d);
            if (!world.isWall(next)) return d;
        }
        return null;
    }
    
    /**
     * Updates player each game tick.
     * @param world game world
     */
    @Override
    public void update(GameWorld world) {
        Direction d = chooseMove(world);
        tryMove(d, world);
    }
    /**
    * gets sprite size
    * @return sprite width
    */
    @Override
    protected int getSpriteSize() {
        return sprite.getWidth();
    }
    
    @Override
    public void onCollide(Collidable other) {
        // No special behavior yet
    }
}


