package pacman.board;

public enum BoardItem {
    NONE(true, 0, '0'),
    WALL(false, 0, 'X'),
    DOT(true, 10, '1'),
    BIG_DOT(true, 15, 'B'),
    BIG_DOT_SPAWN(true, 0, 'b'),
    GHOST_SPAWN(true, 0, '$'),
    PACMAN_SPAWN(true, 0, 'P');

    private final boolean pathable;
    private final int score;
    private final char charKey;

    private BoardItem(boolean pathable, int score, char charKey) {
        this.pathable = pathable;
        this.score = score;
        this.charKey = charKey;
    }

    /**
     * Gets the score of the item
     * @return the score associated with an item
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the path-able
      * @return whether the item is path-able
     */
    public boolean getPathable() {
        return pathable;
    }

    /**
     * Gets the character key of the item.
     * @return the character key associated with the item.
     */
    public char getChar() {
        return charKey;
    }

    /**
     * Takes a character and returns the associated BoardItem as presented in
     * the Enum comment's "Enum definition" table. see BoardItem
     * @param key a character that represents the board item. The acceptable
     * characters are defined in the Enum's "Enum definition" table.
     * @return the board Item associated with the character
     * @throws IllegalArgumentException if the character is not part of the
     * supported Items
    */
     public static BoardItem getItem(char key) throws IllegalArgumentException {
        switch (key) {
            case '0':
                return NONE;
            case 'X':
                return WALL;
            case '1':
                return DOT;
            case 'B':
                return  BIG_DOT;
            case 'b':
                return BIG_DOT_SPAWN;
            case '$':
                return GHOST_SPAWN;
            case 'P':
                return PACMAN_SPAWN;
            default:
                throw new IllegalArgumentException();
        }
    }
}
