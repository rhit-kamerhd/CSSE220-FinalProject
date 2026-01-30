package game;
import mobile.InputHandler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Game {
    private GameWorld world;
    private InputHandler input;
    private HUD hud;
    private boolean paused;
    private int levelNum = 1;

    public void init() {
        while (levelNum < 6) {
            paused = false; GameStatus s = GameStatus.RUNNING;
            WorldBuilder worldBuilder = new WorldBuilder();
            world = worldBuilder.buildFromTemplate(levelNum);

            while (s == GameStatus.RUNNING) {

            }
            if (s == GameStatus.WON) {
                levelNum++; this.onWin();
            }
        }
    }

    public void update(double dt){

    }

    public void render(){

    }

    public void togglePause(GameStatus s){
        JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!paused){

                    }
                    if (paused){

                    }
                }
            }
        });
    }
    public void onWin(){
    }

}
