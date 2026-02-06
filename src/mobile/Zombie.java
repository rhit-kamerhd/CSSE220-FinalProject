package mobile;

import java.util.Random;
import game.GameWorld;
import game.HUD;
import game.Position;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents an enemy zombie that moves randomly. Extends Entity
 */
public class Zombie extends Entity {

    private final Random rand = new Random();;
    public final BufferedImage sprite;
    
    /**
     * Creates a zombie.
     * @param p starting position
     */
    public Zombie(Position p) throws IOException{
        setPosition(p);
        sprite = HUD.load("/zombie_sprite.png");
    }

    /**
     * Chooses a random direction.
     * @param world game world
     * @return Direction or null if none valid
     */
    public Direction chooseMove(GameWorld world) {
        Direction[] directions = Direction.values();
        for (int i = 0; i < 10; i++) {
            Direction d = dirsections[rand.nextInt(dirstions.length)];
            Position next = getPosition().translate(d);
            if (!world.isWall(next)) return d;
        }
        return null;
    }

    /**
     * Attempts to move zombie.
     */
    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) setPosition(next);
    }

    @Override
    public void update(GameWorld world) {
        Direction d = chooseMove(world);
        tryMove(d, world);
    }
}


