package mobile;

import java.util.Random;
import game.GameWorld;
import game.Position;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Zombie extends Entity {
    private Position pos;
    private final Random rand = new Random();
    private Image sprite = null;

    public Zombie(Position p) {
        setPosition(p);
        sprite = new ImageIcon(getClass().getResource("/zombie_sprite.png")).getImage();
    }

    public Image getSprite() {
        return sprite;
    }

    public Direction chooseMove(GameWorld world) {
        Direction[] dirs = Direction.values();
        for (int i = 0; i < 10; i++) {
            Direction d = dirs[rand.nextInt(dirs.length)];
            Position next = getPosition().translate(d);
            if (!world.isWall(next)) return d;
        }
        return null;
    }

    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) setPosition(next);
    }

    @Override
    public void update(GameWorld world) {
        Direction d = chooseMove(world);
        tryMove(d, world);
    }
}


