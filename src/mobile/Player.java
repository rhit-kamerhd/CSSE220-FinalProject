package mobile;

import game.GameWorld;
import game.Position;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Player extends Entity {

    private int lives = 3;
    private int score = 0;
    private InputHandler input;
    private Image sprite;

    public Player(Position p, InputHandler input) {
        setPosition(p);
        this.input = input;
        sprite = new ImageIcon(getClass().getResource("/sprites/player_sprite.png")).getImage();
    }

    public Image getSprite() {
        return sprite;
    }

    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) setPosition(next);
    }

    public void loseLife() {
        if (lives > 0) lives--;
    }

    public int getLivesRemaining() {
        return lives;
    }

    public void pushAttempt(Direction d, GameWorld world) {
        if (d == null) return;

        Position adjacent = getPosition().translate(d);
        Zombie z = world.getZombieAt(adjacent);
        if (z == null) return;

        Position launchPos = adjacent;

        for (int i = 0; i < 4; i++) {
            Position next = launchPos.translate(d);
            if (world.isWall(next)) break;
            launchPos = next;
        }

        z.setPosition(launchPos);
    }

    @Override
    public void update(GameWorld world) {
        Direction d = input.getMoveDirection();
        tryMove(d, world);
        if (input.isPushPressed()) pushAttempt(d, world);
    }
}

