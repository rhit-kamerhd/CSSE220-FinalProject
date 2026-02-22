package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Panel displayed when the player wins the game.
 *
 * <p>This panel renders a background image, if available, and overlays a centered
 * "You Win!" message. It is intended to replace the main {@link Game} panel inside
 * {@link Game#frame} when the win condition is reached.
 */
public class WinPanel extends JPanel {

    /**
     * Background image displayed behind the win message.
     * If loading fails, a translucent fallback background is rendered instead.
     */
    private final BufferedImage backgroundImage;

    /**
     * Constructs a new WinPanel and initializes UI components.
     *
     * <p>The constructor attempts to load a background image from application resources.
     * If loading fails, a null image is stored and a fallback background will be drawn in
     * {@link #paintComponent(Graphics)}.
     *
     * @throws IOException if resource loading fails unexpectedly
     */
    public WinPanel() throws IOException {
        setLayout(new BorderLayout());
        setFocusable(true);

        BufferedImage img;
        try {
            img = HUD.load("/winBackground.png");
        } catch (Exception ex) {
            img = null;
        }
        backgroundImage = img;

        JLabel titleLabel = new JLabel("You Win!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 64));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(80, 10, 20, 10));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setOpaque(false);
        center.add(titleLabel);

        add(center, BorderLayout.CENTER);
    }

    /**
     * Replaces the current content of {@link Game#frame} with a new WinPanel and pauses the game.
     *
     * <p>This method clears existing frame content, adds a fresh win panel instance, and forces a
     * layout validation and repaint.
     *
     * @param game the active game instance to pause
     * @throws IOException if constructing the replacement panel fails
     */
    public void renderWinPanel(Game game) throws IOException {
        Game.frame.getContentPane().removeAll();
        Game.frame.setLayout(new BorderLayout());

        Game.frame.add(new WinPanel());
        game.setPaused(true);

        Game.frame.revalidate();
        Game.frame.repaint();

        game.setFocusable(true);
        SwingUtilities.invokeLater(game::requestFocusInWindow);
    }

    /**
     * Paints the panel background.
     *
     * <p>If a background image was successfully loaded, it is scaled to fill the panel.
     * Otherwise, a semi-transparent dark overlay is rendered as a fallback.
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