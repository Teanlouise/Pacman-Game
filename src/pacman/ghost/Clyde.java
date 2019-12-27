package pacman.ghost;

import pacman.game.PacmanGame;
import pacman.util.Position;

/**
 * Clyde is a ghost that behaves in a very scared manner when close to a hunter.
 * When not chasing the hunter down, clyde likes to hang out in the bottom left
 * corner of the board in a orange glow.
 */
public class Clyde extends Ghost {

    /**
     * Get Clydes colour.
     * @since - getColour in class Ghost
     * @return - "#e78c45"
     */
    @Override
    public String getColour() {
        return "#e78c45";
    }

    /**
     * Get Clydes type/name.
     * @since - getType in class Ghost
     */
    @Override
    public GhostType getType() {
        return GhostType.CLYDE;
    }

    /**
     * Clyde will target the hunter if equal to or greater than a distance of
     * 8 away from the hunter.
     * Otherwise if closer than 8 it will target its home position.
     * See: home(PacmanGame) See: PacmanGame.getHunter()
     * @param game to read the board from.
     * @return home if less than 8 away from hunter, otherwise hunter position
     */
    @Override
    public Position chaseTarget(PacmanGame game) {
        // TODO: 11/27/19 ORIGINAL (check Phase) or NEW (no check)?
        // ORIGINAL
//        // default - no change to current position
//        Position targetPosition = getPosition();
//        // CHASE - Initialise home as default
//        if (getPhase() == Phase.CHASE) {
//            targetPosition = home(game);
//            // Check if distance to hunter is greater than 8
//            Position hunterPosition = game.getHunter().getPosition();
//            if (getPosition().distance(hunterPosition) >= 8) {
//                targetPosition = hunterPosition;
//            }
//        }
//        return targetPosition;

        // NEW
        Position hunterPosition = game.getHunter().getPosition();
        if (getPosition().distance(hunterPosition) >= 8) {
            return hunterPosition;
        } else {
            return home(game);
        }
    }

    /**
     * Clyde's home position is one block outside of the bottom left of the board.
     * Where the top left position of the board is (0, 0).
     * @param game - to read the board from
     * @return One diagional block out from the bottom left corner.
     */
    @Override
    public Position home(PacmanGame game) {
        // TODO: 11/27/19 ORIGINAL (check Phase) or NEW (no check)?
        // ORIGINAL
//        // default - no change to current position
//        Position homePosition = getPosition();
//        // SCATTER - get home position
//        if (getPhase() == Phase.SCATTER) {
//            homePosition = new Position(-1, game.getBoard().getHeight());
//        }
//        return homePosition;

        // NEW
        return new Position(-1, game.getBoard().getHeight());
    }
}