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
}
