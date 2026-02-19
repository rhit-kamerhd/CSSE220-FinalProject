package game;

import javax.swing.*;

public class PausePanel extends JFrame {
    JButton unpause;
    JTextField livesText;
    JTextField scoreText;
    JTextField levelText;
    JTextField gamePaused;
    int lives;
    int score;
    int level;

    public PausePanel(GameWorld world, Game game){
        lives = world.getPlayer().getLivesRemaining();
        score = world.getPlayer().score; level = game.levelNum;
        unpause = new JButton("Unpause");
        livesText = new JTextField("Lives: " + lives);
        scoreText = new JTextField("Score: " + score);
        levelText = new JTextField("Level " + level);
        gamePaused = new JTextField("");

    }

}
