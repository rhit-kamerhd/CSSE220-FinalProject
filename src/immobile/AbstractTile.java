package immobile;

import java.awt.image.BufferedImage;
/*designed around using this override for rendering-Jacob Lund
 * @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (Tile[] row : tileMap) {
        for (Tile tile : row) {
            tile.render(g);
        }
    }

    for (Collectable c : collectables) {
        c.render(g);
    }

    player.render(g);
}*/
public abstract class AbstractTile implements Tile {

    protected int x, y, size;
    protected BufferedImage sprite;

    public AbstractTile(int x, int y, int size, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.sprite = sprite;
    }

    public int getX() {
    	return x; }

    public int getY() {
    	return y; }

    public int getSize() {
    	return size; }

    public BufferedImage getSprite() {
        return sprite;
    }
}