package pacman.hunter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.ghost.Blinky;
import pacman.ghost.Phase;
import pacman.util.Direction;
import pacman.util.Position;

import static org.junit.Assert.*;

public class HunterTest {
    // hunters
    private Hungry hungry;
    private Phasey phasey;
    private Phil phil;
    private Speedy speedy;
    private Blinky blinky;
    // position
    private Position positionTwoThree;
    // ghosts
    private Blinky ghost;
    // game set up
    private PacmanGame game;
    private Hunter hunter;
    private PacmanBoard board;


    @Before
    public void setUp() throws Exception {
        // hunters
        hungry = new Hungry();
        phil = new Phil();
        speedy = new Speedy();
        phasey = new Phasey();

        // position
        positionTwoThree = new Position(2,3);

        // ghost
        blinky = new Blinky();
        ghost = new Blinky();

        //set up game
        board = new PacmanBoard(4,5);
        board.setEntry(new Position(1,1), BoardItem.GHOST_SPAWN);
        board.setEntry(new Position(2,3), BoardItem.PACMAN_SPAWN);
        game = new PacmanGame("title", "author", speedy, board);
        hunter = game.getHunter();
        board = game.getBoard();
    }

    /**
     * ASSIGNMENT ONE
     */
    @Test
    public void constructorTest() {
        assertEquals(false, hungry.isDead());
        assertEquals(false, hungry.isSpecialActive());
        assertEquals(new Position(0,0), hungry.getPosition());
        assertEquals(Direction.UP, hungry.getDirection());
        assertEquals(0, hungry.getSpecialDurationRemaining());

        hungry.activateSpecial(20);
        assertEquals(20, hungry.getSpecialDurationRemaining());
    }


    @Test
    public void copyTest() {
        hungry.setDirection(Direction.LEFT);
        hungry.setPosition(new Position(5,5));
        Hungry copy = new Hungry(hungry);
        assertEquals(Direction.LEFT, copy.getDirection());
        assertEquals(new Position(5,5), copy.getPosition());

        phasey.activateSpecial(20);
        Phasey copyPhasey = new Phasey(phasey);
        assertEquals(true, copyPhasey.isSpecialActive());

        speedy.activateSpecial(20);
        Speedy copySpeedy = new Speedy(speedy);

        phil.activateSpecial(20);
        Phil copyPhil = new Phil(phil);
    }

    @Test
    public void activateSpecialTest(){
        hungry.activateSpecial(0);
        assertEquals(false, hungry.isSpecialActive());
        assertEquals(0, hungry.getSpecialDurationRemaining());

        hungry.activateSpecial(-5);
        assertEquals(false, hungry.isSpecialActive());
        assertEquals(0, hungry.getSpecialDurationRemaining());

        hungry.activateSpecial(50);
        assertEquals(true, hungry.isSpecialActive());
        assertEquals(50, hungry.getSpecialDurationRemaining());

        hungry.activateSpecial(100);
        assertEquals(true, hungry.isSpecialActive());
        assertEquals(50, hungry.getSpecialDurationRemaining());
    }

    @Test
    public void hitPhilTest() {
        // NOT FRIGHTENED
        // move ghost so not same position(Scatter phase)
        blinky.setPosition(new Position(1,1));
        phil.hit(blinky);
        // neither should be dead
        assertEquals(false, blinky.isDead());
        assertEquals(false, hungry.isDead());

        // move hunter to same
        phil.setPosition(new Position(1,1));
        phil.hit(blinky);
        // hunter die. ghost alive
        assertEquals(false, blinky.isDead());
        assertEquals(true, phil.isDead());

        // FRIGHTENED
        phil.reset();
        // set frightened and move ghost so not the same
        blinky.setPhase(Phase.FRIGHTENED, 10);
        blinky.setPosition(new Position(1,1));
        phil.hit(blinky);
        // neither should be killed
        assertEquals(false, blinky.isDead());
        assertEquals(false, phil.isDead());
        // set hunter to same position
        phil.setPosition(new Position(1,1));
        phil.hit(blinky);
        // ghost die, hunter alive
        assertEquals(true, blinky.isDead());
        assertEquals(false, phil.isDead());
    }

    @Test
    public void hitHungryTest() {
        // same position, ghost SCATTER, hunter special NOT active
        hungry.hit(blinky);
        assertEquals(false, blinky.isDead());
        assertEquals(true, hungry.isDead());

        hungry.setPosition(new Position(1,1));
        assertEquals(new Position(1,1), hungry.getPosition());
        // same position, activate hunter special, ghost SCATTER
        hungry.reset();
        hungry.activateSpecial(10);
        hungry.hit(blinky);
        // ghost die, hunter alive
        assertEquals(true, blinky.isDead());
        assertEquals(false, hungry.isDead());

        // reset, same position, ghost FRIGHTENED, hunter special active
        blinky.reset();
        blinky.setPhase(Phase.FRIGHTENED, 10);
        hungry.hit(blinky);
        assertEquals(true, blinky.isDead());
        assertEquals(false, hungry.isDead());

        //reset, same position, ghost CHASE, hungry special active
        blinky.reset();
        blinky.setPhase(Phase.CHASE, 10);
        hungry.hit(blinky);
        assertEquals(true, blinky.isDead());
        assertEquals(false, hungry.isDead());
    }

    @Test
    public void hitPhaseyTest() {
        // same position, ghost SCATTER, hunter special NOT active
        phasey.hit(blinky);
        assertEquals(false, blinky.isDead());
        assertEquals(true, phasey.isDead());

        // same position, activate hunter special, ghost SCATTER
        phasey.reset();
        phasey.activateSpecial(10);
        phasey.hit(blinky);
        // neither die
        assertEquals(false, blinky.isDead());
        assertEquals(false, phasey.isDead());

        //reset, same position, ghost CHASE, hunter special active
        blinky.setPhase(Phase.CHASE, 10);
        phasey.hit(blinky);
        assertEquals(false, blinky.isDead());
        assertEquals(false, phasey.isDead());

        // same position, ghost FRIGHTENED, hunter special active
        blinky.setPhase(Phase.FRIGHTENED, 10);
        phasey.hit(blinky);
        assertEquals(true, blinky.isDead());
        assertEquals(false, phasey.isDead());
    }

    @Test(expected = NullPointerException.class)
    public void hitNullTest() {
        hungry.hit(null);
    }

    @Test
    public void resetTest() {
        hungry.reset();
        assertEquals(false, hungry.isDead());
        assertEquals(false, hungry.isSpecialActive());
        assertEquals(new Position(0,0), hungry.getPosition());
        assertEquals(Direction.UP, hungry.getDirection());
        assertEquals(0, hungry.getSpecialDurationRemaining());

        hungry.activateSpecial(20);
        assertEquals(20, hungry.getSpecialDurationRemaining());

        hungry.setPosition(new Position(5,5));
        hungry.setDirection(Direction.DOWN);
        hungry.reset();
        assertEquals(new Position(0,0), hungry.getPosition());
        assertEquals(Direction.UP, hungry.getDirection());
}

    /**
     * ASSIGNMENT TWO
     */
    @Test
    public void toStringHunterTest() {
        //"x,y,DIRECTION,specialDuration,HUNTER"
        assertEquals("0,0,UP,0,HUNGRY", hungry.toString());
        assertEquals("0,0,UP,0,SPEEDY", speedy.toString());
        assertEquals("0,0,UP,0,PHASEY", phasey.toString());
        assertEquals("0,0,UP,0,PHIL", phil.toString());
        // change position
        hungry.setPosition(positionTwoThree);
        assertEquals("2,3,UP,0,HUNGRY", hungry.toString());
        // change direction
        hungry.setDirection(Direction.RIGHT);
        assertEquals("2,3,RIGHT,0,HUNGRY", hungry.toString());
        // change phase
        hungry.activateSpecial(16);
        assertEquals("2,3,RIGHT,16,HUNGRY", hungry.toString());
    }

    @Test
    public void equalsHunterTest(){
        Hungry hungryTwo = new Hungry();
        assertEquals(true, hungry.equals(hungryTwo));
        // change dead (change ghost to chase and try to hit
        ghost.setPhase(Phase.CHASE,20);
        hungry.hit(ghost);
        assertEquals(false, hungry.equals(hungryTwo));
        hungryTwo.hit(ghost);
        assertEquals(true, hungry.equals(hungryTwo));
        // change position
        hungry.setPosition(positionTwoThree);
        assertEquals(false, hungry.equals(hungryTwo));
        hungryTwo.setPosition(positionTwoThree);
        assertEquals(true, hungry.equals(hungryTwo));
        // change direction
        hungry.setDirection(Direction.RIGHT);
        assertEquals(false, hungry.equals(hungryTwo));
        hungryTwo.setDirection(Direction.RIGHT);
        assertEquals(true, hungry.equals(hungryTwo));
        // activate special and change duration
        hungry.activateSpecial(50);
        assertEquals(false, hungry.equals(hungryTwo));
        hungryTwo.activateSpecial(50);
        assertEquals(true, hungry.equals(hungryTwo));
        // reset
        hungry.reset();
        hungryTwo.reset();
        assertEquals(true, hungry.equals(hungryTwo));
    }

    @Test
    public void moveHunter(){


        // if 1 position forward is pathable, move hunter
        hunter.setPosition(positionTwoThree);
        hunter.move(game);
        assertEquals(new Position(2,2), game.getHunter().getPosition());

        // move and eat
        board.setEntry(new Position (2,1), BoardItem.BIG_DOT);
        hunter.move(game);
        assertEquals(new Position(2,1), game.getHunter().getPosition());
        assertEquals(BoardItem.BIG_DOT_SPAWN, game.getBoard().getEntry(new Position(2,1)));
        assertEquals(15, game.getScores().getScore());

        // not pathable, dont move
        hunter.setDirection(Direction.RIGHT);
        hunter.move(game);
        assertEquals(new Position(2,1), game.getHunter().getPosition());

        // ticks decrease if greater than 0
        hunter.activateSpecial(1);
        hunter.move(game);
        assertEquals(0, game.getHunter().getSpecialDurationRemaining());
        // ticks not decreased as equal to 0
        hunter.move(game);
        assertEquals(0, game.getHunter().getSpecialDurationRemaining());
    }

    @Test
    public void moveSpeedy(){
        // special active
        hunter.setPosition(positionTwoThree);
        hunter.activateSpecial(2);
        board.setEntry(new Position (2,2), BoardItem.DOT);
        board.setEntry(new Position (2,1), BoardItem.BIG_DOT);
        hunter.move(game);
        // eat DOT on first move, then BIG DOT on second, finish at (2,1), score of 25
        assertEquals(BoardItem.NONE, game.getBoard().getEntry(new Position(2,2)));
        assertEquals(BoardItem.BIG_DOT_SPAWN, game.getBoard().getEntry(new Position(2,1)));
        assertEquals(new Position(2,1), game.getHunter().getPosition());
        assertEquals(25, game.getScores().getScore());
        // special ticks twice
        assertEquals(0, game.getHunter().getSpecialDurationRemaining());

        // not pathable, dont move
        hunter.setDirection(Direction.RIGHT);
        hunter.move(game);
        assertEquals(new Position(2,1), game.getHunter().getPosition());
    }

    @Test
    public void testhashCode() {
        Hunter hunterOne = new Hungry();
        Hunter hunterTwo = new Hungry();
        assertEquals(hunterOne.hashCode(), hunterTwo.hashCode());
        hunterTwo.setPosition(new Position(10,20));
        assertEquals(false, hunterOne.hashCode() == hunterTwo.hashCode());
        hunterOne.setPosition(new Position(10,20));
        assertEquals(hunterOne.hashCode(), hunterTwo.hashCode());
    }









    @After
    public void tearDown() throws Exception {
    }
}