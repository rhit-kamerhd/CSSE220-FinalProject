package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartPanel extends JPanel {
    private final BufferedImage backgroundImage;

    public StartPanel(GameWorld world) throws IOException {
        setLayout(new BorderLayout()); setFocusable(true);
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
        bottom.setOpaque(false); bottom.add(startButton);
        add(titleLabel, BorderLayout.NORTH); add(bottom, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}