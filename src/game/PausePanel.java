package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * Panel displayed when the game is paused.
 *
 * <p>This panel overlays a pause background and shows basic runtime statistics
 * (lives, level, score) plus an "Unpause" button. Rendering this panel replaces
 * the contents of {@link Game#frame}; unrendering restores the main {@link Game}
 * panel and HUD.
 */
public class PausePanel extends JPanel {

    /**
     * Background image drawn behind the pause UI. If unavailable, a translucent
     * fallback background is painted in {@link #paintComponent(Graphics)}.
     */
    private final BufferedImage backgroundImage;

    /** Label displaying the player's remaining lives. */
    private final JLabel livesLabel = new JLabel();

    /** Label displaying the current level number. */
    private final JLabel levelLabel = new JLabel();

    /** Label displaying the player's current score. */
    private final JLabel scoreLabel = new JLabel();

    /**
     * Constructs a pause menu panel and initializes its UI elements.
     *
     * <p>The constructor loads the pause background image, creates the title and stats UI,
     * and wires the unpause button to restore gameplay by calling {@link #unRenderPauseMenu(Game, GameWorld)}.
     *
     * @param world the active world used to populate pause statistics
     * @param game the active game instance used to toggle pause and restore the game panel
     * @throws IOException if the pause background image cannot be loaded
     */
    public PausePanel(GameWorld world, Game game) throws IOException {
        setLayout(new BorderLayout());
        setFocusable(true);

        backgroundImage = HUD.load("/pauseBackground.png");

        JLabel titleLabel = new JLabel("Pause", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 10, 20, 10));

        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        livesLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        levelLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        livesLabel.setForeground(Color.WHITE);
        levelLabel.setForeground(Color.WHITE);
        scoreLabel.setForeground(Color.WHITE);

        livesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(livesLabel);
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(levelLabel);
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(scoreLabel);

        JButton unpauseButton = new JButton("Unpause");
        unpauseButton.setFont(new Font("Serif", Font.PLAIN, 18));
        unpauseButton.setFocusPainted(false);
        unpauseButton.addActionListener(_ -> {
            game.setPaused(false);
            this.unRenderPauseMenu(game, world);
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        bottom.setOpaque(false);
        bottom.add(unpauseButton);

        add(titleLabel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        updateStats(world);
    }

    /**
     * Updates the displayed pause statistics from the current world state.
     *
     * @param world the active world providing lives and score information
     */
    public void updateStats(GameWorld world) {
        livesLabel.setText("Lives: " + world.getPlayer().getLivesRemaining() + "/3");
        levelLabel.setText("Level: " + Game.levelNum);
        scoreLabel.setText("Score: " + world.getScore());
    }

    /**
     * Replaces the current content of {@link Game#frame} with a new {@link PausePanel}
     * and pauses the provided {@link Game} instance.
     *
     * <p>This method clears existing frame content, adds a fresh pause panel instance,
     * and revalidates/repaints the frame.
     *
     * @param game the active game instance to pause
     * @param world the active world used by the pause panel
     * @throws IOException if the pause panel cannot be constructed (for example, due to missing resources)
     */
    public void renderPauseMenu(Game game, GameWorld world) throws IOException {
        Game.frame.getContentPane().removeAll();
        Game.frame.setLayout(new BorderLayout());

        Game.frame.add(new PausePanel(world, game));
        game.setPaused(true);

        Game.frame.revalidate();
        Game.frame.repaint();

        game.setFocusable(true);
        SwingUtilities.invokeLater(game::requestFocusInWindow);
    }

    /**
     * Restores the main {@link Game} panel and HUD to {@link Game#frame} after unpausing.
     *
     * <p>This method removes pause UI content from the frame, re-adds the game panel and HUD,
     * reattaches the shared input handler, and revalidates/repaints the frame.
     *
     * @param game the game panel to restore into the frame
     * @param world the active world whose player input handler is reattached
     */
    public void unRenderPauseMenu(Game game, GameWorld world) {
        Game.frame.getContentPane().removeAll();
        Game.frame.setLayout(new BorderLayout());

        Game.frame.add(game, BorderLayout.CENTER);
        Game.frame.addKeyListener(Game.input);

        world.getPlayer().setInputHandler(Game.input);

        game.hudPanel.setPreferredSize(new Dimension(90, 0));
        game.hudPanel.setLayout(new BorderLayout());
        game.hudPanel.add(game.hud, BorderLayout.CENTER);

        Game.frame.add(game.hudPanel, BorderLayout.EAST);

        Game.frame.revalidate();
        Game.frame.repaint();

        System.out.println(world.getPlayer().getInput());
    }

    /**
     * Paints the pause background.
     *
     * <p>If a background image is available, it is scaled to fill the panel. Otherwise,
     * a semi-transparent dark overlay is drawn as a fallback.
     *
     * @param g the graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 170));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}