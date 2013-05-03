package krpi.boardgame.tetris;

import java.util.Objects;

public class Tile {
    
    private final int x;
    private final int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    
    public Tile move(int dx, int dy) {
        return new Tile(x + dx, y + dy);
    }
    
    public Tile rotateCcw(float a, float b) {
        return new Tile((int)(a-b)+y, (int)(a+b)-x);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tile{" + "x=" + x + ", y=" + y + '}';
    }

    public boolean intersects(Rectangle rect) {
        return new Rectangle(x, y, 1, 1).intersects(rect);
    }

}
