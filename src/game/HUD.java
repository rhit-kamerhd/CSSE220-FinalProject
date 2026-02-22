package game;

import immobile.*;
import mobile.Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * Heads-up display (HUD) and rendering helper for the Cave Game.
 *
 * <p>This panel is used for two related responsibilities:
 * <ul>
 *   <li>Displaying status text (lives, gems collected, level number) and a Pause button</li>
 *   <li>Rendering the tile map and entities (player, zombies, gems) via {@link #renderWorld(GameWorld, Graphics, ImageObserver)}</li>
 * </ul>
 *
 * <p>Sprite resources are loaded once through {@link #initAssets()} and then reused for rendering.
 */
public class HUD extends JPanel {

    /** Default font used for HUD labels and the pause button. */
    private final Font font = new Font("Cambria", Font.PLAIN, 14);

    /**
     * Pause button reference. Note: the current implementation declares a local variable in the
     * constructor, which shadows this field.
     */
    private JButton pauseButton;

    /** Label displaying the current level number. */
    private final JLabel levelLabel = new JLabel();

    /** Pixel size (width and height) of each tile when rendered. */
    private static final int TILE = 35;

    /** Sprite used to render wall tiles. */
    private static BufferedImage wallSprite;

    /** Sprite used to render floor tiles. */
    private static BufferedImage floorSprite;

    /** Sprite used to render the exit tile. */
    private static BufferedImage exitSprite;

    /** Sprite used to render gems. */
    private static BufferedImage gemSprite;

    /** Sprite used to render the player. */
    private static BufferedImage playerSprite;

    /** Sprite used to render zombies. */
    private static BufferedImage zombieSprite;

    /** Label displaying the player's remaining lives. */
    private final JLabel livesLabel = new JLabel();

    /** Label displaying the number of gems collected out of the total. */
    private final JLabel gemsLabel = new JLabel();

    /**
     * Constructs a HUD panel wired to a game instance and world.
     *
     * <p>This constructor creates and lays out HUD labels and a pause button. Pressing pause
     * sets the game's paused state and transitions to the {@link PausePanel}.
     *
     * @param game the active game instance (used to toggle pause and request focus)
     * @param world the world used by the pause menu and HUD updates
     * @throws IOException if constructing the pause panel or related resource work fails
     */
    public HUD(Game game, GameWorld world) throws IOException {
        PausePanel pausePanel = new PausePanel(world, game);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        livesLabel.setFont(font);
        gemsLabel.setFont(font);
        levelLabel.setFont(font);

        livesLabel.setLocation(900, 50);
        gemsLabel.setLocation(900, 100);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(font);
        pauseButton.setLocation(900, 200);

        levelLabel.setLocation(900, 150);

        add(livesLabel);
        add(gemsLabel);
        add(levelLabel);
        add(Box.createVerticalStrut(10));
        add(pauseButton);

        livesLabel.setText("Lives: ");
        gemsLabel.setText("Gems: ");
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

    /**
     * Updates the HUD labels to reflect the current game state.
     *
     * <p>The gem display assumes a fixed total of 5 gems per level and computes collected
     * gems as {@code total - world.getGemsRemaining()}.
     *
     * @param world the world providing player lives and remaining gems
     * @param levelNum the current level number to display
     */
    public void updateHUD(GameWorld world, int levelNum) {
        livesLabel.setText("Lives: " + world.getPlayer().getLivesRemaining() + "/3");
        levelLabel.setText("Level: " + levelNum);

        int total = 5;
        int collected = total - world.getGemsRemaining();
        gemsLabel.setText("Gems: " + collected + "/" + total);
    }

    /**
     * Paint hook for Swing. The HUD uses labels and components for its UI, and world rendering is
     * performed by {@link #renderWorld(GameWorld, Graphics, ImageObserver)}.
     *
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Loads all sprite assets used for rendering tiles and entities.
     *
     * <p>This method is idempotent: if the wall sprite is already loaded, the method returns
     * immediately and does not reload any resources.
     *
     * @throws RuntimeException if a required sprite cannot be loaded
     */
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

    /**
     * Loads an image resource from the classpath.
     *
     * @param path classpath resource path (for example, {@code "/wall_sprite.png"})
     * @return the decoded {@link BufferedImage}
     * @throws IOException if reading or decoding fails
     * @throws IllegalStateException if the resource does not exist on the classpath
     */
    public static BufferedImage load(String path) throws IOException {
        var in = HUD.class.getResourceAsStream(path);
        if (in == null) throw new IllegalStateException("Missing resource: " + path);
        return ImageIO.read(in);
    }

    /**
     * Renders the tile map and all entities (gems, zombies, player) to the provided graphics context.
     *
     * <p>Tiles are rendered first (wall, exit, floor), then gems, then zombies, then the player.
     * The observer is typically the calling Swing component.
     *
     * <p>Note: {@link #initAssets()} must be called before rendering so that sprite fields are populated.
     *
     * @param world the world to render
     * @param g the graphics context used for drawing
     * @param observer image observer used by {@link Graphics#drawImage(Image, int, int, int, int, ImageObserver)}
     */
    public void renderWorld(GameWorld world, Graphics g, ImageObserver observer) {
        Tile[][] map = world.getMap();

        Color initialColor = g.getColor();
        g.setColor(Color.BLACK);
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
            if (p != null) {
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