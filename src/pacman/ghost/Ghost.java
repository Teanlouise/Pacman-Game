package pacman.ghost;

import pacman.game.Entity;
import pacman.game.PacmanGame;
import pacman.util.Direction;
import pacman.util.Position;

import java.util.Arrays;
import java.util.List;

/**
 * An Abstract Ghost which is a game entity.
 */
public abstract class Ghost extends Entity {
    private Phase phase;
    private int phaseDuration;
    private boolean dead;


    /**
     * Creates a ghost which is alive and starts in the SCATTER phase with a
     * duration of Phase.SCATTER.duration(). This ghost also has a default
     * position of (0, 0) and a default direction of facing up.
     */
    public Ghost() {
        this.phaseDuration = Phase.SCATTER.getDuration();
        this.phase = Phase.SCATTER;
        this.dead = false;
    }

    /**
     * Sets the Ghost Phase and its duration overriding any current phase
     * information. If Phase is null then no changes are made.
     * If the duration is less than zero then the duration is set to 0.
     * @param newPhase - to set the ghost to.
     * @param duration - of ticks for the phase to lat for
     */
     public void setPhase(Phase newPhase, int duration) {
         if (newPhase != null) {
             this.phase = newPhase;
         }

         if (duration >= 0) {
             this.phaseDuration = duration;
         } else {
             this.phaseDuration = 0;
         }
     }

    /**
     * Get the phase that the ghost currently is in.
     * @return the set phase.
     */
    public Phase getPhase() {
        return phase;
         }

    /*
     * NextPhase decreases our phase duration and moves us to the
     * next phase if it is 0.
     *
     * - CHASE goes to SCATTER.
     * - FRIGHTENED && SCATTER go to CHASE.
     */
    private void nextPhase() {
        phaseDuration = Integer.max(0, phaseDuration - 1);
        if (phaseDuration == 0) {
            switch (getPhase()) {
                case CHASE:
                    setPhase(Phase.SCATTER, Phase.SCATTER.getDuration());
                    break;
                case FRIGHTENED:
                case SCATTER:
                    setPhase(Phase.CHASE, Phase.CHASE.getDuration());
                    break;
            }
        }
    }

    /**
     * Gets the phase info of the ghost.
     * @return the phase and duration formatted as such: "PHASE:DURATION".
     */
    public String phaseInfo() {
        return "" + phase + ":" + phaseDuration + "";
    }

    /**
     *Gets the ghosts colour.
     * @return hex version of the ghosts colour, e.g. #FFFFFF for white
     */
    public abstract String getColour();

    /**
     * Gets the ghosts type.
     * @return this ghosts type.
     */
    public abstract GhostType getType();

    /**
     * Kills this ghost by setting its status to isDead.
     */
    public void kill() {
        dead = true;
    }

    /**
     * Checks if this ghost is dead.
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Resets the ghost back to an initial state where it is "alive" with a
     * Phase of SCATTER with the SCATTER.getDuration() and facing UP.
     */
    public void reset() {
        dead = false;
        phaseDuration = Phase.SCATTER.getDuration();
        phase = Phase.SCATTER;
        setPosition(new Position(0, 0));
        setDirection(Direction.UP);
    }

    /**
     * Checks if another object instance is equal to this Ghost.
     * Ghosts are equal if they have the same alive/dead status, phase duration,
     * current phase, direction and position.     *
     * @return true if equal, false otherwise.
     */
    public boolean equals(Object o) {
        if (!(o instanceof Ghost)) {
            return false;
        }

        Ghost other = (Ghost) o;
        return super.equals(other)
                && this.dead == other.dead
                && this.phaseDuration == other.phaseDuration
                && this.phase == other.phase;
    }

    /**
     * For two objects that are equal the hash should also be equal.
     * For two objects that are not equal the hash does not have to be different.
     * @return hash of Ghost.
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        if (this.dead) {
            hash *= 5;
        } else {
            hash *= 3;
        }

        return hash + this.phaseDuration;
    }

    /**
     * Represents this Ghost in a comma-seperated string format.
     * Format is: "x,y,DIRECTION,PHASE:phaseDuration".
     * DIRECTION is the uppercase enum type value for Direction.
     * PHASE is the uppercase enum type value for Phase.
     * @return "x,y,DIRECTION,PHASE:phaseDuration"
     */
    @Override
    public String toString() {
        return super.toString()
                + "," + this.phase
                + ":" + this.phaseDuration;
    }

    /**
     * If Ghost is FRIGHTENED, then choose a target position with coordinates:
     *      targetPositionX = (x*24 mod (2 * board width )) - board width,
     *      targetPositionY = (y*36 mod (2 * board height)) - board height
     * where x and y are the current coordinates of the ghost.
     * @param game that the ghost is a part of
     * @return the position of the ghost when frightened
     */
    private Position frightenedPosition(PacmanGame game) {
        int boardWidth = game.getBoard().getWidth();
        int boardHeight = game.getBoard().getHeight();
        Position current = getPosition();

        int targetX = (current.getX() *24 % (2 * boardWidth)) - boardWidth;
        int targetY = (current.getY()*36 % (2 * boardHeight)) - boardHeight;
        return new Position(targetX, targetY);
    }

    /**
     * Get the target position.
     * If the phase is CHASE, then get the chaseTarget.
     * If the phase is SCATTER, then the position is the ghost's home position.
     * If the phase is FRIGHTENED, then choose according to frightenedPosition.
     * @param game that ghost is currently a part of
     * @return the target position according to the Ghost Phase conditions.
     */
    private Position getTarget(PacmanGame game) {
        switch(getPhase()){
            case CHASE:
                return chaseTarget(game);
            case SCATTER:
                return home(game);
            case FRIGHTENED:
                return frightenedPosition(game);
            default:
                return getPosition();
        }
    }

    /**
     * Set the new direction and position of the ghost per step 3-5 of move().
     * 3 - Choose direction that the current Ghost position when moved 1 step
     * has the smallest euclidean distance to the target position.
     * The board item in the move position must be pathable for it to be chosen.
     * The chosen direction cannot be opposite to the current direction.
     * If multiple directions have the same shortest distance,
     * then choose the direction in the order UP, LEFT, DOWN, RIGHT
     * 4 - Set direction of the Ghost to the chosen direction.
     * 5 - Set position of this Ghost to be one forward step in chosen direction.
     * @param game that the ghost is currently a part of
     */
    private void setMovePosition(PacmanGame game) {
        // 3- chose direction  and corresponding position
        Direction chosenDirection = getDirection();
        Position chosenPosition = getPosition();
        // Directions put in list in opposite order, go through each
        List<Direction> directionList = Arrays.asList(
                Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP);
        // initialise smallest distance as the largest possible distance on board
        int boardHeight = game.getBoard().getHeight();
        int boardWidth = game.getBoard().getWidth();
        double smallestDistance = Math.max(boardHeight, boardWidth);
        Position targetPosition = getTarget(game);

        for (Direction d : directionList) {
            // Find Ghost's new position based on one step in this direction
            int newX = getPosition().getX() + d.offset().getX();
            int newY = getPosition().getY() + d.offset().getY();
            Position newPosition = new Position(newX, newY);
            // Check if this new position has smallest distance,
            // is pathable and not opposite to current
            try {
                if (newPosition.distance(targetPosition) <= smallestDistance
                        && game.getBoard().getEntry(newPosition).getPathable()
                        && !(d == getDirection().opposite())) {
                    // Passed check, new chosen direction and position
                    smallestDistance = newPosition.distance(getTarget(game));
                    chosenDirection = d;
                    chosenPosition = newPosition;
                }
            } catch(IndexOutOfBoundsException e) {
                // position is out of bounds, continue to next direction
                continue;
            }

        }
        // 4 - set direction & position
        setDirection(chosenDirection);
        setPosition(chosenPosition);
    }

    /**
     * Move advances the ghost in a direction by one point on the board.
     * The direction this move is made is done as follows:
     * 1 - nextPhase()
     * 2 - getTargetPosition()
     * 3 -5 - setMovePosition()
     * @param game information needed to decide movement.
     */
    @Override
    public void move(PacmanGame game) {
        //decrease phase duration by 1 and if duration 0, move to next phase
        nextPhase();
        // set position and direction based on target position.
        setMovePosition(game);
    }

    /**
     * Gets the home block that we should be heading towards when in SCATTER.
     * @param game - to read the board from
     * @return the ghosts home position
     */
    public abstract Position home(PacmanGame game);

    /**
     * Gets the target block that we should be heading towards when in CHASE.
     * @param game to read the board from.
     */
    public abstract Position chaseTarget(PacmanGame game);
}