package pacman.hunter;

import pacman.game.PacmanGame;

/**
 * A Speedy hunter that has a special ability that allows the hunter to travel
 * twice as fast.
 * In Assignment 2 we will implement this hunters special ability.
 */
public class Speedy extends Hunter {

    /**
     * Creates a Speedy Hunter with its special ability. see Hunter()
     */
    public Speedy() {
        super();
    }

    /**
     * Creates a Speedy Hunter by copying the internal state of another hunter.
     * @param original hunter to copy from
     */
    public Speedy(Hunter original) {
        super(original);
    }

    /**
     * Represents this Speedy in a comma-seperated string format.
     * Format is: "x,y,DIRECTION,specialDuration,SPEEDY".
     * DIRECTION is the uppercase enum type value.
     * Example: "4,5,LEFT,12,SPEEDY"
     */
    @Override
    public String toString() {
        return super.toString()
                + ","
                + HunterType.SPEEDY;
    }

    /**
     * If Speedy's special is active then we move twice instead of once.
     * While moving we still do all the normal steps that Hunter.move does.
     * @param game information needed to decide movement.
     */
    @Override
    public void move(PacmanGame game) {
        super.move(game);
        if(isSpecialActive()) {
            // special is active so move again
            super.move(game);
        }
    }
}