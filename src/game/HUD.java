package game;

import mobile.Player;
import javax.swing.*;
import java.awt.*;

public class HUD extends JPanel{
    public void render(JPanel hud, Player p){
        int totalGems = Game.levelNum + 1;
        setFont(new Font("Cambria", Font.BOLD, 16)); hud.setLocation(0, 0);
        JTextField lives = new JTextField("Lives: " + Player.getLivesRemaining() + "/3");
        JTextField gemsCollected = new JTextField("Gems Collected: " + GameWorld.getGemsRemaining() + "/" + totalGems);
        JTextField level = new JTextField("Level " + Game.levelNum);
        level.setLocation(1030, 0);
        lives.setLocation(0, 0); gemsCollected.setLocation(0, 50);
        hud.add(lives); hud.add(gemsCollected); hud.add(level);
    }

    //TODO: complete method renderPauseMenu()
    public void renderPauseMenu(){

    }

}
