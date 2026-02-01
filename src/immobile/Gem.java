package immobile;
import java.awt.Color;
import java.awt.Graphics;

public class Gem implements Collectible {

    private int x, y, size;
    private boolean collected;

    public Gem(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.collected = false;
    }

    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }

    @Override
    public int getSize() { return size; }

    @Override
    public boolean isCollected() {
        return collected;
    }

    @Override
    public void onCollect() {
        collected = true;
        // trigger sound, score, animation, etc.
    }

    @Override
    public void render(Graphics g) {
        if (!collected) {
            g.setColor(Color.CYAN);
            g.fillOval(x + 4, y + 4, size - 8, size - 8);
        }
    }
}
