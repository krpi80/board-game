package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Block {
    
    private final int x;
    private final int y;
    private final float a;
    private final float b;
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

    protected Block(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.a = x + 0.5f;
        this.b = y + 1.5f;
        this.orientation = orientation;
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
    
    private List<Tile> createTilesEast() {
        return rotateTiles(createTilesSouth(), a, b);
    }

    private List<Tile> createTilesSouth() {
        return rotateTiles(createTilesWest(), a, b);
    }

    private List<Tile> createTilesWest() {
        return rotateTiles(createTilesNorth(), a, b);
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
    
    private static List<Tile> rotateTiles(List<Tile> tiles, float a, float b) {
        List<Tile> result = new ArrayList<>(tiles.size());
        for (Tile tile : tiles) {
            result.add(rotateTile(tile,a , b));
        }
        return result;
    }
    
    private static Tile rotateTile(Tile tile, float a, float b) {
        return new Tile((int)(a-b)+tile.getY(), (int)(a+b)-tile.getX());
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
