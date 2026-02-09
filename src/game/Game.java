package game;

import mobile.Direction;
import mobile.InputHandler;
import mobile.Zombie;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Game extends JPanel {

    private final GameWorld world;
    private static HUD hud;
    private static boolean paused;
    public static int levelNum = 1;

    private int time = 0;

    public Game(GameWorld world) {
        this.world = world;
        hud = new HUD();
        setFocusable(true);
    }

    static void main() throws IOException {
        GameWorld world = WorldBuilder.buildFromTemplate(levelNum);
        HUD.initAssets();
        Game game = new Game(world);
        InputHandler input = new InputHandler();
        world.getPlayer().setInputHandler(input);
        JFrame frame = new JFrame("Cave Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setSize(new Dimension(990, 910));
        game.addKeyListener(input);
        game.setFocusable(true);
        frame.setVisible(true);
        SwingUtilities.invokeLater(game::requestFocusInWindow);
        Timer loop = new Timer(100, e -> {
            game.time++;
            if (game.time % 60 == 0) game.requestFocusInWindow();
            if (!paused) {
            	world.getPlayer().update(world);          
                if (game.time % 10 == 0) {
                    for (Zombie z : world.getZombies()) {
                        Direction zMove = z.chooseMove(world);
                        z.tryMove(zMove, world);
                    }
                }
            }
            game.repaint();
        });
        loop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        HUD.renderWorld(world, g, this);
        if (paused) {
            hud.renderPauseMenu();
        }
    }
}


