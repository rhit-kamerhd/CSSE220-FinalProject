package game;

import mobile.Direction;

/**
 * Represents a row and column coordinate within the game grid.
 *
 * <p>A Position is used throughout the game to track the location of
 * players, zombies, gems, and tiles. Positions are expressed in grid
 * coordinates, not pixel coordinates.
 */
public class Position {

    /** Row index in the grid. */
    public int row;

    /** Column index in the grid. */
    public int col;

    /**
     * Constructs a new Position with the given row and column.
     *
     * @param r the row index
     * @param c the column index
     */
    public Position(int r, int c) {
        row = r;
        col = c;
    }

    /**
     * Returns a new Position translated one tile in the specified direction.
     *
     * <p>The original Position is not modified. A new Position instance
     * is created and returned.
     *
     * @param d the direction in which to move
     * @return a new Position offset by one tile in the given direction
     */
    public Position translate(Direction d) {
        if (d == Direction.UP) return new Position(this.row - 1, this.col);
        if (d == Direction.DOWN) return new Position(this.row + 1, this.col);
        if (d == Direction.LEFT) return new Position(this.row, this.col - 1);
        return new Position(this.row, this.col + 1);
    }

    /**
     * Returns the position as an integer array of length 2.
     *
     * <p>Index 0 contains the row, and index 1 contains the column.
     *
     * @return an array containing {row, col}
     */
    public int[] getPosition() {
        int[] pos = new int[2];
        pos[0] = row;
        pos[1] = col;
        return pos;
    }

    /**
     * Compares this Position to another object for equality.
     *
     * <p>Two Position objects are considered equal if they have the same
     * row and column values.
     *
     * @param o the object to compare against
     * @return true if {@code o} is a Position with identical row and column
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position other)) return false;
        return this.row == other.row && this.col == other.col;
    }
}