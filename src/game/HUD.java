package game;
import immobile.ExitTile;
import immobile.FloorTile;
import immobile.Tile;
import immobile.Wall;
import immobile.Collectible;
import immobile.Gem;
import game.Position;
import mobile.Player;
import mobile.Zombie;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class HUD {
    //DONE: complete method render(GameWorld world)
    public void render(GameWorld world, Graphics g){
        super.paintComponent(g);
        BufferedImage wallSprite = ImageIO.read(new File("./sprites/wallSprite"));
        BufferedImage floorSprite = ImageIO.read(new File("./sprites/floorSprite"));
        BufferedImage exitSprite = ImageIO.read(new File("./sprites/exitSprite"));
        BufferedImage gemSprite =  ImageIO.read(new File("./sprites/gemSprite"));
        BufferedImage playerSprite = ImageIO.read(new File("./sprites/playerSprite"));
        BufferedImage zombieSprite = ImageIO.read(new File("./sprites/zombieSprite"));
        Tile[][] map = world.getMap();
        ArrayList<Collectible> collectibles = world.getCollectibles();
        ArrayList<Zombie> zombies = world.getZombies();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++){
                int xCoordinate = (36 * i); int yCoordinate = -(36 * j);
                if (map[i][j] instanceof Wall){
                    g.drawImage(wallSprite, xCoordinate, yCoordinate);
                }
                if (map[i][j] instanceof FloorTile){
                    g.drawImage(floorSprite, xCoordinate, yCoordinate);
                }
                if (map[i][j] instanceof ExitTile){
                    g.drawImage(exitSprite, xCoordinate, yCoordinate);
                }
            }
        }
        for (Collectible gem : collectibles){
            Position gemPos = (Position) gem.getPosition(); int[] c1 = gemPos.getPosition();
            int xCoordinate = 36 * c1[0]; int yCoordinate = (-36) * c1[1];
            g.drawImage(gemSprite, xCoordinate, yCoordinate);
        }
        for (Zombie zombie : zombies){
            Position zPos = zombie.getPosition(); int[] c2 = zPos.getPosition();
            int xCoordinate = 36 * c2[0]; int yCoordinate = (-36) * c2[2];
            g.drawImage(zombieSprite, xCoordinate, yCoordinate);
        }
        Position playerPosition = world.getPlayerPosition(); int[] c3 = playerPosition.getPosition();
        int xCoordinate = 36 * c3[0]; int yCoordinate = (-36) * c3[1];
        g.drawImage(playerSprite, xCoordinate, yCoordinate);

    }

    //TODO: complete method renderPauseMenu()
    public void renderPauseMenu(){

    }

}
