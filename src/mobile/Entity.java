package mobile;

import game.GameWorld;
import game.Position;

public abstract class Entity {

    private Position pos;

    public Position getPosition() {
        return pos;
    }

    public void setPosition(Position p) {
        pos = p;
    }

    public abstract void update(GameWorld world);

    public void tryMove(Direction d, GameWorld world) {
        if (d == null) return;
        Position next = getPosition().translate(d);
        if (!world.isWall(next)) setPosition(next);
    }

}
