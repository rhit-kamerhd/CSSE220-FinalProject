package game;

import immobile.*;
import mobile.Player;
import mobile.Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observer;

public class HUD extends JPanel{
    private boolean showPauseOverlay;
    private static JButton unpauseButton = null;
    private static JLabel levelLabel = null;
    private static final int TILE = 25;

    public static void renderZombies(Zombie zombie, GameWorld world, int TILE_SIZE, Graphics g, ImageObserver observer) throws IOException {
        BufferedImage zombieSprite = load("/zombie_sprite.png");
        Position zombiePos = zombie.getPosition();
        int x = TILE_SIZE * zombiePos.col;
        int y = TILE_SIZE * zombiePos.row;
        g.drawImage(zombieSprite, x, y, TILE_SIZE, TILE_SIZE, observer);
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



    public static BufferedImage load(String path) throws IOException {
        var in = HUD.class.getResourceAsStream(path);
        if (in == null) throw new IllegalStateException("Missing resource: " + path);
        return ImageIO.read(in);
    }

    public static void renderPlayer(Player player, GameWorld world, int TILE_SIZE, Graphics g, ImageObserver observer){
        BufferedImage playerSprite = player.sprite;
        Position playerPos = world.getPlayer().getPosition();
        int x = TILE_SIZE * playerPos.col;
        int y = TILE_SIZE * playerPos.row;
        g.drawImage(playerSprite, x, y, TILE_SIZE, TILE_SIZE, observer);
    }


    public static void renderWorld(GameWorld world, Graphics g, ImageObserver observer) throws IOException {
        BufferedImage wallSprite = load("/wall_sprite.png");
        BufferedImage floorSprite = load("/floor_sprite.png");
        BufferedImage exitSprite = load("/exit_sprite.png");
        BufferedImage gemSprite = load("/coin_sprite.png");
        BufferedImage playerSprite = load("/player_sprite.png");
        BufferedImage zombieSprite = load("/zombie_sprite.png");
        Tile[][] map = GameWorld.getMap();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int x = col * TILE;
                int y = row * TILE;
                Tile t = map[row][col];
                switch (t) {
                    case null -> {
                        g.drawImage(floorSprite, x, y, TILE, TILE, observer);
                    }
                    case Wall _ -> g.drawImage(wallSprite, x, y, TILE, TILE, observer);
                    case ExitTile _ -> g.drawImage(exitSprite, x, y, TILE, TILE, observer);
                    default -> g.drawImage(floorSprite, x, y, TILE, TILE, observer);
                }
            }
        }
        for (Collectible gem : world.getCollectibles()) {
            Position p = (Position) gem.getPosition();
            int[] rc = p.getPosition();
            int x = rc[1] * TILE;
            int y = rc[0] * TILE;
            g.drawImage(gemSprite, x, y, TILE, TILE, observer);
        }
        for (Zombie z : world.getZombies()) {
            Position p = z.getPosition();
            int[] rc = p.getPosition();
            int x = rc[1] * TILE;
            int y = rc[0] * TILE;
            g.drawImage(zombieSprite, x, y, TILE, TILE, observer);
        }
        Position p = world.getPlayerPosition();
        int[] rc = p.getPosition();
        int x = rc[1] * TILE;
        int y = rc[0] * TILE;
        g.drawImage(playerSprite, x, y, TILE, TILE, observer);
    }

    public void renderHUD(JPanel hud){
        int totalGems = Game.levelNum + 1;
        setFont(new Font("Cambria", Font.BOLD, 16)); hud.setLocation(0  , 0);
        JTextField lives = new JTextField("Lives: " + Player.getLivesRemaining() + "/3");
        JTextField gemsCollected = new JTextField("Gems Collected: " + GameWorld.getGemsRemaining() + "/" + totalGems);
        JTextField level = new JTextField("Level " + Game.levelNum);
        level.setLocation(1000, 0);
        lives.setLocation(0, 0); gemsCollected.setLocation(0, 50);
        hud.add(lives); hud.add(gemsCollected); hud.add(level);
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
