package game;

import immobile.*;
import mobile.InputHandler;
import mobile.Player;
import mobile.Zombie;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Builds {@link GameWorld} instances from integer grid templates.
 *
 * <p>This builder maintains a set of hard-coded level templates that map integer codes
 * to tiles/entities. {@link #buildFromTemplate(int)} converts a template into a 25x25
 * {@link Tile} grid and constructs the corresponding player, zombies, and gems.
 *
 * <p>Template legend (as used by this class):
 * <ul>
 *   <li>0: {@link Wall}</li>
 *   <li>1: {@link FloorTile}</li>
 *   <li>2: {@link ExitTile}</li>
 *   <li>3: player start position (a {@link FloorTile} plus a {@link Player})</li>
 *   <li>4: gem position (a {@link FloorTile} plus a {@link Gem})</li>
 *   <li>5: zombie spawn position (a {@link Zombie}; tile assignment depends on template defaults)</li>
 * </ul>
 */
public class WorldBuilder {

    /**
     * Registry of level templates keyed by level number.
     * Each template is a 2D int array encoding tiles/entities using the legend described in the class Javadoc.
     */
    private static final HashMap<Integer, int[][]> mapTemplates = new HashMap<>();

    /** Template for level 2 (25x25). */
    static int[][] level2 = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,2,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,0,0,0,0,1,1,4,0},
            {0,1,1,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,1,1,1,1,0,0,0},
            {0,1,0,0,0,1,0,1,1,1,1,0,1,1,0,1,0,0,1,0,0,0,0,5,0},
            {0,1,0,1,1,1,0,0,0,0,1,0,1,0,0,5,0,0,1,0,1,1,1,1,0},
            {0,1,0,1,0,0,1,5,1,1,1,0,4,0,1,1,0,1,1,0,1,0,0,0,0},
            {0,1,0,1,0,1,1,1,0,0,0,0,1,0,1,0,0,1,0,0,1,0,1,1,0},
            {0,1,0,1,0,1,0,0,0,1,1,1,1,0,1,0,1,1,0,1,1,0,1,0,0},
            {0,1,0,1,0,1,1,1,1,1,1,1,0,0,1,0,1,0,0,1,0,0,0,0,0},
            {0,5,1,1,0,0,0,0,0,0,0,1,0,1,1,0,1,1,1,5,1,0,0,0,0},
            {0,1,0,1,1,1,1,1,1,1,0,1,0,1,0,0,1,0,1,0,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,0,1,0,0,1,0,1,0,0,0,0,1,0},
            {0,1,1,5,1,1,1,1,0,1,0,1,0,1,1,1,1,0,1,1,1,1,1,1,0},
            {0,0,1,1,5,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
            {0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0},
            {0,1,0,0,0,0,0,0,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0},
            {0,1,0,1,0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,1,1,1,1,5,1,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,0},
            {0,1,0,1,0,0,0,0,0,1,0,1,1,0,1,0,0,0,0,0,0,0,0,1,0},
            {0,1,0,4,1,1,1,1,1,1,0,1,4,0,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };

    /** Template for level 1 (25x25). */
    static int[][] level1 = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,3,1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,0,4,1,1,0},
            {0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,1,1,1,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,1,1,1,5,0,1,0},
            {0,1,0,0,1,1,0,0,0,1,0,1,0,1,0,1,0,0,0,1,1,0,0,0,0},
            {0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,1,1,1,1,0},
            {0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0},
            {0,1,0,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,0},
            {0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0},
            {0,1,0,1,1,5,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,0},
            {0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
            {0,1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0},
            {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0},
            {0,1,1,4,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,0},
            {0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,5,0,0,0,1,0,1,0},
            {0,1,1,1,1,1,1,1,0,1,4,1,1,1,0,1,1,1,1,1,0,1,1,1,0},
            {0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0},
            {0,1,0,1,1,5,0,1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,1,1,0},
            {0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0},
            {0,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,0,1,1,1,1,1,0,1,0},
            {0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0},
            {0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0},
            {0,1,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,1,1,1,1,2,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };

    /** Initializes the static template registry with the included level templates. */
    static {
        mapTemplates.put(1, level1);
        mapTemplates.put(2, level2);
    }

    /**
     * Builds a {@link GameWorld} from the integer template registered for the given level number.
     *
     * <p>This method iterates through the selected template and:
     * <ul>
     *   <li>Creates the corresponding {@link Tile} for each cell</li>
     *   <li>Creates the {@link Player} at the cell with code 3</li>
     *   <li>Creates {@link Gem} instances for cells with code 4</li>
     *   <li>Creates {@link Zombie} instances for cells with code 5</li>
     * </ul>
     *
     * @param levelNum the level number whose template should be used
     * @return a new {@link GameWorld} initialized from the template
     * @throws IOException if world construction triggers resource-dependent failures
     * @throws NullPointerException if no template exists for {@code levelNum}
     */
    public GameWorld buildFromTemplate(int levelNum) throws IOException {
        int[][] template = mapTemplates.get(levelNum);

        Tile[][] grid = new Tile[25][25];
        ArrayList<Gem> gems = new ArrayList<>();

        Player player = null;
        InputHandler input = new InputHandler();

        ArrayList<Zombie> zombies = new ArrayList<>();

        for (int i = 0; i < template.length; i++) {
            for (int j = 0; j < template[i].length; j++) {
                if (template[i][j] == 0) grid[i][j] = new Wall();
                if (template[i][j] == 1) grid[i][j] = new FloorTile();
                if (template[i][j] == 2) grid[i][j] = new ExitTile();

                if (template[i][j] == 3) {
                    grid[i][j] = new FloorTile();
                    player = new Player(new Position(i, j), input);
                }

                if (template[i][j] == 4) {
                    grid[i][j] = new FloorTile();
                    Position gemPos = new Position(i, j);
                    gems.add(new Gem(gemPos));
                }

                if (template[i][j] == 5) {
                    Position zPos = new Position(i, j);
                    zombies.add(new Zombie(zPos));
                }
            }
        }

        return new GameWorld(grid, player, zombies, gems);
    }
}