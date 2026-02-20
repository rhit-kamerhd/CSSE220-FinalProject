package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class PausePanel extends JPanel {

    private final BufferedImage backgroundImage;

    private final JLabel livesLabel = new JLabel();
    private final JLabel levelLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();

    public PausePanel(GameWorld world, Game game) throws IOException {
        setLayout(new BorderLayout()); setFocusable(true);
        backgroundImage = HUD.load("/pauseBackground.png");
        JLabel titleLabel = new JLabel("Pause", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 10, 20, 10));
        JPanel statsPanel = new JPanel(); statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        livesLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        levelLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        livesLabel.setForeground(Color.WHITE); levelLabel.setForeground(Color.WHITE);
        scoreLabel.setForeground(Color.WHITE); livesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT); scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(Box.createVerticalStrut(10)); statsPanel.add(livesLabel);
        statsPanel.add(Box.createVerticalStrut(10)); statsPanel.add(levelLabel);
        statsPanel.add(Box.createVerticalStrut(10)); statsPanel.add(scoreLabel);
        JButton unpauseButton = new JButton("Unpause");
        unpauseButton.setFont(new Font("Serif", Font.PLAIN, 18));
        unpauseButton.setFocusPainted(false);
        unpauseButton.addActionListener(_ -> {
            game.setPaused(false);
            this.unRenderPauseMenu(game, world);
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        bottom.setOpaque(false); bottom.add(unpauseButton);
        add(titleLabel, BorderLayout.NORTH); add(statsPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        updateStats(world);
    }

    public void updateStats(GameWorld world) {
        livesLabel.setText("Lives: " + world.getPlayer().getLivesRemaining() + "/3");
        levelLabel.setText("Level: " + Game.levelNum);
        scoreLabel.setText("Score: " + world.getScore());
    }

    public void renderPauseMenu(Game game, GameWorld world) throws IOException {
        game.frame.getContentPane().removeAll(); game.frame.setLayout(new BorderLayout());
        game.frame.add(new PausePanel(world, game)); game.setPaused(true);
        game.frame.revalidate(); game.frame.repaint();
        game.setFocusable(true); SwingUtilities.invokeLater(game::requestFocusInWindow);
    }

    public void unRenderPauseMenu(Game game, GameWorld world){
        game.frame.getContentPane().removeAll(); game.frame.setLayout(new BorderLayout());
        game.frame.add(game, BorderLayout.CENTER); game.frame.addKeyListener(Game.input);
        world.getPlayer().setInputHandler(Game.input);
        game.hudPanel.setPreferredSize(new Dimension(90, 0));
        game.hudPanel.setLayout(new BorderLayout()); game.hudPanel.add(game.hud, BorderLayout.CENTER);
        game.frame.add(game.hudPanel, BorderLayout.EAST); game.frame.revalidate();
        game.frame.repaint();
        System.out.println(world.getPlayer().getInput());
    }

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