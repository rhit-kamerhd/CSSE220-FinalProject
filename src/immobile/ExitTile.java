package immobile;

import game.GameStatus;
import game.GameWorld;

/**
 * Represents the exit tile in the game world.
 *
 * <p>An {@code ExitTile} marks the location the player must reach
 * after collecting all required gems. The win condition is evaluated
 * through {@link #winOnEnter(GameWorld)}.
 */
public class ExitTile implements Tile {

    /**
     * Evaluates whether the player wins upon entering the exit tile.
     *
     * <p>The player wins only if all gems have been collected.
     * If {@code world.getGemsRemaining() == 0}, the world's status
     * is updated to {@link GameStatus#WON} and the method returns true.
     * Otherwise, the world state is unchanged and the method returns false.
     *
     * @param world the current game world whose state may be updated
     * @return true if the win condition is satisfied, false otherwise
     */
    public static boolean winOnEnter(GameWorld world) {
        if (world.getGemsRemaining() == 0) {
            world.setStatus(GameStatus.WON);
            System.out.println("You beat the level!");
            return true;
        } else {
            return false;
        }
    }
}