package krpi.boardgame.tetris;

public class Rectangle {
    
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    boolean intersects(Rectangle rect) {
        return rect.x < x+width && 
           rect.x+rect.width > x && 
           rect.y < y+height &&
           rect.y+rect.height > y;
    }
    
}