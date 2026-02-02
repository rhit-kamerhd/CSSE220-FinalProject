package mobile;

import game.GameWorld;
import game.HUD;
import game.Position;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    private static int lives = 3;
    private int score = 0;
    private InputHandler input;
    public final BufferedImage sprite;

    public Player(Position p, InputHandler input) throws IOException {
        setPosition(p); this.input = input;
        sprite = HUD.load("/player_sprite.png");
        }

    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) {
            setPosition(next);
        }
    }

    public void loseLife() {
        if (lives > 0) lives--;
    }

    public static int getLivesRemaining() {
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

