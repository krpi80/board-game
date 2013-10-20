package krpi.boardgame.tetris;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class TileTest {

    @Test
    public void testMove() {
        Tile t = new Tile(7, 10).move(3, -3);
        assertEquals(10, t.getX());
        assertEquals(7, t.getY());
    }

    @Test
    public void testRotateCcw() {
        Tile t1 = new Tile(7, 10);
        Tile t2 = new Tile(9, 2);
        Tile t = t1.rotateCcw(4, 5);
        assertEquals(t2, t);
    }

    @Test
    public void testRotateCw() {
        Tile t1 = new Tile(9, 2);
        Tile t2 = new Tile(7, 10);
        Tile t = t1.rotateCw(4, 5);
        assertEquals(t2, t);
    }

    @Test
    public void testIntersects() {
        Tile t = new Tile(1, 1);
        assertTrue(t.intersects(new Rectangle(0, 0, 2, 2)));
    }

    @Test
    public void testHashCodeMatch() {
        Tile t1 = new Tile(3, 5);
        Tile t2 = new Tile(3, 5);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void testHashCodeDifferent() {
        Tile t1 = new Tile(3, 5);
        Tile t2 = new Tile(5, 3);
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void testMatchingEqual() {
        Tile t1 = new Tile(3, 5);
        Tile t2 = new Tile(3, 5);
        assertEquals(t1, t2);
    }

    @Test
    public void testDifferentNotEqual() {
        Tile t1 = new Tile(3, 5);
        Tile t2 = new Tile(5, 3);
        assertNotEquals(t1, t2);
    }

    @Test
    public void testToString() {
        Tile t = new Tile(3, 5);
        assertTrue(t.toString().contains(String.valueOf(t.getX())));
        assertTrue(t.toString().contains(String.valueOf(t.getY())));
        assertTrue(t.toString().contains(Tile.class.getSimpleName()));
    }
}
