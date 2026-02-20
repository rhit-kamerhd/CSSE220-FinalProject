package mobile;

import game.GameWorld;
import game.HUD;
import game.Position;
import immobile.Gem;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the player entity.
 * Handles movement, gem collection, zombie knockback, and life tracking.
 * The player occupies one tile and uses a rectangular hitbox for collision.
 * 
 * @author Daniel Vermilya
 */
public class Player extends Entity {

    private static int lives = 3;
    public int score = 0;

    private InputHandler input;
    public final BufferedImage sprite;
    private final Position spawn;

    /**
     * Constructs a Player at a given starting position.
     *
     * @param p starting tile position (also respawn location)
     * @param input input handler for player controls
     * @throws IOException if sprite file cannot be loaded
     */
    public Player(Position p, InputHandler input) throws IOException {
        setPosition(p);
        this.spawn = p;
        this.input = input;
        this.sprite = HUD.load("/player_sprite.png");
    }

    /**
     * Sets the input handler used for player movement.
     *
     * @param input input handler
     */
    public void setInputHandler(InputHandler input) {
        this.input = input;
    }

    /**
     * Gets the number of lives remaining.
     *
     * @return remaining lives
     */
    public int getLivesRemaining() {
        return lives;
    }

    /**
     * Pushes an adjacent zombie four tiles away in the same direction,
     * stopping early if a wall blocks movement.
     *
     * @param world the game world
     */
    public void knockbackAdjacentZombie(GameWorld world) {
        Direction[] dirs = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

        for (Direction d : dirs) {
            Position adjacent = getPosition().translate(d);
            Zombie z = world.getZombieAt(adjacent);
            if (z == null) continue;

            Position dest = adjacent;
            for (int i = 0; i < 4; i++) {
                Position next = dest.translate(d);
                if (world.isWall(next)) break;
                dest = next;
            }
            z.setPosition(dest);
            return;
        }
    }

    /**
     * Picks up a gem from an adjacent tile if present.
     *
     * @param world the game world
     */
    public void gemPickup(GameWorld world) {
        Direction[] dirs = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        for (Direction d : dirs) {
            Position adjacent = getPosition().translate(d);
            Gem g = world.gemAt(adjacent);
            if (g == null) continue;
            world.removeGem(g);
            score++;
            return;
        }
    }

    /**
     * Handles being hit by a zombie: decreases life count and respawns.
     */
    private void onZombieHit() {
        if (lives > 0) lives--;
        setPosition(spawn);
        System.out.println("Lives: " + lives);
    }

    /**
     * Updates the player each game tick.
     *
     * @param world the game world
     */
    @Override
    public void update(GameWorld world) {
        if (input == null) return;

        Direction d = input.getMoveDirection();

        if (d != null) tryMove(d, world);

        if (input.consumePush()) {
            knockbackAdjacentZombie(world);
            gemPickup(world);
        }

        if (world.getZombieAt(getPosition()) != null) {
            onZombieHit();
        }

        gemPickup(world);
    }

    @Override
    protected int getSpriteSize() {
        return sprite.getWidth();
    }

    @Override
    public void onCollide(Collidable other) {
        // Collision handled in update()
    }

    public InputHandler getInput(){
        return this.input;
    }

}
