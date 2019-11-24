package pacman.display;

import org.junit.Before;
import org.junit.Test;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.hunter.Hungry;
import pacman.score.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreViewModelTest {

    // initial variables for ScoreViewModel
    private ScoreViewModel scoreVM;
    private PacmanGame model;
    private ScoreBoard scores;

    // variables for testing
    private ScoreBoard expectedScores;

    @Before
    public void setUp() {
        // Setup game
        this.model = new PacmanGame(
                        "Title",
                        "Author",
                        new Hungry(),
                        new PacmanBoard(14,15)
        );

        // Create ScoreViewModel
        this.scoreVM = new ScoreViewModel(model);
        this.scores = model.getScores();

        // Setup expected variables for control
        this.expectedScores = new ScoreBoard();
        expectedScores.setScore("A", 5);
        expectedScores.setScore("B", 10);
    }

    @Test
    public void updateTest() {
        scoreVM.setPlayerScore("A", 5);
        scoreVM.setPlayerScore("B", 10);
        scoreVM.update();

        // Default 0 and "Name"
        assertEquals("Score: 0", scoreVM.getCurrentScoreProperty().get());
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());

        // Change score and order
        scores.increaseScore(100);
        scoreVM.switchScoreOrder();

        // Check no change without update
        assertEquals("Score: 0", scoreVM.getCurrentScoreProperty().get());
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());

        // Update and check change
        scoreVM.update();
        assertEquals("Score: 100", scoreVM.getCurrentScoreProperty().get());
        assertEquals("Sorted by Score", scoreVM.getSortedBy().get());
        assertEquals(expectedScores.getEntriesByScore(), scoreVM.getScores());

        // Change again
        scores.increaseScore(62);
        scoreVM.switchScoreOrder();
        scoreVM.update();
        assertEquals("Score: 162", scoreVM.getCurrentScoreProperty().get());
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());
    }

    @Test
    public void switchScoreOrderTest() {
        scoreVM.setPlayerScore("A", 5);
        scoreVM.setPlayerScore("B", 10);
        scoreVM.update();

        // Default is "name"
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());

        // Switch
        scoreVM.switchScoreOrder();
        scoreVM.update();
        assertEquals("Sorted by Score", scoreVM.getSortedBy().get());
        assertEquals(expectedScores.getEntriesByScore(), scoreVM.getScores());

        // Switch again
        scoreVM.switchScoreOrder();
        scoreVM.update();
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());
    }

    @Test
    public void getCurrentScorePropertyTest() {
        // Default score is 0
        assertEquals("Score: 0", scoreVM.getCurrentScoreProperty().get());

        // Change score but dont update, should be no change
        scores.increaseScore(20);
        assertEquals("Score: 0", scoreVM.getCurrentScoreProperty().get());

        // Now update
        scoreVM.update();
        assertEquals("Score: 20", scoreVM.getCurrentScoreProperty().get());
    }

    @Test
    public void getSortedByTest() {
        // Default (by name)
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());

        // Switch order but dont update, no change (by name)
        scoreVM.switchScoreOrder();
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());

        // Now update (by score)
        scoreVM.update();
        assertEquals("Sorted by Score", scoreVM.getSortedBy().get());

        // Switch again and update (by name)
        scoreVM.switchScoreOrder();
        scoreVM.update();
        assertEquals("Sorted by Name", scoreVM.getSortedBy().get());
    }

    @Test
    public void getScoresTest() {
        scoreVM.setPlayerScore("A", 5);
        scoreVM.setPlayerScore("B", 10);
        scoreVM.update();

        // Default (by name)
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());

        // Switch but dont update, no change (by name)
        scoreVM.switchScoreOrder();
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());

        // Now update (by score)
        scoreVM.update();
        assertEquals(expectedScores.getEntriesByScore(), scoreVM.getScores());

        // Switch again and update (by name)
        scoreVM.switchScoreOrder();
        scoreVM.update();
        assertEquals(expectedScores.getEntriesByName(), scoreVM.getScores());
    }

    @Test
    public void getCurrentScoreTest() {
        // Starting at 0
        assertEquals(0, scoreVM.getCurrentScore());

        // Change score
        scores.increaseScore(62);
        assertEquals(62, scoreVM.getCurrentScore());
    }

    @Test
    public void setPlayerScoreTest() {
        // Default empty to start
        List<String> scoreList = new ArrayList<>();
        assertEquals(scoreList, scoreVM.getScores());

        // Add a player score
        scoreList.add("player1 : 105");
        scoreVM.setPlayerScore("player1", 105);
        scoreVM.update();
        assertEquals(scoreList, scoreVM.getScores());

        // Update score to higher (override)
        scoreList.set(0, "player1 : 155");
        scoreVM.setPlayerScore("player1", 155);
        scoreVM.update();
        assertEquals(scoreList, scoreVM.getScores());

        // Update score to lower (override)
        scoreList.set(0, "player1 : 89");
        scoreVM.setPlayerScore("player1", 89);
        scoreVM.update();
        assertEquals(scoreList, scoreVM.getScores());

        // Add another player
        scoreList.add("player2 : 5");
        scoreVM.setPlayerScore("player2", 5);
        scoreVM.update();
        assertEquals(scoreList, scoreVM.getScores());

        // Add invalid name (ignore)
        scoreVM.setPlayerScore("*", 15);
        scoreVM.update();
        assertEquals(scoreList, scoreVM.getScores());
    }
}