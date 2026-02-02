package game;
import mobile.Direction;
import mobile.InputHandler;
import mobile.Player;
import mobile.Zombie;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;

public class Game extends JPanel{
    private final GameWorld world;
    private static HUD hud;
    private static boolean paused;
    public static int levelNum = 1;
    private static JPanel panel;
    private InputHandler input;
    private KeyEvent event;
    private int time = 0;

    public Game(GameWorld world) {
        this.world = world;
        hud = new HUD();
        setFocusable(true);
    }
    static void main(String[] args) throws IOException {
        GameWorld world = WorldBuilder.buildFromTemplate(levelNum);
        Game game = new Game(world); InputHandler input = new InputHandler();
        JFrame frame = new JFrame("Cave Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setSize(new Dimension(900, 1050));
        frame.setVisible(true); game.setFocusable(true);
        game.requestFocusInWindow(); game.addKeyListener(input);
        Timer loop = new Timer(20, e -> {
            game.time++;
            if (!paused) {
                Direction d = input.getMoveDirection();
                if (d != null) {
                    world.getPlayer().tryMove(d, world);
                }
                if (game.time % 5 == 0){
                    for (Zombie z : world.zombies){
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
        try {
            Player player = world.getPlayer();
            HUD.renderWorld(world, g, this);
            HUD.renderPlayer(player, world, 25, g, this);
            for (Zombie z : world.zombies) {
                HUD.renderZombies(z, world, 25, g, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (paused) {
            hud.renderPauseMenu();
        }
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
}
