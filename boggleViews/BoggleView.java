package boggleViews;

import boggle.BoggleGame;
import boggle.BoggleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BoggleView {
    // attempting to put together a proper looking grid, trial and error
    private BoggleGame game;

    private String choice;

    private String output;

    public BoggleView() {
        //this.game = new BoggleGame();
    }

    private Button[] buttons = new Button[16];
    private String letters;

    public BorderPane createBoard() {
        game = new BoggleGame();
        letters = game.initRound();

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #ffd0fe;");
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        Button nightmode = new Button("NightMode");
        Button hints = new Button("Hints");
        Button timeRush = new Button("Time Rush");
        Button surpriseMechanic = new Button("Surprise Mechanic");
        hints.setFont(new Font(14));
        timeRush.setFont(new Font(14));
        surpriseMechanic.setFont(new Font(14));
        nightmode.setFont(new Font(14));

        hints.setPrefWidth(Integer.MAX_VALUE);
        timeRush.setPrefWidth(Integer.MAX_VALUE);
        surpriseMechanic.setPrefWidth(Integer.MAX_VALUE);
        nightmode.setPrefWidth(Integer.MAX_VALUE);

        HBox controls = new HBox(10, hints, timeRush, surpriseMechanic, nightmode);
        controls.setPadding(new Insets(10, 10, 10, 10));
        controls.setAlignment(Pos.TOP_CENTER);
        controls.setPrefHeight(75);
        borderPane.setTop(controls);

//text try
        Text text = new Text();
        choice = "1";
        output = "gg ";
        text.setText(output + "\n"); // make variable containing our text
        text.setFont(new Font(12));
        text.setStyle("-fx-background-color: #ceffc6;-fx-padding: 50px;");
        Button enter = new Button("ENTER");
        TextField input = new TextField();
        VBox comms = new VBox(text, input, enter);
        comms.setPrefWidth(300);
        comms.setAlignment(Pos.CENTER);
        borderPane.setRight(comms);
        enter.setOnAction(e -> {
            BoggleGame.MoveResult res = game.humanMoveOnce(input.getText());

            switch(res) {
                case BAD_WORD -> {

                }
                case EMPTY -> {

                }
                case WORD_FOUND -> {

                }
            }
        });

        Label scoreLabel = new Label("Player Score is: 0");
        scoreLabel.setFont(new Font(12));
        scoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");


        Label cscoreLabel = new Label("Computer Score is: 0");
        cscoreLabel.setFont(new Font(12));
        cscoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");

        Label wordCount = new Label("Number of words found: ");
        wordCount.setFont(new Font(12));
        wordCount.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");


        Label wordLabel = new Label("Words Found: ");
        wordLabel.setFont(new Font(12));
        wordLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");

        VBox wordBox = new VBox(10, scoreLabel, cscoreLabel, wordCount, wordLabel);
        wordBox.setPadding(new Insets(10, 10, 10, 10));
        wordBox.setSpacing(30);
        wordBox.setAlignment(Pos.TOP_CENTER);
        wordBox.setPrefWidth(200);
        borderPane.setLeft(wordBox);

        for (int i = 0; i < buttons.length; ++i) {
            buttons[i] = new Button(String.valueOf(letters.charAt(i)));
            buttons[i].setPrefWidth(Integer.MAX_VALUE);
            buttons[i].setPrefHeight(Integer.MAX_VALUE);

            Button button = buttons[i];
            buttons[i].setOnAction(e -> {
                input.setText(input.getText() + button.getText());
            });
        }

        GridPane gridPane = new GridPane();

        gridPane.add(buttons[0], 0, 0, 1, 1);

        gridPane.add(buttons[1], 1, 0, 1, 1);
        gridPane.add(buttons[2], 2, 0, 1, 1);
        gridPane.add(buttons[3], 3, 0, 1, 1);
        gridPane.add(buttons[4], 0, 1, 1, 1);
        gridPane.add(buttons[5], 1, 1, 1, 1);
        gridPane.add(buttons[6], 2, 1, 1, 1);
        gridPane.add(buttons[7], 3, 1, 1, 1);
        gridPane.add(buttons[8], 0, 2, 1, 1);
        gridPane.add(buttons[9], 1, 2, 1, 1);
        gridPane.add(buttons[10], 2, 2, 1, 1);
        gridPane.add(buttons[11], 3, 2, 1, 1);
        gridPane.add(buttons[12], 0, 3, 1, 1);
        gridPane.add(buttons[13], 1, 3, 1, 1);
        gridPane.add(buttons[14], 2, 3, 1, 1);
        gridPane.add(buttons[15], 3, 3, 1, 1);
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);

        Label ScoreM = createLabel("Score Multiplier: ");
        //bottom.setStyle("-fx-padding: 10px;");
        ScoreM.setStyle("-fx-background-color: #befaff;-fx-padding: 10px;");
        Label Time = createLabel("Time: ");
        //bottom.setStyle("-fx-padding: 10px;");
        Time.setStyle("-fx-background-color: #befaff;-fx-padding: 10px;");

        VBox timeRushBox = new VBox(10, Time, ScoreM);
        timeRushBox.setPadding(new Insets(10, 10, 10, 10));
        timeRushBox.setAlignment(Pos.BOTTOM_LEFT);
        borderPane.setBottom(timeRushBox);


        return borderPane;
    }

    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-padding: 10px;");
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return label;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
