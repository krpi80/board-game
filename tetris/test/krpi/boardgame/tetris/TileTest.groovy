package krpi.boardgame.tetris

import org.junit.Test

import static junit.framework.Assert.assertEquals
import static org.testng.Assert.assertNotEquals
import static org.testng.Assert.assertTrue

class TileTest {

    @Test
    void testMove() {
        def t = new Tile(7, 10).move(3, -3);
        assertEquals(10, t.x)
        assertEquals(7, t.y)
    }

    @Test
    void testRotateCcw() {
        def t1 = new Tile(7, 10)
        def t2 = new Tile(9, 2)
        def t = t1.rotateCcw(4, 5)
        assertEquals(t2, t)
    }

    @Test
    void testRotateCw() {
        def t1 = new Tile(9, 2)
        def t2 = new Tile(7, 10)
        def t = t1.rotateCw(4, 5)
        assertEquals(t2, t)
    }

    @Test
    void testIntersects() {
        def t = new Tile(1, 1)
        assertTrue(t.intersects(new Rectangle(0, 0, 2, 2)))
    }

    @Test
    void testHashCodeMatch() {
        def t1 = new Tile(3, 5)
        def t2 = new Tile(3, 5)
        assertEquals(t1.hashCode(), t2.hashCode())
    }

    @Test
    void testHashCodeDifferent() {
        def t1 = new Tile(3, 5)
        def t2 = new Tile(5, 3)
        assertNotEquals(t1.hashCode(), t2.hashCode())
    }

    @Test
    void testMatchingEqual() {
        def t1 = new Tile(3, 5)
        def t2 = new Tile(3, 5)
        assertEquals(t1, t2)
    }

    @Test
    void testDifferentNotEqual() {
        def t1 = new Tile(3, 5)
        def t2 = new Tile(5, 3)
        assertNotEquals(t1, t2)
    }

    @Test
    void testToString() {
        def t = new Tile(3, 5)
        assertTrue(t.toString().contains(t.x.toString()))
        assertTrue(t.toString().contains(t.y.toString()))
        assertTrue(t.toString().contains(Tile.class.getSimpleName()))
    }
}
