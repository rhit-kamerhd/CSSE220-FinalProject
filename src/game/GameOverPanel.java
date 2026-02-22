package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOverPanel extends JPanel {

    private final BufferedImage backgroundImage;

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

    public void renderGameOverPanel(Game game) throws IOException {
        Game.frame.getContentPane().removeAll(); Game.frame.setLayout(new BorderLayout());
        Game.frame.add(new GameOverPanel()); game.setPaused(true);
        Game.frame.revalidate(); Game.frame.repaint();
        game.setFocusable(true); SwingUtilities.invokeLater(game::requestFocusInWindow);
    }

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