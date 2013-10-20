package krpi.boardgame.tetris;

import java.util.*;

public class TetrisBoard {
    
    private static final int SPAWN_AREA_WIDTH = 5;
    
    private static final int SPAWN_AREA_HEIGHT = 5;

    private boolean finished;

    private final List<Tile> tiles = new LinkedList<>();

    private final int width;

    private final int height;

    private final TilesFactory tf;

    private Block block;

    private int points;

    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        tf = new TilesFactory(
                new String[][]{
                    new String[]{
                        "#####"
                    },
                    new String[]{
                        "#",
                        "####"
                    },
                    new String[]{
                        " #",
                        "###"
                    },
                    new String[]{
                        " #",
                        "###",
                        " #"
                    },
                    new String[]{
                        "##",
                        " ##",
                        "  ##"
                    },
                });
        block = spawnBlock();
    }


    public boolean isFinished() {
        return finished;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getSpawnArea() {
        return new Rectangle((width-SPAWN_AREA_WIDTH) / 2 ,
                0,
                SPAWN_AREA_WIDTH,
                SPAWN_AREA_HEIGHT);
    }

    public int getPoints() {
        return points;
    }
    
    public Collection<Tile> getTiles() {
        Set<Tile> allTiles = new HashSet<>();
        allTiles.addAll(tiles);
        allTiles.addAll(block.getTiles());
        return allTiles;
    }

    public boolean moveLeft() {
        return move(-1, 0);
    }
    
    public boolean moveRight() {
        return move(1, 0);
    }

    public boolean moveUp() {
        return move(0, -1);
    }

    public boolean moveDown() {
        return move(0, 1);
    }

    public void tick() {
        boolean moved = moveDown();
        if (!moved) {

            boolean spawned = spawnNext();
            if (!spawned) {
                finish();
            }
        }
    }

    private int clearRows() {
        int points = 0;
        NextRow:
        for(int y = height - 1; y >=0; --y) {
            List<Tile> row = new ArrayList<>(width);
            for(int x = 0; x < width; x++) {

                Tile tile = findTile(x, y);
                if(tile == null) {
                    continue NextRow;
                }
                row.add(tile);

            }

            tiles.removeAll(row);
            points += row.size();

            List<Tile> above = new ArrayList<>(width*height);
            for (Tile t : tiles) {
                if (t.getY() < y) {
                    above.add(t);
                }
            }

            tiles.removeAll(above);

            List<Tile> moved = new ArrayList<>(above.size());
            for (Tile t : above) {
                moved.add(t.move(0, 1));
            }

            tiles.addAll(moved);

        }
        return points;
    }

    private Tile findTile(int x, int y) {
        for (Tile t : tiles) {
            if (t.getX() == x && t.getY() == y) {
                return t;
            }
        }
        return null;
    }

    private boolean move(int x, int y) {
        return setBlockIfValid(block.move(x, y));
    }
    
    public void rotateCcw() {
        setBlockIfValid(block.rotateCcw());
    }

    public void rotateCw() {
        setBlockIfValid(block.rotateCw());
    }
    
    public boolean spawnNext() {
        if (isSpawnAreaClean()) {
            tiles.addAll(block.getTiles());
            int points = clearRows();
            block = spawnBlock();
            this.points += points;
            return true;
        }
        return false;
    }  
    
    private boolean isSpawnAreaClean() {
        return ! block.intersects(getSpawnArea());
    }
    
    private Block spawnBlock() {
        return positionInSpawnAreaBottomCenter(createBlock());
    }

    private Block positionInSpawnAreaBottomCenter(Block b) {
        return b.move(getSpawnArea().getX(), getSpawnArea().getY())
                .move((getSpawnArea().getWidth() - b.getWidth()) / 2,
                        getSpawnArea().getHeight() - b.getHeight());
    }

    private Block createBlock() {
        return new Block(tf.create());
    }
    
    private boolean setBlockIfValid(Block b) {
        if (isValid(b)) {
            block = b;
            return true;
        }
        return false;
    }
    
    private boolean isValid(Block b) {
        for (Tile t : b.getTiles()) {
            if (!isValid(t)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(Tile t) {
        return withinBoard(t)
                && ! tiles.contains(t)
                ;
    }
    
    private boolean withinBoard(Tile t) {
        return t.getX() >= 0 
                && t.getX() < width
                && t.getY() >= 0
                && t.getY() < height;
    }


    private void finish() {
        finished = true;
    }

}
