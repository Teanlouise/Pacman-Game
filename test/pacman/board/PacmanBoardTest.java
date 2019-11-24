package pacman.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pacman.util.Position;

import static org.junit.Assert.*;

public class PacmanBoardTest {
    /**
     * ASSIGNMENT ONE
     */
    // Generic positions
    private Position positionTopLeft;
    private Position positionOneOne;
    private Position positionTwoThree;

    // Regular board variables
    private PacmanBoard board;
    private Position positionBottomRightBoard;
    private Position positionBottomLeftBoard;
    private Position positionTopRightBoard;
    private Position positionMiddleBoard;
    private BoardItem[][] boardDefaultExpected;
    private BoardItem[][] boardResetExpected;

    // Min board variables
    private PacmanBoard boardOne;
    private Position positionBottomRightBoardOne;

    /**
     * ASSIGNMENT TWO
     */
    private PacmanBoard boardTestOne;
    private PacmanBoard boardTestTwo;
    private String boardTestOneExpectedString;
    private PacmanBoard boardTestThree;

    @Before
    public void setUp() throws Exception {
        positionTopLeft = new Position(0,0);
        positionOneOne = new Position(1,1);
        positionTwoThree = new Position(2,3);

        boardTestOne = new PacmanBoard(1,1);
        positionBottomRightBoardOne = new Position(boardTestOne.getWidth()-1, boardTestOne.getHeight()-1);

        board = new PacmanBoard(4,5);
        positionBottomRightBoard = new Position(board.getWidth()-1, board.getHeight()-1);
        positionTopRightBoard = new Position(3,0);
        positionBottomLeftBoard = new Position(0,4);
        positionMiddleBoard = new Position(1,2);
        boardDefaultExpected = new BoardItem[][]{
                {BoardItem.WALL, BoardItem.WALL,BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL}
        };
        boardResetExpected = new BoardItem[][]{
                {BoardItem.WALL,BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL}};

        /**
         * ASSIGNMENT TWO
         */
        boardTestOne = new PacmanBoard(4,5);
        boardTestTwo = new PacmanBoard(4,5);
        boardTestThree = new PacmanBoard(5,4);
        boardTestOneExpectedString = new String(
                "XXXX" + System.lineSeparator()
                        +"X00X"+ System.lineSeparator()
                        + "X00X" + System.lineSeparator()
                        +"X00X" + System.lineSeparator()
                        + "XXXX");

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
     * Pacman Board Constructor tests
     */
    @Test
    public void PacmanBoardConstructorAllPositiveTest() {
        // Check for height positive and width positive
        assertEquals(true, compareBoards(board, boardDefaultExpected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void PacmanBoardConstructorZeroTest() {
        // Check for height zero and width zero
        PacmanBoard boardA = new PacmanBoard(0, 0);
        // check for height zero and width positive
        PacmanBoard boardB = new PacmanBoard(5, 0);
        // Check for height zero and width negative
        PacmanBoard boardC = new PacmanBoard(-20, 0);
        // check for height positive and width zero
        PacmanBoard boardD = new PacmanBoard(0, 50);
        // Check for height negative and width zero
        PacmanBoard boardE = new PacmanBoard(0, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void PacmanBoardConstructorNegativeTest() {
        // Check for height negative and width negative
        PacmanBoard boardA = new PacmanBoard(-1, -100);
        // check for height negative and width positive
        PacmanBoard boardB = new PacmanBoard(5, -100);
        // check for height positive and width negative
        PacmanBoard boardC = new PacmanBoard(-5, 100);
    }

    @Test(expected = NullPointerException.class)
    public void PacmanBoardCopyNullTest() {
         // Check when other is null
        PacmanBoard board = new PacmanBoard(null);
    }

    @Test
    public void PacmanBoardCopyDefaultTest() {
        // Check when other is valid
        PacmanBoard boardCopy = new PacmanBoard(board);
        assertEquals(4, boardCopy.getWidth());
        assertEquals(5, boardCopy.getHeight());
        assertEquals(true, compareBoards(boardCopy, boardDefaultExpected));
    }

    @Test
    public void PacmanBoardCopyItemsTest() {
        BoardItem[][] boardExpected = new BoardItem[][]{
                {BoardItem.NONE, BoardItem.WALL, BoardItem.WALL, BoardItem.BIG_DOT},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.GHOST_SPAWN, BoardItem.NONE, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.NONE, BoardItem.PACMAN_SPAWN, BoardItem.WALL},
                {BoardItem.DOT, BoardItem.WALL, BoardItem.WALL, BoardItem.BIG_DOT_SPAWN}};
        // Add all items
        board.setEntry(positionTopLeft, BoardItem.NONE);
        board.setEntry(positionOneOne, BoardItem.WALL);
        board.setEntry(positionBottomLeftBoard, BoardItem.DOT);
        board.setEntry(positionTwoThree, BoardItem.PACMAN_SPAWN);
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        board.setEntry(positionTopRightBoard, BoardItem.BIG_DOT);
        board.setEntry(positionBottomRightBoard, BoardItem.BIG_DOT_SPAWN);
        // Copy the board
        PacmanBoard boardCopy = new PacmanBoard(board);
        // Check matches
        assertEquals(4, boardCopy.getWidth());
        assertEquals(5, boardCopy.getHeight());
        assertEquals(true, compareBoards(boardCopy, boardExpected));
    }

    @Test
    public void PacmanBoardCopyModifyOriginalTest() {
        PacmanBoard boardCopy = new PacmanBoard(board);
        // Check when change original no change to copy
        board.reset();
        assertNotEquals(boardDefaultExpected, boardResetExpected);
        assertEquals(true, compareBoards(boardCopy, boardDefaultExpected));
    }

    /**
     * setEntry tests
     */
    @Test
    public void setEntryOnePacmanSpawnTest() {
        // place pacman spawn and check
        board.setEntry(positionMiddleBoard, BoardItem.PACMAN_SPAWN);
        assertEquals(BoardItem.PACMAN_SPAWN, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryMultiplePacmanSpawnTest() {
        // Add two and check second is placed and first changed to NONE
        board.setEntry(positionMiddleBoard, BoardItem.PACMAN_SPAWN);
        board.setEntry(positionTwoThree, BoardItem.PACMAN_SPAWN);
        assertEquals(BoardItem.PACMAN_SPAWN, board.getEntry(positionTwoThree));
        assertEquals(BoardItem.NONE, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryOneGhostSpawnTest() {
        /// Place ghost spawn and check
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        assertEquals(BoardItem.GHOST_SPAWN, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryMultipleGhostSpawnTest() {
        // Add two and check second is placed and first changed to NONE
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        board.setEntry(positionOneOne, BoardItem.GHOST_SPAWN);
        assertEquals(BoardItem.GHOST_SPAWN, board.getEntry(positionOneOne));
        assertEquals(BoardItem.NONE, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryDotTest() {
        // Add multiple dots and check both exists
        board.setEntry(positionMiddleBoard, BoardItem.DOT);
        board.setEntry(positionOneOne, BoardItem.DOT);
        assertEquals(BoardItem.DOT, board.getEntry(positionOneOne));
        assertEquals(BoardItem.DOT, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryBigDotTest() {
        // Add BIG Dots and check both exists
        board.setEntry(positionMiddleBoard, BoardItem.BIG_DOT);
        board.setEntry(positionOneOne, BoardItem.BIG_DOT);
        assertEquals(BoardItem.BIG_DOT, board.getEntry(positionOneOne));
        assertEquals(BoardItem.BIG_DOT, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryBigDotSpawnTest() {
        // Add multiple and check both exists
        board.setEntry(positionMiddleBoard, BoardItem.BIG_DOT_SPAWN);
        // Add another
        board.setEntry(positionOneOne, BoardItem.BIG_DOT_SPAWN);
        // Check both exist
        assertEquals(BoardItem.BIG_DOT_SPAWN, board.getEntry(positionOneOne));
        assertEquals(BoardItem.BIG_DOT_SPAWN, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryNone() {
        // Add NONE and check both exists
        board.setEntry(positionMiddleBoard, BoardItem.NONE);
        board.setEntry(positionOneOne, BoardItem.NONE);
        assertEquals(BoardItem.NONE, board.getEntry(positionOneOne));
        assertEquals(BoardItem.NONE, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void setEntryWall() {
        // Add walls and check both exist
        board.setEntry(positionMiddleBoard, BoardItem.WALL);
        board.setEntry(positionOneOne, BoardItem.WALL);
        assertEquals(BoardItem.WALL, board.getEntry(positionOneOne));
        assertEquals(BoardItem.WALL, board.getEntry(positionMiddleBoard));
    }

    @Test(expected = NullPointerException.class)
    public void setEntryAllNullTest() {
        // check if position null and item null
        board.setEntry(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void setEntryPositionNullTest() {
        // check if position null and item valid
        board.setEntry(null, BoardItem.NONE);
    }

    @Test(expected = NullPointerException.class)
    public void setEntryItemNullTest() {
        // check if position valid and item null
        board.setEntry(positionMiddleBoard, null);
    }

    @Test
    public void entryMinBoardTest() {
        // check top left
        boardTestOne.setEntry(positionTopLeft, BoardItem.BIG_DOT);
        assertEquals(BoardItem.BIG_DOT, boardTestOne.getEntry(positionTopLeft));
        // check bottom right
        boardTestOne.setEntry(positionBottomRightBoardOne, BoardItem.DOT);
        assertEquals(BoardItem.DOT, boardTestOne.getEntry(positionBottomRightBoardOne));
        // should be the same as topLeft
        assertEquals(BoardItem.DOT, boardTestOne.getEntry(positionTopLeft));
    }

    @Test
    public void setEntryPositionTopLeftTest() {
        // check (0,0) of normal board or min bounds
        board.setEntry(positionTopLeft, BoardItem.DOT);
        assertEquals(BoardItem.DOT, board.getEntry(positionTopLeft));
    }

    @Test
    public void setEntryPositionBottomRightTest() {
        // check sets at (width-1, height-1) or max bounds
        board.setEntry(positionBottomRightBoard, BoardItem.DOT);
        assertEquals(BoardItem.DOT, board.getEntry(positionBottomRightBoard));
        // should be same 'board' position as (3,4)
        assertEquals(BoardItem.DOT, board.getEntry(new Position(3,4)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setEntryPositionHeightInvalidTest() {
        // check doesn’t set when height out of bounds
        board.setEntry((new Position(2,6)), BoardItem.DOT);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setEntryPositionWidthInvalidTest() {
        // check doesn’t set when width out of bounds
        board.setEntry((new Position(5,2)), BoardItem.DOT);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setEntryPositionAllInvalidTest() {
        // check doesn’t set when height and width out of bounds
        board.setEntry((new Position(5,6)), BoardItem.DOT);
    }

    /**
    * getEntry tests
    */

    @Test(expected = NullPointerException.class)
    public void getEntryPositionNullTest() {
        // check when positon null
        board.getEntry(null);
    }

    @Test
    public void getEntryTopLeftTest() {
        // check gets at (0,0) or min bounds
        assertEquals(BoardItem.WALL, board.getEntry(positionTopLeft));
    }

    @Test
    public void getEntryBottomRightTest() {
        // check gets at (width-1, height-1) or max bounds
        assertEquals(BoardItem.WALL, board.getEntry(positionBottomRightBoard));
        // should be same 'board' position as (3,4)
        assertEquals(board.getEntry(positionBottomRightBoard), board.getEntry(new Position(3,4)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getEntryHeightInvalidTest() {
        // check doesn’t set when height out of bounds
        board.getEntry(new Position(2,6));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getEntryWidthInvalidTest() {
        // check doesn’t get when width out of bounds
        Position position = new Position(4,2);
        board.getEntry(position);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getEntryAllInvalidTest() {
    // check doesn’t get when height and width out of bounds
        board.getEntry(new Position(5,6));
    }

    /**
     * reset tests
     */

    @Test
    public void resetDefaultTest() {
        // reset board without anything changed i.e. no items
        board.reset();
        assertEquals(true, compareBoards(board, boardResetExpected));
    }

    @Test
    public void resetWallTest() {
        // Add extra wall, reset and check wall is left untouched
        board.setEntry(positionMiddleBoard, BoardItem.WALL);
        board.reset();
        assertEquals(BoardItem.WALL, board.getEntry(positionMiddleBoard));

        // check whole array
        BoardItem[][] boardExpected = new BoardItem[][]{
                {BoardItem.WALL,BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL}};
        assertEquals(true, compareBoards(board, boardExpected));
    }

    @Test
    public void resetPacmanSpawnTest() {
        // Add pacmanspawn, reset and check left untouched
        board.setEntry(positionMiddleBoard, BoardItem.PACMAN_SPAWN);
        board.reset();
        assertEquals(BoardItem.PACMAN_SPAWN, board.getEntry(positionMiddleBoard));

        // check whole array
        BoardItem[][] boardExpected = new BoardItem[][]{
                {BoardItem.WALL,BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.PACMAN_SPAWN, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL}};
        assertEquals(true, compareBoards(board, boardExpected));
    }

    @Test
    public void resetGhostSpawnTest() {
        // Add pacmanspawn, reset and check left untouched
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        board.reset();
        assertEquals(BoardItem.GHOST_SPAWN, board.getEntry(positionMiddleBoard));

        // check whole array
        BoardItem[][] boardExpected = new BoardItem[][]{
                {BoardItem.WALL,BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.GHOST_SPAWN, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL}};
        assertEquals(true, compareBoards(board, boardExpected));

    }

    @Test
    public void resetBigDotSpawnTest() {
        // Add pacmanspawn, reset and check left untouched
        board.setEntry(positionMiddleBoard, BoardItem.BIG_DOT_SPAWN);
        board.reset();
        assertEquals(BoardItem.BIG_DOT, board.getEntry(positionMiddleBoard));

        // check whole array
        BoardItem[][] boardExpected = new BoardItem[][]{
                {BoardItem.WALL,BoardItem.WALL, BoardItem.WALL, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.BIG_DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.WALL, BoardItem.WALL}};
        assertEquals(true, compareBoards(board, boardExpected));
    }

    @Test
    public void resetAllTest() {
        // Add all items
        board.setEntry(positionTopLeft, BoardItem.NONE); // change to DOT
        board.setEntry(positionOneOne, BoardItem.WALL); // stay
        board.setEntry(positionBottomLeftBoard, BoardItem.DOT);
        board.setEntry(positionTwoThree, BoardItem.PACMAN_SPAWN); //stay
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN); // stay
        board.setEntry(positionTopRightBoard, BoardItem.BIG_DOT); // stay
        board.setEntry(positionBottomRightBoard, BoardItem.BIG_DOT_SPAWN); // change to big Dot
        board.reset();
        // check all arrays
        BoardItem[][] boardExpected = new BoardItem[][]{
                {BoardItem.DOT, BoardItem.WALL, BoardItem.WALL, BoardItem.BIG_DOT},
                {BoardItem.WALL, BoardItem.WALL, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.GHOST_SPAWN, BoardItem.DOT, BoardItem.WALL},
                {BoardItem.WALL, BoardItem.DOT, BoardItem.PACMAN_SPAWN, BoardItem.WALL},
                {BoardItem.DOT, BoardItem.WALL, BoardItem.WALL, BoardItem.BIG_DOT}};
        assertEquals(true, compareBoards(board, boardExpected));
    }

    /**
     * eatDot tests
     */

    @Test(expected = NullPointerException.class)
    public void eatDotNullTest() {
        // eat null
        board.eatDot(null);
    }

    @Test
    public void eatDotPositionTopLeftTest() {
        // check sets at (0,0)
        assertEquals(BoardItem.WALL, board.eatDot(positionTopLeft));
        assertEquals(BoardItem.WALL, boardTestOne.eatDot(positionTopLeft));
    }

    @Test
    public void eatDotPositionBottomRightTest() {
        // check sets at (width-1, height-1)
        assertEquals(BoardItem.WALL, board.eatDot(positionBottomRightBoard));
        assertEquals(BoardItem.WALL, boardTestOne.eatDot(positionBottomRightBoardOne));
        assertEquals(BoardItem.WALL, board.eatDot(new Position(3,4)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void eatDotPositionHeightInvalidTest() {
        // check doesn’t set when height out of bounds
        //min out of bounds for height
        Position positionOne = new Position(2,5);
        Position positionTwo = new Position(2,20);
        board.eatDot(positionOne);
        board.eatDot(positionTwo);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void eatDotPositionWidthInvalidTest() {
        // check doesn’t set when width out of bounds
        Position positionOne = new Position(4,2);
        Position positionTwo = new Position(20,2);
        board.eatDot(positionOne);
        board.eatDot(positionTwo);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void eatDotPositionAllInvalidTest() {
        // check doesn’t set when height and width out of bounds
        Position positionOne = new Position(4,5);
        Position positionTwo = new Position(20,50);
        board.eatDot(positionOne);
        board.eatDot(positionTwo);
    }

    @Test
    public void eatDotDotTest() {
        // eat dot, return DOT, replace with NONE
        board.setEntry(positionMiddleBoard, BoardItem.DOT);
        assertEquals(BoardItem.DOT, board.eatDot(positionMiddleBoard));
        assertEquals(BoardItem.NONE, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void eatDotBigDotTest() {
        // eat big dot, return BIG_DOT_SPAWN
        board.setEntry(positionMiddleBoard , BoardItem.BIG_DOT);
        assertEquals(BoardItem.BIG_DOT, board.eatDot(positionMiddleBoard));
        assertEquals(BoardItem.BIG_DOT_SPAWN, board.getEntry(positionMiddleBoard));
    }

    @Test
    public void eatDotBigDotSpawnTest() {
        // eat big dot spawn, return self
        board.setEntry(positionMiddleBoard, BoardItem.BIG_DOT_SPAWN);
        assertEquals(BoardItem.BIG_DOT_SPAWN, board.eatDot(positionMiddleBoard));
    }

    @Test
    public void eatDotPacmanSpawnTest() {
        // return self
        board.setEntry(positionMiddleBoard, BoardItem.PACMAN_SPAWN);
        assertEquals(BoardItem.PACMAN_SPAWN, board.eatDot(positionMiddleBoard));
    }

    @Test
    public void eatDotGhostSpawnTest() {
        // return self
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        assertEquals(BoardItem.GHOST_SPAWN, board.eatDot(positionMiddleBoard));
    }
    @Test
    public void eatDotNoneTest() {
        // return self
        board.setEntry(positionMiddleBoard, BoardItem.NONE);
        assertEquals(BoardItem.NONE, board.eatDot(positionMiddleBoard));
    }

    @Test
    public void eatDotWallTest() {
        // eat dot, return NONE
        board.setEntry(positionMiddleBoard, BoardItem.NONE);
        assertEquals(BoardItem.NONE, board.eatDot(positionMiddleBoard));
    }

    /**
     * getGhostSpawn Tests
     */
    @Test
    public void getGhostSpawnZeroTest() {
        // return null
        assertEquals(null, board.getGhostSpawn());
    }

    @Test
    public void getGhostSpawnOneTest() {
        // return position
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        assertEquals(positionMiddleBoard, board.getGhostSpawn());
    }

    @Test
    public void getGhostSpawnChangeTest() {
        // add ghost spawn in two seperate positions
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        board.setEntry(positionOneOne, BoardItem.GHOST_SPAWN);
        // check ghost spawn is in new location, and only 1 exists
        assertEquals(positionOneOne, board.getGhostSpawn());
        assertEquals(BoardItem.NONE, board.getEntry(positionMiddleBoard));
    }

    /**
     * getpacmanSpawn Tests
     */
    @Test
    public void getPacmanSpawnZeroTest() {
        // return null
        assertEquals(null, board.getPacmanSpawn());
    }

    @Test
    public void getPacmanSpawnOneTest() {
        // return position
        board.setEntry(positionMiddleBoard, BoardItem.PACMAN_SPAWN);
        assertEquals(positionMiddleBoard, board.getPacmanSpawn());
    }

    @Test
    public void getPacmanSpawnChangeTest() {
        // add pacman spawn in two seperate positions
        board.setEntry(positionMiddleBoard, BoardItem.PACMAN_SPAWN);
        board.setEntry(positionOneOne, BoardItem.PACMAN_SPAWN);
        // check ghost spawn is in new location, and only 1 exists
        assertEquals(positionOneOne, board.getPacmanSpawn());
        assertEquals(BoardItem.NONE, board.getEntry(positionMiddleBoard));
    }

    /**
     * isEmpty Tests
     */

    @Test
    public void isEmptyDefaultTest() {
        // check default is empty for min board and regular
        assertEquals(true, board.isEmpty());
        assertEquals(true, boardTestOne.isEmpty());
    }

    @Test
    public void isEmptyAllItemsNoDotsTest() {
        // all items but no dots
        board.setEntry(positionTopLeft, BoardItem.NONE);
        board.setEntry(positionOneOne, BoardItem.WALL);
        board.setEntry(positionBottomLeftBoard, BoardItem.PACMAN_SPAWN);
        board.setEntry(positionMiddleBoard, BoardItem.GHOST_SPAWN);
        assertEquals(true, board.isEmpty());
    }

    @Test
    public void isEmptyDotTest() {
        // add DOT, return false
        board.setEntry(positionMiddleBoard, BoardItem.DOT);
        assertEquals(false, board.isEmpty());
    }

    @Test
    public void isEmptyBigDotTest() {
        // add bigdot, return false
        board.setEntry(positionMiddleBoard, BoardItem.BIG_DOT);
        assertEquals(false, board.isEmpty());
    }

    @Test
    public void isEmptyDotsTest() {
        // both dot and big dot, return false
        board.setEntry(positionBottomLeftBoard, BoardItem.DOT);
        board.setEntry(positionTopRightBoard, BoardItem.BIG_DOT);
        assertEquals(false, board.isEmpty());
    }

    @Test
    public void isEmptyAllItemsTest() {
        // all items, return false
        board.setEntry(positionTopLeft, BoardItem.NONE);
        board.setEntry(positionOneOne, BoardItem.WALL);
        board.setEntry(positionBottomLeftBoard, BoardItem.DOT);
        board.setEntry(positionMiddleBoard, BoardItem.PACMAN_SPAWN);
        board.setEntry(positionTopRightBoard, BoardItem.GHOST_SPAWN);
        board.setEntry(positionBottomRightBoard, BoardItem.BIG_DOT);
        board.setEntry(new Position(2,2), BoardItem.BIG_DOT_SPAWN);
        assertEquals(false, board.isEmpty());
    }

    /**
     * ASSIGNMENT TWO
     */
    @Test
    public void toStringTest() {
        // print as multiline with no empty lines
        assertEquals(boardTestOneExpectedString, boardTestOne.toString());
    }

    @Test
    public void equalsTest(){
        // compare two new boards
        assertEquals(true, boardTestOne.equals(boardTestTwo));
        // compare after adding
        boardTestOne.setEntry(positionTwoThree, BoardItem.DOT);
        assertEquals(false, boardTestOne.equals(boardTestTwo));
        boardTestTwo.setEntry(positionTwoThree, BoardItem.DOT);
        assertEquals(true, boardTestOne.equals(boardTestTwo));
    }

    @Test
    public void hashCodeTest() {
        int boardOneHash = boardTestOne.hashCode();
        int boardTwoHash = boardTestTwo.hashCode();
        int boardThreeHash = boardTestThree.hashCode();
        assertEquals(true, boardOneHash == boardTwoHash);
        assertEquals(false, boardOneHash == boardThreeHash);
    }






    @After
    public void tearDown() throws Exception {
    }
}