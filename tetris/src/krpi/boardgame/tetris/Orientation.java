package krpi.boardgame.tetris;

public enum Orientation {
    
    NORTH,
    EAST,
    SOUTH,
    WEST;
    
    public Orientation rotate90CW() {
        switch(this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            default: return NORTH;
        }
    }

    public Orientation rotate90CCW() {
        switch(this) {
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            default: return NORTH;
        }
    }
    
    public boolean isVertical() {
        return this == NORTH || this == SOUTH;
    }
}
