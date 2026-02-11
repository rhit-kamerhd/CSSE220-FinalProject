package mobile;

import game.GameWorld;
import game.HUD;
import game.Position;
import immobile.Gem;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the player entity.
 * Handles movement, zombie knockback (space), and life tracking.
 */
public class Player extends Entity {
    private static int lives = 3;
    public int score = 0;
    private InputHandler input;
    public final BufferedImage sprite;
    private final Position spawn;

    /**
     * Constructs a Player at a given position.
     * @param p starting position (also used as respawn position)
     * @param input input handler for controls
     * @throws IOException if sprite file cannot be loaded
     */
    public Player(Position p, InputHandler input) throws IOException {
        setPosition(p);
        this.spawn = p;
        this.input = input;
        this.sprite = HUD.load("/player_sprite.png");
    }

    /**
     * Sets the input handler used to control this player.
     * @param input input handler for movement/actions
     */
    public void setInputHandler(InputHandler input) {
        this.input = input;
    }

    /**
     * Gets remaining lives.
     * @return number of lives remaining
     */
    public int getLivesRemaining() {
        return lives;
    }

    /**
     * Moves the zombie exactly four tiles away from the player in the given direction,
     * stopping if a wall blocks the path.
     *
     * @param world game world
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
     * Handles player picking up gems on adjacent tiles 
     *
     * @param world game world
     */
    public void gemPickup(GameWorld world) {
    Direction[] dirs = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
    for (Direction d : dirs) {
        Position adjacent = getPosition().translate(d);
        Gem g = world.gemAt(adjacent);
        if (g==null) continue;
        else {
        	world.removeGem(g);
        	score++;
        }
        return;
    }
	}
    
    /**
     * Handles player being hit by a zombie: lose one life and respawn.
     */
    private void onZombieHit() {
        if (lives > 0) lives--;
        setPosition(spawn);
        System.out.println("Lives: " + lives);
    }

    /**
     * Updates player each game tick.
     * @param world game world
     */
    @Override
    public void update(GameWorld world) {
        if (input == null) return;
        Direction d = input.getMoveDirection();
        tryMove(d, world);
        if (input.consumePush()) {
            knockbackAdjacentZombie(world);
            gemPickup(world);
        }
        if (world.getZombieAt(getPosition()) != null) {
            onZombieHit();
        }
        gemPickup(world);
    }
}
