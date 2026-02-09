package game;

import immobile.*;
import mobile.Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;


public class HUD extends JPanel {

    private boolean showPauseOverlay;
    private static JButton unpauseButton = null;
    private static JLabel levelLabel = null;
    private static final int TILE = 35;
    private static BufferedImage wallSprite;
    private static BufferedImage floorSprite;
    private static BufferedImage exitSprite;
    private static BufferedImage gemSprite;
    private static BufferedImage playerSprite;
    private static BufferedImage zombieSprite;

    public HUD() {
        Game.levelNum = 1;
        setLayout(null);
        levelLabel = new JLabel();
        levelLabel.setForeground(Color.BLACK);
        levelLabel.setFont(new Font("Cambria", Font.BOLD, 18));
        levelLabel.setVisible(false);
        unpauseButton = new JButton("Unpause");
        unpauseButton.setFocusable(false);
        unpauseButton.setVisible(false);
        add(levelLabel);
        add(unpauseButton);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


    public static void initAssets() {
        if (wallSprite != null) return;
        try {
            wallSprite = load("/wall_sprite.png");
            floorSprite = load("/floor_sprite.png");
            exitSprite = load("/exit_sprite.png");
            gemSprite = load("/coin_sprite.png");
            playerSprite = load("/player_sprite.png");
            zombieSprite = load("/zombie_sprite.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

 
    public static BufferedImage load(String path) throws IOException {
        var in = HUD.class.getResourceAsStream(path);
        if (in == null) throw new IllegalStateException("Missing resource: " + path);
        return ImageIO.read(in);
    }


    public static void renderZombies(Zombie zombie, int tileSize, Graphics g, ImageObserver observer) {
        Position zombiePos = zombie.getPosition();
        int x = tileSize * zombiePos.col;
        int y = tileSize * zombiePos.row;
        g.drawImage(zombieSprite, x, y, tileSize, tileSize, observer);
    }

    
    public static void renderPlayer(GameWorld world, int tileSize, Graphics g, ImageObserver observer) {
        Position playerPos = world.getPlayer().getPosition();
        int x = tileSize * playerPos.col;
        int y = tileSize * playerPos.row;

        g.drawImage(playerSprite, x, y, tileSize, tileSize, observer);
    }

    public static void renderHUD(){

    }
    public static void renderWorld(GameWorld world, Graphics g, ImageObserver observer) {
        Tile[][] map = GameWorld.getMap();
        Color initialColor = g.getColor(); g.setColor(Color.BLACK);
        g.drawRect(0, 0, 870, 1020);
        g.setColor(initialColor);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int x = col * TILE;
                int y = row * TILE;
                Tile t = map[row][col];
                if (t == null) {
                    g.drawImage(floorSprite, x, y, TILE, TILE, observer);
                } else if (t instanceof Wall) {
                    g.drawImage(wallSprite, x, y, TILE, TILE, observer);
                } else if (t instanceof ExitTile) {
                    g.drawImage(exitSprite, x, y, TILE, TILE, observer);
                } else {
                    g.drawImage(floorSprite, x, y, TILE, TILE, observer);
                }
            }
        }

       
        for (Collectible gem : world.getCollectibles()) {
            Position p = gem.getPosition();
            if (p != null){
                int[] rc = p.getPosition();
                int x = rc[1] * TILE;
                int y = rc[0] * TILE;
                g.drawImage(gemSprite, x, y, TILE, TILE, observer);
        }}

      
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

 
    public void renderPauseMenu() {
        showPauseOverlay = true;

        levelLabel.setText("Level: " + Game.levelNum);

        Dimension size = getSize();
        int centerX = size.width / 2;
        int centerY = size.height / 2;
        int labelW = 200;
        int labelH = 30;
        levelLabel.setBounds(centerX - labelW / 2, centerY - 90, labelW, labelH);
        int buttonW = 140;
        int buttonH = 35;
        unpauseButton.setBounds(centerX - buttonW / 2, centerY - 30, buttonW, buttonH);
        levelLabel.setVisible(true);
        unpauseButton.setVisible(true);
        revalidate();
        repaint();
    }

 
    public void hidePauseMenu() {
        showPauseOverlay = false;
        levelLabel.setVisible(false);
        unpauseButton.setVisible(false);
        repaint();
    }
}
