package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Block {
    
    private final List<Tile> tiles;
    
    public Block() {
        tiles = Arrays.asList(
                new Tile(0, 0),
                new Tile(0, 1),
                new Tile(0, 2),
                new Tile(0, 3),
                new Tile(1, 3)
                );
    }
    
    public Block move(int x, int y) {
        List<Tile> result = new ArrayList<>(tiles.size());
        for (Tile tile : tiles) {
            result.add(new Tile(tile.getX() + x, tile.getY() + y));
        }
        return new Block(result);
    }
    
    public Block rotateRight() {
        return rotateLeft().rotateLeft().rotateLeft();
    }

    public Block rotateLeft() {
        if (tiles.isEmpty()) {
            return new Block(tiles);
        }
        int minX = tiles.get(0).getX();
        int maxX = minX;
        int minY = tiles.get(0).getY();
        int maxY = minY;
        for (Tile tile : tiles) {
            minX = Math.min(minX, tile.getX());
            maxX = Math.max(maxX, tile.getX());
            minY = Math.min(minY, tile.getY());
            maxY = Math.max(maxY, tile.getY());
        }
        float a = minX + (maxX - minX) / 2f;
        float b = minY + (maxY - minY) / 2f;
        
        return new Block(rotateTiles(tiles, a, b));
    }

    protected Block(List<Tile> tiles) {
        this.tiles = tiles;
    }
    
    private static List<Tile> rotateTiles(List<Tile> tiles, float a, float b) {
        List<Tile> result = new ArrayList<>(tiles.size());
        for (Tile tile : tiles) {
            result.add(rotateTile(tile, a, b));
        }
        return result;
    }
    
    private static Tile rotateTile(Tile tile, float a, float b) {
        return new Tile((int)(a-b)+tile.getY(), (int)(a+b)-tile.getX());
    }

    int getWidth() {
        if (tiles.isEmpty()) {
            return 0;
        }
        int minX = tiles.get(0).getX();
        int maxX = minX;
        for (Tile tile : tiles) {
            minX = Math.min(minX, tile.getX());
            maxX = Math.max(maxX, tile.getX());
        }
        return maxX - minX + 1;
    }

    int getHeight() {
        if (tiles.isEmpty()) {
            return 0;
        }
        int minY = tiles.get(0).getY();
        int maxY = minY;
        for (Tile tile : tiles) {
            minY = Math.min(minY, tile.getY());
            maxY = Math.max(maxY, tile.getY());
        }
        return maxY - minY + 1;
    }

    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
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
