package pacman.hunter;

import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.Entity;
import pacman.game.PacmanGame;
import pacman.ghost.Ghost;
import pacman.ghost.Phase;
import pacman.util.Direction;
import pacman.util.Position;


/**
 * Hunters are entities which are controlled by the player to clear the board
 * and win the game.  Hunters in this version have special abilities like
 * "phasing through ghost's" and "run's two blocks at a time" and so on.
 * These special abilities are too add a bit of variety into the gam
 */
public abstract class Hunter extends Entity {
    private boolean dead;
    private boolean used;
    private int duration;
    public static final int SPECIAL_DURATION = 20;

    /**
     * Creates a Hunter setting the hunter to be alive with the following
     * conditions:
     *      The hunter has not used it's special yet.
     *      The hunter also does not have its special active.
     *      This hunter has a position of (0, 0) with a direction of UP.
     */
    public Hunter() {
        this.dead = false;
        this.used = false;
        this.duration = 0; //Special not active
    }

    /**
     * Creates a Hunter where the following attributes are the same between
     * this hunter and the original:
     *      - Dead/Alive status
     *      - Whether the hunter has used its special ability yet.
     *      - The duration remaining of the special ability.
     *      - The position and direction.
     * @param original hunter to copy.
     */
    public Hunter(Hunter original) {
        this.dead = original.dead;
        this.used = original.used;
        this.duration = original.duration;
        setPosition(original.getPosition());
        setDirection(original.getDirection());
    }

    /**
     * Tells if the hunter is dead
     * @return true if dead, false otherwise
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Activates hunter's special if the hunter hasn't already used its special
     * before. If the hunter has already used its special then do not change the
     * special duration. If the duration for the special is greater than zero
     * then use the hunter's special and set the special's duration to the given
     * duration. If the duration for the special is zero or lower than do not
     * change the special duration and do not use up the hunter's special.
     *@param duration to activate the special for.
     */
    public void activateSpecial(int duration) {
        if (!used && duration > 0) {
            used = true;
            this.duration = duration;
        }
    }

    /**
     * Gets how many ticks of our special ability is remaining.
     * @return the amount of ticks remaining for the special.
     */
    public int getSpecialDurationRemaining() {
        return duration;
    }

    /**
     * Checks if the special is currently active.
     * @return true if the special ability has a duration remaining that is
     * greater than 0 ticks.
     */
    public boolean isSpecialActive() {
        return (duration > 0);
    }

    /**
     * Checks to see if the hunter is at the same position of the ghost. If the
     * ghost and hunter do have the same position then if the ghost is
     * Phase.FRIGHTENED the ghost is killed Ghost.kill() otherwise the ghost
     * kills the hunter. If the ghost and hunter are not at the same position
     * then do nothing.
     * @param ghost to check if were colliding with.
     * @throws NullPointerException if ghost is null.
     */
    public void hit(Ghost ghost) throws NullPointerException {
        if (ghost == null) {
            throw new NullPointerException();
        }

        Position ghostPosition = ghost.getPosition();
        Position myPosition = getPosition();

        // TODO: 11/24/19 - check names to make shorter
        // Compare if X and Y of hunter and ghost are the same
        if (myPosition.getX() == ghostPosition.getX() && myPosition.getY() == ghostPosition.getY()) {
            if (ghost.getPhase() == Phase.FRIGHTENED) {
                ghost.kill();
            } else {
                dead = true;
            }
        }
    }

    /**
     * Resets this hunter to be:
     * - Alive
     * - With a special that has not been used yet
     * - A special that is not active ( duration of 0 )
     * - With a Direction of Direction.UP
     * - With a Position of ( 0, 0 )
     */
    public void reset() {
        dead = false;
        used = false;
        duration = 0;
        setPosition(new Position(0,0));
        setDirection(Direction.UP);
    }

    /**
     * Checks if another object instance is equal to this. Hunters are equal
     * if they have the same:
     *      Alive/dead value.
     *      Special duration.
     *      Special used status.
     *      Direction.
     *      Position.
     * @return true if same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Hunter)) {
            return false;
        }

        Hunter other = (Hunter) o;
        return super.equals(other)
                && this.dead == other.dead
                && this.duration == other.duration
                && this.used == other.used;
    }

    /**
     * For two objects that are equal the hash should also be equal.
     * For two objects that are not equal the hash does not have to be different.
     *
     * @return hash of Hunter
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        if (this.dead && this.used) { // both true
            hash *= 11;
        } else if (this.dead && !(this.used)) { // true and false
            hash *= 13;
        } else if (!(this.dead) && this.used) { // false and true
            hash *= 17;
        } else { // both false
            hash *= 31;
        }
        return hash + this.duration;
    }

    /**
     * Represents this Hunter in a comma-seperated string format.
     * Format is: "x,y,DIRECTION,specialDuration".
     * DIRECTION is the uppercase enum type value.
     * Example: "4,5,LEFT,12"
     *
     * @return "x,y,DIRECTION,specialDuration"
     */
    @Override
    public String toString() {
        return super.toString()
                + "," + this.duration;
    }

    /**
     * Moves the Hunter across the board.
     * If the BoardItem one position forward in the hunter's current direction
     * is pathable, move the hunter into this position.
     * Otherwise the hunter stays in its current position.
     * After moving, the hunter will eat the item that occupied the block and
     * will add its score to the game score.
     * Lastly the amount of special ticks will be decreased by 1 if it is
     * greater than 0.
     * @param game information needed to decide movement.
     */
    @Override
    public void move(PacmanGame game) {
        PacmanBoard board = game.getBoard();
        try {
            // get the new position that is one step forward of direction
            int newX = getPosition().getX() + getDirection().offset().getX();
            int newY = getPosition().getY() + getDirection().offset().getY();
            Position newPosition = new Position(newX, newY);
            BoardItem item = board.getEntry(newPosition);
            // check if position is pathable, else dont move
            if (item.getPathable()) {
                // move hunter
                setPosition(newPosition);
                // eat item
                board.eatDot(getPosition());
                // add score
                game.getScores().increaseScore(item.getScore());
            }
        } catch (IndexOutOfBoundsException e) {
            // position is out of bounds, so dont move
            ;
        }
        // decrease ticks
        if (duration > 0) {
            duration -= 1;
        }
    }
}