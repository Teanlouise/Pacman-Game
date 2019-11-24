package pacman.display;

import javafx.util.Pair;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.ghost.Ghost;
import pacman.ghost.Phase;
import pacman.hunter.Hunter;
import pacman.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * BoardViewModel is the intermediary between BoardView and the PacmanGame
 */
public class BoardViewModel {
    // Variables for board setup
    private PacmanGame model;
    private Hunter hunter;

    /**
     * Constructs a new BoardViewModel to model the given PacmanGame.
     * @param model - the game to be played
     * @require model != null
     */
    public BoardViewModel(PacmanGame model) {
        this.model = model;
        this.hunter = model.getHunter();
    }

    /**
     * Returns the number of lives left for the player in the game.
     * @return the number of lives
     */
    public int getLives() {
        return model.getLives();
    }

    /**
     * Returns the current level that the player is on.
     * @return the current level of the game
     */
    public int getLevel() {
        return model.getLevel();
    }

    /**
     * Returns a colour string to represent how the hunter should be displayed.
     * If game's hunter special is active it should return "#CDC3FF",
     * otherwise return "#FFE709".
     * @return the colour associated with the game's hunter
     */
    public String getPacmanColour() {
        String pacmanColour;
        // Check if hunter special is active and set colour
        if (hunter.isSpecialActive()) {
           pacmanColour = "#CDC3FF";
        } else {
            pacmanColour = "#FFE709";
        }
        return pacmanColour;
    }

    /**
     * Returns the starting angle of the mouth arc of the pacman depending on
     * its direction.
     *      If the hunter's direction is RIGHT, return 30.
     *      If the hunter's direction if UP, return 120.
     *      If the hunter's direction is LEFT, return 210.
     *      If the hunter's direction is DOWN, return 300.
     * @return the angle based on the direction of the game's hunter
     */
    public int getPacmanMouthAngle() {
        int pacmanMouthAngle = 0;
        // Get hunters direction and set mouth angle
        switch (hunter.getDirection()) {
            case RIGHT:
                pacmanMouthAngle = 30;
                break;
            case UP:
                pacmanMouthAngle = 120;
                break;
            case LEFT:
                pacmanMouthAngle = 210;
                break;
            case DOWN:
                pacmanMouthAngle = 300;
                break;
        }
        return pacmanMouthAngle;
    }

    /**
     * Returns the current position of the game's hunter.
     * @return the position of the hunter
     */
    public Position getPacmanPosition() {
        return hunter.getPosition();
    }

    /**
     * Returns the board associated with the game.
     * @return the game board
     */
    public PacmanBoard getBoard() {
        return model.getBoard();
    }

    /**
     * Returns the positions and colours of the ghosts in the game.
     * Each ghost should be represented as a Pair(position, colour), where
     * position is their current position on the board, and colour is their colour.
     * The ghost's colour should be given be by Ghost.getColour(). However, if
     * the ghost's phase is FRIGHTENED, this colour should instead be "#0000FF".
     * @return a list of Pair<position, colour > representing the ghosts in the
     * game, in no particular order
     */
    public List<Pair<Position,String>> getGhosts() {
        String ghostColour;
        Position ghostPosition;
        List<Pair<Position, String>> ghostPairList = new ArrayList<>();

        // Add every ghost in game to pair list
        for (Ghost g : model.getGhosts()) {
            // Set the colour of the ghost
            if (g.getPhase() == Phase.FRIGHTENED) {
                ghostColour = "#0000FF";
            } else {
                ghostColour = g.getColour();
            }
            // Set the position of the ghost
            ghostPosition = g.getPosition();
            // Add pair to list
            ghostPairList.add(new Pair<>(ghostPosition, ghostColour));
        }
        // All ghost pairs have been added to list, return
        return ghostPairList;
    }
}