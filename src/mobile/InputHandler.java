package mobile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Handles keyboard input states for player controls.
 * Tracks movement keys and action keys like push and pause.
 */
public class InputHandler implements KeyListener {

    private boolean up, down, left, right, push, pause;

    /**
     * Determines the player's movement direction based on keys held.
     * @return Direction of movement or null if none pressed
     */
    public Direction getMoveDirection() {
        if (up) return Direction.UP;
        if (down) return Direction.DOWN;
        if (left) return Direction.LEFT;
        if (right) return Direction.RIGHT;
        return null;
    }

    /**
     * Allows push to occur only once per key press.
     * @return true if push action should trigger
     */
    public boolean consumePush() {
        if (!push) return false;
        push = false;
        return true;
    }

    /**
     * @return true if pause key is pressed
     */
    public boolean isPausePressed() {
        return pause;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP) up = true;
        if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) down = true;
        if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) left = true;
        if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) right = true;
        if (k == KeyEvent.VK_SPACE) push = true;
        if (k == KeyEvent.VK_P) pause = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP) up = false;
        if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) down = false;
        if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) left = false;
        if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) right = false;
        if (k == KeyEvent.VK_P) pause = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

