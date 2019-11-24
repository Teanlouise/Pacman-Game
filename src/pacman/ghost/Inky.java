package pacman.ghost;

import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.util.Direction;
import pacman.util.Position;

/**
 * Inky is a ghost that likes to tail close behind the hunter.
 * When not chasing the hunter down, Inky likes to hang out in the bottom right corner of the board in a blue glow
 */
public class Inky extends Ghost {

    /**
     * Get Inkys colour.
     * @since getColour in class Ghost
     * @return "#7aa6da"
     */
    @Override
    public String getColour() {
        return "#7aa6da";
    }

    /**
     * Get Inkys type/name.
     * @since getType in class Ghost
     */
    @Override
    public GhostType getType() {
        return GhostType.INKY;
    }

    /**
     * Inky will chase 2 blocks behind the hunter's current direction.
     * See: PacmanGame.getHunter()
     * @param game
     * @return
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
                    // same x, 2 down for y
                    targetPosition = new Position(hunterX, hunterY + 2);
                    break;
                case DOWN:
                    // same x, 2 up for y
                    targetPosition = new Position(hunterX, hunterY - 2);
                    break;
                case RIGHT:
                    // 2 left for x, same y
                    targetPosition = new Position(hunterX - 2, hunterY);
                    break;
                case LEFT:
                    // 2 right for x, same y
                    targetPosition = new Position(hunterX + 2, hunterY);
                    break;
            }
        }
        return targetPosition;
    }

    /**
     * Inky's home position is one block outside of the bottom right of the board.
     * Where the top left position of the board is (0, 0).
     * @param game to read the board from
     * @return One diagonal block out from the bottom right corner
     */
    @Override
    public Position home(PacmanGame game){
        // default - no change to current position
        Position homePosition = getPosition();
        PacmanBoard board = game.getBoard();
        // SCATTER - get home position
        if(getPhase() == Phase.SCATTER) {
            homePosition = new Position(board.getWidth(), board.getHeight());
        }
        return homePosition;
    }
}