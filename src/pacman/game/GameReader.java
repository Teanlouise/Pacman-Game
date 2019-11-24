package pacman.game;

import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.ghost.*;
import pacman.hunter.*;
import pacman.util.Direction;
import pacman.util.Position;
import pacman.util.UnpackableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * GameReader Reads in a saved games state and returns a game instance.
 */
public class GameReader {

    /**
     * Reads in a game according to the the following specification:
     * Note: any lines starting with a ';' are comments and are skipped.
     * A Game File has 3 blocks ( Board, Game, Scores ) which must be in order
     * of Board first, Game second and Scores last.
     * Each block is defined by its name enclosed in square brackets.
     * e.g: [Board], [Game], [Scores]
     * @param reader to read the save game from
     * @return a PacmanGame that reflects the state from the reader
     * @throws: UnpackableException - when the saved data is invalid.
     * IOException - when unable to read from the reader.
     */
    public static PacmanGame read(Reader reader) throws
            UnpackableException, IOException {

        // Iniatiate all the variables for the game
        PacmanBoard board = new PacmanBoard(1,1);
        Hunter hunter = new Hungry();
        PacmanGame game = new PacmanGame("title","author", hunter, board);
        BufferedReader r2 = new BufferedReader(reader);

        // Use currentStage to track order of blocks:
        // 0 - Board, 1 - Empty, 2 - Game, 3 - Empty, 4 - Score
        int currentStage = 0;
        try {
            for (String line = r2.readLine(); line != null; line = r2.readLine()) {
                // There must be no empty lines before the first block
                if (line.equals(System.lineSeparator()) || line.equals("")) {
                    // In between blocks there must be a single blank line.
                    currentStage++;
                } else if (line.charAt(0) == ';') {
                    // any lines starting with a ';' are comments and are skipped
                    continue;
                } else if (line.equals("[Board]") && currentStage == 0) {
                    // Board found first
                    board = readBoard(r2);
                    // Board complete, move to next stage
                    currentStage++;
                } else if (line.equals("[Game]") && currentStage == 2) {
                    // Game found second
                    game = readGame(game,board,hunter,r2);
                    // Game done, move to next stage
                    currentStage++;
                } else if (line.equals("[Scores]") && currentStage == 4) {
                    // Scores found third
                    readScores(r2, line, game);
                    // Score finished, the end
                    currentStage++;
                } else {
                    // Blocks not in order or extra spaces between
                    throw new UnpackableException();
                }
            }

        } catch (Exception e) {
            if (e instanceof IOException) {
                // file cannot be read
                throw new IOException();
            } else {
                // data is invalid
                throw new UnpackableException();
            }
        }

        // Check if have all blocks
        isInvalid(currentStage != 5);
        // Everything is valid
        return game;
    }

    /**
     * Check if the data is invalid according to the condition. If true throws
     * an Unpackable Exception.
     * @param cond when the data is invalid
     * @throws UnpackableException
     */
    private static void isInvalid(Boolean cond) throws UnpackableException {
        if (cond == true) {
            throw new UnpackableException();
        }
    }

    /**
     * Read the baord and set conditions according to the file
     * @param r2 to read each line of the board
     * @return board with all dimensions and items set as given in file
     * @throws UnpackableException if data invalid
     * @throws IOException if file cannot be read
     */
    private static PacmanBoard readBoard(BufferedReader r2) throws
            UnpackableException, IOException {

        // WIDTH,HEIGHT
        // First line in the block is a comma separated getWidth and getHeight.
        String dimension = r2.readLine();
        String[] dimensionArray = dimension.split(",");
        isInvalid(dimensionArray.length != 2);
        // Board dimensions must be greater than 0
        int width = Integer.parseInt(dimensionArray[0]);
        isInvalid(width < 1);
        int height = Integer.parseInt(dimensionArray[1]);
        isInvalid(height < 1);
        PacmanBoard board = new PacmanBoard(width, height);

        //BOARD SETUP
        for (int j = 0; j < height; j++) {
            String row = r2.readLine().trim(); //was strip
            isInvalid(row.length() != width);
            for (int i = 0; i < width; i++) {
                // Each character in these lines must be of the BoardItem keys
                board.setEntry(new Position(i, j), BoardItem.getItem(row.charAt(i)));
            }
        }

        return board;
    }

    /**
     * Read [Game] block of the board and assign all parameters
     * @param game being read
     * @param board for this game instance
     * @param hunter for this game instance
     * @param r2 to read line at a time
     * @return the game with all assignments set with new given parameters
     * @throws UnpackableException when data is invalid
     * @throws IOException if game cannot be read
     */
    private static PacmanGame readGame(PacmanGame game, PacmanBoard board, Hunter hunter, BufferedReader r2) throws
            UnpackableException, IOException {

        // Contains newline separated list of assignments
        //  Can be given in any order which are unique.
        String title = null;
        String author = null;
        int lives = 4;
        int level = 0;
        int score = 0;
        Blinky blinky = new Blinky();
        Inky inky = new Inky();
        Pinky pinky = new Pinky();
        Clyde clyde = new Clyde();

        // Use gameCount to keep track of how many assignments there are
        int gameCount = 0;
        // An assignment is a 'Key = Value' where the Key and Value
        for (int i = 0; i < 10; i++) {
            String assignments = r2.readLine();
            String[] valueArray = assignments.split(" = ");
            switch (valueArray[0]) {
                case "title":
                    title = valueArray[1];
                    gameCount += 1;
                    break;
                case "author":
                    author = valueArray[1];
                    gameCount += 2;
                    break;
                case "lives":
                    lives = Integer.parseInt(valueArray[1]);
                    isInvalid(lives < 0);
                    gameCount += 3;
                    break;
                case "level":
                    level = Integer.parseInt(valueArray[1]);
                    isInvalid(level < 0);
                    gameCount += 4;
                    break;
                case "score":
                    score = Integer.parseInt(valueArray[1]);
                    isInvalid(score < 0);
                    gameCount += 5;
                    break;
                case "hunter":
                    hunter = readHunter(valueArray, hunter, board);
                    gameCount += 6;
                    break;
                case "blinky":
                    blinky = (Blinky) readGhost(valueArray, new Blinky(), board);
                    gameCount += 7;
                    break;
                case "inky":
                    inky = (Inky) readGhost(valueArray, new Inky(), board);
                    gameCount += 8;
                    break;
                case "pinky":
                    pinky = (Pinky) readGhost(valueArray, new Pinky(), board);
                    gameCount += 9;
                    break;
                case "clyde":
                    clyde = (Clyde) readGhost(valueArray, new Clyde(), board);
                    gameCount += 10;
                    break;
            }
        }

        isInvalid(gameCount != 55);
        // There are 10 assignments of each, so setup game
        game = new PacmanGame(title, author, hunter, board);
        game.setLevel(level);
        game.setLives(lives);
        game.getScores().increaseScore(score);
        List<Ghost> ghostList = game.getGhosts();
        ghostList.set(0, blinky);
        ghostList.set(1, inky);
        ghostList.set(2, pinky);
        ghostList.set(3, clyde);

        return game;
    }

    /**
     * Read and set the conditions for the hunter according to the file
     * @param valueArray of all assignments in the game
     * @param hunter initiated for this game
     * @return the hunter with all the new parameters
     */
    private static Hunter readHunter(String[] valueArray, Hunter hunter, PacmanBoard board)
            throws UnpackableException {

        // HUNTER - A comma separated list of attributes in the following order:
        // x, y, DIRECTION, special duration, HunterType
        String[] hunterArray = valueArray[1].split(",");
        isInvalid(hunterArray.length != 5);

        // POSITION - where x and y are integers
        int x = Integer.parseInt(hunterArray[0]);
        isInvalid(x >= board.getWidth() || x < 0);
        int y = Integer.parseInt(hunterArray[1]);
        isInvalid(y >= board.getHeight() || y < 0);
        Position hunterPosition = new Position(x, y);

        // DIRECTION - string representation of a DIRECTION
        Direction hunterDirection = Direction.valueOf(hunterArray[2]);

        // SPECIAL DURATION - is a integer greater than or equal to zero.
        int hunterSpecialDuration = Integer.parseInt(hunterArray[3]);
        isInvalid(hunterSpecialDuration < 0);

        // HUNTER TYPE
        switch (HunterType.valueOf(hunterArray[4])) {
            case HUNGRY:
                hunter = new Hungry();
                break;
            case PHASEY:
                hunter = new Phasey();
                break;
            case SPEEDY:
                hunter = new Speedy();
                break;
            case PHIL:
                hunter = new Phil();
                break;
        }

        // HUNTER CHOSEN - set conditions
        hunter.setPosition(hunterPosition);
        hunter.setDirection(hunterDirection);
        hunter.activateSpecial(hunterSpecialDuration);

        return hunter;
    }

    /**
     * Read and set the conditions for each ghost according to the file
     * @param valueArray the string of the ghost
     * @param ghost type to be initiated
     * @return ghost with all parameters set as given in file
     */
    private static Ghost readGhost(String[] valueArray, Ghost ghost, PacmanBoard board)
            throws UnpackableException {

        // blinky|inky|pinky|clyde
        // A comma separated list of attributes in the following order:
        //x,y,DIRECTION,PHASE:PhaseDuration
        String[] ghostArray = valueArray[1].split(",");
        isInvalid(ghostArray.length != 4);

        //POSITION - where x and y are Integers,
        int x = Integer.parseInt(ghostArray[0]);
        isInvalid(x >= board.getWidth() || x < 0);
        int y = Integer.parseInt(ghostArray[1]);
        isInvalid(y >= board.getHeight() || y < 0);
        Position ghostPosition = new Position(x, y);
        ghost.setPosition(ghostPosition);

        // DIRECTION - string representation of a DIRECTION
        Direction ghostDirection = Direction.valueOf(ghostArray[2]);
        ghost.setDirection(ghostDirection);

        // PHASE - a string representation of the PhaseType with a
        // duration that is an Integer greater than or equal to zero
        String[] ghostPhaseArray = ghostArray[3].split(":");
        Phase ghostPhase = Phase.valueOf(ghostPhaseArray[0]);
        int ghostPhaseDuration = Integer.parseInt(ghostPhaseArray[1]);
        isInvalid(ghostPhaseDuration < 0);
        ghost.setPhase(ghostPhase, ghostPhaseDuration);

        return ghost;
    }

    /**
     * Read [Game] block of the board and check all parameters
     * @param r2 to read each line of the scores
     * @param line of the scores
     * @return the map of scores for this game according to the file
     * @throws IOException if file cannot be read
     */
    private static void readScores(BufferedReader r2, String line, PacmanGame game)
            throws IOException, UnpackableException{

        // A newline seperated list of unique scores in form of: 'Name : Value'
        Map<String, Integer> scoreList = new HashMap<>();
        // Keep track of order of scores
        List<String> myList = new ArrayList<String>();
        while ((line = r2.readLine()) != null) {
            String[] scoreArray = line.split(" : ");
            // entry must be unique, check if already exists
            isInvalid(scoreList.get(scoreArray[0]) != null);
            // where name is a string and value is an Integer.
            scoreList.put(scoreArray[0], Integer.parseInt(scoreArray[1]));
            // add to order tracking
            myList.add(line);
        }

        // set the scores to the game
        game.getScores().setScores(scoreList);

        // check the order of the scores matches getEntriesByName()
        List<String> orderedList = game.getScores().getEntriesByName();
        for (int i = 0; i< myList.size(); i++) {
            isInvalid(!(myList.get(i).equals(orderedList.get(i))));
        }
    }
}