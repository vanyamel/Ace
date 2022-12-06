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
    public BoggleView(){
        //this.game = new BoggleGame();
    }

public BorderPane createBoard(){
        game = new BoggleGame();
        String Letters = "helloalinadeem19";
//        if (!this.game.getLetters().isEmpty()) {
//            Letters = this.game.getLetters();
//        }

    BorderPane borderPane = new BorderPane();
    borderPane.setStyle("-fx-background-color: #ffd0fe;");
    borderPane.setPadding(new Insets(10, 10, 10, 10));

    Button Nightmode = new Button("NightMode");
    Button Hints = new Button("Hints");
    Button TimeRush = new Button("Time Rush");
    Button SurpriseMechanic = new Button("Surprise Mechanic");
    Hints.setFont(new Font(14));
    TimeRush.setFont(new Font(14));
    SurpriseMechanic.setFont(new Font(14));
    Nightmode.setFont(new Font(14));

    Hints.setPrefWidth(Integer.MAX_VALUE);
    TimeRush.setPrefWidth(Integer.MAX_VALUE);
    SurpriseMechanic.setPrefWidth(Integer.MAX_VALUE);
    Nightmode.setPrefWidth(Integer.MAX_VALUE);

    HBox controls = new HBox(10, Hints, TimeRush, SurpriseMechanic,Nightmode);
    controls.setPadding(new Insets(10, 10, 10, 10));
    controls.setAlignment(Pos.TOP_CENTER);
    controls.setPrefHeight(75);
    borderPane.setTop(controls);

//text try
    Text text = new Text();
    choice = "1";
    output = "gg ";
    text.setText(output+"\n"); // make variable containing our text
    text.setFont(new Font(12));
    text.setStyle("-fx-background-color: #ceffc6;-fx-padding: 50px;");
    Button enter = new Button("ENTER");
    TextField input = new TextField();
    VBox comms = new VBox(text,input, enter);
    comms.setPrefWidth(300);
    comms.setAlignment(Pos.CENTER);
    borderPane.setRight(comms);
    enter.setOnAction(e -> choice = input.getText());


//

    Label scoreLabel = new Label("Player Score is: 0");
    scoreLabel.setFont(new Font(12));
    scoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");


    Label cscoreLabel = new Label("Computer Score is: 0");
    cscoreLabel.setFont(new Font(12));
    cscoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");

//    VBox cscoreBox = new VBox(10, scoreLabel, cscoreLabel);
//    cscoreBox.setPadding(new Insets(10, 10, 10, 10));
//    cscoreBox.setSpacing(30);
//    cscoreBox.setAlignment(Pos.TOP_CENTER);
//    borderPane.setRight(cscoreBox);
    //

    Label wordCount = new Label("Number of words found: ");
    wordCount.setFont(new Font(12));
    wordCount.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");


    Label wordLabel = new Label("Words Found: ");
    wordLabel.setFont(new Font(12));
    wordLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");

    VBox wordBox = new VBox(10,scoreLabel,cscoreLabel,wordCount, wordLabel);
    wordBox.setPadding(new Insets(10, 10, 10, 10));
    wordBox.setSpacing(30);
    wordBox.setAlignment(Pos.TOP_CENTER);
    wordBox.setPrefWidth(200);
    borderPane.setLeft(wordBox);
//


//
    Button button1 = new Button(String.valueOf(Letters.charAt(0)));
    Button button2 = new Button(String.valueOf(Letters.charAt(1)));
    Button button3 = new Button(String.valueOf(Letters.charAt(2)));
    Button button4 = new Button(String.valueOf(Letters.charAt(3)));
    Button button5 = new Button(String.valueOf(Letters.charAt(4)));
    Button button6 = new Button(String.valueOf(Letters.charAt(5)));
    Button button7 = new Button(String.valueOf(Letters.charAt(6)));
    Button button8 = new Button(String.valueOf(Letters.charAt(7)));
    Button button9 = new Button(String.valueOf(Letters.charAt(8)));
    Button button10 = new Button(String.valueOf(Letters.charAt(9)));
    Button button11 = new Button(String.valueOf(Letters.charAt(10)));
    Button button12 = new Button(String.valueOf(Letters.charAt(11)));
    Button button13 = new Button(String.valueOf(Letters.charAt(12)));
    Button button14 = new Button(String.valueOf(Letters.charAt(13)));
    Button button15 = new Button(String.valueOf(Letters.charAt(14)));
    Button button16 = new Button(String.valueOf(Letters.charAt(15)));
    button1.setPrefWidth(Integer.MAX_VALUE);
    button2.setPrefWidth(Integer.MAX_VALUE);
    button3.setPrefWidth(Integer.MAX_VALUE);
    button4.setPrefWidth(Integer.MAX_VALUE);
    button5.setPrefWidth(Integer.MAX_VALUE);
    button6.setPrefWidth(Integer.MAX_VALUE);
    button7.setPrefWidth(Integer.MAX_VALUE);
    button8.setPrefWidth(Integer.MAX_VALUE);
    button9.setPrefWidth(Integer.MAX_VALUE);
    button10.setPrefWidth(Integer.MAX_VALUE);
    button11.setPrefWidth(Integer.MAX_VALUE);
    button12.setPrefWidth(Integer.MAX_VALUE);
    button13.setPrefWidth(Integer.MAX_VALUE);
    button14.setPrefWidth(Integer.MAX_VALUE);
    button15.setPrefWidth(Integer.MAX_VALUE);
    button16.setPrefWidth(Integer.MAX_VALUE);
    button1.setPrefHeight(Integer.MAX_VALUE);
    button2.setPrefHeight(Integer.MAX_VALUE);
    button3.setPrefHeight(Integer.MAX_VALUE);
    button4.setPrefHeight(Integer.MAX_VALUE);
    button5.setPrefHeight(Integer.MAX_VALUE);
    button6.setPrefHeight(Integer.MAX_VALUE);
    button7.setPrefHeight(Integer.MAX_VALUE);
    button8.setPrefHeight(Integer.MAX_VALUE);
    button9.setPrefHeight(Integer.MAX_VALUE);
    button10.setPrefHeight(Integer.MAX_VALUE);
    button11.setPrefHeight(Integer.MAX_VALUE);
    button12.setPrefHeight(Integer.MAX_VALUE);
    button13.setPrefHeight(Integer.MAX_VALUE);
    button14.setPrefHeight(Integer.MAX_VALUE);
    button15.setPrefHeight(Integer.MAX_VALUE);
    button16.setPrefHeight(Integer.MAX_VALUE);


    GridPane gridPane = new GridPane();

    gridPane.add(button1, 0, 0, 1, 1);

    gridPane.add(button2, 1, 0, 1, 1);
    gridPane.add(button3, 2, 0, 1, 1);
    gridPane.add(button4, 3, 0, 1, 1);
    gridPane.add(button5, 0, 1, 1, 1);
    gridPane.add(button6, 1, 1, 1, 1);
    gridPane.add(button7, 2, 1, 1, 1);
    gridPane.add(button8, 3, 1, 1, 1);
    gridPane.add(button9, 0, 2, 1, 1);
    gridPane.add(button10, 1, 2, 1, 1);
    gridPane.add(button11, 2, 2, 1, 1);
    gridPane.add(button12, 3, 2, 1, 1);
    gridPane.add(button13, 0, 3, 1, 1);
    gridPane.add(button14, 1, 3, 1, 1);
    gridPane.add(button15, 2, 3, 1, 1);
    gridPane.add(button16, 3, 3, 1, 1);
    gridPane.setAlignment(Pos.CENTER);
    borderPane.setCenter(gridPane);


    Label ScoreM = createLabel("Score Multiplier: ");
    //bottom.setStyle("-fx-padding: 10px;");
    ScoreM.setStyle("-fx-background-color: #befaff;-fx-padding: 10px;");
    Label Time = createLabel("Time: ");
    //bottom.setStyle("-fx-padding: 10px;");
    Time.setStyle("-fx-background-color: #befaff;-fx-padding: 10px;");

    VBox timeRush = new VBox(10, Time, ScoreM);
    timeRush.setPadding(new Insets(10, 10, 10, 10));
    timeRush.setAlignment(Pos.BOTTOM_LEFT);
    borderPane.setBottom(timeRush);



//    Label scoreLabel = new Label("Player Score is: 0");
//    scoreLabel.setFont(new Font(12));
//    scoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");
//
//
//    Label cscoreLabel = new Label("Computer Score is: 0");
//    cscoreLabel.setFont(new Font(12));
//    cscoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");
//
//    VBox cscoreBox = new VBox(10, scoreLabel, cscoreLabel);
//    cscoreBox.setPadding(new Insets(10, 10, 10, 10));
//    cscoreBox.setSpacing(30);
//    cscoreBox.setAlignment(Pos.TOP_CENTER);
//    borderPane.setRight(cscoreBox);
    return borderPane;
}

    private static Label createLabel(String text){
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
