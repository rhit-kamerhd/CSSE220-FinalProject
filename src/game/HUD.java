package game;

import mobile.Player;
import javax.swing.*;
import java.awt.*;

public class HUD extends JPanel{
    private static boolean showPauseOverlay;
    private static JButton unpauseButton = null;
    private static JLabel levelLabel = null;

    public HUD() {
        showPauseOverlay = false; Game.levelNum = 1;
        setLayout(null); levelLabel = new JLabel();
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Cambria", Font.BOLD, 18));
        levelLabel.setVisible(false);
        unpauseButton = new JButton("Unpause");
        unpauseButton.setFocusable(false); unpauseButton.setVisible(false);
        add(levelLabel); add(unpauseButton);
    }
    public void render(JPanel hud){
        int totalGems = Game.levelNum + 1;
        setFont(new Font("Cambria", Font.BOLD, 16)); hud.setLocation(0, 0);
        JTextField lives = new JTextField("Lives: " + Player.getLivesRemaining() + "/3");
        JTextField gemsCollected = new JTextField("Gems Collected: " + GameWorld.getGemsRemaining() + "/" + totalGems);
        JTextField level = new JTextField("Level " + Game.levelNum);
        level.setLocation(1000, 0);
        lives.setLocation(0, 0); gemsCollected.setLocation(0, 50);
        hud.add(lives); hud.add(gemsCollected); hud.add(level);
    }

    //DONE: complete method renderPauseMenu()
    public void renderPauseMenu(){
        showPauseOverlay = true; levelLabel.setText("Level: " + Game.levelNum);
        Dimension size = getSize();
        int centerX = size.width / 2; int centerY = size.height / 2;
        int labelW = 200; int labelH = 30;
        levelLabel.setBounds(centerX - labelW / 2, centerY - 90, labelW, labelH);
        int buttonW = 140; int buttonH = 35;
        unpauseButton.setBounds(centerX - buttonW / 2, centerY - 30, buttonW, buttonH);
        levelLabel.setVisible(true); unpauseButton.setVisible(true);
        revalidate(); repaint();
    }
    public void hidePauseMenu() {
        showPauseOverlay = false;
        levelLabel.setVisible(false);
        unpauseButton.setVisible(false);
        repaint();
    }

}
