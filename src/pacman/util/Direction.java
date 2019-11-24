package pacman.util;

/**
 * Direction represents directions in a 2D plane. Each direction stores a relative position
 */

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1);

    private final int x;
    private final int y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Gets the offset associated with this direction.*/
    public Position offset() {
        return new Position(this.x, this.y);
    }

    /**Gets the opposite direction to this direction.*/
    public Direction opposite() {
        switch (this) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
           // If no opposite direction, return this direction
            default:
                return this;
        }
    }
}