package pacman.board;

import pacman.util.Position;

import java.util.Arrays;

/**
 * Represents the Pac Man game board. The board can be any size, it is set out as a grid with each space containing
 * only one BoardItem. game boards are by default surrounded by a BoardItem.WALL with every other space being BoardItem.NONE.
 * The coordinate positions for the board is the top left position is (0, 0) and the bottom right position is (getWidth-1, getHeight-1).
 */
public class PacmanBoard {
    private int width;
    private int height;
    private BoardItem[][] board;

    /**
     * Constructor taking the getWidth and getHeight creating a board that is filled with BoardItem.NONE
     * except a 1 block wide border wall around the entire board ( BoardItem.WALL ).
     * @param width the horizontal size of the board which is greater than zero.
     * @param height the vertical size of the board which is greater than zero.
     * @throws IllegalArgumentException- when getHeight || getWidth is less than or equal to 0.
     */
    public PacmanBoard(int width, int height) throws IllegalArgumentException {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }

        this.width = width;
        this.height = height;

        this.board = new BoardItem[height][width];
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                // If position in first or last row or column, place WALL
                if (row == 0 || row == height - 1) {
                    board[row][column] = BoardItem.WALL;
                } else if (column == 0 || column == width - 1) {
                    board[row][column] = BoardItem.WALL;
                } else {
                    board[row][column] = BoardItem.NONE;
                }
            }
        }
    }

    /**
     * Constructor taking an existing PacmanBoard and making a deep copy.
     * A deep copy should have the same getWidth, getHeight and board as the given board.
     * When a change is made to the other board this should not change this copy.
     * @param other copy of an existing PacmanBoard.
     * @throws NullPointerException if copy is null.
     */
    public PacmanBoard(PacmanBoard other) throws NullPointerException {
        if (other == null) {
            throw new NullPointerException();
        }

        this.width = other.width;
        this.height = other.height;
        this.board = new BoardItem[height][width];
        for (int row = 0; row < height; row++) {
            board[row] = Arrays.copyOf(other.board[row], other.board[row].length);
        }
    }

    /**
     * Gets the width of the board
     * @return width of the game board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the board
     * @return height of the game board
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets a tile on the board to an item.
     * @param position the position to place the item
     * @param item the board item that is to be placed at the position
     * @throws IndexOutOfBoundsException when the position trying to be set is not within the board
     * @throws NullPointerException when the position or item is null.
     */
    public void setEntry(Position position, BoardItem item) throws IndexOutOfBoundsException, NullPointerException {
        // Check for exceptions first
        // Assume can place anywhere on board i.e. NULL or NONE space
        if (position == null || item == null) {
            throw new NullPointerException();
        } else if (position.getX() < 0 || position.getX() > width - 1) {
            throw new IndexOutOfBoundsException();
        } else if (position.getY() < 0 || position.getY() > height - 1) {
            throw new IndexOutOfBoundsException();
        }

        // Check if PacmanSpawn or GhostSpawn already exist, if so replace with NONE
        switch (item) {
            case PACMAN_SPAWN:
                if (getPacmanSpawn() != null) {
                    Position currentPacman = getPacmanSpawn();
                    setEntry(currentPacman, BoardItem.NONE);
                }
            case GHOST_SPAWN:
                if (getGhostSpawn() != null) {
                    Position currentGhost = getGhostSpawn();
                    setEntry(currentGhost, BoardItem.NONE);
                }
        }
        // Place tile on valid position
        board[position.getY()][position.getX()] = item;
    }

    /**
     * Returns what item the board has on a given position.
     * @param position wanting to be checked
     * @return BoardItem at the location given.
     * @throws IndexOutOfBoundsException when the position is not within the board.
     * @throws NullPointerException if position is null.
     */
    public BoardItem getEntry(Position position) throws IndexOutOfBoundsException, NullPointerException {
        if (position == null) {
            throw new NullPointerException();
        } else if (position.getX() < 0 || position.getX() > width - 1) {
            throw new IndexOutOfBoundsException();
        } else if (position.getY() < 0 || position.getY() > height - 1) {
            throw new IndexOutOfBoundsException();
        }

        return board[position.getY()][position.getX()];
    }

    /**
     * Tries to eat a dot off the board and returns the item that it ate/tried to eat.
     * If a BoardItem.DOT is eaten then it is replaced with a BoardItem.NONE.
     * If a BoardItem.BIG_DOT is eaten then it is replaced with a BoardItem.BIG_DOT_SPAWN.
     * If the item is any other BoardItem then do nothing and return the item.     *
     * @param position to eat
     * @return the item that was originally the position before trying to eat.
     * @throws IndexOutOfBoundsException when the position trying to be eaten is not within the board.
     * @throws NullPointerException if position is null.
     */
    public BoardItem eatDot(Position position) throws IndexOutOfBoundsException, NullPointerException {
        // Check exceptions first
        if (position == null) {
            throw new NullPointerException();
        } else if (position.getX() < 0 || position.getX() > width - 1) {
            throw new IndexOutOfBoundsException();
        } else if (position.getY() < 0 || position.getY() > height - 1) {
            throw new IndexOutOfBoundsException();
        }

        // Return items
        switch (getEntry(position)) {
            case DOT:
                setEntry(position, BoardItem.NONE);
                return BoardItem.DOT;
            case BIG_DOT:
                setEntry(position, BoardItem.BIG_DOT_SPAWN);
                return BoardItem.BIG_DOT;
            default:
                return getEntry(position);
        }
    }

    /**
     * Get the spawn position for the ghosts. (Requires: board to contain 0 or 1 GHOST_SPAWN's)
     * @return the position of the ghost spawn or null if none found.
     */
    public Position getGhostSpawn() {
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                // Check each position for GHOST_SPAWN
                if (board[row][column] == BoardItem.GHOST_SPAWN) {
                    return new Position(column, row);
                }
            }
        }
        // No GHOST_SPAWN found
        return null;
    }

    /**
     * Get the spawn position for pacman. (Requires: board to contain 0 or 1 PACMAN_SPAWN's)     *
     * @return the postion of pacmans spawn or null if none found.
     */
    public Position getPacmanSpawn() {
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                // Check each position for PACMAN_SPAWN
                if (board[row][column] == BoardItem.PACMAN_SPAWN) {
                    return new Position(column, row);
                }
            }
        }
        // No PACMAN_SPAWN found
        return null;
    }

    /**
     * Checks if the board contains any pickup items.     *
     * @return true if the board does not contain any DOT's or BIG_DOT's.
     */
    public boolean isEmpty() {
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                // Check each position for DOT or BIG DOT
                if (board[row][column] == BoardItem.DOT || board[row][column] == BoardItem.BIG_DOT) {
                    return false;
                }
            }
        }
        // No DOT or BIG DOT in any board position
        return true;
    }

    /**
     * Resets the board to place a DOT in every position that has no item ( NONE BoardItem ) and respawns BIG_DOT's
     * in the BIG_DOT_SPAWN locations. Leaves walls, pacman spawns and ghost spawns intact.
     */
    public void reset() {

        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                // Check each position for DOT or BIG DOT
                if (board[row][column] == BoardItem.NONE) {
                    board[row][column] = BoardItem.DOT;
                } else if (board[row][column] == BoardItem.BIG_DOT_SPAWN) {
                    board[row][column] = BoardItem.BIG_DOT;
                }
            }
        }
    }

    /**
     * Creates a multiline string ( using System.lineSeparator() as newline )
     * that is a printout of each index of the board with the character key.
     * Example of a board with height 3 width 4.
     *      XXXX
     *      X00X
     *      XXXX
     * Note: the lines are not indented but may show in the JavaDoc as such.
     * There are no spaces. The last line does not have a newline.
     * @return board as multiline string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                sb.append(board[x][y].getChar());
            }
            // Reached end of row, add line but not for the last line
            if (y != getHeight() -1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    /**
     * Checks if another object instance is equal to this instance.
     * Boards are equal if they have the same dimensions (width, height) and
     * have equal items for all board positions.
     * @return true if same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PacmanBoard)) {
            return false;
        }

        PacmanBoard other = (PacmanBoard) o;
        // Check width and height variables first
        if (!(this.width == other.width) || !(this.height == other.height)) {
            return false;
        }
        // Check board entries are ALL equal
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (this.board[x][y] != (other.getEntry(new Position(x, y)))) {
                    return false;
                }
            }
        }
        // Here boards must be equal
        return true;
    }

    /**
     * For two objects that are equal the hash should also be equal.
     * For two objects that are not equal the hash does not have to be different.
     * @return hash of PacmanBoard
     */
    @Override
    public int hashCode() {
        // Hashcode unique. Same hashcode only when same dimensions and entries
        int hash = 2;
        hash = 3 * hash * this.width;
        hash = 5 * hash * this.height;
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                hash = 7 * (hash + x - y + this.board[x][y].getChar());
            }
        }
        return hash;
    }
}
