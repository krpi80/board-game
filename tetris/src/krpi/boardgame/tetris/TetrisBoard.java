package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.List;

public class TetrisBoard {
    
    private static final int SPAWN_AREA_WIDTH = 4;
    
    private static final int SPAWN_AREA_HEIGHT = 4;

    public static abstract class Observer {
        public abstract void onChange();
    }
    
    private static final Observer NULL_OBSERVER = new Observer() {
        @Override
        public void onChange() {
            
        }
    };
    
    private final List<Tile> tiles = new ArrayList<>();

    private final int width;
    
    private final int height;

    private Observer observer = NULL_OBSERVER;
    
    private Block block;
    
    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        block = createBlock();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setObserver(Observer observer) {
        this.observer = observer;
    }
    
    public void removeObserver() {
        this.observer = NULL_OBSERVER;
    }

    public List<Tile> getTiles() {
        List<Tile> allTiles = new ArrayList<>(this.tiles);
        allTiles.addAll(block.getTiles());
        return allTiles;
    }

    public void moveLeft() {
        move(-1, 0);
    }
    
    public void moveRight() {
        move(1, 0);
    }

    public void moveUp() {
        move(0, -1);
    }

    public void moveDown() {
        move(0, 1);
    }
    
    private void move(int x, int y) {
        setBlockIfValid(block.move(x, y));
    }
    
    public void rotateLeft() {
        setBlockIfValid(block.rotateLeft());
    }

    public void rotateRight() {
        setBlockIfValid(block.rotateRight());
    }
    
    public void spawnNext() {
        if ( ! block.intersects(getSpawnArea())) {
            tiles.addAll(block.getTiles());
            setBlockAndPropagate(createBlock());
        }
    }  
    
    private Block createBlock() {
        Block b = new Block(getSpawnArea().getX(), getSpawnArea().getX());
        return b.move((getSpawnArea().getWidth()-b.getWidth()) / 2, 
                (getSpawnArea().getHeight()-b.getHeight()) / 2);
    }
    
    private void setBlockIfValid(Block b) {
        if (isValid(b)) {
            setBlockAndPropagate(b);
        }
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
    
    private void setBlockAndPropagate(Block b) {
        block = b;
        propagateChange();
    }
    
    private void propagateChange() {
        observer.onChange();
    }
    
    public Rectangle getSpawnArea() {
        return new Rectangle((width-SPAWN_AREA_WIDTH) / 2 , 
                (height-SPAWN_AREA_HEIGHT) / 2, 
                SPAWN_AREA_WIDTH, 
                SPAWN_AREA_HEIGHT);
    }
}
