package pacman.score;

import java.util.*;
import java.util.stream.Stream;

/**
 * ScoreBoard contains previous scores and the current score of the PacmanGame.
 * A score is a name and value that a valid name only contains the following characters:
 *      - A to Z
 *      - a to z
 *      - 0 to 9
 *      - and must have a length greater than 0.
 * The value is a integer that is equal to or greater than 0.
 */
public class ScoreBoard {

    // current overall score
    private int currentScore;
    // mapping of names to score values
    private Map<String, Integer> scores;

    /**
     * Creates a score board that has no entries and a current score of 0.
     */
    public ScoreBoard() {
        this.currentScore = 0;
        this.scores = new TreeMap<String, Integer>();
    }

    /**
     * Checks whether the name of the player is valid.
     * A valid name contains only the following characters:
     * - A to Z
     * - a to z
     * - 0 to 9
     * and must have a length greater than 0.
     * @param name of scorer
     * @return true if name is valid
     */
    private Boolean isValidName(String name) {
        // Matches regex for alphanumeric and + checks for length of 0
        return name.matches("^[a-zA-Z0-9]+");
    }

    /**
     * Gets the stored entries ordered by Name in lexicographic order.
     * The format of the list should be:
     * - Score name with a single space afterwards
     * - A single colon
     * - A space then the value of the score with no leading zeros.
     * @return List of scores formatted as "NAME : VALUE" in the order described above or an empty list if no entries are stored.
     */
    public List<String> getEntriesByName() {
        List<String> lexiList = new ArrayList<>();
        // entries is TreeMap so already in lexi order
        for (Map.Entry<String, Integer> e : scores.entrySet()) {
            lexiList.add("" + e.getKey() + " : " + e.getValue());
        }
        return lexiList;

    }

    /**
     * Gets the stored entries ordered by the score in descending order ( 9999 first then 9998 and so on ...)
     * then in lexicographic order of the name if the scores match. The format of the list should be:
     * - Score name with a single space afterwards
     * - A single colon
     * - A space then the value of the score with no leading zeros.
     * @return List of scores formatted as "NAME : VALUE" in the order described above or an empty list if no entries are stored.
     */
    public List<String> getEntriesByScore() {
        // entries is TreeMap so already in lexi order and then stream sorts in descending order by value
        Stream<Map.Entry<String, Integer>> descStream = scores.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        Iterator<Map.Entry<String, Integer>> it = descStream.iterator();
        List<String> descList = new ArrayList<>();

        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            descList.add("" + e.getKey() + " : " + e.getValue());
        }
        return descList;
    }

    /**
     * Sets the score for the given name if:
     * - name is not null
     * - name is a valid score name
     * - score is equal to or greater than zero.
     * This should override any score stored for the given name if name and score are valid.
     * @param name of scorer
     * @param score to set to the given name
     */
    public void setScore(String name, int score) {
        if (name != null && isValidName(name) && score >= 0) {
            scores.put(name, score);
        }
    }

    /**
     * Sets a collection of scores if "scores" is not null, otherwise no scores are modified. For each score contained in the scores if:
     * - name is not null
     * - name is a valid score name
     * - score is equal to or greater than zero.
     * - the score will be set and override any stored score for the given name, otherwise it will be skipped.
     * @param scores to add
     */
    public void setScores(Map<String, Integer> scores) {
        if (scores != null) { //& inside scores all valid
            for (Map.Entry<String, Integer> e : scores.entrySet()) {
                setScore(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * Increases the score if the given additional is greater than 0. No change to the current score if
     * additional is less than or equal to 0.
     * @param additional score to add
     */
    public void increaseScore(int additional) {
        if (additional > 0) {
            currentScore += additional;
        }
    }

    /**
     * Get the current score.
     * @return the current score
     */
    public int getScore() {
        return currentScore;
    }

    /**
     * Set the current score to 0.
     */
    public void reset() {
        currentScore = 0;
    }

    /**
     * Checks if a given name is valid.
     * A valid name consists of only one or more alphanumeric characters.
     */
    private boolean validName(String name) {
        // regex checks string contains only alphanumericals, and at least 1.
        return name.matches("[a-zA-Z0-9]+");
    }

    /**
     * Checks if another object instance is equal to this ScoreBoard.
     * ScoreBoards are equal if the current scores are equal and all score
     * entries are equal (both names and score values).
     * @return true if same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScoreBoard)) {
            return false;
        }

        ScoreBoard other = (ScoreBoard) o;
        return this.currentScore == other.currentScore
                && this.scores.equals(other.scores);
    }

    /**
     * For two objects that are equal the hash should also be equal.
     * For two objects that are not equal the hash does not have to be different.
     * @return hash of ScoreBoard
     */
    @Override
    public int hashCode() {
        int hash = 11;
        for(Map.Entry<String, Integer> e : scores.entrySet()) {
            hash += e.getValue();
        }
        hash =  13 * hash + this.currentScore;
        return hash;
    }
}