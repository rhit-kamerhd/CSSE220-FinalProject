package game;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    public StartPanel(GameWorld world) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Cave Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.PLAIN, 18));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            world.setStatus(GameStatus.RUNNING);
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(titleLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}