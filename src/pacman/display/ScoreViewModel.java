package pacman.display;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pacman.game.PacmanGame;
import pacman.score.ScoreBoard;

import java.util.List;

/**
 * ScoreViewModel is an intermediary between ScoreView and the PacmanGame.
 * Used for displaying the player's score in the GUI.
 */
public class ScoreViewModel {
    // initial variables
    private PacmanGame model;
    private ScoreBoard scores;
    private boolean order;

    // property variables
    private StringProperty currentScoreProp;
    private StringProperty sortedByProp;
    private ObservableList scoresList;

    // order variables
    private List<String> scoreOrder;
    private String sortedBy;

    /**
     * Creates a new ScoreViewModel and updates its properties.
     * The default ordering of the scores should be by name.
     * @param model  - the PacmanGame to link to the view
     * @require model != null
     */
    public ScoreViewModel(PacmanGame model) {
        // Create ScoreViewModel
        this.model = model;
        this.scores = model.getScores();
        this.order = true;
        setScoreOrder();

        // Initialise properties
        this.currentScoreProp = new SimpleStringProperty();
        this.sortedByProp = new SimpleStringProperty();
        this.scoresList = FXCollections.observableArrayList();

        // Update its properties
        update();
    }

    /*
     * Sets the order of the score according to the order variable.
     * Update scoreOrder and sortedBy.
     * If order = true entries will be sorted by name, else Score.
     */
    private void setScoreOrder() {
        if (order) {
            // if order true, order is getEntriesByName
            this.scoreOrder = scores.getEntriesByName();
            this.sortedBy = "Name";
        } else {
            // if false, order is getEntriesByScore
            this.scoreOrder = scores.getEntriesByScore();
            this.sortedBy = "Score";
        }
    }

    /**
     * Updates the properties containing the current score, the sort order of
     * the scoreboard and the list of sorted scores.
     *
     * The format for the current score property should be
     * "Score: [currentScore]" without quotes or brackets, where currentScore
     * is the value returned by ScoreBoard.getScore().
     *
     * The sort order property should be set to either "Sorted by Name" or
     * "Sorted by Score", depending on the current score sort order.
     *
     * Finally, the property representing the list of scores should be updated
     * to contain a list of scores sorted according to the current score sort
     * order, as returned by ScoreBoard.getEntriesByName() and
     * ScoreBoard.getEntriesByScore().
     */
    public void update() {
        // ALL properties only changed here

        // Assign score to property
        getCurrentScoreProperty().set("Score: " + getCurrentScore());

        // Set current order of scores
        setScoreOrder();

        // Update properties based on order
        getSortedBy().set("Sorted by " + sortedBy);
        getScores().setAll(scoreOrder);
    }

    /**
     * Changes the order in which player's scores are displayed.
     * The possible orderings are by name or by score. Calling this method once
     * should switch between these two orderings.
     */
    public void switchScoreOrder() {
        order = !order;
    }

    /**
     * Returns the StringProperty containing the current score for the player.
     * @return the property representing the current score
     */
    public StringProperty getCurrentScoreProperty() {
        return currentScoreProp;
    }

    /**
     * Returns the StringProperty of how the player's scores are displayed.
     * @return StringProperty representing how the player's scores are displayed
     */
    public StringProperty getSortedBy() {
        return sortedByProp;
    }

    /**
     * Returns a list containing all "Name : Value" score entries in the game's
     * ScoreBoard, sorted by the current sort order.
     * @return the list of sorted scores
     */
    public ObservableList<String> getScores() {
        return scoresList;
    }

    /**
     * Returns the overall current score for the game.
     * @return current score
     */
    public int getCurrentScore() {
        return scores.getScore();
    }

    /**
     * Sets the given player's score to score.
     * This should override the score if it was previously set (even if new
     * score is lower). Invalid player names should be ignored.
     * @param player - the player
     * @param score - the new score
     */
    public void setPlayerScore(String player, int score) {
        scores.setScore(player, score);
    }
}