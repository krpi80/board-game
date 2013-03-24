package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.List;

public class TetrisBoard {

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
    
    private Block block = new Block(5, 5);
    
    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
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
        rotate(1);
    }

    public void rotateRight() {
        rotate(-1);
    }
    
    private void rotate(int r) {
        setBlockIfValid(block.rotate(r));
    }    
    
    // todo: ???
    public void spawnNext() {
        tiles.addAll(block.getTiles());
        setBlockIfValid(new Block(5, 5));
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
}
