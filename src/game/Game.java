package game;
import mobile.Direction;
import mobile.InputHandler;
import mobile.Player;
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

    public Game(GameWorld world) {
        this.world = world;
        hud = new HUD();
        setFocusable(true);
    }
    static void main(String[] args) throws IOException {
        GameWorld world = WorldBuilder.buildFromTemplate(levelNum);
        Game game = new Game(world);
        InputHandler input = new InputHandler();

        JFrame frame = new JFrame("Cave Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setSize(new Dimension(900, 1050));
        frame.setVisible(true);

        game.setFocusable(true);
        game.requestFocusInWindow();
        game.addKeyListener(input);

        Timer loop = new Timer(20, e -> {
            if (!paused) {
                Direction d = input.getMoveDirection();
                if (d != null) {
                    world.getPlayer().tryMove(d, world);
                }
            }
            game.repaint();
        });
        loop.start();
    }

//    static void main(String[] args) throws IOException {
//        GameWorld world = WorldBuilder.buildFromTemplate(levelNum);
//        Game game = new Game(world);
//        game.input = new InputHandler();
//        JFrame frame = new JFrame("Cave Game");
//        frame.setSize(new Dimension(900, 1050));
//        frame.setBackground(Color.BLACK);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(true); frame.add(game);
//        frame.setVisible(true); frame.addKeyListener(game.input);
//        Timer gameLoop = new Timer(16, e -> {
//            game.addKeyListener(new KeyAdapter(){
//                @Override
//                public void keyPressed(KeyEvent e){
//                    if (!paused) {
//                        game.input.keyPressed(game.event);
//                        Direction d = game.input.getMoveDirection();
//                        if (d != Direction.NONE) {
//                            System.out.println("moving" + d);
//                            world.getPlayer().tryMove(d, world);
//                        }
//                    }
//                }
//            });
//            game.repaint();
//        });
//        gameLoop.start();
//    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Player player = world.getPlayer();
            HUD.renderWorld(world, g, this);
            HUD.renderPlayer(player, world, 25, g, this);
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
