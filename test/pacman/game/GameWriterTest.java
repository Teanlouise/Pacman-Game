package pacman.game;

import org.junit.Before;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.GameReader;
import pacman.game.GameWriter;
import pacman.game.PacmanGame;
import pacman.ghost.Ghost;
import pacman.ghost.Phase;
import pacman.hunter.Phil;
import pacman.hunter.Speedy;
import pacman.score.ScoreBoard;
import pacman.util.Direction;
import pacman.util.Position;
import pacman.util.UnpackableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class GameWriterTest {
    private GameWriter gw;
    private StringWriter sw;

    // map one
    private PacmanBoard mapOneBoard;
    private PacmanGame mapOneGame;
    private ScoreBoard mapOneScores;
    private Phil gameOneHunter;

    // map two
    private Speedy mapTwoHunter;
    private PacmanBoard mapTwoBoard;
    private PacmanGame mapTwoGame;

    @Before
    public void setUp() throws Exception {
        gw = new GameWriter();

        // MAP 1
        gameOneHunter = new Phil();
        mapOneBoard = new PacmanBoard(25, 9);
        mapOneGame = new PacmanGame(
                "Default CSSE2002 PacMan Map",
                "Evan Hughes",
                gameOneHunter,
                mapOneBoard);

        // MAP 2
        mapTwoHunter = new Speedy();
        mapTwoBoard = new PacmanBoard(5, 4);
        mapTwoGame = new PacmanGame(
                "This is a very long title that to test there is no limit on the number of characters in the title",
                "This is the author of this game, and they have a very long name",
                mapTwoHunter,
                mapTwoBoard);
    }

    /**
     * Set conditions for map one and write it.
     * Then test with StringReader that all conditions are output correctly.
     * @throws IOException if file cannot be read
     */
    private void mapOneSetup() throws IOException {
        // [Board]
        mapOneBoard.setEntry(new Position(1,1), BoardItem.DOT);
        mapOneBoard.setEntry(new Position(6,2), BoardItem.BIG_DOT);
        mapOneBoard.setEntry(new Position(12,2), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(19,2), BoardItem.BIG_DOT);
        mapOneBoard.setEntry(new Position(12,3), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(8,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(9,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(10,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(11,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(12,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(13,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(14,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(15,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(16,4), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(12,5), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(6,6), BoardItem.BIG_DOT);
        mapOneBoard.setEntry(new Position(19,6), BoardItem.BIG_DOT);
        mapOneBoard.setEntry(new Position(1,7), BoardItem.PACMAN_SPAWN);
        mapOneBoard.setEntry(new Position(12,7), BoardItem.WALL);
        mapOneBoard.setEntry(new Position(23,7), BoardItem.GHOST_SPAWN);
        //[Game]
        mapOneGame.setLives(5);
        mapOneGame.setLevel(2);
        mapOneGame.getScores().increaseScore(123);
        gameOneHunter.setPosition(new Position(1, 1));
        gameOneHunter.setDirection(Direction.LEFT);
        gameOneHunter.activateSpecial(20);
        for (Ghost g : mapOneGame.getGhosts()) {
            switch (g.getType()) {
                case BLINKY:
                    g.setPosition(new Position(3, 6));
                    g.setPhase(Phase.FRIGHTENED, 15);
                    break;
                case INKY:
                    g.setPosition(new Position(1, 6));
                    g.setPhase(Phase.SCATTER, 7);
                    break;
                case PINKY:
                    g.setPosition(new Position(8, 6));
                    g.setPhase(Phase.FRIGHTENED, 15);
                    break;
                case CLYDE:
                    g.setPosition(new Position(6, 4));
                    g.setPhase(Phase.CHASE, 4);
                    break;
            }
        }
        //[Score]
        mapOneScores = mapOneGame.getScores();
        mapOneScores.setScore("A", 0);
        mapOneScores.setScore("B", 5);
        mapOneScores.setScore("C", 100);

        sw = new StringWriter();
        gw.write(sw, mapOneGame);
        sw.close();
    }

    @Test
    public void mapOneTest() throws IOException {
        mapOneSetup();
        BufferedReader br = new BufferedReader(new StringReader(sw.toString()));
        assertEquals("[Board]", br.readLine());
        assertEquals("25,9", br.readLine());
        assertEquals("XXXXXXXXXXXXXXXXXXXXXXXXX", br.readLine());
        assertEquals("X10000000000000000000000X", br.readLine());
        assertEquals("X00000B00000X000000B0000X", br.readLine());
        assertEquals("X00000000000X00000000000X", br.readLine());
        assertEquals("X0000000XXXXXXXXX0000000X", br.readLine());
        assertEquals("X00000000000X00000000000X", br.readLine());
        assertEquals("X00000B000000000000B0000X", br.readLine());
        assertEquals("XP0000000000X0000000000$X", br.readLine());
        assertEquals("XXXXXXXXXXXXXXXXXXXXXXXXX", br.readLine());
        assertEquals("", br.readLine());
        assertEquals("[Game]", br.readLine());
        assertEquals("title = Default CSSE2002 PacMan Map", br.readLine());
        assertEquals("author = Evan Hughes", br.readLine());
        assertEquals("lives = 5", br.readLine());
        assertEquals("level = 2", br.readLine());
        assertEquals("score = 123", br.readLine());
        assertEquals("hunter = 1,1,LEFT,20,PHIL", br.readLine());
        assertEquals("blinky = 3,6,UP,FRIGHTENED:15", br.readLine());
        assertEquals("inky = 1,6,UP,SCATTER:7", br.readLine());
        assertEquals("pinky = 8,6,UP,FRIGHTENED:15", br.readLine());
        assertEquals("clyde = 6,4,UP,CHASE:4", br.readLine());
        assertEquals("", br.readLine());
        assertEquals("[Scores]", br.readLine());
        assertEquals("A : 0", br.readLine());
        assertEquals("B : 5", br.readLine());
        assertEquals("C : 100", br.readLine());
        assertEquals(null, br.readLine());
        br.close();
    }

    /**
     * Set conditions for map two and write it.
     * Then test with StringReader that all conditions are output correctly.
     * Then test with GameReader to check that both work correctly.
     * @throws IOException if file cannot be read
     */
    private void mapTwoSetUp() throws IOException {
        //[Game]
        mapTwoGame.setLives(10000);
        mapTwoGame.setLevel(500);
        mapTwoGame.getScores().increaseScore(123456789);
        mapTwoHunter.setPosition(new Position(2, 3));
        mapTwoHunter.setDirection(Direction.DOWN);
        mapTwoHunter.activateSpecial(750);
        for (Ghost g : mapTwoGame.getGhosts()) {
            switch (g.getType()) {
                case BLINKY:
                    g.setPosition(new Position(1, 2));
                    g.setDirection(Direction.DOWN);
                    g.setPhase(Phase.CHASE, 15);
                    break;
                case INKY:
                    g.setPosition(new Position(3, 4));
                    g.setPhase(Phase.CHASE, 1068);
                    g.setDirection(Direction.RIGHT);
                    break;
                case PINKY:
                    g.setPhase(Phase.SCATTER, 123456789);
                    g.setPosition(new Position(4, 3));
                    g.setDirection(Direction.UP);
                    break;
                case CLYDE:
                    g.setDirection(Direction.LEFT);
                    g.setPhase(Phase.FRIGHTENED, 0);
                    g.setPosition(new Position(2, 1));
                    break;
            }
        }
        //[Score]
        ScoreBoard mapTwoScores = mapTwoGame.getScores();
        // check being written as per getEntriesByName
        // (third entry will not print as invalid and leading zero removed)
        mapTwoScores.setScore("Fred", 05);
        mapTwoScores.setScore("fred", 1000000);
        mapTwoScores.setScore("Hello my name is long", 100);

        // write file
        sw = new StringWriter();
        gw.write(sw, mapTwoGame);
        sw.close();
    }

    @Test
    public void mapTwoTest() throws IOException {
        mapTwoSetUp();
        BufferedReader br = new BufferedReader(new StringReader(sw.toString()));
        assertEquals("[Board]", br.readLine());
        assertEquals("5,4", br.readLine());
        assertEquals("XXXXX", br.readLine());
        assertEquals("X000X", br.readLine());
        assertEquals("X000X", br.readLine());
        assertEquals("XXXXX", br.readLine());
        assertEquals("", br.readLine());
        assertEquals("[Game]", br.readLine());
        assertEquals("title = This is a very long title that to test there is no limit on the number of characters in the title", br.readLine());
        assertEquals("author = This is the author of this game, and they have a very long name", br.readLine());
        assertEquals("lives = 10000", br.readLine());
        assertEquals("level = 500", br.readLine());
        assertEquals("score = 123456789", br.readLine());
        assertEquals("hunter = 2,3,DOWN,750,SPEEDY", br.readLine());
        assertEquals("blinky = 1,2,DOWN,CHASE:15", br.readLine());
        assertEquals("inky = 3,4,RIGHT,CHASE:1068", br.readLine());
        assertEquals("pinky = 4,3,UP,SCATTER:123456789", br.readLine());
        assertEquals("clyde = 2,1,LEFT,FRIGHTENED:0", br.readLine());
        assertEquals("", br.readLine());
        assertEquals("[Scores]", br.readLine());
        assertEquals("Fred : 5", br.readLine());
        assertEquals("fred : 1000000", br.readLine());
        assertEquals(null, br.readLine());
        br.close();
    }

    @Test
    public void mapTwoReadTest() throws IOException, UnpackableException {
        mapTwoSetUp();
        // Use GameReader and GameWriter together
        GameReader gr = new GameReader();

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
                        + "author = This is the author of this game, and they have a very long name"
                        + System.lineSeparator()
                        + "blinky = 1,2,DOWN,CHASE:15"
                        + System.lineSeparator()
                        + "level = 500"
                        + System.lineSeparator()
                        + "title = This is a very long title that to test there is no limit on the number of characters in the title"
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

        PacmanGame readMapTwo = gr.read(sr);

        assertEquals(5, readMapTwo.getBoard().getWidth());
        assertEquals(4, readMapTwo.getBoard().getHeight());

        String mapTwoBoardString = (
                "XXXXX" + System.lineSeparator()
                        +"X000X"+ System.lineSeparator()
                        + "X000X" + System.lineSeparator()
                        + "XXXXX");

        assertEquals(mapTwoBoardString, readMapTwo.getBoard().toString());
        assertEquals("This is a very long title that to test there is no limit on the number of characters in the title", readMapTwo.getTitle());
        assertEquals("This is the author of this game, and they have a very long name", readMapTwo.getAuthor());
        assertEquals(10000, readMapTwo.getLives());
        assertEquals(500, readMapTwo.getLevel());
        assertEquals(123456789, readMapTwo.getScores().getScore());
        assertEquals("2,3,DOWN,750,SPEEDY", readMapTwo.getHunter().toString());
        for (Ghost g : readMapTwo.getGhosts()) {
            switch(g.getType()) {
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
        assertEquals("[Fred : 5, fred : 1000000]", readMapTwo.getScores().getEntriesByName().toString());
    }
}