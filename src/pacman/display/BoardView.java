package pacman.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.util.Pair;
import pacman.board.PacmanBoard;
import pacman.ghost.Ghost;
import pacman.util.Position;

/**
 * View representation of the Games playable area.
 * 
 * @given
 */
public class BoardView {

    // canvas
    private static final int CANVAS_WIDTH = 840;
    private static final int CANVAS_HEIGHT = 640;
    private static final int BLOCK_SIZE = 24;

    // dots
    private static final int DOT_SIZE = 4;
    private static final int BIG_DOT_SIZE = DOT_SIZE * 3;

    // Lives
    private static final int LIVES_SIZE = 24;
    private static final int LIVES_X_LOC = 30;
    private static final int LIVES_Y_LOC = CANVAS_HEIGHT - 20 - LIVES_SIZE;
    private static final int LIVES_GAP = 10;

    // Level
    private static final int LEVEL_X_LOC = 650;

    // Pacman
    private static final int PACMAN_ARC_START = 30;
    private static final int PACMAN_ARC_STOP = 360 - PACMAN_ARC_START * 2;


    private BoardViewModel viewModel;
    private Pane mainPane;
    private Canvas playArea;

    /**
     * Construct a View that displays the graphical board stored in
     * the given viewModel.
     * @param viewModel the mode to display in the view.
     * @given
     */
    public BoardView(BoardViewModel viewModel) {
        this.viewModel = viewModel;

        mainPane = new GridPane();
        playArea = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        mainPane.getChildren().addAll(playArea);
    }

    /**
     * Gets the main entry point for this View.
     * @return the root pane for the view.
     * @given
     */
    public Pane getPane() {
        return this.mainPane;
    }

    /**
     * Redraws the the board play area.
     * @given
     */
    public void redraw() {
        GraphicsContext context = playArea.getGraphicsContext2D();

        Position offset = new Position(
                (CANVAS_WIDTH - (viewModel.getBoard().getWidth() * BLOCK_SIZE))
                        / 2,
                ((CANVAS_HEIGHT - 30) - (viewModel.getBoard().getHeight()
                        * BLOCK_SIZE)) / 2);

        drawCanvas(context);
        drawLives(context);
        drawLevel(context);
        drawBoard(context, offset);
        drawPacman(context, offset);
        for (Pair<Position, String> ghost : viewModel.getGhosts()) {
            drawGhost(context, ghost.getKey().multiply(BLOCK_SIZE).add(offset),
                    ghost.getValue());
        }
    }

    /*
     * Draws the background fill on the given context.
     */
    private void drawCanvas(GraphicsContext gc) {
        gc.setFill(Color.web("#00033D"));
        gc.fillRect(0, 0, 840, 700);
    }

    /*
     * Draws the lives remaining of the player using the given context.
     */
    private void drawLives(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        for (int i = 0; i < viewModel.getLives(); i++) {
            gc.fillArc(LIVES_X_LOC + i * (LIVES_SIZE + LIVES_GAP), LIVES_Y_LOC,
                    LIVES_SIZE, LIVES_SIZE, PACMAN_ARC_START, PACMAN_ARC_STOP,
                    ArcType.ROUND);
        }
    }

    /*
     * Draws the level splash on the screen using the given context.
     */
    private void drawLevel(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(32));
        gc.fillText(String.format("Level %d", viewModel.getLevel()),
                LEVEL_X_LOC, LIVES_Y_LOC + 20);
    }

    /*
     * Draws the board of the game on the given context applying the
     * offset given to each point.
     */
    private void drawBoard(GraphicsContext gc, Position offset) {
        PacmanBoard board = viewModel.getBoard();

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Position point = new Position(x, y).multiply(BLOCK_SIZE)
                        .add(offset);

                switch (board.getEntry(new Position(x, y))) {
                    case WALL:
                        gc.setFill(Color.web("#3A3770"));
                        gc.fillRect(point.getX(), point.getY(), BLOCK_SIZE,
                                BLOCK_SIZE);
                        break;
                    case DOT:
                        gc.setFill(Color.YELLOW);
                        gc.fillRect(point.getX() + (BLOCK_SIZE - DOT_SIZE) / 2,
                                point.getY() + (BLOCK_SIZE - DOT_SIZE) / 2,
                                DOT_SIZE, DOT_SIZE);
                        break;
                    case BIG_DOT:
                        gc.setFill(Color.YELLOW);
                        gc.fillRoundRect(
                                point.getX() + (BLOCK_SIZE - BIG_DOT_SIZE) / 2,
                                point.getY() + (BLOCK_SIZE - BIG_DOT_SIZE) / 2,
                                BIG_DOT_SIZE, BIG_DOT_SIZE, 16, 16);
                        break;
                    case GHOST_SPAWN:
                        gc.setFill(Color.web("#AA3770"));
                        gc.fillRect(point.getX(), point.getY(), BLOCK_SIZE,
                                BLOCK_SIZE);
                        break;
                }
            }
        }
    }

    /*
     * Draws a static pacman on the context with the given offset.
     */
    private void drawPacman(GraphicsContext gc, Position offset) {
        gc.setFill(Color.web(viewModel.getPacmanColour()));
        int arcStart = viewModel.getPacmanMouthAngle();
        Position position = viewModel.getPacmanPosition().multiply(BLOCK_SIZE)
                .add(offset);
        gc.fillArc(position.getX(), position.getY(), LIVES_SIZE, LIVES_SIZE,
                arcStart, 360 - 2 * PACMAN_ARC_START, ArcType.ROUND);
    }

    /*
     * Draws a Ghost ( each bit speratly ) on the given context at the
     * location provided with a specified colour.
     */
    private void drawGhost(GraphicsContext gc, Position location,
            String colour) {
        gc.setFill(Color.web(colour));
        gc.fillOval(location.getX(), location.getY(), BLOCK_SIZE, BLOCK_SIZE);
        gc.fillRect(location.getX(), location.getY() + (BLOCK_SIZE / 2),
                BLOCK_SIZE, BLOCK_SIZE / 2);
        gc.setFill(Color.WHITE);
        gc.fillOval(location.getX() + 4, location.getY() + (BLOCK_SIZE / 2), 6,
                6);
        gc.fillOval(location.getX() + BLOCK_SIZE - 4 - 6,
                location.getY() + (BLOCK_SIZE / 2), 6, 6);
        gc.setFill(Color.BLACK);
        gc.fillOval(location.getX() + 6,
                location.getY() + (BLOCK_SIZE / 2) + 3, 2, 2);
        gc.fillOval(location.getX() + BLOCK_SIZE - 6 - 2,
                location.getY() + (BLOCK_SIZE / 2) + 3, 2, 2);
    }
}
