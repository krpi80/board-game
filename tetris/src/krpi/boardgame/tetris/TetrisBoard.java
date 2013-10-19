package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TetrisBoard {
    
    private static final int SPAWN_AREA_WIDTH = 5;
    
    private static final int SPAWN_AREA_HEIGHT = 5;

    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    public interface Observer {
        void onChange();
    }
    
    private static final Observer NULL_OBSERVER = new Observer() {
        @Override
        public void onChange() {
            // do nothing
        }
    };
    
    private final List<Tile> tiles = new ArrayList<>();

    private final int width;
    
    private final int height;

    private final TilesFactory tf;

    private Observer observer = NULL_OBSERVER;
    
    private Block block;
    
    private int points;

    private boolean propagate = true;
    
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getSpawnArea() {
        return new Rectangle((width-SPAWN_AREA_WIDTH) / 2 ,
                0*(height-SPAWN_AREA_HEIGHT) / 2 ,
                SPAWN_AREA_WIDTH,
                SPAWN_AREA_HEIGHT);
    }

    public int getPoints() {
        return points;
    }
    
    public void setObserver(Observer observer) {
        this.observer = observer;
    }
    
    public void removeObserver() {
        this.observer = NULL_OBSERVER;
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

    public boolean isPropagate() {
        return propagate;
    }

    public void setPropagate(boolean propagate) {
        this.propagate = propagate;
    }

    public void tick() {
        setPropagate(false);
        boolean moved = moveDown();
        if (!moved) {

            boolean spawned = spawnNext();
            if (!spawned) {
                finish();
            }
        }
        setPropagate(true);
        propagateChange();
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
            setBlockAndPropagate(spawnBlock());
            increasePointsAndPropagate(points);
            return true;
        }
        return false;
    }  
    
    private boolean isSpawnAreaClean() {
        return ! block.intersects(getSpawnArea());
    }
    
    private Block spawnBlock() {
        return positionInSpawnAreaCenter(createBlock());
    }

    private Block positionInSpawnAreaCenter(Block b) {
        return b.move(getSpawnArea().getX(), getSpawnArea().getY())
                .move((getSpawnArea().getWidth() - b.getWidth()) / 2,
                        (getSpawnArea().getHeight() - b.getHeight()) / 2);
    }

    private Block createBlock() {
        return new Block(tf.create());
    }
    
    private boolean setBlockIfValid(Block b) {
        if (isValid(b)) {
            setBlockAndPropagate(b);
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
                /*&& t.getY() >= 0*/
                && t.getY() < height;
    }
    
    private void setBlockAndPropagate(Block b) {
        block = b;
        propagateChange();
    }

    private void increasePointsAndPropagate(int points) {
        this.points += points;
        propagateChange();
    }

    private void propagateChange() {
        if (isPropagate()) {
            observer.onChange();
        }
    }

    private void finish() {
        finished = true;
    }

}
