package pacman.display;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pacman.game.GameWriter;
import pacman.game.PacmanGame;
import pacman.hunter.Hunter;
import pacman.util.Direction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Used as an intermediary between the game and the MainView
 */
public class MainViewModel {
    // Main variables for view
    private PacmanGame model;
    private boolean paused;
    private boolean gameOver;
    private int tick;
    private String saveFilename;

    // ScoreView and BoardView variables
    private ScoreViewModel scoreVM;
    private BoardViewModel boardVM;

    // Property variables
    private BooleanProperty pausedProp;
    private StringProperty titleProp;
    private BooleanProperty gameOverProp;

    /**
     * Creates a MainViewModel and updates the properties and the ScoreViewModel
     * created. ScoreViewModel and BoardViewModel should be created here.
     * By default, the game should be paused.
     * @param model - the PacmanGame to link to the view
     * @param saveFilename - the name for saving the game
     * @require model != null && saveFilename != null && !saveFilename.isEmpty()
     */
    public MainViewModel(PacmanGame model, String saveFilename) {
        // Create MainView
        this.model = model;
        this.paused = true;
        this.gameOver = false;
        this.tick = 0;
        this.saveFilename = saveFilename;

        // Create BoardView and ScoreView
        this.scoreVM = new ScoreViewModel(this.model);
        this.boardVM = new BoardViewModel(this.model);

        // Initialize properties
        this.pausedProp = new SimpleBooleanProperty();
        this.pausedProp.set(true);
        this.titleProp = new SimpleStringProperty();
        this.gameOverProp = new SimpleBooleanProperty();

        // Updates properties and ScoreVM
        update();
    }

    /**
     * Updates the title of the game window and the score view model.
     * @ensures title format is "[title] by [author]" without the quotes or brackets.
     * For example, "Default CSSE2002 PacMan Map by Evan Hughes".
     * There should be a single space either side of "by"
     */
    public void update() {
        // Update title of game window
        titleProp.set(model.getTitle() + " by " + model.getAuthor());

        // Update ScoreView
        getScoreVM().update();
    }

    /**
     * Gets the title property of the Game Window.
     * @return the title property of the game
     */
    public StringProperty getTitle() {
        return titleProp;
    }

    /**
     * Gets the property representing whether the game is over or not.
     * @return the game over status
     */
    public BooleanProperty isGameOver() {
        return gameOverProp;
    }

    /**
     * Saves current state of the game to file location given in the constructor.
     * An IOException should not cause the program to crash and should be ignored.
     *
     */
    public void save() {
        try {
            // Setup writers
            GameWriter gw = new GameWriter();
            FileWriter fw = new FileWriter(saveFilename);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write game to savefile
            gw.write(bw, model);
            bw.close();

        } catch (IOException e) {
            // Exception to be ignored
            return;
        }
    }

    /**
     * Tick is to be called by the view at around 60 times a second.
     * This method will pass these ticks down to the model at a reduced rate
     * depending on the level of the game. The game starts with zero ticks.
     *
     * If the game is not paused:
     *      Check if the current tick count is integer-divisible by the delay
     *      specified for the current level. If it is integer-divisible:
     *          Tick the model. See PacmanGame.tick()
     *      Increment the tick count by 1.
     *
     * Finally, update the "game over" property to be true if the player
     * currently has 0 lives left, and false otherwise. This should be done
     * regardless of whether or not the game is paused.
     */
    public void tick() {
        int delay = 0;
        int level = boardVM.getLevel();

        // Check tick/delay only when game NOT paused
        if (paused == false) {
            switch(level) {
                // Get delay according to level
                case (0):
                case (1):
                    delay = 50;
                    break;
                case (2):
                case (3):
                    delay = 40;
                    break;
                case (4):
                case (5):
                    delay = 30;
                    break;
                case (6):
                case (7):
                case (8):
                    delay = 20;
                    break;
                default:
                    delay = 10;
                    break;
            }

            // Check if integer-divisible
            if ((tick % delay) == 0) {
                // Yes so tick game
                model.tick();
            }

            // Increment tick
            tick++;
        }

        // Check if game over (anytime)
        if (boardVM.getLives() == 0) {
            gameOver = true;
        } else {
            gameOver = false;
        }

        // Update gameover prop
        gameOverProp.set(gameOver);
    }

    /**
     * Accepts key input from the view and acts according to the key.
     *      P, p	-   true	-   Pauses or unpauses the game.
     *      R, r	-   true	-   Resets the model.
     *      A, a	-   false	-   Changes pacman to face left.
     *      D, d	-   false	-   Changes pacman to face right.
     *      W, w	-   false	-   Changes pacman to face up.
     *      S, s	-   false	-   Changes pacman to face down.
     *      O, o	-   false	-   Activates the hunter's special ability
     *     (if it is available) for a duration of Hunter.SPECIAL_DURATION.
     * @param input - incoming input from the view.
     * @require input != null
     */
    public void accept(String input) {
        input.toUpperCase();

        // Available anytime
        switch (input) {
            case ("P"):
                paused = !paused;
                pausedProp.set(paused);
                break;
            case ("R"):
                model.reset();
                break;
        }

        // Available only when not paused
        Hunter hunter = model.getHunter();
        if (paused == false) {
            switch(input) {
                case ("A"):
                    hunter.setDirection(Direction.LEFT);
                    break;
                case ("D"):
                    hunter.setDirection(Direction.RIGHT);
                    break;
                case ("W"):
                    hunter.setDirection(Direction.UP);
                    break;
                case ("S"):
                    hunter.setDirection(Direction.DOWN);
                    break;
                case ("O"):
                    hunter.activateSpecial(hunter.SPECIAL_DURATION);
                    break;
            }
        }
    }

    /**
     * Gets the paused property of the game.
     * @return the property associated with the pause state.
     */
    public BooleanProperty isPaused() {
        return pausedProp;
    }

    /**
     * Gets the ScoreViewModel created at initialisation.
     * @return the ScoreViewModel associated wtih the game's scores
     */
    public ScoreViewModel getScoreVM() {
        return scoreVM;
    }

    /**
     * Gets the BoardViewModel created at initialisation.
     * @return the BoardViewModel associated with the game play
     */
    public BoardViewModel getBoardVM() {
        return boardVM;
    }
}