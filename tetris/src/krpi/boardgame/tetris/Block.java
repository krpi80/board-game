package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Block {
    
    private final List<Tile> tiles;
    
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
        BlockInfo bi = getBlockInfo();
        return new Block(rotateTiles(tiles, bi.centerX, bi.centerY));
    }

    public Block(List<Tile> tiles) {
        if (tiles == null || tiles.isEmpty()) {
            throw new IllegalArgumentException("Need tiles!");
        }
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
        BlockInfo bi = getBlockInfo();
        return bi.maxX - bi.minX + 1;
    }

    int getHeight() {
        BlockInfo bi = getBlockInfo();
        return bi.maxY - bi.minY + 1;
    }
    
    private BlockInfo getBlockInfo() {
        return BlockInfo.getBlockInfo(tiles);
    }
    
    private static final class BlockInfo {
        
        private final int minX;
        private final int maxX;
        private final int minY;
        private final int maxY;
        private final float centerX;
        private final float centerY;

        public BlockInfo(int minX, int maxX, int minY, int maxY, float centerX, float centerY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.centerX = centerX;
            this.centerY = centerY;
        }
        
        public static BlockInfo getBlockInfo(List<Tile> tiles) {
            assert tiles != null && ! tiles.isEmpty();
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
            float centerX = minX + (maxX - minX) / 2f;
            float centerY = minY + (maxY - minY) / 2f;
            
            if (hasFraction(centerX + centerY)) {
                if (hasFraction(centerX)) {
                    centerX += 0.5f;
                } else {
                    centerX -= 0.5f;
                }
            }

            return new BlockInfo(minX, maxX, minY, maxY, centerX, centerY);
        }
        
    }    
    
    private static boolean hasFraction(float f) {
        return Math.abs(f % 1) > 0.1;
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
