package pacman.ghost;

/**
 * Phase Defines the different phases a ghost can be in. The phases are defined
 * as "CHASE", "SCATTER" and "FRIGHTENED".
 *      "CHASE" - Phase where the ghosts chase the hunter. Has a duration of 20.
 *      "FRIGHTENED" - Phase where the ghosts are frightened and confused.
 *       Has a duration of 30
 *      "SCATTER" - Phase where the ghosts run home. Has a duration of 10. *
 */
public enum Phase {
    CHASE(20),
    FRIGHTENED(30),
    SCATTER(10);

    private int duration;

    private Phase(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the duration of the phase.
     * @return duration of the phase.
     */
    public int getDuration() {
        return duration;
    }
}