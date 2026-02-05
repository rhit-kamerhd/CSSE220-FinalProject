package mobile;

/**
 * Represents movement directions in the grid.
 * Each direction stores x and y translation offsets.
 */
public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0);

    /** Change in x position */
    public final int dx;

    /** Change in y position */
    public final int dy;

    /**
     * Constructs a Direction with movement offsets.
     * @param dx change in x
     * @param dy change in y
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}


