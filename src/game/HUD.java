package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Dimension;

public class HUD extends JPanel{
    private boolean showPauseOverlay;
    private static JButton unpauseButton = null;
    private static JLabel levelLabel = null;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1050, 1050);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public HUD() {
        Game.levelNum = 1; setLayout(null); levelLabel = new JLabel();
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Cambria", Font.BOLD, 18));
        levelLabel.setVisible(false);
        unpauseButton = new JButton("Unpause");
        unpauseButton.setFocusable(false); unpauseButton.setVisible(false);
        add(levelLabel); add(unpauseButton); setFocusable(true);
    }

    public void renderHUD(JPanel hud){
        int totalGems = Game.levelNum + 1;
        setFont(new Font("Cambria", Font.BOLD, 16)); hud.setLocation(0, 0);
        JTextField lives = new JTextField("Lives: " + Player.getLivesRemaining() + "/3");
        JTextField gemsCollected = new JTextField("Gems Collected: " + GameWorld.getGemsRemaining() + "/" + totalGems);
        JTextField level = new JTextField("Level " + Game.levelNum);
        level.setLocation(1000, 0);
        lives.setLocation(0, 0); gemsCollected.setLocation(0, 50);
        hud.add(lives); hud.add(gemsCollected); hud.add(level);
    }

    public static void renderWorld(GameWorld world, Graphics g, ImageObserver observer) throws IOException {
        BufferedImage wallSprite = ImageIO.read(new File("../sprites/wall_sprite.png"));
        BufferedImage floorSprite = ImageIO.read(new File("../sprites/floor_sprite"));
        BufferedImage exitSprite = ImageIO.read(new File("../sprites/exit_sprite"));
        BufferedImage gemSprite =  ImageIO.read(new File("../sprites/gem_sprite"));
        BufferedImage playerSprite = ImageIO.read(new File("../sprites/player_sprite"));
        BufferedImage zombieSprite = ImageIO.read(new File("../sprites/zombie_sprite"));
        Tile[][] map = GameWorld.getMap();
        ArrayList<Collectible> collectibles = world.getCollectibles();
        ArrayList<Zombie> zombies = world.getZombies();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++){
                int xCoordinate = (36 * i); int yCoordinate = -(36 * j);
                if (map[i][j] instanceof Wall) g.drawImage(wallSprite, xCoordinate, yCoordinate, observer);
                if (map[i][j] instanceof FloorTile) g.drawImage(floorSprite, xCoordinate, yCoordinate, observer);
                if (map[i][j] instanceof ExitTile) g.drawImage(exitSprite, xCoordinate, yCoordinate, observer);
            }}
        for (Collectible gem : collectibles){
            Position gemPos = (Position) gem.getPosition(); int[] c1 = gemPos.getPosition();
            int xCoordinate = 36 * c1[0]; int yCoordinate = (-36) * c1[1];
            g.drawImage(gemSprite, xCoordinate, yCoordinate, observer);
        }
        for (Zombie zombie : zombies){
            Position zPos = zombie.getPosition(); int[] c2 = zPos.getPosition();
            int xCoordinate = 36 * c2[0]; int yCoordinate = (-36) * c2[2];
            g.drawImage(zombieSprite, xCoordinate, yCoordinate, observer);
        }
        Position playerPosition = world.getPlayerPosition(); int[] c3 = playerPosition.getPosition();
        int xCoordinate = 36 * c3[0]; int yCoordinate = (-36) * c3[1];
        g.drawImage(playerSprite, xCoordinate, yCoordinate, observer);
    }

    public void renderPauseMenu(){
        showPauseOverlay = true; levelLabel.setText("Level: " + Game.levelNum);
        Dimension size = getSize();
        int centerX = size.width / 2; int centerY = size.height / 2;
        int labelW = 200; int labelH = 30;
        levelLabel.setBounds(centerX - labelW / 2, centerY - 90, labelW, labelH);
        int buttonW = 140; int buttonH = 35;
        unpauseButton.setBounds(centerX - buttonW / 2, centerY - 30, buttonW, buttonH);
        levelLabel.setVisible(true); unpauseButton.setVisible(true);
        revalidate(); repaint();
    }
    public void hidePauseMenu() {
        showPauseOverlay = false;
        levelLabel.setVisible(false);
        unpauseButton.setVisible(false);
        repaint();
    }

}
