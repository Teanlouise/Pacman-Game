package pacman.ghost;

import pacman.game.PacmanGame;
import pacman.util.Position;

/**
 * Blinky is a ghost that behaves in a very aggressive manner towards a hunter.
 * When not chasing the hunter down, Blinky likes to hang out in the top right corner of the board in a red glow.
 */
public class Blinky extends Ghost {

    /**
     * Get Blinkys colour.
     * @since getColour in class Ghost
     * @return "#d54e53"
     */
    @Override
    public String getColour() {
        return "#d54e53";
    }

    /**
     * Get Blinkys type/name.
     * @since getType in class Ghost
     */
    @Override
    public GhostType getType() {
        return GhostType.BLINKY;
    }

    /**
     * Blinky targets the hunters position. See: PacmanGame.getHunter()
     * @param game to read the board from.
     * @return the ghosts target position.
     *
     */
    @Override
    public Position chaseTarget(PacmanGame game) {
        // default - no change to current position
        Position targetPosition = getPosition();
        // CHASE - target hunter position
        if (getPhase() == Phase.CHASE) {
            targetPosition = game.getHunter().getPosition();
        }
        return targetPosition;
    }

    /**
     * Blinky's home position is one block outside of the top right of the board.
     * The top left position of the board is (0, 0).
     * @param game to read the board from
     */
    @Override
    public Position home(PacmanGame game) {
        // default - no change to current position
        Position homePosition = getPosition();
        // SCATTER - get home position
        if(getPhase() == Phase.SCATTER) {
            homePosition = new Position(game.getBoard().getWidth() , -1);
        }
        return homePosition;
    }
}