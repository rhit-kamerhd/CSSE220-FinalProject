package game;

import immobile.ExitTile;
import mobile.Direction;
import mobile.InputHandler;
import mobile.Zombie;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game extends JPanel {
    private final GameWorld world;
    public final JPanel hudPanel = new JPanel();
    public HUD hud;
    private boolean paused;
    public static int levelNum = 1;
    public final JFrame frame = new JFrame("Cave Game");
    public static final InputHandler input = new InputHandler();

    private int time = 0;

    public Game(GameWorld world) throws IOException {
        this.world = world;
        hud = new HUD(this, world);
        setFocusable(true);
    }

    static void main() throws IOException{
        GameWorld world = WorldBuilder.buildFromTemplate(levelNum);
        StartPanel start = new StartPanel(world);
        Game game = new Game(world);
        game.addKeyListener(input);
        HUD.initAssets();
        game.hudPanel.setBounds(900, 0, 90, 910);
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setSize(new Dimension(990, 910));
        game.frame.add(start); game.frame.setVisible(true);
        PausePanel pausePanel = new PausePanel(world, game);
        Timer loop = new Timer(100, _ -> {
            if (world.getPlayer().getLivesRemaining() < 1) world.setStatus(GameStatus.LOST);
            if (world.getPlayerPosition() == world.getExitTilePosition()) ExitTile.onEnter(world);
            if (world.getStatus() == GameStatus.STARTING){
                game.frame.getContentPane().removeAll(); game.frame.setLayout(new BorderLayout());
                world.getPlayer().setInputHandler(input);
                game.frame.add(game, BorderLayout.CENTER); game.hudPanel.removeAll();
                game.hudPanel.setPreferredSize(new Dimension(90, 0));
                game.hudPanel.setLayout(new BorderLayout()); game.hudPanel.add(game.hud, BorderLayout.CENTER);
                game.frame.add(game.hudPanel, BorderLayout.EAST);
                game.setFocusable(true);
                game.frame.revalidate();
                game.frame.repaint();
                SwingUtilities.invokeLater(game::requestFocusInWindow);
                world.setStatus(GameStatus.RUNNING);
            }
            if (world.getStatus() == GameStatus.RUNNING) {
                if (game.paused){
                    pausePanel.updateStats(world);
                    pausePanel.repaint();
                }
                else{
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
            }
            game.repaint();
        });
        game.startTimer(loop);
    }

    public void startTimer(Timer loop){
        loop.start();
    }

    public void setPaused(boolean bool){
        paused = bool;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        hud.renderWorld(world, g, this);
        hud.updateHUD(world, levelNum);
    }
}


