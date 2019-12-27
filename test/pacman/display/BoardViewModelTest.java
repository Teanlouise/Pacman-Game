package pacman.display;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.ghost.Ghost;
import pacman.hunter.Hungry;
import pacman.util.Direction;
import pacman.util.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardViewModelTest {

    // Positions used for testing
    private Position hunterStart;
    private Position hunterSpawn;
    private Position hunterHit;
    private Position ghostsSpawn;
    private Position bigDot;
    private Position hunterEat;

    // variables to setup BoardViewModel
    private PacmanBoard board;
    private Hungry hunter;
    private PacmanGame model;
    private BoardViewModel boardVM;

    @Before
    public void setUp() {
        // setup positions for testing
        this.hunterStart = new Position(8,9);
        this.hunterSpawn = new Position(10,12);
        this.hunterHit = new Position(4,5);
        this.ghostsSpawn = new Position(4,4);
        this.bigDot = new Position(1,1);
        this.hunterEat = new Position(1,2);

        // Must initialise before game and boardModel
        // - board and its dimensions
        this.board = new PacmanBoard(14,15);
        // - hunter and its starting position and spawn
        this.hunter = new Hungry();
        hunter.setPosition(hunterStart);
        board.setEntry(hunterSpawn, BoardItem.PACMAN_SPAWN);
        // - ghosts spawn location (also their starting position)
        board.setEntry(ghostsSpawn, BoardItem.GHOST_SPAWN);
        // - one dot on the board for first level
        board.setEntry(bigDot, BoardItem.BIG_DOT);

        // Now can initialise game and BoardModelView
        this.model = new PacmanGame("Title", "Author", hunter, board);
        this.boardVM = new BoardViewModel(model);
    }

    @Test
    public void getLivesTest() {
        // Default lives
        assertEquals(4, boardVM.getLives());

        // Set Lives
        model.setLives(20);
        assertEquals(20, boardVM.getLives());

        // Lose a life (set hunter to one away from GhostSpawn)
        hunter.setPosition(hunterHit);
        model.tick();
        assertEquals(19, boardVM.getLives());
    }

    @Test
    public void getLevelTest() {
        // Default level
        assertEquals(0, boardVM.getLevel());

        // Set level
        model.setLevel(15);
        assertEquals(15, boardVM.getLevel());

        // Increase level (set hunter position one away from only dot)
        hunter.setPosition(hunterEat);
        model.tick();
        assertEquals(16, boardVM.getLevel());
    }

    @Test
    public void getPacmanColourTest() {
        // Default colour
        assertEquals("#FFE709", boardVM.getPacmanColour());

        // Activate special
        hunter.activateSpecial(1);
        assertEquals("#CDC3FF", boardVM.getPacmanColour());

        // Special duration now 0, back to default
        model.tick();
        assertEquals("#FFE709", boardVM.getPacmanColour());
    }

    @Test
    public void getPacmanMouthAngleTest() {
        // UP (default)
        assertEquals(120, boardVM.getPacmanMouthAngle());

        // LEFT
        hunter.setDirection(Direction.LEFT);
        assertEquals(210, boardVM.getPacmanMouthAngle());

        // DOWN
        hunter.setDirection(Direction.DOWN);
        assertEquals(300, boardVM.getPacmanMouthAngle());

        // RIGHT
        hunter.setDirection(Direction.RIGHT);
        assertEquals(30, boardVM.getPacmanMouthAngle());
    }

    @Test
    public void getPacmanPositionTest() {
        // Starting position for test game
        assertEquals(hunterStart, boardVM.getPacmanPosition());

        // Move hunter to one away from ghostspawn
        hunter.setPosition(hunterHit);
        assertEquals(hunterHit, boardVM.getPacmanPosition());

        // Hunter dies, should be at pacmanSpawn location
        model.setLives(1);
        model.tick();
        assertEquals(hunterSpawn, boardVM.getPacmanPosition());
    }

    @Test
    public void getBoardTest() {
        PacmanBoard expectedBoard = new PacmanBoard(14,15);
        expectedBoard.setEntry(hunterSpawn, BoardItem.PACMAN_SPAWN);
        expectedBoard.setEntry(ghostsSpawn, BoardItem.GHOST_SPAWN);
        expectedBoard.setEntry(bigDot, BoardItem.BIG_DOT);

        // Check dimensions
        assertEquals(15, boardVM.getBoard().getHeight());
        assertEquals(14, boardVM.getBoard().getWidth());

        // Compare with starting 'board'
        assertEquals(ghostsSpawn, boardVM.getBoard().getGhostSpawn());
        assertEquals(hunterSpawn, boardVM.getBoard().getPacmanSpawn());
        assertEquals(BoardItem.BIG_DOT, boardVM.getBoard().getEntry(bigDot));
        assertEquals(true, expectedBoard.equals(boardVM.getBoard()));

        // Increase level
        hunter.setPosition(hunterEat);
        model.tick();
        // Reset 'board' can be used as expected
        expectedBoard.reset();
        // Check board has updated to match expected
        assertEquals(ghostsSpawn, boardVM.getBoard().getGhostSpawn());
        assertEquals(hunterSpawn, boardVM.getBoard().getPacmanSpawn());
        assertEquals(BoardItem.BIG_DOT, boardVM.getBoard().getEntry(bigDot));
        assertEquals(true, expectedBoard.equals(boardVM.getBoard()));
    }

    @Test
    public void getGhostsTest() {
        // Initialise unique position for each ghost type
        Position blinky = new Position(1, 2);
        Position inky = new Position(5, 0);
        Position clyde = new Position(10, 6);
        Position pinky = new Position(4, 11);

        // Set each ghost to position
        for (Ghost g : model.getGhosts()) {
            switch (g.getType()) {
                case BLINKY:
                    g.setPosition(blinky);
                    break;
                case INKY:
                    g.setPosition(inky);
                    break;
                case CLYDE:
                    g.setPosition(clyde);
                    break;
                case PINKY:
                    g.setPosition(pinky);
                    break;
            }
        }

        // Get List of pairs of ghosts from board model
        List<Pair<Position, String>> ghostList = boardVM.getGhosts();

        // Check there are exactly 4 ghosts in the list
        assertEquals(4, ghostList.size());

        // TODO: 11/27/19 Check that has one of each of the ghosts (test board4 passed with same ghost)
//        for (int i = 0; i < model.getGhosts().size(); i++) {
//            Ghost ghost = model.getGhosts().get(0);
//            Pair<Position, String> info;
//            if (ghost.getPhase() == Phase.FRIGHTENED) {


        // Check that each ghost matches its position and colour
        for (Pair<Position, String> g : ghostList) {
            Position ghost = g.getKey();
            if (ghost.equals(blinky)) {
                assertEquals("#d54e53", g.getValue());
            } else if (ghost.equals(inky)) {
                assertEquals("#7aa6da", g.getValue());
            } else if (ghost.equals(clyde)) {
                assertEquals("#e78c45", g.getValue());
            } else if (ghost.equals(pinky)) {
                assertEquals("#c397d8", g.getValue());
            } else {
                // Unexpected position
                fail();
            }
        }

        // Check colour changes when frightened
        model.setGhostsFrightened();
        for (Pair<Position, String> g : boardVM.getGhosts()) {
            assertEquals("#0000FF",g.getValue());
        }
    }
}