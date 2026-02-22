package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinPanel extends JPanel {

    private final BufferedImage backgroundImage;

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

    public void renderWinPanel(Game game) throws IOException {
        Game.frame.getContentPane().removeAll(); Game.frame.setLayout(new BorderLayout());
        Game.frame.add(new WinPanel()); game.setPaused(true);
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