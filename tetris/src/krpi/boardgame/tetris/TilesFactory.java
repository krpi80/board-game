package krpi.boardgame.tetris;

import java.util.ArrayList;
import java.util.List;

public class TilesFactory {

    private final String[][] defs;
    private int current;
    
    public TilesFactory(String[][] defs) {
        this.defs = defs.clone();
    }
    
    public List<Tile> create() {
        String[] rows = nextDefs();
        return createTiles(rows);
    }
    
    private String[] nextDefs() {
        return defs[getCurrentAndAdvance()];
    }
    
    private int getCurrentAndAdvance() {
        int result = current;
        current++;
        current %= defs.length;
        return result;
    }
    
    private List<Tile> createTiles(String[] rows) {
        List<Tile> tiles = new ArrayList<>();
        int y = 0;
        for (String row : rows) {
            int x = 0;
            for (char c : row.toCharArray()) {
                if (! Character.isWhitespace(c)) {
                    tiles.add(new Tile(x, y));
                }
                x++;
            }
            y++;
        }
        return tiles;
    }

}
