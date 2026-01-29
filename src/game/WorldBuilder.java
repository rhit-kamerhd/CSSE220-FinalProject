package game;

import immobile.*;
import mobile.Entity;
import mobile.Player;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class WorldBuilder {
    static HashMap<String, int[][]> mapTemplates;


    //TODO: complete method buildFromTemplate(String TemplateID)
    public GameWorld buildFromTemplate(String templateID){
        int[][] template = mapTemplates.get(templateID);
        Tile[][] grid = new Tile[36][36];
        ArrayList<Collectible> collectibles = new ArrayList<Collectible>();
        for (int i = 0; i < template.length; i++){
            for (int j = 0; j < template[i].length; j++){
                if (template[i][j] == 0) grid[i][j] = new Wall();
                if (template[i][j] == 1) grid[i][j] = new FloorTile();
                if (template[i][j] == 2) grid[i][j] = new ExitTile();
                if (template[i][j] == 3){
                    grid[i][j] = new FloorTile();
                    Entity player = new Player(new Position(36 - i, j));
                }
                if (template[i][j] == 4){
                    grid[i][j] = new FloorTile();
                    collectibles.add(new Gem(new Position(36 - i, j)));
                }
            }
        }
        ArrayList<Zombie> zombies = new ArrayList<>();
        GameWorld world = new GameWorld(grid, player, zombies, collectibles);
    }

}



