package pacman.ghost;

/**
 * GhostType contains all the valid ghosts; the current list of ghosts are:
 *      "BLINKY" - Agressive red ghost.
 *      "CLYDE" - Scared orange ghost.
 *      "INKY" - Following blue ghost.
 *      "PINKY" - Ambushing pink ghost.
 */
public enum GhostType {
    BLINKY("red","#d54e53"),
    CLYDE("orange","#e78c45"),
    INKY("blue","#7aa6da"),
    PINKY("pink","#c397d8");

    private String colour;
    private String colourHex;

    private GhostType(String colour, String colourHex) {
        this.colour = colour;
        this.colourHex = colourHex;
    }
}