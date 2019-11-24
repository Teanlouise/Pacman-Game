package pacman.display;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.LinkedList;
/**
 * Main entry point for the Pacman View.
 * @given
 */
public class MainView {

    // help information
    private static final String help =
            "" + "Instructions\n---------\nWASD :: Direction controls\n"
                    + "R :: Reset Game\n" + "P :: Pause Game\n"
                    + "O :: Activate Special";
    // models
    private MainViewModel viewModel;
    private ScoreView scoreView;
    private BoardView boardView;
    // jfx stages
    private Stage root;
    private Group rootGroup;
    // button press action queue
    private LinkedList<String> input;

    /**
     * MainView of the Pacman Game made for CSSE2002 at UQ.
     *
     * @param parent stage to add gui items to.
     * @param viewModel to use to get the games information.
     * @given
     */
    public MainView(Stage parent, MainViewModel viewModel) {
        this.root = parent;
        this.viewModel = viewModel;

        // set the title
        root.setTitle("CSSE2002/7023 PacMan");

        // set the window size
        root.setWidth(1080);
        root.setHeight(720);
        // create the scene
        rootGroup = new Group();
        Scene rootScene = new Scene(rootGroup);
        rootScene.getStylesheets().add("style.css");
        root.setScene(rootScene);
        // enable the event handlers
        input = new LinkedList<>();

        // This grabs key presses and adds it to the queue
        rootScene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            input.push(code);
        });

        boardView = new BoardView(viewModel.getBoardVM());
        scoreView = new ScoreView(viewModel.getScoreVM());

        viewModel.isGameOver().addListener(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Game Over");
            dialog.setContentText("Please enter your name:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);

            dialog.show();
            dialog.setOnHidden(closeEvent -> {
                String name = dialog.getEditor().getText();

                if (name != null && !name.isEmpty()) {
                    viewModel.getScoreVM().setPlayerScore(name,
                            viewModel.getScoreVM().getCurrentScore());
                }
            });
        });

        createWindow();
    }

    /**
     * Ticks and updates the game though the ViewModel. Also
     * applies queued user input.
     * @given
     */
    public void run() {
        new AnimationTimer() {

            public void handle(long currentNanoTime) {
                while (!input.isEmpty()) {
                    String key = input.pop();
                    viewModel.accept(key);
                    boardView.redraw();
                }
                viewModel.tick();
                viewModel.update();
                boardView.redraw();

            }
        }.start();

        // Show the game screen
        this.root.show();
    }

    /*
     * Creates the top level window at the fixed width and height.
     */
    private void createWindow() {
        HBox mainArea = new HBox();
        mainArea.setMaxSize(1080, 720);

        Label mapTitle = new Label();
        mapTitle.setTextAlignment(TextAlignment.CENTER);
        mapTitle.setMaxHeight(20);
        mapTitle.setMaxWidth(Double.MAX_VALUE);
        mapTitle.setAlignment(Pos.CENTER);
        mapTitle.textProperty().bindBidirectional(viewModel.getTitle());
        mapTitle.getStyleClass().add("header-text");


        Label pausedInfo = new Label();
        pausedInfo.setTextAlignment(TextAlignment.CENTER);
        pausedInfo.setMaxHeight(20);
        pausedInfo.setMaxWidth(Double.MAX_VALUE);
        pausedInfo.setAlignment(Pos.CENTER);
        pausedInfo.getStyleClass().add("header-text");
        pausedInfo.setText(viewModel.isPaused().getValue() ? "Paused" : "");
        viewModel.isPaused().addListener(e -> {
            pausedInfo.setText(
                    viewModel.isPaused().getValue() ? "Paused" : "");
        });
        
        
        
        VBox gameSide = new VBox();

        gameSide.getChildren().addAll(mapTitle, pausedInfo, 
                boardView.getPane());

        VBox infoSide = new VBox();
        infoSide.setMaxHeight(Double.MAX_VALUE);
        infoSide.setAlignment(Pos.TOP_CENTER);
        infoSide.setStyle("" + "-fx-background-color: #00033D;");
        VBox.setVgrow(scoreView.getPane(), Priority.ALWAYS);

        Button saveBtn = new Button("SAVE");
        saveBtn.setMaxWidth(180);
        saveBtn.setAlignment(Pos.CENTER);
        saveBtn.setOnAction(e -> viewModel.save());

        TextArea helpBox = new TextArea(help);
        helpBox.setEditable(false);
        helpBox.setFocusTraversable(false);
        helpBox.setWrapText(true);

        infoSide.getChildren().addAll(helpBox, saveBtn, scoreView.getPane());
        mainArea.getChildren().addAll(gameSide, infoSide);

        rootGroup.getChildren().add(mainArea);
    }
}
