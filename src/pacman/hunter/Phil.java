package pacman.hunter;

/**
 * A Phil hunter that has no special ability.
 */
public class Phil extends Hunter {

    /**
     * Creates a Phil Hunter with its special ability. see Hunter()
     */
    public Phil() {
        super();
    }

    /**
     * Creates a Phil Hunter by copying the internal state of another hunter. see Hunter(Hunter)
     * @param original -  - hunter to copy from
     */
    public Phil(Hunter original) {
        super(original);
    }

    /**
     *  Phil does not have a special.
     * @return false
     */
    @Override
    public boolean isSpecialActive() {
        return false;
    }

    /**
     * Represents this Phil in a comma-seperated string format.
     * Format is: "x,y,DIRECTION,specialDuration,PHIL".
     * DIRECTION is the uppercase enum type value.
     * Example: "4,5,LEFT,12,PHIL"
     */
    @Override
    public String toString() {
        return super.toString()
                + ","
                + HunterType.PHIL;
    }
}