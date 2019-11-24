package pacman.game;

import org.junit.Before;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.ghost.*;
import pacman.hunter.Speedy;
import pacman.score.ScoreBoard;
import pacman.util.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PacmanGameTest {

    private PacmanGame game;
    private Speedy speedy;
    private PacmanBoard board;
    private Blinky blinky;
    private Inky inky;
    private Pinky pinky;
    private Clyde clyde;


    @Before
    public void setUp() throws Exception {


        speedy = new Speedy();
        board = new PacmanBoard(4, 5);
        board.setEntry(new Position(1, 1), BoardItem.GHOST_SPAWN);
        board.setEntry(new Position(2, 3), BoardItem.PACMAN_SPAWN);
        game = new PacmanGame("title", "author", speedy, board);

        // one of each ghost
        blinky = (Blinky) game.getGhosts().get(0);
        inky = (Inky) game.getGhosts().get(1);
        pinky = (Pinky) game.getGhosts().get(2);
        clyde = (Clyde) game.getGhosts().get(3);

    }

    @Test
    public void constructorTest() {
        // test constructor correct
        // one of each ghost (getGhosts)
        assertEquals(GhostType.BLINKY, blinky.getType());
        for (Ghost g : game.getGhosts()) {
            assertEquals(new Position(1, 1), g.getPosition());
        }
        // given title (getTitle)
        assertEquals("title", game.getTitle());
        // given author (getAuthor)
        assertEquals("author", game.getAuthor());
        // given hunter (getHunter)
        assertEquals(speedy, game.getHunter());
        // ticks start at 0 (getTick)
        assertEquals(0, game.getTick());
        // level starts at 0 (getLevel)
        assertEquals(0, game.getLevel());
        // lives start at 4 (getLives)
        assertEquals(4, game.getLives());
        // empty scoreboard (getScores)
        assertEquals(new ScoreBoard(), game.getScores());
        // given board (getBoard)
        assertEquals(board, game.getBoard());
        // score of 0
        assertEquals(0, game.getScores().getScore());
    }

    @Test
    public void setLivesTest() {
        // choose max(0,given)
        game.setLives(5);
        assertEquals(5, game.getLives());
        game.setLives(-5);
        assertEquals(0, game.getLives());
    }

    @Test
    public void setLevelTest() {
        // choose max(0,given)
        game.setLevel(10);
        assertEquals(10, game.getLevel());
        game.setLevel(-10);
        assertEquals(0, game.getLevel());
    }

    @Test
    public void resetTest() {
        List<String> scoreList = new ArrayList();
        scoreList.add("Tean : 5");
        scoreList.add("Todd : 10");
        // change all variables
        game.setLives(10);
        game.setLevel(5);
        game.getScores().increaseScore(20);
        game.getScores().setScore("Tean", 5);
        game.getScores().setScore("Todd", 10);
        game.getBoard().setEntry(new Position(1, 2), BoardItem.BIG_DOT);
        game.getBoard().setEntry(new Position(3, 4), BoardItem.BIG_DOT_SPAWN);
        game.getBoard().setEntry(new Position(1, 1), BoardItem.GHOST_SPAWN);
        game.getBoard().setEntry(new Position(2, 2), BoardItem.PACMAN_SPAWN);
        game.getHunter().activateSpecial(5);
        game.getHunter().setPosition(new Position(1, 1));
        Blinky blinky = (Blinky) game.getGhosts().get(0);
        blinky.setPhase(Phase.CHASE, 4);
        blinky.setPosition(new Position(2, 2));
        game.reset();

        // lives set to 4 (was 10)
        assertEquals(4, game.getLives());
        // level set to 0 (was 5)
        assertEquals(0, game.getLevel());
        // Scoreboard reset - current score to 0 and empty (20, 2 entries)
        assertEquals(0, game.getScores().getScore());
        assertEquals(scoreList, game.getScores().getEntriesByName());
        // PacmanBoard reset (BIG DOT & WALL stay, new BIG DOT at spawn, DOT where none, ghostspawn & pacman spawn in tact)
        assertEquals(BoardItem.WALL, game.getBoard().getEntry(new Position(0, 0)));
        assertEquals(BoardItem.BIG_DOT, game.getBoard().getEntry(new Position(1, 2)));
        assertEquals(BoardItem.BIG_DOT, game.getBoard().getEntry(new Position(3, 4)));
        assertEquals(BoardItem.DOT, game.getBoard().getEntry(new Position(1, 3)));
        // Entities reset (Hunter alive, special not used, duration of 0 / Ghosts alive, scatter, duration of 10)
        assertEquals(0, game.getHunter().getSpecialDurationRemaining());
        assertEquals(false, game.getHunter().isSpecialActive());
        assertEquals(Phase.SCATTER, blinky.getPhase());
        assertEquals(10, blinky.getPhase().getDuration());
        // Entity positions set to spawn
        assertEquals(new Position(2, 2), game.getHunter().getPosition());
        assertEquals(new Position(1, 1), blinky.getPosition());
        // tick value reset to zero
        assertEquals(0, game.getTick());
    }

    @Test
    public void setGhostsFrightenedTest() {
        game.setGhostsFrightened();
        for (Ghost g : game.getGhosts()) {
            assertEquals(Phase.FRIGHTENED, g.getPhase());
            assertEquals(30, g.getPhase().getDuration());
        }
    }

    // TODO: 18/09/2019 -how to test not impacting internal copy
//    @Test
//    public void getGhostsTest() {
//        game.getGhosts().add(new Inky());
//        assertEquals(ghosts, game.getGhosts());
//    }

    @Test
    public void tickLivesZeroTest() {
        // lives 0 then do nothing i.e. level 0, score 0, tick 0
        game.setLives(0);
        game.tick();
        assertEquals(0, game.getLives());
        assertEquals(0, game.getLevel());
        assertEquals(0, game.getScores().getScore());
        assertEquals(0, game.getTick());
    }

    @Test
    public void tickTestOne() {
        // setup - set spawn locations
        List<Ghost> getGhosts = game.getGhosts();
        Blinky blinky = (Blinky) getGhosts.get(0);
        System.out.println(blinky.getPosition());

        // TEST ONE - no movement, no hits, all alive, board empty so level increase
        game.tick();
        // 1. Hunter doesnt move
        assertEquals(0, game.getScores().getScore());
        // 2. Hunter doesnt hit any ghosts
        // 3. Ghosts dont move (odd tick)
        // 4. Hunter still doesnt hit any ghosts
        // 5. No Ghosts are dead
        for (Ghost g : game.getGhosts()) {
            assertEquals(new Position(1, 1), g.getPosition());
            assertEquals(false, g.isDead());
            assertEquals(Phase.SCATTER, blinky.getPhase());
        }
        // 6. Hunter is not dead
        assertEquals(false, game.getHunter().isDead());
        assertEquals(4, game.getLives());
        // 7. Board is empty i.e. no pickup items, reset entities and board
        assertEquals(BoardItem.DOT, game.getBoard().getEntry(new Position(1, 2)));
        assertEquals(new Position(2, 3), game.getHunter().getPosition());
        //8. Level was increased, ticks stay at 0
        assertEquals(1, game.getLevel());
        assertEquals(0, game.getTick());


        // TEST TWO - hunter moves and kills a ghost, board not empty so increase tick
        game.getHunter().setPosition(new Position(2, 3));
        blinky.setPosition(new Position(2, 2));
        blinky.setPhase(Phase.FRIGHTENED, 10);
        game.getBoard().setEntry(new Position(1, 4), BoardItem.BIG_DOT);
        game.tick();
        // 1. Hunter moves
        // 2. Hunter hit blinky, blinky dies
        // 3. Ghosts dont move
        // 4. Hunter doesnt hit again
        // 5. Ghost is dead - reset and increase score
        for (Ghost g : game.getGhosts()) {
            if (g.getType() == GhostType.BLINKY) {
            } else {
                assertEquals(new Position(2, 1), g.getPosition());
                assertEquals(false, g.isDead());
                assertEquals(Phase.SCATTER, g.getPhase());

            }
        }
        assertEquals(210, game.getScores().getScore());
        //6. hunter not dead
        assertEquals(false, game.getHunter().isDead());
        assertEquals(4, game.getLives());
        // 7. board not empty i.e. entities and board not reset, level stays
        assertEquals(new Position(2, 2), game.getHunter().getPosition());
        assertEquals(BoardItem.BIG_DOT, game.getBoard().getEntry(new Position(1, 4)));
        assertEquals(1, game.getLevel());
        //8. Incease tick
        assertEquals(1, game.getTick());


        // TEST THREE - eat dot, kill hunter, increase tick
        game.getBoard().setEntry(new Position(2, 1), BoardItem.BIG_DOT);
        blinky.setPosition(new Position(2, 1));
        game.tick();
        // 1. Hunter moves and eats dot
        assertEquals(225, game.getScores().getScore());
        // 2. Hunter get hit
        // 3. Ghosts move
        // 4. Hunter doesnt hit again
        // 5. Ghosts not dead
        for (Ghost g : game.getGhosts()) {
            assertEquals(new Position(1, 1), g.getPosition());
            assertEquals(false, g.isDead());
            assertEquals(Phase.SCATTER, g.getPhase());
        }
        // 6. Hunter dead - decrease lives, reset all entities to spawn
        assertEquals(false, game.getHunter().isDead());
        assertEquals(3, game.getLives());
        assertEquals(new Position(2, 3), game.getHunter().getPosition());
        // 7. board not empty i.e. entities and board not reset, level stays
        assertEquals(BoardItem.BIG_DOT, game.getBoard().getEntry(new Position(1, 4)));
        assertEquals(BoardItem.BIG_DOT_SPAWN, game.getBoard().getEntry(new Position(2, 1)));
        assertEquals(1, game.getLevel());
        //8. Incease tick
        assertEquals(2, game.getTick());


        // TEST FOUR - even tick, ghosts move
    }
}
