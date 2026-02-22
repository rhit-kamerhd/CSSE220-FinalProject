package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Panel displayed when the player loses the game.
 *
 * <p>This panel renders a background image, if available, and overlays
 * centered text indicating that the game has ended. It is intended to
 * replace the main {@link Game} panel inside {@link Game#frame}
 * when the player runs out of lives.
 */
public class GameOverPanel extends JPanel {

    /**
     * Background image displayed behind the "Game Over" message.
     * If loading fails, a translucent fallback background is rendered instead.
     */
    private final BufferedImage backgroundImage;

    /**
     * Constructs a new GameOverPanel and initializes UI components.
     *
     * <p>The constructor attempts to load a background image from the
     * application resources. If loading fails, a null image is stored
     * and a fallback background will be drawn in {@link #paintComponent(Graphics)}.
     *
     * @throws IOException if resource loading fails unexpectedly
     */
    public GameOverPanel() throws IOException {
        setLayout(new BorderLayout());
        setFocusable(true);

        BufferedImage img;
        try {
            img = HUD.load("/gameOverBackground.png");
        } catch (Exception ex) {
            img = null;
        }
        backgroundImage = img;

        JLabel titleLabel = new JLabel("Game Over", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 64));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 10, 20, 10));

        JLabel subtitleLabel = new JLabel("You ran out of lives.", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 40, 10));

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(Box.createVerticalGlue());
        center.add(titleLabel);
        center.add(Box.createVerticalStrut(12));
        center.add(subtitleLabel);
        center.add(Box.createVerticalGlue());

        add(center, BorderLayout.CENTER);
    }

    /**
     * Replaces the current content of {@link Game#frame} with a new GameOverPanel
     * and pauses the provided {@link Game} instance.
     *
     * <p>This method:
     * <ul>
     *   <li>Clears the existing frame content</li>
     *   <li>Adds a fresh GameOverPanel</li>
     *   <li>Sets the game to a paused state</li>
     *   <li>Forces layout validation and repaint</li>
     * </ul>
     *
     * @param game the active game instance to pause
     * @throws IOException if constructing the replacement panel fails
     */
    public void renderGameOverPanel(Game game) throws IOException {
        Game.frame.getContentPane().removeAll();
        Game.frame.setLayout(new BorderLayout());

        Game.frame.add(new GameOverPanel());
        game.setPaused(true);

        Game.frame.revalidate();
        Game.frame.repaint();

        game.setFocusable(true);
        SwingUtilities.invokeLater(game::requestFocusInWindow);
    }

    /**
     * Paints the panel background.
     *
     * <p>If a background image was successfully loaded, it is scaled
     * to fill the panel. Otherwise, a semi-transparent dark overlay
     * is rendered as a fallback.
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
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}