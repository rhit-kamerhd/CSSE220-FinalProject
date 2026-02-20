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
    private final Font font = new Font("Cambria", Font.PLAIN, 14);
    private JButton pauseButton;
    private final JLabel levelLabel = new JLabel();
    private static final int TILE = 35;
    private static BufferedImage wallSprite;
    private static BufferedImage floorSprite;
    private static BufferedImage exitSprite;
    private static BufferedImage gemSprite;
    private static BufferedImage playerSprite;
    private static BufferedImage zombieSprite;
    private final JLabel livesLabel = new JLabel();
    private final JLabel gemsLabel = new JLabel();

    public HUD(Game game, GameWorld world) throws IOException {
        PausePanel pausePanel = new PausePanel(world, game);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); setOpaque(false);
        livesLabel.setFont(font); gemsLabel.setFont(font); levelLabel.setFont(font);
        livesLabel.setLocation(900, 50); gemsLabel.setLocation(900, 100);
        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(font);
        pauseButton.setLocation(900, 200);
        levelLabel.setLocation(900, 150);
        add(livesLabel); add(gemsLabel); add(levelLabel);
        add(Box.createVerticalStrut(10)); add(pauseButton);
        livesLabel.setText("Lives: "); gemsLabel.setText("Gems: ");
        levelLabel.setText("Level: ");
        pauseButton.addActionListener(e -> {
            game.setPaused(true);
            try {
                pausePanel.renderPauseMenu(game, world);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void updateHUD(GameWorld world, int levelNum) {
        livesLabel.setText("Lives: " + world.getPlayer().getLivesRemaining() + "/3");
        levelLabel.setText("Level: " + levelNum);
        int total = 5;
        int collected = total - world.getGemsRemaining();
        gemsLabel.setText("Gems: " + collected + "/" + total);
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

    public void renderWorld(GameWorld world, Graphics g, ImageObserver observer) {
        Tile[][] map = GameWorld.getMap();
        Color initialColor = g.getColor(); g.setColor(Color.BLACK);
        g.drawRect(0, 0, 870, 1020);
        g.setColor(initialColor);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int x = col * TILE;
                int y = row * TILE;
                Tile t = map[row][col];
                switch (t) {
                    case Wall wall -> g.drawImage(wallSprite, x, y, TILE, TILE, observer);
                    case ExitTile exitTile -> g.drawImage(exitSprite, x, y, TILE, TILE, observer);
                    case null, default -> g.drawImage(floorSprite, x, y, TILE, TILE, observer);
                }
            }
        }
        for (Gem gem : world.getGems()) {
            Position p = gem.getPosition();
            if (p != null){
                int[] rc = p.getPosition();
                int x = rc[1] * TILE;
                int y = rc[0] * TILE;
                g.drawImage(gemSprite, x, y, TILE, TILE, observer);
            }
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
}
