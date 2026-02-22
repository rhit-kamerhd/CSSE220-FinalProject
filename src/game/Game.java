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
    public final JPanel hudPanel = new JPanel();
    public HUD hud;
    private boolean paused;
    public static int levelNum = 1;
    public static final JFrame frame = new JFrame("Cave Game");
    public static final InputHandler input = new InputHandler();

    private int time = 0;

    public Game(GameWorld world) throws IOException {
        this.world = world;
        hud = new HUD(this, world);
        setFocusable(true);
    }

    static void main() throws IOException{
        run();
    }

    private static void run() throws IOException {
        WorldBuilder level = new WorldBuilder();
        GameWorld world = level.buildFromTemplate(levelNum);
        Game game = new Game(world);
        if (levelNum == 2){
            world.setStatus(GameStatus.STARTING);
        }
        game.addKeyListener(input);
        HUD.initAssets();
        game.hudPanel.setBounds(900, 0, 90, 910);
        Game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game.frame.setSize(new Dimension(990, 910));
        if (levelNum == 1) {
            StartPanel start = new StartPanel(world);
            Game.frame.add(start);
        }
        else Game.frame.add(game);
        Game.frame.setVisible(true);
        PausePanel pausePanel = new PausePanel(world, game);
        Timer loop = new Timer(100, e -> {
            System.out.println(world.getStatus());
            if (world.getPlayer().getLivesRemaining() < 1) world.setStatus(GameStatus.LOST);
            if (world.getPlayerPosition() == world.getExitTilePosition()) ExitTile.winOnEnter(world);
            if (world.getStatus() == GameStatus.STARTING){
                Game.frame.getContentPane().removeAll(); Game.frame.setLayout(new BorderLayout());
                world.getPlayer().setInputHandler(input);
                Game.frame.add(game, BorderLayout.CENTER); game.hudPanel.removeAll();
                game.hudPanel.setPreferredSize(new Dimension(90, 0));
                game.hudPanel.setLayout(new BorderLayout()); game.hudPanel.add(game.hud, BorderLayout.CENTER);
                Game.frame.add(game.hudPanel, BorderLayout.EAST);
                game.setFocusable(true);
                Game.frame.revalidate();
                Game.frame.repaint();
                SwingUtilities.invokeLater(game::requestFocusInWindow);
                world.setStatus(GameStatus.RUNNING);
            }
            if (world.getStatus() == GameStatus.RUNNING) {
                SwingUtilities.invokeLater(game::requestFocusInWindow);
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
            else if (world.getPlayer().getLivesRemaining() == 0){
                world.setStatus(GameStatus.LOST);
                Timer current = (Timer) e.getSource();
                try {
                    GameOverPanel gameOverPanel = new GameOverPanel();
                    gameOverPanel.renderGameOverPanel(game);
                    current.stop();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
            else if (world.getExitTile().winOnEnter(world)){
                if (levelNum < 2) {
                    levelNum++;
                    world.setStatus(GameStatus.ON_START);
                    world.setGemsRemaining(5);
                    Timer current = (Timer) e.getSource();
                    try {
                        run();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    current.stop();
                }
                if (levelNum == 2){
                    world.setStatus(GameStatus.WON);
                    world.setGemsRemaining(5);
                    Timer current = (Timer) e.getSource();
                    current.stop();
                    try {
                        WinPanel winPanel = new WinPanel();
                        winPanel.renderWinPanel(game);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            if (world.getGemsRemaining() == 0){
                if (world.getPlayerPosition().row == world.getExitTilePosition().row
                        && world.getPlayerPosition().col == world.getExitTilePosition().col){
                    System.out.println(ExitTile.winOnEnter(world));
                }
            }
            game.repaint();
        });
        game.startTimer(loop);
    }

    private void startTimer(Timer loop){
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


