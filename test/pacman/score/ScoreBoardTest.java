package pacman.score;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ScoreBoardTest {
    // ASSIGNMENT ONE
    private ScoreBoard board;
    private Map<String, Integer> scores;

    // ASSIGNMENT TWO
    private ScoreBoard scoreOne;
    private ScoreBoard scoreTwo;

    @Before
    public void setUp() throws Exception {
        board = new ScoreBoard();
        scores = new TreeMap<String, Integer>();

        scoreOne = new ScoreBoard();
        scoreTwo = new ScoreBoard();
    }

    /**
     * ASSIGNMENT ONE
     */

    /**
     * Constructor test
     */
    @Test
    public void newScoreBoardTest() {
        // check scoreboard has no entries and a current score of 0
        assertEquals(0, board.getScore());
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
        assertEquals(new ArrayList<>(), board.getEntriesByName());
    }

    /**
     * setScoreTests
     */
    //setScore VALUE must be greater than 0 and override existing
    @Test
    public void setScoreValueNegativeTest() {
        // Check score less than 0
        board.setScore("A", -1);
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
    }

    @Test
    public void setScoreValueZeroTest() {
        // Check score is equal to 0
        board.setScore("A", 0);
        assertEquals(Arrays.asList("A : 0"), board.getEntriesByName());
    }

    @Test
    public void setScoreValuePositiveTest() {
        // check score greater than 0
        board.setScore("A", 10);
        assertEquals(Arrays.asList("A : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoreValueOverrideValidTest() {
        // Check override existing
        board.setScore("A", 10);
        board.setScore("A", 20);
        assertEquals(Arrays.asList("A : 20"), board.getEntriesByName());
    }

    @Test
    public void setScoreValueOverrideInvalidTest() {
        // Check invalid entry doesnt override
        board.setScore("A", 10);
        board.setScore("A", -10);
        assertEquals(Arrays.asList("A : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoreNameNullTest() {
        // Name is null
        board.setScore(null, 10);
        assertEquals(new ArrayList<>(), board.getEntriesByName());
    }

    @Test
    public void setScoreNameLengthZeroTest() {
        // Check length at 0
        board.setScore("", 10);
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
    }

    @Test
    public void setScoreNameLengthValidTest() {
        // Check length greater than 0
        board.setScore("A", 10);
        board.setScore("Aa0", 10);
        assertEquals(Arrays.asList("A : 10", "Aa0 : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoreNameCharacterInvalidTest() {
        // Check non-valid character
        board.setScore("$", 10);
        board.setScore("$a", 10);
        board.setScore("a$", 10);
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
    }

    @Test
    public void setScoreNameCharacterBoundsTest() {
        // Test mix of bounds for valid characters and length greater than zero
        board.setScore("Aa0", 10);
        board.setScore("Mm5", 10);
        board.setScore("Zz9", 10);
        assertEquals(Arrays.asList("Aa0 : 10", "Mm5 : 10", "Zz9 : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoreNameUpperCaseCharacterBoundsTest() {
        // Check bounds A and Z
        board.setScore("A", 10);
        board.setScore("M", 10);
        board.setScore("Z", 10);
        assertEquals(Arrays.asList("A : 10", "M : 10", "Z : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoreNameLowerCaseBoundsTest() {
        // Check bounds a and z
        board.setScore("a", 10);
        board.setScore("m", 10);
        board.setScore("z", 10);
        assertEquals(Arrays.asList("a : 10", "m : 10", "z : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoreNameNumberBoundsTest() {
        // Check bounds a and z
        board.setScore("0", 10);
        board.setScore("5", 10);
        board.setScore("9", 10);
        assertEquals(Arrays.asList("0 : 10", "5 : 10", "9 : 10"), board.getEntriesByName());
    }

    /**
     * increaseScore test
     */
    @Test
    public void increaseScorePositiveTest() {
        // Check if additional score greater than 0
        board.increaseScore(5);
        assertEquals(5, board.getScore());
    }

    @Test
    public void increaseScoreZeroTest() {
        // check if additional score is equal to 0
        board.increaseScore(5);
        board.increaseScore(0);
        assertEquals(5, board.getScore());
    }

    @Test
    public void increaseScoreNegativeTest() {
        // Check if additional score is less than 0
        board.increaseScore(5);
        board.increaseScore(-5);
        assertEquals(5, board.getScore());
    }

    /**
     * reset Tests
     */
    @Test
    public void resetZeroScoreTest() {
        // Check when board is empty
        board.reset();
        assertEquals(0, board.getScore());
    }

    @Test
    public void resetNonZeroScoreTest() {
        // Check when board has a score
        board.increaseScore(5);
        board.reset();
        assertEquals(0, board.getScore());
    }

    /**
     * getEntriesByScore tests
     */
    @Test
    public void getEntriesByScoreNoEntriesTest() {
        // Check returns empty list as no entries
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
    }

    @Test
    public void getEntriesByScoreFormatTest() {
        // Check prints format "NAME : VALUE"
        board.setScore("a", 20);
        assertEquals(Arrays.asList("a : 20"), board.getEntriesByScore());
    }

    @Test
    public void getEntriesByScoreOrderTest() {
        // Add valid entries to check keeps descending score followed by lexi order
        // Add higher name and higher score
        board.setScore("a", 20);
        board.setScore("0", 30);
        assertEquals(Arrays.asList("0 : 30", "a : 20"), board.getEntriesByScore());
        // Add lower name and lower score
        board.setScore("Z", 10);
        assertEquals(Arrays.asList("0 : 30", "a : 20", "Z : 10"), board.getEntriesByScore());
        // Add higher name and lower score
        board.setScore("A", 0);
        assertEquals(Arrays.asList("0 : 30", "a : 20", "Z : 10", "A : 0"), board.getEntriesByScore());
        // Add lower name and higher score
        board.setScore("9", 40);
        assertEquals(Arrays.asList("9 : 40", "0 : 30", "a : 20", "Z : 10", "A : 0"), board.getEntriesByScore());
        // Add higher name as existing score
        board.setScore("New", 0);
        assertEquals(Arrays.asList("9 : 40", "0 : 30", "a : 20", "Z : 10", "A : 0", "New : 0"), board.getEntriesByScore());
        // Add lower name as existing score
        board.setScore("z", 10);
        assertEquals(Arrays.asList("9 : 40", "0 : 30", "a : 20", "Z : 10", "z : 10", "A : 0", "New : 0"), board.getEntriesByScore());
        // Change existing name to higher score
        board.setScore("New", 20);
        assertEquals(Arrays.asList("9 : 40", "0 : 30", "New : 20", "a : 20", "Z : 10", "z : 10", "A : 0"), board.getEntriesByScore());
        // Change existing name to lower score
        board.setScore("a", 10);
        assertEquals(Arrays.asList("9 : 40", "0 : 30", "New : 20", "Z : 10", "a : 10", "z : 10", "A : 0"), board.getEntriesByScore());
    }

    /**
     * getEntriesByName tests
     */
    @Test
    public void getEntriesByNameNoEntriesTest() {
        // Check returns empty list as no entries
        assertEquals(new ArrayList<>(), board.getEntriesByName());
    }

    @Test
    public void getEntriesByNameFormatTest() {
        // Check prints format "NAME : VALUE"
        board.setScore("Aa", 20);
        assertEquals(Arrays.asList("Aa : 20"), board.getEntriesByScore());
    }

    @Test
    public void getEntriesByNameOrderTest() {
        // Add valid entries to check keeps lexi order
        // Add name after
        board.setScore("Aa", 20);
        board.setScore("z", 10);
        assertEquals(Arrays.asList("Aa : 20", "z : 10"), board.getEntriesByScore());
        // Add name in the middle
        board.setScore("aA", 10);
        assertEquals(Arrays.asList("Aa : 20", "aA : 10", "z : 10"), board.getEntriesByScore());
        // Change score of existing to higher
        board.setScore("aA", 20);
        assertEquals(Arrays.asList("Aa : 20", "aA : 20", "z : 10"), board.getEntriesByScore());
    }

    /**
     * setScoreTests
     */
    @Test
    public void setScoresNullTest() {
        board.setScores(null);
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
    }

    @Test
    public void setScoresValueNegativeTest() {
        // Check score less than 0
        scores.put("A", -1);
        scores.put("B", -5);
        board.setScores(scores);
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
    }

    @Test
    public void setScoresValueZeroTest() {
        // Check score is equal to 0
        scores.put("A", 0);
        scores.put("B", 0);
        board.setScores(scores);
        assertEquals(Arrays.asList("A : 0", "B : 0"), board.getEntriesByName());
    }

    @Test
    public void setScoresValuePositiveTest() {
        // check score greater than 0
        scores.put("A", 7);
        scores.put("B", 100);
        board.setScores(scores);
        assertEquals(Arrays.asList("A : 7", "B : 100"), board.getEntriesByName());
    }

    @Test
    public void setScoresValueMixedTest() {
        // check score greater than 0
        scores.put("A", -20);
        scores.put("B", 100);
        board.setScores(scores);
        assertEquals(Arrays.asList("B : 100"), board.getEntriesByName());
    }

    @Test
    public void setScoresValueOverrideValidTest() {
        // Check override existing
        scores.put("A", 20);
        scores.put("B", 100);
        board.setScores(scores);
        scores.put("A",50);
        board.setScores(scores);
        assertEquals(Arrays.asList("A : 50", "B : 100"), board.getEntriesByName());
    }

    @Test
    public void setScoresValueOverrideInvalidTest() {
        // Check override existing
        scores.put("A", 20);
        scores.put("B", 100);
        board.setScores(scores);
        scores.put("A",-50);
        board.setScores(scores);
        assertEquals(Arrays.asList("A : 20", "B : 100"), board.getEntriesByName());
    }

    @Test
    public void setScoresNameNullTest() throws NullPointerException {
        // Name is null
        try {
            scores.put(null, 1);
            scores.put("B", 5);
            board.setScores(scores);
            assertEquals(Arrays.asList("B : 5"), board.getEntriesByScore());
        } catch (NullPointerException e) {
            ;
        }
    }

    @Test
    public void setScoresNameLengthZeroTest() {
        // Check length at 0
        scores.put("", 1);
        scores.put("B", 5);
        board.setScores(scores);
        assertEquals(Arrays.asList("B : 5"), board.getEntriesByScore());
    }

    @Test
    public void setScoresNameLengthValidTest() {
        // Check length greater than 0
        scores.put("Hello", 10);
        scores.put("B", 5);
        board.setScores(scores);
        assertEquals(Arrays.asList("Hello : 10", "B : 5"), board.getEntriesByScore());
    }

    @Test
    public void setScoresNameCharacterInvalidTest() {
        // Check non-valid character
        scores.put("$", 10);
        scores.put("$a", 10);
        scores.put("a$", 10);
        board.setScores(scores);
        assertEquals(new ArrayList<>(), board.getEntriesByScore());
    }

    @Test
    public void setScoresNameCharacterBoundsTest() {
        // Test mix of bounds for valid characters and length greater than zero
        scores.put("Aa0", 10);
        scores.put("Mm5", 10);
        scores.put("Zz9", 10);
        board.setScores(scores);
        assertEquals(Arrays.asList("Aa0 : 10", "Mm5 : 10", "Zz9 : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoresNameUpperCaseCharacterBoundsTest() {
        // Check bounds A and Z
        scores.put("A", 10);
        scores.put("M", 10);
        scores.put("Z", 10);
        board.setScores(scores);
        assertEquals(Arrays.asList("A : 10", "M : 10", "Z : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoresNameLowerCaseBoundsTest() {
        // Check bounds a and z
        scores.put("a", 10);
        scores.put("m", 10);
        scores.put("z", 10);
        board.setScores(scores);
        assertEquals(Arrays.asList("a : 10", "m : 10", "z : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoresNameNumberBoundsTest() {
        // Check bounds a and z
        scores.put("0", 10);
        scores.put("5", 10);
        scores.put("9", 10);
        board.setScores(scores);
        assertEquals(Arrays.asList("0 : 10", "5 : 10", "9 : 10"), board.getEntriesByName());
    }

    @Test
    public void setScoresOrderTest() {
        // check orders of getEntriesByName and getEntriesByScore with setScores
        scores.put("A", 10);
        scores.put("a", 20);
        scores.put("0", 20);
        scores.put("9", 30);
        scores.put("Z", 20);
        scores.put("z", 40);
        scores.put("aMb", 10);
        board.setScores(scores);
        assertEquals(Arrays.asList("0 : 20", "9 : 30", "A : 10", "Z : 20", "a : 20", "aMb : 10", "z : 40"), board.getEntriesByName());
        assertEquals(Arrays.asList("z : 40", "9 : 30",  "0 : 20", "Z : 20", "a : 20",  "A : 10", "aMb : 10"), board.getEntriesByScore());
    }

    /**
     * ASSIGNMENT TWO
     */
    @Test
    public void equalsScoreBoardTest(){
        assertEquals(true, scoreOne.equals(scoreTwo));
        //increase score
        scoreOne.increaseScore(20);
        assertEquals(false, scoreOne.equals(scoreTwo));
        scoreTwo.increaseScore(20);
        assertEquals(true, scoreOne.equals(scoreTwo));
        // set score
        scoreOne.setScore("Tean", 5);
        assertEquals(false, scoreOne.equals(scoreTwo));
        scoreTwo.setScore("Tean", 5);
        assertEquals(true, scoreOne.equals(scoreTwo));
        //set scores
        Map<String, Integer> scores = new HashMap<String, Integer>();
        scores.put("Todd",20);
        scores.put("Fred",50);
        scoreOne.setScores(scores);
        assertEquals(false, scoreOne.equals(scoreTwo));
        scoreTwo.setScores(scores);
        assertEquals(true, scoreOne.equals(scoreTwo));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(true, scoreOne.hashCode() == scoreTwo.hashCode());
        //scoreOne.increaseScore(400);
        scoreOne.setScore("todd", 5);
        System.out.println(scoreOne.hashCode());
        System.out.println(scoreTwo.hashCode());
        assertEquals(false, scoreOne.hashCode() == scoreTwo.hashCode());
    }
















    @After
    public void tearDown() throws Exception {
        board = null;
    }
}