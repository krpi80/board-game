package krpi.boardgame.tetris;

import java.util.Arrays;
import java.util.List;

public class Block {
    
    private int x = 5;
    private int y = 5;
    private int r = 0;
    
    protected Block(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    public Block(int x, int y) {
        this(x, y, 0);
    }
    
    public Block move(int x, int y) {
        return transform(x, y, 0);
    }
    
    public Block rotate(int r) {
        return transform(0, 0, r);
    }

    private Block transform(int x, int y, int r) {
        return new Block(this.x + x, this.y + y, calculateRotation(r));
    }

    private int calculateRotation(int rotationAmount) {
        return positiveModulo(this.r + rotationAmount, 4);
    }
    
    private static int positiveModulo(int a, int m) {
        int signedModulo = a % m;
        return signedModulo < 0
                ? signedModulo + m 
                : signedModulo;
    }

    public List<Tile> getTiles() {
        switch(r) {
            case 1: return getTiles90();
            case 2: return getTiles180();
            case 3: return getTiles270();
            default: return getTiles0();
        }
    }
    
    private List<Tile> getTiles0() {
        return Arrays.asList(
                new Tile(x, y),
                new Tile(x, y+1),
                new Tile(x, y+2),
                new Tile(x, y+3),
                new Tile(x+1, y+3)
                );
    }

    private List<Tile> getTiles90() {
        return Arrays.asList(
                new Tile(x, y+1),
                new Tile(x+1, y+1),
                new Tile(x+2, y+1),
                new Tile(x+3, y+1),
                new Tile(x+3, y)
                );
    }

    private List<Tile> getTiles180() {
        return Arrays.asList(
                new Tile(x, y),
                new Tile(x+1, y),
                new Tile(x+1, y+1),
                new Tile(x+1, y+2),
                new Tile(x+1, y+3)
                );
    }

    private List<Tile> getTiles270() {
        return Arrays.asList(
                new Tile(x, y+1),
                new Tile(x, y),
                new Tile(x+1, y),
                new Tile(x+2, y),
                new Tile(x+3, y)
                );
    }

}
