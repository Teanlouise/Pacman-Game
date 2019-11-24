package pacman.game;

import org.junit.Before;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.GameReader;
import pacman.game.PacmanGame;
import pacman.ghost.Ghost;
import pacman.util.Position;
import pacman.util.UnpackableException;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GameReaderTest {
    private GameReader gr;

    // MAP ONE
    private StringReader mapOneString;
    private PacmanGame mapOneGame;
    private String mapOneBoardString;
    private BoardItem[][] mapOneBoard;

    // MAP TWO
    private StringReader mapTwoString;
    private PacmanGame mapTwoGame;
    private String mapTwoBoardString;
    private BoardItem[][] mapTwoBoard;

    @Before
    public void setUp() throws Exception {
        gr = new GameReader();
    }

    /**
     * Compare whether two boards have the same items
     * @return true if all arrays are the same
     */
    private boolean compareBoards(PacmanBoard board1, BoardItem[][] board2) {
        for (int column = 0; column < board1.getWidth(); column++) {
            for (int row = 0; row < board1.getHeight(); row++) {
                // Check the item at each position matches the expected
                if(board2[row][column] != board1.getEntry(new Position(column, row))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * mapOne Tests
     * This is a valid board with comments, should not expect any exceptions
     *  - board
     *  - game
     *  - ghost
     *  - scores
     */

    private void mapOneSetup() throws IOException, UnpackableException {
        //MAP ONE - valid format, order as per write(), has comments
        mapOneString = new StringReader(
                "; Default CSSE2002 Pacman Map"
                        + System.lineSeparator()
                        + "; board Keys"
                        + System.lineSeparator()
                        + "; getWidth, getHeight"
                        + System.lineSeparator()
                        + ";   - X Wall"
                        + System.lineSeparator()
                        + ";   - 0 No Item, 1 dots"
                        + System.lineSeparator()
                        + ";   - B Big dots spawn ( occupied )"
                        + System.lineSeparator()
                        + ";   - $ Ghost spawn zone"
                        + System.lineSeparator()
                        + ";   - P pacman spawn zone"
                        + System.lineSeparator()
                        + "[Board]"
                        + System.lineSeparator()
                        + "25,9"
                        + System.lineSeparator()
                        + "XXXXXXXXXXXXXXXXXXXXXXXXX"
                        + System.lineSeparator()
                        +"X10000000000000000000000X"
                        + System.lineSeparator()
                        + "X00000B00000X000000B0000X"
                        + System.lineSeparator()
                        + "X00000000000X00000000000X"
                        + System.lineSeparator()
                        +"X0000000XXXXXXXXX0000000X"
                        + System.lineSeparator()
                        + "X00000000000X00000000000X"
                        + System.lineSeparator()
                        + "X00000B000000000000B0000X"
                        + System.lineSeparator()
                        + "XP0000000000X0000000000$X"
                        + System.lineSeparator()
                        + "XXXXXXXXXXXXXXXXXXXXXXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "title = Default CSSE2002 PacMan Map"
                        + System.lineSeparator()
                        + "author = Evan Hughes"
                        + System.lineSeparator()
                        + "lives = 5"
                        + System.lineSeparator()
                        + "level = 2"
                        + System.lineSeparator()
                        + "score = 123"
                        + System.lineSeparator()
                        + "hunter = 1,1,LEFT,20,PHIL"
                        + System.lineSeparator()
                        + "blinky = 3,6,UP,FRIGHTENED:15"
                        + System.lineSeparator()
                        + "inky = 1,6,UP,SCATTER:7"
                        + System.lineSeparator()
                        + "pinky = 8,6,UP,FRIGHTENED:15"
                        + System.lineSeparator()
                        + "clyde = 6,4,UP,CHASE:4"
                        + System.lineSeparator()
                        + "; Must have one blank line between each block."
                        + System.lineSeparator()
                        + "; Comments do not count as a line"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "A : 0"
                        + System.lineSeparator()
                        + "B : 5"
                        + System.lineSeparator()
                        + "C : 100");
        mapOneGame = gr.read(mapOneString);
        mapOneString.close();
    }

    @Test
    public void mapOneBoardTest() throws IOException, UnpackableException {
        mapOneSetup();
        mapOneBoardString = (
                        "XXXXXXXXXXXXXXXXXXXXXXXXX" + System.lineSeparator()
                        +"X10000000000000000000000X"+ System.lineSeparator()
                        + "X00000B00000X000000B0000X" + System.lineSeparator()
                        + "X00000000000X00000000000X" + System.lineSeparator()
                        +"X0000000XXXXXXXXX0000000X" + System.lineSeparator()
                        + "X00000000000X00000000000X"+ System.lineSeparator()
                        + "X00000B000000000000B0000X" + System.lineSeparator()
                        + "XP0000000000X0000000000$X" + System.lineSeparator()
                        + "XXXXXXXXXXXXXXXXXXXXXXXXX"
        );

        mapOneBoard = new BoardItem[][]{
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.BIG_DOT, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.BIG_DOT, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.BIG_DOT, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.BIG_DOT, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.PACMAN_SPAWN, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.GHOST_SPAWN, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
        };

        // [Board]
        assertEquals(25, mapOneGame.getBoard().getWidth());
        assertEquals(9, mapOneGame.getBoard().getHeight());
        assertEquals(mapOneBoardString, mapOneGame.getBoard().toString());
        assertEquals(true, compareBoards(mapOneGame.getBoard(), mapOneBoard));
    }

    @Test
    public void mapOneGameTest() throws IOException, UnpackableException {
        mapOneSetup();
        // [Game}
        assertEquals("Default CSSE2002 PacMan Map", mapOneGame.getTitle());
        assertEquals("Evan Hughes", mapOneGame.getAuthor());
        assertEquals(5, mapOneGame.getLives());
        assertEquals(2, mapOneGame.getLevel());
        assertEquals(123, mapOneGame.getScores().getScore());
        assertEquals("1,1,LEFT,20,PHIL", mapOneGame.getHunter().toString());
    }

    @Test
    public void mapOneGhostTest() throws IOException, UnpackableException {
        mapOneSetup();
        // Check only 4 ghosts instances
        assertEquals(4, mapOneGame.getGhosts().size());
        // Check correct details for each ghostType
        mapOneGame.getGhosts();
        for (Ghost g : mapOneGame.getGhosts()) {
            switch (g.getType()) {
                case BLINKY:
                    assertEquals("3,6,UP,FRIGHTENED:15", g.toString());
                    break;
                case INKY:
                    assertEquals("1,6,UP,SCATTER:7", g.toString());
                    break;
                case PINKY:
                    assertEquals("8,6,UP,FRIGHTENED:15", g.toString());
                    break;
                case CLYDE:
                    assertEquals("6,4,UP,CHASE:4", g.toString());
                    break;
            }
        }
    }

    @Test
    public void mapOneScoresTest() throws IOException, UnpackableException {
        mapOneSetup();
        // [Scores]
        mapOneGame.getScores().getEntriesByName();
        List<String> testOneScoreList = new ArrayList<>();
        testOneScoreList.add("A : 0");
        testOneScoreList.add("B : 5");
        testOneScoreList.add("C : 100");
        assertEquals(testOneScoreList, mapOneGame.getScores().getEntriesByName());
    }

    /**
     * mapTwo Tests
     * This is a valid board that should not expect any exceptions
     *  - board
     *  - game
     *  - ghost
     *  - scores
     */
    private void mapTwoSetup() throws IOException, UnpackableException {
        // MAP TWO - valid format, order of game unique
        mapTwoString = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        mapTwoGame = gr.read(mapTwoString);
        mapTwoString.close();
    }


    @Test
    public void mapTwoBoardTest() throws IOException, UnpackableException {
        mapTwoSetup();
        mapTwoBoardString = (
                        "XXXXX" + System.lineSeparator()
                        +"X000X"+ System.lineSeparator()
                        + "X000X" + System.lineSeparator()
                        + "XXXXX");

        mapTwoBoard = new BoardItem[][]{
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL}
        };

        // [Board]
        assertEquals(5, mapTwoGame.getBoard().getWidth());
        assertEquals(4, mapTwoGame.getBoard().getHeight());
        assertEquals(mapTwoBoardString, mapTwoGame.getBoard().toString());
        assertEquals(true, compareBoards(mapTwoGame.getBoard(), mapTwoBoard));
    }

    @Test
    public void mapTwoGameTest() throws IOException, UnpackableException {
        mapTwoSetup();
        // [Game}
        assertEquals("This is a very long title that to test the limit", mapTwoGame.getTitle());
        assertEquals("This is the author of this game, it is long", mapTwoGame.getAuthor());
        assertEquals(10000, mapTwoGame.getLives());
        assertEquals(500, mapTwoGame.getLevel());
        assertEquals(123456789, mapTwoGame.getScores().getScore());
        assertEquals("2,3,DOWN,750,SPEEDY", mapTwoGame.getHunter().toString());
    }

    @Test
    public void mapTwoGhostTest() throws IOException, UnpackableException {
        mapTwoSetup();
        // Check only 4 ghosts instances
        assertEquals(4, mapTwoGame.getGhosts().size());
        // Check correct details for each ghostType
        mapTwoGame.getGhosts();
        for (Ghost g : mapTwoGame.getGhosts()) {
            switch (g.getType()) {
                case BLINKY:
                    assertEquals("1,2,DOWN,CHASE:15", g.toString());
                    break;
                case INKY:
                    assertEquals("3,3,RIGHT,CHASE:1068", g.toString());
                    break;
                case PINKY:
                    assertEquals("4,3,UP,SCATTER:123456789", g.toString());
                    break;
                case CLYDE:
                    assertEquals("2,1,LEFT,FRIGHTENED:0", g.toString());
                    break;
            }
        }
        //String testOneBlinky = game.getGhosts().get(game.getGhosts().indexOf(Blinky.class)).toString();

    }

    @Test
    public void mapTwoScoresTest() throws IOException, UnpackableException {
        mapTwoSetup();
        // [Scores]
        mapTwoGame.getScores().getEntriesByName();
        assertEquals("[Fred : 5, fred : 1000000]", mapTwoGame.getScores().getEntriesByName().toString());
    }

    /**
     * BAD FORMAT TEST - Expect unpackable exception for all
     * - blocks in wrong Order
     * - block missing
     * - block title no bracket
     * - block title wrong
     * - skip comments
     * - line before [BOARD]
     * - line missing between [BOARD] and [GAME]
     * - line missing between [game] and [score]
     * - line extra between [game] and [score]
     * - line extra between [board] and [game]
     * - line not blank between [board] and [game]
     *
     */
    @Test(expected = UnpackableException.class)
    public void blocksWrongOrder() throws IOException, UnpackableException {
        // blocks out of order
        StringReader sr =new StringReader(
                "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void blockMissing() throws IOException, UnpackableException {
        // should be three blocks
        StringReader sr =new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void blockTitleNoBracket() throws IOException, UnpackableException {
        // blocks not []
        StringReader sr =new StringReader(
                "Board"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void blockTitleWrong() throws IOException, UnpackableException {
        // block names should be [Board], [Score], [Game]
        StringReader sr =new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Gamme]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void skipComments() throws IOException, UnpackableException {
        // comments should be skipped (if pass they are not)
        StringReader sr =new StringReader(
                ";this is a comment"
                        + System.lineSeparator()
                        + "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "; this is another comment"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void lineBeforeBoardTest() throws IOException, UnpackableException {
        // space before first block
        StringReader sr =new StringReader(
                System.lineSeparator()
                        + "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    // missing blank line between
    @Test(expected = UnpackableException.class)
    public void lineMissingBoardGameTest() throws IOException, UnpackableException {
        // check exception raised if no space between board and game
        StringReader sr =new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }



    @Test(expected = UnpackableException.class)
    public void lineMissingGameScoreTest() throws IOException, UnpackableException {
        // check exception raised if no blank line between game and score
        StringReader sr =new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void lineExtraGameScoreTest() throws IOException, UnpackableException {
        // check exception raised if extra line between game and score
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void lineExtraBoardGameTest() throws IOException, UnpackableException {
        // check exception raised if extra line between board and game
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void lineNotBlankBoardGameTest() throws IOException, UnpackableException {
        // check exception raised if line between not blank
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "hi"
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void lineNotBlankGameScoreTest() throws IOException, UnpackableException {
        // check exception raised if line between not blank
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + "hi"
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    /**
     * [Board] - Invalid data - throw unpackable exception for:
     * - extra dimension
     * - missing dimension
     * - wrong dimension format
     * - missing row
     * - missing column
     * - not all boarditems
     * - board extra blank line
     * - board same line
     * - board extra row or column
     */
    @Test(expected = UnpackableException.class)
    public void boardExtraDimensionTest() throws IOException, UnpackableException {
        // check exception raised if more than int,int in first line
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4,6"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void boardMissingDimensionTest() throws IOException, UnpackableException {
        // check exception raised if not value for both width and height
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }



    @Test(expected = UnpackableException.class)
    public void boardDimensionFormatTest() throws IOException, UnpackableException {
        // Board not seperated by comma
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5 4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void boardMissingRowTest() throws IOException, UnpackableException {
        //  There must be (height) lines of (width) following the first line.
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void boardMissingColumnTest() throws IOException, UnpackableException {
        //  There must be (height) lines of (width) following the first line.
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXX"
                        + System.lineSeparator()
                        + "X000"
                        + System.lineSeparator()
                        + "X000"
                        + System.lineSeparator()
                        + "XXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void boardItemMatchTest() throws IOException, UnpackableException {
        //  Unpackable if not all BoardItem keys
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X0S0X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void boardExtraRowTest() throws IOException, UnpackableException {
        // check exception raised if more height of rows
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void boardExtraColumnTest() throws IOException, UnpackableException {
        // check exception raised if more width of cols
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXXX"
                        + System.lineSeparator()
                        + "X000XX"
                        + System.lineSeparator()
                        + "X000XX"
                        + System.lineSeparator()
                        + "XXXXXX"
                        + System.lineSeparator()
                        + "XXXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void boardExtraLineTest() throws IOException, UnpackableException {
        // check exception raised if extra line in block
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    /**
     *
     [Game] - data invalid - throw unpackable exception
     - missing assignment
     - key value order wrong
     - key extra space
     - value extra space
     - key missing space
     - value missing space
     - extra blank line
     - assignments same line
     - wrong assignment separator
     - lives not an integer
     - lives not positive
     - level not an integer
     - level not positive
     - ghost not valid direction
     - ghost phase format
     - ghost phase type format
     - ghost phase duration integer
     - ghost separator wrong
     - ghost format order
     - ghost position integer
     - hunter direction
     - hunter position
     - hunter separator
     - hunter duration integer
     - hunter duration positive
     - hunter type format
     - hunter format order
     */
    @Test(expected = UnpackableException.class)
    public void gameMissingAssignmentTest() throws IOException, UnpackableException {
        //  All game assignments mandatory
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameKeyValueOrderTest() throws IOException, UnpackableException {
        //  Must be key then value
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "4,3,UP,SCATTER:123456789 = pinky"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameKeyExtraSpaceTest() throws IOException, UnpackableException {
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky  = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameValueExtraSpaceTest() throws IOException, UnpackableException {
        //  Extra whitespace
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky =  4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameKeyMissingSpaceTest() throws IOException, UnpackableException {
        //  Missing whitespace
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky= 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameValueMissingSpaceTest() throws IOException, UnpackableException {
        //  Extra whitespace
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky =4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameExtraLineTest() throws IOException, UnpackableException {
        //  Additional lines
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameSameLineTest() throws IOException, UnpackableException {
        //  assignments not on next line
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameWrongSeparatorTest() throws IOException, UnpackableException {
        //  assignments not separated by " = "
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky : 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 10000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameLivesIntegerTest() throws IOException, UnpackableException {
        //  lives and level and score not integer or >=0
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = hello"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameLivesPositiveTest() throws IOException, UnpackableException {
        //  lives and level and score not integer or >=0
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = -500"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameLevelIntegerTest() throws IOException, UnpackableException {
        //  lives and level and score not integer or >=0
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = hi"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameLevelPositiveTest() throws IOException, UnpackableException {
        //  lives and level and score not integer or >=0
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = -500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostDirectionTest() throws IOException, UnpackableException {
        //  toString of Direction
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,up,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostPhaseFormat() throws IOException, UnpackableException {
        //  as per toString
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER,123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostPhaseTypeFormat() throws IOException, UnpackableException {
        //  toString
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,scatter:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostPhaseDurationIntegerTest() throws IOException, UnpackableException {
        //  phase duration should be integer
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:hi"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameGhostPhaseDurationPositiveTest() throws IOException, UnpackableException {
        //  phase duration should be greater than 0
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:-20"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostSeparatorTest() throws IOException, UnpackableException {
        //  separated by ";"
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4 3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostFormatOrderTest() throws IOException, UnpackableException {
        //  needs to be in order
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,SCATTER:123456789, UP"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostPositionIntegerTest() throws IOException, UnpackableException {
        //  toString
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = hi,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameGhostPositionBounds() throws IOException, UnpackableException {
        //  toString
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 5,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameHunterDirection() throws IOException, UnpackableException {
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,down,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameHunterPositionIntegerTest() throws IOException, UnpackableException {
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = h,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameHunterSeparatorTest() throws IOException, UnpackableException {
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3 DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameHunterFormatOrderTest() throws IOException, UnpackableException {
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,SPEEDY,750"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameHunterDurationIntegerTest() throws IOException, UnpackableException {
        //  phase duration should be integer
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,hi,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }


    @Test(expected = UnpackableException.class)
    public void gameHunterDurationPositiveTest() throws IOException, UnpackableException {
        //  phase duration should be integer
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,-10,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameHunterTypeFormatTest() throws IOException, UnpackableException {
        //  phase duration should be integer
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,BLINKY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void gameHunterPositionBounds() throws IOException, UnpackableException {
        //  toString
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 5,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    /**
     * [Score] - data invalid - throw unpackable exception for:
     * - score value integer
     * - score separator
     * - score same line
     * - score wrong order
     * - score unique
     *
     */
    @Test(expected = UnpackableException.class)
    public void scoreValueIntegerTest() throws IOException, UnpackableException {
        //  value must be an integer
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : Hello"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void scoreSeparatorTest() throws IOException, UnpackableException {
        //  should be NAME : VALUE
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred = 5"
                        + System.lineSeparator()
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void scoreSameLine() throws IOException, UnpackableException {
        //  doesnt match getEntriesByName
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + "fred : 1000000");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void scoreWrongOrderTest() throws IOException, UnpackableException {
        //  doesnt match getEntriesByName
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "fred : 1000000"
                        + System.lineSeparator()
                        + "Fred : 5");
        gr.read(sr);
        sr.close();
    }

    @Test(expected = UnpackableException.class)
    public void scoreUniqueTest() throws IOException, UnpackableException {
        StringReader sr = new StringReader(
                "[Board]"
                        + System.lineSeparator()
                        + "5,4"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "X000X"
                        + System.lineSeparator()
                        + "XXXXX"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Game]"
                        + System.lineSeparator()
                        + "pinky = 4,3,UP,SCATTER:123456789"
                        + System.lineSeparator()
                        + "author = This is the author of this game, it is long"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test the limit"
                        + System.lineSeparator()
                        + "lives = 1000"
                        + System.lineSeparator()
                        + "inky = 3,3,RIGHT,CHASE:1068"
                        + System.lineSeparator()
                        + "hunter = 2,3,DOWN,750,SPEEDY"
                        + System.lineSeparator()
                        + "clyde = 2,1,LEFT,FRIGHTENED:0"
                        + System.lineSeparator()
                        + "score = 123456789"
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "[Scores]"
                        + System.lineSeparator()
                        + "Fred : 5"
                        + System.lineSeparator()
                        + "fred : 500"
                        + System.lineSeparator()
                        + "fred : 1000000"
        );
        gr.read(sr);
        sr.close();
    }


    /**
     * Can't read file
     * @thows IOException
     */

    @Test(expected = IOException.class)
    public void fileNotExist() throws IOException, UnpackableException {
        // cant read file
        FileReader fr = new FileReader("test/fileNotExist.map");
        gr.read(fr);
        fr.close();
    }
}