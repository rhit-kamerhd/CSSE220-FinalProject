package game;
import immobile.*;
import mobile.InputHandler;
import mobile.Zombie;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;

public class Game extends JPanel{
    private GameWorld world;
    private InputHandler input;
    private HUD hud;
    private boolean paused;
    public static int levelNum = 1;

    public void init() {
        while (levelNum < 6) {
            paused = false; GameStatus s = GameStatus.RUNNING;
            WorldBuilder worldBuilder = new WorldBuilder();
            world = worldBuilder.buildFromTemplate(levelNum);

            while (s == GameStatus.RUNNING) {

            }
            if (s == GameStatus.WON) {
                levelNum++; this.onWin();
            }
        }
    }

    public void update(double dt){

    }

    public void renderWorld(GameWorld world, Graphics g) throws IOException {
        super.paintComponent(g);
        BufferedImage wallSprite = ImageIO.read(new File("../sprites/wallSprite.png"));
        BufferedImage floorSprite = ImageIO.read(new File("../sprites/floorSprite"));
        BufferedImage exitSprite = ImageIO.read(new File("../sprites/exitSprite"));
        BufferedImage gemSprite =  ImageIO.read(new File("../sprites/gemSprite"));
        BufferedImage playerSprite = ImageIO.read(new File("../sprites/playerSprite"));
        BufferedImage zombieSprite = ImageIO.read(new File("../sprites/zombieSprite"));
        Tile[][] map = world.getMap();
        ArrayList<Collectible> collectibles = world.getCollectibles();
        ArrayList<Zombie> zombies = world.getZombies();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++){
                int xCoordinate = (36 * i); int yCoordinate = -(36 * j);
                if (map[i][j] instanceof Wall) g.drawImage(wallSprite, xCoordinate, yCoordinate, this);
                if (map[i][j] instanceof FloorTile) g.drawImage(floorSprite, xCoordinate, yCoordinate, this);
                if (map[i][j] instanceof ExitTile) g.drawImage(exitSprite, xCoordinate, yCoordinate, this);
            }}
        for (Collectible gem : collectibles){
            Position gemPos = (Position) gem.getPosition(); int[] c1 = gemPos.getPosition();
            int xCoordinate = 36 * c1[0]; int yCoordinate = (-36) * c1[1];
            g.drawImage(gemSprite, xCoordinate, yCoordinate, this);
        }
        for (Zombie zombie : zombies){
            Position zPos = zombie.getPosition(); int[] c2 = zPos.getPosition();
            int xCoordinate = 36 * c2[0]; int yCoordinate = (-36) * c2[2];
            g.drawImage(zombieSprite, xCoordinate, yCoordinate, this);
        }
        Position playerPosition = world.getPlayerPosition(); int[] c3 = playerPosition.getPosition();
        int xCoordinate = 36 * c3[0]; int yCoordinate = (-36) * c3[1];
        g.drawImage(playerSprite, xCoordinate, yCoordinate, this);
    }

    public void togglePause(GameStatus s){
        JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!paused){

                    }
                    if (paused){

                    }
                }
            }
        });
    }
    public void onWin(){
    }

}
