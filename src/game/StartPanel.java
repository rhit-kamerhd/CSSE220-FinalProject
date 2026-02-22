package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Panel displayed at application startup before gameplay begins.
 *
 * <p>This panel renders a start screen background, displays the game title,
 * and provides a button that transitions the associated {@link GameWorld}
 * to the {@link GameStatus#STARTING} state when pressed.
 */
public class StartPanel extends JPanel {

    /**
     * Background image drawn behind the start screen UI.
     */
    private final BufferedImage backgroundImage;

    /**
     * Constructs a start screen panel for the given world.
     *
     * <p>The start button updates the world status to {@link GameStatus#STARTING},
     * which signals the main game loop to initialize and begin gameplay.
     *
     * @param world the world whose status will be updated when the game starts
     * @throws IOException if the start screen background image cannot be loaded
     */
    public StartPanel(GameWorld world) throws IOException {
        setLayout(new BorderLayout());
        setFocusable(true);

        backgroundImage = HUD.load("/startBackground.png");

        JLabel titleLabel = new JLabel("Cave Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.PLAIN, 18));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> world.setStatus(GameStatus.STARTING));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        bottom.setOpaque(false);
        bottom.add(startButton);

        add(titleLabel, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Paints the start screen background.
     *
     * <p>If a background image is available, it is scaled to fill the panel.
     *
     * @param g the graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}