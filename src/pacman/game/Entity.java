package pacman.game;

import pacman.util.Direction;
import pacman.util.Position;

/**
 * Entity A entity is the animated objects in the game that can traverse the
 * game board and interact with other entities.
 */
public abstract class Entity implements Moveable {
    private Position position;
    private Direction direction;

    /**
     * Creates an entity that is at position (0, 0) and is facing UP.
     */
    public Entity() {
        this.position = new Position(0, 0);
        this.direction = Direction.UP;
    }

    /**
     * Creates an entity at the given position facing in the given direction.
     * If the position is null then the position will be the same as the
     * default position ( 0, 0 ). If the direction is null then the direction
     * will be the same as the default ( UP ).
     * @param position to be set to.
     * @param direction to be facing.
     */
    public Entity(Position position, Direction direction) {

        if (position != null) {
            this.position = position;
        } else {
            this.position = new Position(0, 0);
        }

        if (direction != null) {
            this.direction = direction;
        } else {
            this.direction = Direction.UP;
        }
    }

    /**
     * Gets the current position of the Moveable.
     * @since getPosition in interface Moveable
     * @return current position.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the entity, if position is null the position not set.
     * @since setPosition in interface Moveable
     * @param position to set to the Moveable
     */
    @Override
    public void setPosition(Position position) {
        if (position != null) {
            this.position = position;
        }
    }

    /**
     * Gets the direction that this Moveable is facing.
     * @since getDirection in interface Moveable
     * @return the current direction of the Movable.
     */
    @Override
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the entity, if the direction is null the direction
     * is not set and remains the same.
     * @since setDirection in interface Moveable
     * @param direction to be set.
     */
    @Override
    public void setDirection(Direction direction) {
        if (direction != null) {
            this.direction = direction;
        }
    }

    /**
     * Checks if another object instance is equal to this instance.
     * Entities are equal if their positions and directions are equal.
     * @return true if same, false otherwise.
     */
    public boolean equals(Object o) {
        if (!(o instanceof Entity)) {
            return false;
        }
        Entity other = (Entity) o;
        return this.position.getX() == other.position.getX()
                && this.position.getY() == other.position.getY()
                && this.direction == other.direction;
    }

    /**
     * For two objects that are equal the hash should also be equal.
     * For two objects that are not equal the hash does not have to be different.
     * @return hash of Entity
     */
    @Override
    public int hashCode() {
        return this.position.getX() * 2
                + this.position.getY() * 5
                + this.direction.hashCode();
    }

    /**
     * Represents this entity in a comma-seperated string format.
     * Format is: "x,y,DIRECTION", where DIRECTION is uppercase enum type value.
     * Example: 4,5,LEFT
     * @return "x,y,DIRECTION"
     */
    @Override
    public String toString() {
        return this.position.toString()
                + ","
                + this.direction;
    }
}