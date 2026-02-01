package game;
import mobile.InputHandler;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.*;

public class Game extends JPanel{
    private static HUD hud;
    private static boolean paused;
    public static int levelNum = 1;

    public static void update(double dt){}

    void main(String[] args) throws IOException {
        Graphics g = this.getGraphics();
        GameWorld world = WorldBuilder.buildFromTemplate(levelNum);
        InputHandler input = new InputHandler(); hud = new HUD();
        HUD.renderWorld(world, g, this);
        JFrame frame = new JFrame("Cave Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1080, 1080); frame.add(hud);
        frame.addKeyListener(input);
        frame.setVisible(true); paused = false; input = new InputHandler();
        Timer gameLoop = new Timer(16, e -> {
            update(0.016);
            hud.repaint();
        });
            gameLoop.start();
        }

    public void togglePause(GameStatus s){
        JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!paused){
                        togglePause(s);
                    }
                }
            }
        });
    }

    public static boolean isPaused(){
        return paused;
    }
}
