package pacman.util;

/**
 * Similar to a Point Class but instead called Position
 */
public class Position {
    private int x;
    private int y;

    /** Creates a position at the given x and y coordinates.
     * @param x new location
     * @param y new location
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the X axis location
     * @return x position
     * */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y axis location
     * @return x position
     * */
    public int getY() {
        return y;
    }

    /** Calculates the Euclidean distance from this point to other given point.
     * @param other point to calc distance too.
     * @return the euclidean distance
     */
    public double distance(Position other) {
        return  Math.sqrt(Math.pow(this.getX() - other.getX(), 2)
                + Math.pow(this.getY() - other.getY(), 2));
    }

    /**
     * Adds two positions together.
     * @param other position to add to this line
     * @return this position + other
     */
    public Position add(Position other) {
        return new Position(this.x + other.x, this.y + other.y);
    }

    /**
     * Multiplies by a factor on the x and y axis.
     * @param factor to multiple the axis by.
     * @return a new position with the x axis scaled by factor and y axis
     * scaled by factor.
     */
    public Position multiply(int factor) {
        return new Position(this.x * factor, this.y * factor);
    }

    /**
     * Checks if two positions are equal.
     * @param other object to compare against.
     * @return true if x == this.x and also y == this.y, false otherwise.
     */
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof Position)) {
            return false;
        }
        Position otherPosition = (Position) other;
        return this.x == otherPosition.getX() && this.y == otherPosition.getY();
    }

    /**
     * Calculates the hash of the position. For two objects that are equal the
     * hash should also be equal. For two objects that are not equal the hash
     * does not have to be different.
     * @return hash of this position.
     */
    @Override
    public int hashCode() {
        return (this.x * 3) * (this.y * 7);
    }

    /**
     * Returns a comma-seperated string representation of a Position.
     * Format: "x,y"
     * Example: "3,4"
     * @return "x,y"
     */
    @Override
    public String toString() {
        return x + "," + y;
    }
}