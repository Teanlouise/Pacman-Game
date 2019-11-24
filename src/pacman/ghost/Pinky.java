package pacman.ghost;

import pacman.game.PacmanGame;
import pacman.util.Direction;
import pacman.util.Position;

/**
 * Pinky is a cunning ghost that tries to ambush the hunter.
 * When not chasing the hunter down, Pinky likes to hang out in the top left
 * corner of the board in a pink glow.
 */
public class Pinky extends Ghost {

    /**
     * Get Pinkys colour.
     * @since getColour in class Ghost
     * @return "#c397d8"
     */
    @Override
    public String getColour() {
        return "#c397d8";
    }

    /**
     * Get Pinkys type/name.
     * @since getType in class Ghost
     */
    @Override
    public GhostType getType() {
        return GhostType.PINKY;
    }

    /**
     *Pinky will chase 4 blocks in front of the hunter's current direction.
     * @param game to read positions from
     * @return the position 4 blocks in front of hunter position
     */
    @Override
    public Position chaseTarget(PacmanGame game) {
        // default - no change to current position
        Position targetPosition = getPosition();
        // CHASE - target hunter position
        if (getPhase() == Phase.CHASE) {
            int hunterX = game.getHunter().getPosition().getX();
            int hunterY = game.getHunter().getPosition().getY();
            Direction hunterDirection = game.getHunter().getDirection();
            switch (hunterDirection) {
                case UP:
                    // same x, 4 up for y
                    targetPosition = new Position(hunterX, hunterY - 4);
                    break;
                case DOWN:
                    // same x, 4 down for y
                    targetPosition = new Position(hunterX, hunterY + 4);
                    break;
                case RIGHT:
                    // 4 right for x, same y
                    targetPosition = new Position(hunterX + 4, hunterY);
                    break;
                case LEFT:
                    // 4 left for x, same y
                    targetPosition = new Position(hunterX - 4, hunterY);
                    break;
            }
        }
        return targetPosition;
    }

    /**
     * Pinky's home position is one block outside of the top left of the board.
     * Where the top left position of the board is (0, 0).
     * Note: this will be outside of the board
     * @param game to read the board from
     * @return One diagional block out from the top left corner
     */
    @Override
    public Position home(PacmanGame game){
        // default - no change to current position
        Position homePosition = getPosition();
        // SCATTER - get home position
        if (getPhase() == Phase.SCATTER) {
            return new Position(-1, -1);
        }
        return homePosition;
    }
}