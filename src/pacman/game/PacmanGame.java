package pacman.game;

import pacman.board.PacmanBoard;
import pacman.ghost.*;
import pacman.hunter.Hunter;
import pacman.score.ScoreBoard;

import java.util.Arrays;
import java.util.List;

/**
 * PacmanGame stores the game's state and acts as the model for the entire game.
 */
public class PacmanGame {
    // variables required for game setup
    private String title;
    private String author;
    private Hunter hunter;
    private PacmanBoard board;
    private ScoreBoard scores;
    private Integer tick;
    private Integer lives;
    private Integer level;

    // one of each ghost instance
    private Blinky blinky;
    private Inky inky;
    private Pinky pinky;
    private Clyde clyde;
    private List<Ghost> ghostList;

    /**
     * Creates a new game with the given parameters and spawns one of each type
     * of ghost (Blinky, Clyde, Inky, Pinky). The ghosts should be spawned at
     * the ghost spawn point.
     * The game should start with:
     *      a tick of 0.
     *      a level of 0.
     *      a set of 4 lives.
     *      a empty scoreboard with a initial score of 0.
     * @param title  of the game board
     * @param author of the game board.
     * @param hunter for the current game
     * @param board  to be copied for this game
     */
    public PacmanGame(String title, String author, Hunter hunter, PacmanBoard board) {
        this.title = title;
        this.author = author;
        this.hunter = hunter;
        this.board = board;
        this.scores = new ScoreBoard();
        this.tick = 0;
        this.level = 0;
        this.lives = 4;

        // one of each ghost spawned at GHOST_SPAWN
        this.blinky = new Blinky();
        this.inky = new Inky();
        this.pinky = new Pinky();
        this.clyde = new Clyde();
        this.ghostList = Arrays.asList(blinky, inky, pinky, clyde);
        for (Ghost g : ghostList) {
            g.setPosition(board.getGhostSpawn());
        }
    }

    /**
     * @return title of the map.
     * @ensures result != null
     */
    public String getTitle() {
        assert title != null;
        return title;
    }

    /**
     * @return author of the map
     * @ensures result != null
     */
    public String getAuthor() {
        assert author != null;
        return author;
    }

    /**
     * Gets the current pacman board.     *
     * @return a mutable reference to the board.
     */
    public PacmanBoard getBoard() {
        return board;
    }

    /**
     * Gets the number of times that tick has been called in the current game
     * @return the current game tick value
     */
    public int getTick() {
        return tick;
    }

    /**
     * Gets the current score board.
     * @return a mutable reference to the score board
     */
    public ScoreBoard getScores() {
        return scores;
    }

    /**
     * @return current level of the game.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the game
     * @param level to be set to.
     * @ensures newLevel = max(0, givenLevel)
     */
    public void setLevel(int level) {
        this.level = Math.max(0, level);
    }

    /**
     * @return amount of lives the player currently has.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the lives of the current player.
     *
     * @param lives to be set to.
     * @ensures newLives = max(0, givenLives)
     */
    public void setLives(int lives) {
        this.lives = Math.max(0, lives);
    }

    /**
     * @return a mutable reference to the hunter
     */
    public Hunter getHunter() {
        return hunter;
    }

    /**
     * Note: modifying the returned list shouldn't affect the internal state
     * of PacmanGame.
     * @return a list of ghosts in the game.
     */
    public List<Ghost> getGhosts() {
        return ghostList;
    }

    /**
     * Tick If we do not have any lives (getLives() == 0) then do nothing.
     * Otherwise we do the following in this order:
     * 1. The Hunter moves Hunter.move(PacmanGame).
     * 2. For each ghost in the game, call Hunter.hit(Ghost)
     * 3. The Ghosts that are alive move on even ticks Ghost.move(PacmanGame) .
     * 4. For each Ghost in the game, call Hunter.hit(Ghost) on the game's hunter.
     * 5. For each ghost which is dead:
     *      Reset the ghost.
     *      Set the ghost's position to the ghost spawn position on the current board.
     *      Add 200 points.
     * 6. If the hunter is dead, then decrease the lives and reset all the
     * entities and place them at their spawn points.
     * 7. If the board is empty, then increase the level and set the ticks to 0
     * and reset the board and entities placing them at their spawn points.
     * 8. Increase the tick value. See getTick()
     * Note: game should start at a tick count of zero.
     */
    public void tick() {
        if (getLives() == 0) {
            return;
        }

        // 1 move Hunter
        hunter.move(this);

        // 2 - Hit ghosts
        for (Ghost g : getGhosts()) {
            hunter.hit(g);
            // 3 - Ghost alive & tick even
            if (!(g.isDead()) && tick % 2 == 0) {
                g.move(this);
            }
            // 4 - Hit hunter
            hunter.hit(g);
            // 5 - Ghost is Dead
            if (g.isDead()) {
                g.reset();
                g.setPosition(board.getGhostSpawn());
                scores.increaseScore(200);
            }
        }

        // 6 - hunter is dead
        if (hunter.isDead()) {
            lives -= 1;
            resetAllGhosts();
            resetHunter();
        }

        // 7 - board is empty
        if (board.isEmpty()) {
            level += 1;
            tick = 0;
            board.reset();
            resetAllGhosts();
            resetHunter();
        } else {
            // 8 - increase tick value
            tick += 1;
        }
    }

    /**
     * Resets the Game in the following way:
     *      Lives is set to the default of 4.
     *      Level is set to 0.
     *      ScoreBoard is reset ScoreBoard.reset()
     *      PacmanBoard is reset PacmanBoard.reset()
     *      All entities are reset
     *      All entity positions are set to their spawn locations.
     *      The tick value is reset to zero.
     */
    public void reset() {
        lives = 4;
        level = 0;
        scores.reset();
        board.reset();
        resetAllGhosts();
        resetHunter();
        tick = 0;
    }

    /**
     * For each ghost in the game,
     * set its phase to be Phase.FRIGHTENED with a duration of
     * Phase.FRIGHTENED.getDuration();
     */
    public void setGhostsFrightened() {
        for (Ghost g : getGhosts()) {
            g.setPhase(Phase.FRIGHTENED, Phase.FRIGHTENED.getDuration());
        }
    }

    /**
     * Reset all ghosts in getGhosts() as per Ghost reset() and setting all
     * the ghost's positions at the GHOSTSPAWN location on the board.
     */
    private void resetAllGhosts() {
        for (Ghost g : getGhosts()) {
            g.reset();
            g.setPosition(board.getGhostSpawn());
        }
    }

    /**
     * Reset the hunter per Hunter reset() and set the hunter's position
     *  to the PACMANSPAWN location on the board.
     */
    private void resetHunter() {
        hunter.reset();
        hunter.setPosition(board.getPacmanSpawn());
    }
}