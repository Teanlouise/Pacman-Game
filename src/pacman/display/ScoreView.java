package pacman.display;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * Represents the view for the score display in the application.
 * 
 * @given
 */
public class ScoreView {

    // pane to hold the scores
    private Pane scorePane;

    /**
     * Constructs a ScoreView that displays the given ScoreViewModel.
     * 
     * @param viewModel the model to display in the view
     * @given
     */
    public ScoreView(ScoreViewModel viewModel) {

        VBox sideMenu = new VBox();
        sideMenu.setAlignment(Pos.CENTER);
        sideMenu.setSpacing(2);
        sideMenu.setStyle(
                "-fx-background-color: #00033D;" + "-fx-text-fill: white;");
        Label highScoresTitle = new Label("High Scores");
        highScoresTitle.setTextAlignment(TextAlignment.CENTER);
        highScoresTitle.setAlignment(Pos.CENTER);
        highScoresTitle.setMaxWidth(Double.MAX_VALUE);
        highScoresTitle.setStyle(
                "" + "-fx-text-fill: yellow;" + "-fx-font: 14px Tahoma;"
                        + "-fx-padding: 10 0 5 0;" + "-fx-underline: true;");
        Button sortByBtn = new Button("Sorting By: ?");
        sortByBtn.setMaxWidth(180);
        sortByBtn.setAlignment(Pos.CENTER);
        sortByBtn.setOnAction(e -> viewModel.switchScoreOrder());
        sortByBtn.textProperty().bindBidirectional(viewModel.getSortedBy());
        ListView<String> scoreList = new ListView<>();
        scoreList.setStyle("" + "-fx-background-color: transparent;");

        scoreList.setItems(viewModel.getScores());

        Label currentScoreTitle = new Label("Current Score");
        currentScoreTitle.setTextAlignment(TextAlignment.CENTER);
        currentScoreTitle.setStyle(
                "" + "-fx-text-fill: yellow;" + "-fx-font: 14px Tahoma;"
                        + "-fx-padding: 10 0 5 0;" + "-fx-underline: true;");
        Label currentScore = new Label("Score: ??");
        currentScore.setStyle("-fx-text-fill: white;");
        currentScore.setTextAlignment(TextAlignment.CENTER);
        currentScore.textProperty()
                .bindBidirectional(viewModel.getCurrentScoreProperty());

        VBox.setVgrow(scoreList, Priority.ALWAYS);

        sideMenu.getChildren().addAll(currentScoreTitle, currentScore);
        sideMenu.getChildren().addAll(highScoresTitle, sortByBtn, scoreList);

        this.scorePane = sideMenu;
    }

    /**
     * Gets the Pane that the scores are displayed on
     * 
     * @return score Pane
     * @given
     */
    public Pane getPane() {
        return this.scorePane;
    }
}
