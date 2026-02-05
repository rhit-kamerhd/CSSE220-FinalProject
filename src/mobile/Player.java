package mobile;

import game.GameWorld;
import game.HUD;
import game.Position;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the player entity.
 * Handles movement, zombie pushing, and life tracking.
 */
public class Player extends Entity {

    private static int lives = 3;
    private int score = 0;
    private InputHandler input;
    public final BufferedImage sprite;

    /**
     * Constructs a Player at a given position.
     * @param p starting position
     * @param input input handler for controls
     */
    public Player(Position p, InputHandler input) throws IOException {
        setPosition(p);
        this.input = input;
        sprite = HUD.load("/player_sprite.png");
    }

    /**
     * Attempts to move the player.
     * @param d direction to move
     * @param world game world for collision checks
     */
    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) setPosition(next);
    }

    /** Decreases player lives by one. */
    public void loseLife() {
        if (lives > 0) lives--;
    }

    /**
     * @return number of lives remaining
     */
    public static int getLivesRemaining() {
        return lives;
    }

    /**
     * Attempts to push a zombie in a direction.
     * @param d direction of push
     * @param world game world
     */
    public void pushAttempt(Direction d, GameWorld world) {
        if (d == null) return;

        Position adjacent = getPosition().translate(d);
        Zombie z = world.getZombieAt(adjacent);
        if (z == null) return;

        Position launchPos = adjacent;
        for (int i = 0; i < 4; i++) {
            Position next = launchPos.translate(d);
            if (world.isWall(next)) break;
            launchPos = next;
        }
        z.setPosition(launchPos);
    }

    /**
     * Updates player each game tick.
     * @param world game world
     */
    @Override
    public void update(GameWorld world) {
        Direction d = input.getMoveDirection();
        tryMove(d, world);

        if (input.consumePush()) {
            pushAttempt(d, world);
        }
    }
}
