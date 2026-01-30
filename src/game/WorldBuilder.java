package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;

import java.util.HashMap;
import java.util.ArrayList;

public class WorldBuilder {
    private HashMap<Integer, int[][]> mapTemplates = new HashMap<>();
    {
        mapTemplates.put(1, null);
        mapTemplates.put(2, null);
        mapTemplates.put(3, null);
        mapTemplates.put(4, null);
        mapTemplates.put(5, null);
    }

    /**
    requirements for int[36][36] in mapTemplates:
     1) first int[] and last int[] contain only 0
     2) for every 1 in int[36][36] there should be another 1 in the same int[] at an adjacent index or an adjacent row at the same index
     3) there should be exactly five 4s in the matrix
     4) there should be exactly one 3 in the matrix
     5) there should be exactly two

     */

    //DONE: complete method buildFromTemplate(String TemplateID)
    public GameWorld buildFromTemplate(int levelNum) {
        int[][] template = mapTemplates.get(levelNum);
        Tile[][] grid = new Tile[36][36];
        ArrayList<Collectible> collectibles = new ArrayList<>();
        Player player = null;
        ArrayList<Zombie> zombies = new ArrayList<>();
        for (int i = 0; i < template.length; i++) {
            for (int j = 0; j < template[i].length; j++) {
                if (template[i][j] == 0) grid[i][j] = new Wall();
                if (template[i][j] == 1) grid[i][j] = new FloorTile();
                if (template[i][j] == 2) grid[i][j] = new ExitTile();
                if (template[i][j] == 3) {
                    grid[i][j] = new FloorTile();
                    player = new Player(new Position(36 - i, j));
                }
                if (template[i][j] == 4) {
                    grid[i][j] = new FloorTile();
                    collectibles.add(new Gem(new Position(36 - i, j)));
                }
                if (template[i][j] == 5){
                    Position zPos = new Position(i, -j);
                    zombies.add(new Zombie(zPos));
                }
            }
        }
        return new GameWorld(grid, player, zombies, collectibles);
    }
}



