package krpi.boardgame.tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Block {
    
    private final int x;
    private final int y;
    private final int orientation;
    private final List<Tile> tiles;
    
    public Block(int x, int y) {
        this(x, y, 0);
    }
    
    public Block move(int x, int y) {
        return transform(x, y, 0);
    }
    
    public Block rotate(int rotation) {
        return transform(0, 0, rotation);
    }

    private Block transform(int x, int y, int rotation) {
        return new Block(this.x + x, this.y + y, calculateOrientation(rotation));
    }

    protected Block(int x, int y, int orientations) {
        this.x = x;
        this.y = y;
        this.orientation = orientations;
        this.tiles = Collections.unmodifiableList(createTiles());
    }
    
    private int calculateOrientation(int rotationAmount) {
        return positiveModulo(orientation + rotationAmount, 4);
    }
    
    private static int positiveModulo(int a, int m) {
        int signedModulo = a % m;
        return signedModulo < 0
                ? signedModulo + m 
                : signedModulo;
    }

    private List<Tile> createTiles() {
        switch(orientation) {
            case 1: return createTiles90();
            case 2: return createTiles180();
            case 3: return createTiles270();
            default: return createTiles0();
        }
    }
    
    private List<Tile> createTiles0() {
        return Arrays.asList(
                new Tile(x, y),
                new Tile(x, y+1),
                new Tile(x, y+2),
                new Tile(x, y+3),
                new Tile(x+1, y+3)
                );
    }

    private List<Tile> createTiles90() {
        return Arrays.asList(
                new Tile(x, y+1),
                new Tile(x+1, y+1),
                new Tile(x+2, y+1),
                new Tile(x+3, y+1),
                new Tile(x+3, y)
                );
    }

    private List<Tile> createTiles180() {
        return Arrays.asList(
                new Tile(x, y),
                new Tile(x+1, y),
                new Tile(x+1, y+1),
                new Tile(x+1, y+2),
                new Tile(x+1, y+3)
                );
    }

    private List<Tile> createTiles270() {
        return Arrays.asList(
                new Tile(x, y+1),
                new Tile(x, y),
                new Tile(x+1, y),
                new Tile(x+2, y),
                new Tile(x+3, y)
                );
    }

    int getWidth() {
        return isVertical()
                ? 2
                : 4;
    }

    int getHeight() {
        return isVertical()
                ? 4
                : 2;
    }

    private boolean isVertical() {
        return orientation % 2 == 0;
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
