package game;

import immobile.ExitTile;
import mobile.Direction;
import mobile.InputHandler;
import mobile.Zombie;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Game extends JPanel {

    private final GameWorld world;
    private static HUD hud;
    private boolean paused;
    public static int levelNum = 1;

    private int time = 0;

    public Game(GameWorld world) {
        this.world = world;
        hud = new HUD();
        setFocusable(true);
    }

    static void main() throws IOException {
        GameWorld world = WorldBuilder.buildFromTemplate(levelNum);
        Game game = new Game(world);
        HUD.initAssets();
        JPanel hudPanel = new JPanel();
        hudPanel.setBounds(900, 0, 90, 910);
        hudPanel.add(hud);
        InputHandler input = new InputHandler();
        world.getPlayer().setInputHandler(input);
        JFrame frame = new JFrame("Cave Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setSize(new Dimension(990, 910));
        frame.add(hudPanel, BorderLayout.EAST);
        game.addKeyListener(input);
        game.setFocusable(true);
        frame.setVisible(true);
        SwingUtilities.invokeLater(game::requestFocusInWindow);
        Timer loop = new Timer(100, e -> {
            if (world.getPlayer().getLivesRemaining() < 1) world.setStatus(GameStatus.LOST);
            if (world.getPlayerPosition() == world.getExitTilePosition()) ExitTile.onEnter(world);
            if (world.getStatus() == GameStatus.RUNNING) {
                game.time++;
                if (game.time % 60 == 0) game.requestFocusInWindow();
                if (!game.paused) {
                    world.getPlayer().update(world);
                    if (game.time % 10 == 0) {
                        for (Zombie z : world.getZombies()) {
                            Direction zMove = z.chooseMove(world);
                            z.tryMove(zMove, world);
                        }
                    }
                }
            }
            game.repaint();

        });
        startTimer(loop);
    }

    public static void startTimer(Timer loop){
        loop.start();
    }

    public static void stopTimer(Timer loop){
        loop.stop();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        hud.renderWorld(world, g, this);
        hud.updateHUD(world, levelNum);
        if (paused) {
            hud.renderPauseMenu();
        }
    }
}


