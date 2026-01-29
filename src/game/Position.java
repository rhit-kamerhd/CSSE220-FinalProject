package game;

import mobile.Direction;

public class Position {
    public int row;
    public int col;

    public Position(int r, int c) {
        row = r; col = c;
    }

    //DONE: complete method translate(Direction d)
    public Position translate(Direction d){
        if (d == Direction.UP) return new Position(this.row + 1, this.col);
        if (d == Direction.DOWN) return new Position(this.row - 1, this.col);
        if (d == Direction.LEFT) return new Position(this.row, this.col - 1);
        return new Position(this.row, this.col + 1);
    }

    public int[] getPosition(){
        int[] pos = new int[2]; pos[0] = row; pos[1] = col; return pos;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position other)) return false;
        return this.row == other.row && this.col == other.col;
    }

}
