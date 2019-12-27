package pacman.ghost;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.hunter.Hunter;
import pacman.hunter.Speedy;
import pacman.util.Direction;
import pacman.util.Position;

import java.util.List;

import static org.junit.Assert.*;

public class GhostTest {
    // ASSIGNMENT ONE
    private Blinky blinky;
    private Clyde clyde;
    private Inky inky;
    private Pinky pinky;
    private Ghost ghost;

    // ASSIGNMENT TWO
    // game
    private PacmanGame game;

    // hunter
    private Speedy speedy;

    // position
    private Position positionTwoThree;

    // board
    private PacmanBoard board;
    private int boardWidth;
    private int boardHeight;

    // ghosts
    private List<Ghost> ghostList;
    private Blinky blinkyTwo;
    private Inky inkyTwo;
    private Pinky pinkyTwo;
    private Clyde clydeTwo;

    @Before
    public void setUp() throws Exception {
        blinky = new Blinky();
        clyde = new Clyde();
        inky = new Inky();
        pinky = new Pinky();


        // ASSIGNMENT TWO
        // positions
        positionTwoThree = new Position(2,3);
        //set up game
        speedy = new Speedy();
        board = new PacmanBoard(4,5);
        board.setEntry(new Position(1,1), BoardItem.GHOST_SPAWN);
        board.setEntry(new Position(2,3), BoardItem.PACMAN_SPAWN);
        game = new PacmanGame("title", "author", speedy, board);
        // one of each ghost
        blinkyTwo = (Blinky) game.getGhosts().get(0);
        inkyTwo = (Inky) game.getGhosts().get(1);
        pinkyTwo = (Pinky) game.getGhosts().get(2);
        clydeTwo = (Clyde) game.getGhosts().get(3);
        ghostList = game.getGhosts();
        // board
        boardWidth = game.getBoard().getWidth();
        boardHeight = game.getBoard().getHeight();
    }

    /**
     * ASSIGNMENT ONE
     */
    // ovveride functions work
    @Test
    public void ColourTest() {
        assertEquals("#d54e53", blinky.getColour());
        assertEquals("#e78c45", clyde.getColour());
        assertEquals("#7aa6da", inky.getColour());
        assertEquals("#c397d8", pinky.getColour());
    }

    @Test
    public void typeTest() {
        assertEquals(GhostType.BLINKY, blinky.getType());
        assertEquals(GhostType.CLYDE, clyde.getType());
        assertEquals(GhostType.INKY, inky.getType());
        assertEquals(GhostType.PINKY, pinky.getType());
    }

    @Test
    public void constructorTest() {
        assertEquals(false, blinky.isDead());
        assertEquals(Phase.SCATTER, blinky.getPhase());
        assertEquals(new Position(0,0), blinky.getPosition());
        assertEquals(Direction.UP, blinky.getDirection());

        assertEquals(false, inky.isDead());
        assertEquals(Phase.SCATTER, inky.getPhase());
        assertEquals(new Position(0,0), inky.getPosition());
        assertEquals(Direction.UP, inky.getDirection());

        assertEquals(false, pinky.isDead());
        assertEquals(Phase.SCATTER, pinky.getPhase());
        assertEquals(new Position(0,0), pinky.getPosition());
        assertEquals(Direction.UP, pinky.getDirection());

        assertEquals(false, clyde.isDead());
        assertEquals(Phase.SCATTER, clyde.getPhase());
        assertEquals(new Position(0,0), clyde.getPosition());
        assertEquals(Direction.UP, clyde.getDirection());
    }

    @Test
    public void getPhaseTest() {
        assertEquals(Phase.SCATTER, blinky.getPhase());
    }
    @Test
    public void setPhaseTest() {
        blinky.setPhase(Phase.FRIGHTENED, 10);
        assertEquals(Phase.FRIGHTENED, blinky.getPhase());
        // check duration

        blinky.setPhase(null, 10);
        assertEquals(Phase.FRIGHTENED, blinky.getPhase());
        // check duration
    }

    @Test
    public void setPhaseDurationTest() {
        blinky.setPhase(Phase.FRIGHTENED, 0);
        assertEquals(Phase.FRIGHTENED, blinky.getPhase());
        // check duration set to 0

        blinky.setPhase(null, -10);
        assertEquals(Phase.FRIGHTENED, blinky.getPhase());
        // check duration set to 0
    }

    @Test
    public void phaseInfoTest() {
        assertEquals("SCATTER:10", blinky.phaseInfo());

        blinky.setPhase(Phase.FRIGHTENED, 20);
        assertEquals("FRIGHTENED:20", blinky.phaseInfo());
    }

    @Test
    public void killTest() {
        blinky.kill();
        assertEquals(true, blinky.isDead());
    }

    @Test
    public void resetTest() {
        blinky.reset();
        assertEquals(false, blinky.isDead());
        assertEquals(Phase.SCATTER, blinky.getPhase());
        assertEquals(10, Phase.SCATTER.getDuration());
        assertEquals(Direction.UP, blinky.getDirection());
        assertEquals(new Position(0,0), blinky.getPosition());

        blinky.setPosition(new Position (1,1));
        blinky.setDirection(Direction.DOWN);
        blinky.reset();
        assertEquals(new Position(0,0), blinky.getPosition());
        assertEquals(Direction.UP, blinky.getDirection());
    }

    @Test
    public void setDirectionTest() {
        assertEquals(Direction.UP, blinky.getDirection());

        blinky.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, blinky.getDirection());

        blinky.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, blinky.getDirection());

        blinky.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, blinky.getDirection());

        blinky.setDirection(Direction.UP);
        assertEquals(Direction.UP, blinky.getDirection());
    }

    @Test
    public void setPositionTest() {
        assertEquals(new Position(0,0), blinky.getPosition());

        blinky.setPosition(new Position(1,2));
        assertEquals(new Position(1,2), blinky.getPosition());

        blinky.setPosition(new Position(6,4));
        assertEquals(new Position(6,4), blinky.getPosition());
    }

    /**
     * ASSIGNMENT TWO
     */
    @Test
    public void toStringGhostTest() {
        // "x,y,DIRECTION,PHASE:phaseDuration"
        // default - ghost spawn (same for all ghosts)
        assertEquals("1,1,UP,SCATTER:10", pinkyTwo.toString());
        // change position
        pinkyTwo.setPosition(positionTwoThree);
        assertEquals("2,3,UP,SCATTER:10", pinkyTwo.toString());
        // change direction
        pinkyTwo.setDirection(Direction.RIGHT);
        assertEquals("2,3,RIGHT,SCATTER:10", pinkyTwo.toString());
        // change phase
        pinkyTwo.setPhase(Phase.CHASE, 55);
        assertEquals("2,3,RIGHT,CHASE:55", pinkyTwo.toString());
    }

    @Test
    public void equalsGhostTest(){
        Blinky blinkyThree = new Blinky();
        blinkyTwo.setPosition(board.getGhostSpawn());
        assertEquals(true, blinkyTwo.equals(blinkyThree));
        // change position
        blinkyTwo.setPosition(positionTwoThree);
        assertEquals(false, blinkyTwo.equals(blinkyThree));
        blinkyTwo.setPosition(positionTwoThree);
        assertEquals(true, blinkyTwo.equals(blinkyThree));
        // change direction
        blinkyTwo.setDirection(Direction.RIGHT);
        assertEquals(false, blinkyTwo.equals(blinkyThree));
        blinkyTwo.setDirection(Direction.RIGHT);
        assertEquals(true, blinkyTwo.equals(blinkyThree));
        // change dead
        blinkyTwo.kill();
        assertEquals(false, blinkyTwo.equals(blinkyThree));
        blinkyThree.kill();
        assertEquals(true, blinkyTwo.equals(blinkyThree));
        // change phase & duration
        blinkyTwo.setPhase(Phase.CHASE, 50);
        assertEquals(false, blinkyTwo.equals(blinkyThree));
        blinkyThree.setPhase(Phase.CHASE, 50);
        assertEquals(true, blinkyTwo.equals(blinkyThree));
        // reset
        blinkyTwo.reset();
        blinkyThree.reset();
        assertEquals(true, blinkyTwo.equals(blinkyThree));
        // equal to another ghost?
        //assertEquals(true, blinkyTwo.equals(pinkyTwo));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(true, blinkyTwo.hashCode() == inkyTwo.hashCode());

        Blinky blinkyThree = new Blinky();
        Blinky blinkyFour = new Blinky();
        blinkyFour.setPhase(Phase.FRIGHTENED, 20);
        blinkyThree.setPhase(Phase.FRIGHTENED, 20);
        blinkyFour.setDirection(Direction.DOWN);
        blinkyThree.setDirection(Direction.DOWN);
        assertEquals(true, blinkyFour.hashCode() == blinkyThree.hashCode());
    }

    @Test
    public void chaseTargetNotChaseTest() {
        // check if not not chase, no change
        for (Ghost g : ghostList) {
            assertEquals(g.getPosition(), g.chaseTarget(game));
        }
    }

    @Test
    public void chaseTargetBlinkyTest() {
        // set to chase
        blinkyTwo.setPhase(Phase.CHASE, 20);
        // BLINKY - hunters position
        assertEquals(speedy.getPosition(), blinkyTwo.chaseTarget(game));
    }

    @Test
    public void chaseTargetInkyTest() {
        // set to chase
        inkyTwo.setPhase(Phase.CHASE, 20);
        // INKY - 2 blocks behind hunter
        // hunter default (0,0,UP)
        assertEquals(new Position(0,2), inkyTwo.chaseTarget(game));
        // change hunter direction
        speedy.setDirection(Direction.RIGHT);
        assertEquals(new Position(-2,0), inkyTwo.chaseTarget(game));
        // change hunter position
        speedy.setPosition(positionTwoThree);
        assertEquals(new Position(0,3), inkyTwo.chaseTarget(game));
    }

    @Test
    public void chaseTargetPinkyTest() {
        // set to chase
        pinkyTwo.setPhase(Phase.CHASE, 20);
        // PINKY - 4 blocks front of hunter
        // hunter default (0,0,UP)
        assertEquals(new Position(0,-4), pinkyTwo.chaseTarget(game));
        // change hunter direction
        speedy.setDirection(Direction.RIGHT);
        assertEquals(new Position(4,0), pinkyTwo.chaseTarget(game));
        // change hunter position
        speedy.setPosition(positionTwoThree);
        assertEquals(new Position(6,3), pinkyTwo.chaseTarget(game));
    }

    @Test
    public void chaseTargetClydeTest() {
        // set to chase
        clydeTwo.setPhase(Phase.CHASE, 20);
        // CLYDE - chase if greater than 8 distance
        assertEquals(clydeTwo.home(game), clydeTwo.chaseTarget(game));
        speedy.setPosition(new Position(clydeTwo.getPosition().getX() + 10, clydeTwo.getPosition().getY()+ 10));
        assertEquals(speedy.getPosition(), clydeTwo.chaseTarget(game));
    }


    @Test
    public void homeTest(){
        // Check when not in scatter, home position is current position
        for (Ghost g : ghostList) {
            g.setPhase(Phase.CHASE, 20);
            assertEquals(g.getPosition(), g.home(game));
            // set to scatter
            g.setPhase(Phase.SCATTER, 20);
        }

        // BLINKY - one block outside of the top right of the game board (w, -1)
        assertEquals(new Position(boardWidth, -1), blinkyTwo.home(game));

        //INKY - one block outside of the bottom right of the game board (w,h)
        assertEquals(new Position(boardWidth, boardHeight), inkyTwo.home(game));

        //PINKY - one block outside of the top left of the game board (-1,-1)
        assertEquals(new Position(-1, -1), pinkyTwo.home(game));

        //CLYDE - one block outside of the bottom left of the game board (-1, h)
        assertEquals(new Position(-1, boardHeight), clydeTwo.home(game));
    }


    // MOVE GHOST - STEP 1
    @Test
    public void moveGhostStepOneTest(){
        // check phase duration reduced by 1
        for (Ghost g : game.getGhosts()) {
            g.move(game);
            assertEquals("SCATTER:9", g.phaseInfo());
        }

        // check if 0 move to next phase
        // SCATTER to CHASE
        for (Ghost g : game.getGhosts()) {
            g.setPhase(Phase.SCATTER, 1);
            g.move(game);
            assertEquals(Phase.CHASE, g.getPhase());
        }

        // FRIGHTENED to CHASE
        for (Ghost g : game.getGhosts()) {
            g.setPhase(Phase.FRIGHTENED, 1);
            g.move(game);
            assertEquals(Phase.CHASE, g.getPhase());
        }
        // CHASE to SCATTER
        for (Ghost g : game.getGhosts()) {
            g.setPhase(Phase.CHASE, 1);
            g.move(game);
            assertEquals(Phase.SCATTER, g.getPhase());
        }
    }

//    // MOVE GHOST - STEP 2
//    // MAKE getTargetPosition & defaultTargetPosition public to test
//    @Test
//    public void moveGhostFrightenedTest(){
//
//        // FRIGHTENED - coordinates (defaultTargetPosition)
//        blinky.setPhase(Phase.FRIGHTENED, 10);
//        // test posiion (2,3) with board of width 4, height 5
//        blinky.setPosition(positionTwoThree);
//        // X = (2*24mod(8)) - 4 = -4
//        // Y = (3*36mod(10)) - 5 = 3
//        assertEquals(new Position(-4,3) , blinky.defaultTargetPosition(game));
//        assertEquals(blinky.defaultTargetPosition(game), blinky.getTargetPosition(game));
//    }
//
//    @Test
//    public void moveGhostScatterTest() {
//        // SCATTER - home
//        for (Ghost g : ghostList) {
//            assertEquals(g.home(game), g.getTargetPosition(game));
//        }
//    }
//
//    @Test
//    public void moveGhostChaseTest() {
//        // CHASE - chaseTarget
//        for (Ghost g : game.getGhosts()) {
//            g.setPhase(Phase.CHASE, 10);
//            assertEquals(g.chaseTarget(game), g.getTargetPosition(game));
//        }
//    }

    // STEP 3-5
    @Test
    public void moveGhostTestOne() {
        //MOVE 1 - Check Target Position when CHASE and choose smallest distance
        blinkyTwo.setPhase(Phase.CHASE, 3);
        game.getHunter().setPosition(new Position(2, 2));
        blinkyTwo.setPosition(new Position(1, 2));
        // SCATTER = target (-1, -1) so UP
        // FRIGHTENED = target (-4,-3) so UP
        // X X X X
        // X 0 0 X
        // X G H X
        // X 0 0 X
        // X X X X
        blinkyTwo.move(game);
        // 1 - Phase duration decreased
        // 2 - Target position should be chaseTarget
        // 3 - Direction with smallest distance is RIGHT
        // 4 - Direction set to RIGHT
        // 5 - Position step to (2,2)
        assertEquals(Direction.RIGHT, blinkyTwo.getDirection());
        assertEquals(new Position(2, 2), blinkyTwo.getPosition());
    }

    @Test
    public void moveGhostTestTwo() {
        // MOVE 2 - Check doesnt pick the opposite
        speedy.setPosition(new Position(1, 2));
        clydeTwo.setPosition(new Position (1,1));
        // X X X X
        // X G 0 X
        // X H 0 X
        // X 0 0 X
        // X X X X
        clydeTwo.move(game);
        // 1 - Phase duration decreased
        // 2 - Target position should be hunter
        // 3 - Direction with smallest distance is DOWN but this is opposite
        // 4 - Direction set to RIGHT
        // 5 - Position set to (2,2)
        assertEquals(Direction.RIGHT, clydeTwo.getDirection());
        assertEquals(new Position(2, 1), clydeTwo.getPosition());
    }

    @Test
    public void moveGhostTestThree() {
        //MOVE 3 - Check order, phase decreases, and home when SCATTER
        speedy.setPosition(new Position(1,1));
        clydeTwo.setPosition(new Position(2,2));
        clydeTwo.setDirection(Direction.DOWN);
        clydeTwo.setPhase(Phase.CHASE,1);
        // CHASE = target (1,1) so UP
        // FRIGHTENED = target (-4,-3) so UP
        //  X X X X
        //  X H 0 X
        //  X 0 G X
        //  X 0 0 X
        //  X X X X
        // *
        clydeTwo.move(game);
        // 1 - Phase duration decreased, now move to next phase as SCATTER
        assertEquals("SCATTER:10", clydeTwo.phaseInfo());
        // 2 - Target position is now home (-1, height)
        // 3 - Direction with smallest is DOWN or LEFT
        // 4 - Direction set to LEFT (due to order)
        // 5 - Position set to (1,2)
        assertEquals(Direction.LEFT, clydeTwo.getDirection());
        assertEquals(new Position(1, 2), clydeTwo.getPosition());
    }

    @Test
    public void moveGhostTestFour() {

        // MOVE 4 - Check Frightened position
        speedy.setPosition(new Position(2, 2));
        blinkyTwo.setPosition(new Position(2,3));
        // CHASE (target hunter) or SCATTER (top right) would be UP
        blinkyTwo.setPhase(Phase.FRIGHTENED, 2);
        //   X X X X
        //   X 0 0 X
        //   X 0 H X
        // * X 0 G X
        //   X X X X
        blinkyTwo.move(game);
        // 1 - Phase reduced by 1
        assertEquals("FRIGHTENED:1", blinkyTwo.phaseInfo());
        // 2 - Target position is now home (-4, 3)
        //      current position (2,3) with board of width 4, height 5
        //          X = (2*24mod(8)) - 4 = -4
        //          Y = (3*36mod(10)) - 5 = 3
        // 3 - Direction with smallest and pathable is UP
        // 4 - Direction set to UP
        // 5 - Position set to (2,2)
        assertEquals(Direction.LEFT, blinkyTwo.getDirection());
        assertEquals(new Position(1, 3), blinkyTwo.getPosition());
    }


    private class HunterEntity extends Hunter {

        public HunterEntity() {
            super();
        }

        public HunterEntity(Hunter hunter) {
            super(hunter);
        }

    }
    @Test
    public void testChaseTarget() {
        Hunter hunter = new HunterEntity();
        hunter.setPosition(new Position(5, 8));
        PacmanBoard board = new PacmanBoard(10, 18);
        game = new PacmanGame("title", "author",
                hunter, board);


        Clyde clyde = new Clyde();
        clyde.setPosition(new Position(3, 7));
        assertEquals(new Position(-1, 18), clyde.chaseTarget(game));

        Inky inky = new Inky();
        inky.setPosition(new Position(3,7));
        assertEquals(new Position(5, 10), inky.chaseTarget(game));

    }

    @Test
    public void testChaseTarget2() {
        Clyde clyde = new Clyde();

        game.getHunter().setPosition(new Position(8, 11));

        assertEquals(new Position(8,11), clyde.chaseTarget(game));
    }

    @After
    public void tearDown() throws Exception {
    }
}