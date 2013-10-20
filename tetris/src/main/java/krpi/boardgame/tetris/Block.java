package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Block {
    
    private final List<Tile> tiles;
    
    public Block move(int x, int y) {
        List<Tile> movedTiles = copyTilesAndMove(x, y);
        return new Block(movedTiles);
    }
    
    private List<Tile> copyTilesAndMove(final int x, final int y) {
        return copyTransformedTiles(new TileTransformation() {
            @Override
            public Tile transform(Tile tile) {
                return tile.move(x, y);
            }
        });
    }
    
    public Block rotateCw() {
        BlockInfo bi = getBlockInfo();
        return new Block(copyTilesAndRotateCw(bi.centerX, bi.centerY));
    }

    public Block rotateCcw() {
        BlockInfo bi = getBlockInfo();
        return new Block(copyTilesAndRotateCcw(bi.centerX, bi.centerY));
    }

    private List<Tile> copyTilesAndRotateCcw(final float a, final float b) {
        return copyTransformedTiles(new TileTransformation() {
            @Override
            public Tile transform(Tile tile) {
                return tile.rotateCcw(a, b);
            }
        });
    }

    private List<Tile> copyTilesAndRotateCw(final float a, final float b) {
        return copyTransformedTiles(new TileTransformation() {
            @Override
            public Tile transform(Tile tile) {
                return tile.rotateCw(a, b);
            }
        });
    }

    private List<Tile> copyTransformedTiles(TileTransformation trans) {
        List<Tile> result = new ArrayList<>(tiles.size());
        for (Tile tile : tiles) {
            result.add(trans.transform(tile));
        }
        return result;
    }

    private interface TileTransformation {
        Tile transform(Tile tile);
    }

    public Block(List<Tile> tiles) {
        this.tiles = Objects.requireNonNull(tiles);
        if (tiles.isEmpty()) {
            throw new IllegalArgumentException("Need tiles!");
        }
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
    
    public boolean intersects(Rectangle rect) {
        for (Tile t : getTiles()) {
            if (t.intersects(rect)) {
                return true;
            }
        }
        return false;
    }

}
