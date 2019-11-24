package pacman.game;

import pacman.ghost.Ghost;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Writes the PacmanGame to a standard format.
 */
public class GameWriter {

    /**
     * Saves a PacmanGame to a writer using set of rules.
     * @param writer to output the data to
     * @game to encode into the save data format
     * @throws IOException during an issue with saving to the file
     */
    public static void write(Writer writer, PacmanGame game) throws IOException {
        StringBuilder builder = new StringBuilder();

        //The first line of the file will be the Board block header: "[Board]".
        builder.append("[Board]");
        builder.append(System.lineSeparator());
        // Following this on the line below will be the width and height comma
        // separated with no leading zeros and no spaces.
        builder.append(game.getBoard().getWidth() + "," + game.getBoard().getHeight());
        builder.append(System.lineSeparator());
        // After this on the next line is the Game Board which is to be the
        // toString representation of the board.
        builder.append(game.getBoard().toString());
        builder.append(System.lineSeparator());

        // One blank line
        builder.append(System.lineSeparator());

        // On the next line is the "[Game]" block which will output the
        // following assignments in order: title, author, lives, level, score,
        // hunter, blinky, inky, pinky, clyde
        // The assignments are to have single space before and after equals sign.
        // Each assignment is to be on its own line.
        builder.append("[Game]");
        builder.append(System.lineSeparator());
        builder.append("title = " + game.getTitle());
        builder.append(System.lineSeparator());
        builder.append("author = " + game.getAuthor());
        builder.append(System.lineSeparator());
        builder.append("lives = " + game.getLives());
        builder.append(System.lineSeparator());
        builder.append("level = " + game.getLevel());
        builder.append(System.lineSeparator());
        builder.append("score = " + game.getScores().getScore());
        builder.append(System.lineSeparator());
        // The assignments for ( hunter, blinky, inky, pinky, clyde) are to be
        // the toString representation of these entities.
        builder.append("hunter = " + game.getHunter().toString());
        builder.append(System.lineSeparator());
        List<Ghost> ghostList = game.getGhosts();
        builder.append("blinky = " + ghostList.get(0).toString());
        builder.append(System.lineSeparator());
        builder.append("inky = " + ghostList.get(1).toString());
        builder.append(System.lineSeparator());
        builder.append("pinky = " + ghostList.get(2).toString());
        builder.append(System.lineSeparator());
        builder.append("clyde = " + ghostList.get(3).toString());
        builder.append(System.lineSeparator());

        //One blank line
        builder.append(System.lineSeparator());

        // The last block is the "[Scores]" block which should be output as
        // a multiline list of the scores
        builder.append("[Scores]");
        builder.append(System.lineSeparator());
        // The scores should be output sorted by ScoreBoard.getEntriesByName().
        List<String> scoreList = game.getScores().getEntriesByName();
        for (int i = 0; i < scoreList.size(); i++) {
            builder.append(scoreList.get(i));
            // The last score should not have a newline.
            if (i < scoreList.size() - 1) {
                builder.append(System.lineSeparator());
            }
        }

        // write to file
        writer.write(builder.toString());
    }
}
