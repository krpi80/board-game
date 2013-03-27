package krpi.boardgame.tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Block {
    
    private final int x;
    private final int y;
    private final Orientation orientation;
    private final List<Tile> tiles;
    
    public Block(int x, int y) {
        this(x, y, Orientation.NORTH);
    }
    
    public Block move(int x, int y) {
        return new Block(this.x + x, this.y + y, orientation);
    }
    
    public Block rotateLeft() {
        return new Block(x, y, orientation.rotate90CCW());
    }

    public Block rotateRight() {
        return new Block(x, y, orientation.rotate90CW());
    }

    protected Block(int x, int y, Orientation orientations) {
        this.x = x;
        this.y = y;
        this.orientation = orientations;
        this.tiles = Collections.unmodifiableList(createTiles());
    }
    
    private List<Tile> createTiles() {
        switch(orientation) {
            case EAST: return createTilesEast();
            case SOUTH: return createTilesSouth();
            case WEST: return createTilesWest();
            default: return createTilesNorth();
        }
    }
    
    private List<Tile> createTilesNorth() {
        return Arrays.asList(
                new Tile(x, y),
                new Tile(x, y+1),
                new Tile(x, y+2),
                new Tile(x, y+3),
                new Tile(x+1, y+3)
                );
    }

    private List<Tile> createTilesEast() {
        return Arrays.asList(
                new Tile(x, y+1),
                new Tile(x+1, y+1),
                new Tile(x+2, y+1),
                new Tile(x+3, y+1),
                new Tile(x+3, y)
                );
    }

    private List<Tile> createTilesSouth() {
        return Arrays.asList(
                new Tile(x, y),
                new Tile(x+1, y),
                new Tile(x+1, y+1),
                new Tile(x+1, y+2),
                new Tile(x+1, y+3)
                );
    }

    private List<Tile> createTilesWest() {
        return Arrays.asList(
                new Tile(x, y+1),
                new Tile(x, y),
                new Tile(x+1, y),
                new Tile(x+2, y),
                new Tile(x+3, y)
                );
    }

    int getWidth() {
        return orientation.isVertical()
                ? 2
                : 4;
    }

    int getHeight() {
        return orientation.isVertical()
                ? 4
                : 2;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
    
    boolean intersects(Rectangle rect) {
        for (Tile t : getTiles()) {
            if (t.intersects(rect)) {
                return true;
            }
        }
        return false;
    }

}
