package game;

import immobile.ExitTile;
import mobile.Direction;
import mobile.InputHandler;
import mobile.Zombie;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Main game panel and entry point for the Cave Game.
 *
 * <p>This class is responsible for:
 * <ul>
 *   <li>Constructing the {@link GameWorld} for the current level</li>
 *   <li>Creating and displaying the main {@link JFrame}</li>
 *   <li>Running the Swing {@link Timer}-based game loop</li>
 *   <li>Rendering the world and HUD via {@link #paintComponent(Graphics)}</li>
 *   <li>Handling pause state integration with {@link PausePanel}</li>
 * </ul>
 *
 * <p>Game progression is controlled through {@link GameStatus} transitions stored in the
 * {@link GameWorld} instance.
 */
public class Game extends JPanel {

    /** The current world instance being simulated and rendered. */
    private final GameWorld world;

    /**
     * Container panel placed on the east side of the frame to display the HUD.
     * Exposed as public for integration with other UI panels.
     */
    public final JPanel hudPanel = new JPanel();

    /**
     * Heads-up display component that renders the world and overlays status information.
     * Initialized in the constructor and attached to {@link #hudPanel} when gameplay begins.
     */
    public HUD hud;

    /**
     * Whether the game is currently paused. When paused, the game loop avoids updating the player/zombies
     * and instead updates the {@link PausePanel}.
     */
    private boolean paused;

    /**
     * Current level number to load. Used by {@link #run()} when constructing the world from templates.
     */
    public static int levelNum = 1;

    /** Application window for the game UI. */
    public static final JFrame frame = new JFrame("Cave Game");

    /**
     * Shared keyboard input handler registered on the game panel and assigned to the player on start.
     */
    public static final InputHandler input = new InputHandler();

    /**
     * Tick counter incremented during active (non-paused) gameplay.
     * Used to schedule periodic actions like zombie movement.
     */
    private int time = 0;

    /**
     * Constructs a new game panel bound to a specific {@link GameWorld}.
     *
     * @param world the world to simulate and render
     * @throws IOException if HUD asset initialization or related construction work fails
     */
    public Game(GameWorld world) throws IOException {
        this.world = world;
        hud = new HUD(this, world);
        setFocusable(true);
    }

    /**
     * Program entry point. Delegates to {@link #run()}.
     *
     * @throws IOException if level building or panel initialization fails
     */
    static void main() throws IOException {
        run();
    }

    /**
     * Initializes the UI for the current {@link #levelNum}, builds the corresponding {@link GameWorld},
     * and starts the Swing {@link Timer}-driven game loop.
     *
     * <p>This method:
     * <ul>
     *   <li>Builds a world from a template via {@link WorldBuilder}</li>
     *   <li>Creates the {@link JFrame} content (start panel or game panel)</li>
     *   <li>Initializes HUD assets and configures HUD layout</li>
     *   <li>Runs the main loop which updates entities, checks win/loss, and triggers panel transitions</li>
     * </ul>
     *
     * @throws IOException if building the level or rendering auxiliary panels fails
     */
    private static void run() throws IOException {
        WorldBuilder level = new WorldBuilder();
        GameWorld world = level.buildFromTemplate(levelNum);
        Game game = new Game(world);

        if (levelNum == 2) {
            world.setStatus(GameStatus.STARTING);
        }

        game.addKeyListener(input);
        HUD.initAssets();

        game.hudPanel.setBounds(900, 0, 90, 910);

        Game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game.frame.setSize(new Dimension(990, 910));

        if (levelNum == 1) {
            StartPanel start = new StartPanel(world);
            Game.frame.add(start);
        } else {
            Game.frame.add(game);
        }

        Game.frame.setVisible(true);

        PausePanel pausePanel = new PausePanel(world, game);

        Timer loop = new Timer(100, e -> {
            System.out.println(world.getStatus());

            if (world.getPlayer().getLivesRemaining() < 1) world.setStatus(GameStatus.LOST);
            if (world.getPlayerPosition() == world.getExitTilePosition()) ExitTile.winOnEnter(world);

            if (world.getStatus() == GameStatus.STARTING) {
                Game.frame.getContentPane().removeAll();
                Game.frame.setLayout(new BorderLayout());

                world.getPlayer().setInputHandler(input);

                Game.frame.add(game, BorderLayout.CENTER);

                game.hudPanel.removeAll();
                game.hudPanel.setPreferredSize(new Dimension(90, 0));
                game.hudPanel.setLayout(new BorderLayout());
                game.hudPanel.add(game.hud, BorderLayout.CENTER);
                Game.frame.add(game.hudPanel, BorderLayout.EAST);

                game.setFocusable(true);
                Game.frame.revalidate();
                Game.frame.repaint();
                SwingUtilities.invokeLater(game::requestFocusInWindow);

                world.setStatus(GameStatus.RUNNING);
            }

            if (world.getStatus() == GameStatus.RUNNING) {
                SwingUtilities.invokeLater(game::requestFocusInWindow);
                if (game.paused) {
                    pausePanel.updateStats(world);
                    pausePanel.repaint();
                } else {
                    game.time++;
                    if (game.time % 60 == 0) game.requestFocusInWindow();

                    if (!game.paused) {
                        world.getPlayer().update(world);

                        if (game.time % 10 == 0) {
                            for (Zombie z : world.getZombies()) {
                                Direction zMove = z.chooseMove(world);
                                z.tryMove(zMove, world);
                            }
                        }
                    }
                }
            } else if (world.getPlayer().getLivesRemaining() == 0) {
                world.setStatus(GameStatus.LOST);
                Timer current = (Timer) e.getSource();
                try {
                    GameOverPanel gameOverPanel = new GameOverPanel();
                    gameOverPanel.renderGameOverPanel(game);
                    current.stop();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                world.getExitTile();
                if (ExitTile.winOnEnter(world)) {
                    if (levelNum < 2) {
                        levelNum++;
                        world.setStatus(GameStatus.ON_START);
                        world.setGemsRemaining(5);
                        Timer current = (Timer) e.getSource();
                        try {
                            run();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        current.stop();
                    }
                    if (levelNum == 2) {
                        world.setStatus(GameStatus.WON);
                        world.setGemsRemaining(5);
                        Timer current = (Timer) e.getSource();
                        current.stop();
                        try {
                            WinPanel winPanel = new WinPanel();
                            winPanel.renderWinPanel(game);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

            if (world.getGemsRemaining() == 0) {
                if (world.getPlayerPosition().row == world.getExitTilePosition().row
                        && world.getPlayerPosition().col == world.getExitTilePosition().col) {
                    System.out.println(ExitTile.winOnEnter(world));
                }
            }

            game.repaint();
        });

        game.startTimer(loop);
    }

    /**
     * Starts the provided Swing {@link Timer} that drives the game loop.
     *
     * @param loop the timer instance to start
     */
    private void startTimer(Timer loop) {
        loop.start();
    }

    /**
     * Sets whether the game is paused.
     *
     * @param bool true to pause (suspend world updates), false to resume
     */
    public void setPaused(boolean bool) {
        paused = bool;
    }

    /**
     * Renders the world and HUD onto this panel.
     *
     * <p>This method delegates drawing to the {@link HUD} which is responsible for both
     * world rendering and overlay updates.
     *
     * @param g the graphics context to paint with
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        hud.renderWorld(world, g, this);
        hud.updateHUD(world, levelNum);
    }
}