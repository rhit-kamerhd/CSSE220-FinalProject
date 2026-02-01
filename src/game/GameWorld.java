package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameWorld {
    private final Tile[][] grid;
    private final Player player;
    private final ArrayList<Zombie> zombies;
    private final ArrayList<Collectible> collectibles;
    private static int gemsRemaining;
    private GameStatus status;

    public GameWorld(Tile[][] g, Player p, ArrayList<Zombie> z, ArrayList<Collectible> c){
        grid = g; player = p; zombies = z; collectibles = c; gemsRemaining = c.size();
        status = GameStatus.RUNNING;
    }
//
//    public boolean isWalkable(Position pos){
//        int[] coords = pos.getPosition(); Tile tile = grid[coords[0]][coords[1]];
//        if (tile instanceof FloorTile){
//            for (Zombie z : zombies){
//                if (z.getPosition() != pos){
//                    return true;
//        }}}
//        return false;
//    }
//
//    public Tile tileAt(Position pos) {
//        int[] position = pos.getPosition();
//        return grid[position[0]][position[1]];
//    }
//
//    public Collectible collectibleAt(Position pos){
//        for (Collectible collectible : collectibles) {
//            Position position = (Position) collectible.getPosition();
//            if (position.equals(pos)) return collectible;
//        }
//        return null;
//    }
//
//    public GameStatus getStatus(){
//        return this.status;
//    }
//
//    public void decrementGems(){
//        gemsRemaining--;
//    }
//
//    public void removeCollectible(Collectible c){
//        collectibles.remove(c);
//    }

    public void setStatus(GameStatus s) {
        status = s;
    }

    public static int getGemsRemaining(){
        return gemsRemaining;
    }
    public Tile[][] getMap(){
        return grid;
    }
    public ArrayList<Collectible> getCollectibles(){
        return collectibles;
    }
    public ArrayList<Zombie> getZombies(){
        return zombies;
    }
    public Position getPlayerPosition(){
        return player.getPosition();
    }

    public Zombie getZombieAt(Position p) {
        for (Zombie z : zombies) {
            if (z.getPosition() == p) return z;
        }
        return null;
    }

    public boolean isWall(Position p) {
        int xPos = p.getPosition()[0]; int yPos = p.getPosition()[1];
        return !(grid[xPos][yPos] instanceof Wall);
    }

    public void renderWorld(GameWorld world, Graphics g) throws IOException {
        BufferedImage wallSprite = ImageIO.read(new File("../sprites/wall_sprite.png"));
        BufferedImage floorSprite = ImageIO.read(new File("../sprites/floor_sprite"));
        BufferedImage exitSprite = ImageIO.read(new File("../sprites/exit_sprite"));
        BufferedImage gemSprite =  ImageIO.read(new File("../sprites/gem_sprite"));
        BufferedImage playerSprite = ImageIO.read(new File("../sprites/player_sprite"));
        BufferedImage zombieSprite = ImageIO.read(new File("../sprites/zombie_sprite"));
        Tile[][] map = world.getMap();
        ArrayList<Collectible> collectibles = world.getCollectibles();
        ArrayList<Zombie> zombies = world.getZombies();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++){
                int xCoordinate = (36 * i); int yCoordinate = -(36 * j);
                if (map[i][j] instanceof Wall) g.drawImage(wallSprite, xCoordinate, yCoordinate, (ImageObserver) this);
                if (map[i][j] instanceof FloorTile) g.drawImage(floorSprite, xCoordinate, yCoordinate, (ImageObserver) this);
                if (map[i][j] instanceof ExitTile) g.drawImage(exitSprite, xCoordinate, yCoordinate, (ImageObserver) this);
            }}
        for (Collectible gem : collectibles){
            Position gemPos = (Position) gem.getPosition(); int[] c1 = gemPos.getPosition();
            int xCoordinate = 36 * c1[0]; int yCoordinate = (-36) * c1[1];
            g.drawImage(gemSprite, xCoordinate, yCoordinate, (ImageObserver) this);
        }
        for (Zombie zombie : zombies){
            Position zPos = zombie.getPosition(); int[] c2 = zPos.getPosition();
            int xCoordinate = 36 * c2[0]; int yCoordinate = (-36) * c2[2];
            g.drawImage(zombieSprite, xCoordinate, yCoordinate, (ImageObserver) this);
        }
        Position playerPosition = world.getPlayerPosition(); int[] c3 = playerPosition.getPosition();
        int xCoordinate = 36 * c3[0]; int yCoordinate = (-36) * c3[1];
        g.drawImage(playerSprite, xCoordinate, yCoordinate, (ImageObserver) this);
    }

    public GameStatus getStatus() {
        return status;
    }
}
