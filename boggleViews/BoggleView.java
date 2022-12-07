package boggleViews;

import boggle.BoggleGame;
import boggle.surprise.BadBetException;
import boggle.surprise.BetMode;
import boggle.surprise.Bets;
import boggleViews.themes.LightTheme;
import boggleViews.themes.NightTheme;
import boggleViews.themes.Theme;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import boggle.tts.Speaker;


public class BoggleView {
    private BoggleGame game;

    private boolean nmIntiated = false;
    private HBox bottomText = new HBox();
    private BorderPane borderPane = new BorderPane();
    private GridPane gridPane = new GridPane();
    private String choice;
    private String output;

    private long startTime;
    private String timerush = "";
    private TextField input = new TextField();

    private Text text = new Text();

    private Text textBottom = new Text();
    private String letters;

    //this boolean checks makes sure User cant call hints or timerush after the round ends
    private boolean roundEnded;
    private boolean Check = false;

    private boolean first_round = true;

    private Button[] buttons = new Button[16];

    private Speaker speaker;

    private Theme theme;

    private boolean betsVisible = false;

    public BoggleView() {
        this.speaker = Speaker.getInstance();
        this.game = new BoggleGame();
    }

    Button nightmode;

    //This creates the board , it intilizes the letters of the board to button so it can be used to click and get the value of the button
    //to the textfield. This also puts all the letter buttons in a gridpane which creates a grid with the buttons. This is also creates a borderpane which everything sits on
    //the borderpane gets divided to Vbox,Hbox, text filed, Text and gridpane. This is also implements all the user stories by merging them with textfield inputs
    //It also holds buttons to toggle which ever feature you want to select
    public BorderPane createBoard() {
        this.roundEnded = false;
        // sets borderpane color and puts paddings around it
        borderPane.setStyle("-fx-background-color: #C4A484;");
        borderPane.setPadding(new Insets(10, 10, 10, 10));


        // Creates feature buttons and formats the text with fonts, size and alignment
        this.nightmode = new Button("NightMode");
        Button hints = new Button("Hint");
        Button timeRush = new Button("Time Rush");
        Button surpriseMechanic = new Button("Surprise Mechanic");
        Button tts = new Button("Text-To-Speech: Off");
        hints.setFont(new Font(14));
        timeRush.setFont(new Font(14));
        surpriseMechanic.setFont(new Font(14));
        nightmode.setFont(new Font(14));
        tts.setFont(new Font(14));

        hints.setPrefWidth(Integer.MAX_VALUE);
        timeRush.setPrefWidth(Integer.MAX_VALUE);
        surpriseMechanic.setPrefWidth(Integer.MAX_VALUE);
        nightmode.setPrefWidth(Integer.MAX_VALUE);
        tts.setPrefWidth(Integer.MAX_VALUE);

        // Aligns buttons for features at top of borderpane by using HBox for horizontal alignment
        HBox controls = new HBox(10, tts, hints, timeRush, surpriseMechanic, nightmode);
        controls.setPadding(new Insets(10, 10, 10, 10));
        controls.setAlignment(Pos.CENTER);
        controls.setPrefHeight(75);
        borderPane.setTop(controls);
        controls.setMaxWidth(980);

        //Sets all feature buttons to an action to initiate the feature gameplay/mode
        hints.setOnMouseReleased(e -> {
            if (roundEnded)
                return;

            if (game.hintAllowed()) {
                text.setText("You already had an hint!");
            } else {
                hintInstructions(textBottom);
                text.setText("Starts with " + game.hintWord().toLowerCase().charAt(0) + " and ends with " + game.hintWord().toLowerCase().charAt(game.hintWord().length() - 1));
                game.setpScore(game.getpScore() -1);
            }
        });

        timeRush.setOnMouseReleased(e -> {
            if (!roundEnded) {
                this.startTime = System.currentTimeMillis();
                this.Check = true;
                TrInstructions(textBottom);
                text.setText("You Initiated Time Rush!!\nBE QUICK FOR MORE POINTS!!");
            }
        });

        tts.setOnMouseReleased(e -> {
            this.speaker.enabled = !this.speaker.enabled;

            if (speaker.enabled) {
                tts.setText("Text-To-Speech: On");
            } else {
                tts.setText("Text-To-Speech: Off");
            }
        });

        nightmode.setOnMouseReleased(e -> {
            this.nmIntiated = !this.nmIntiated;

            switchAppearance(nmIntiated);
        });


        // creates and formats labels to Hold and display Scores and words found
        Label scoreLabel = new Label("Player Score is: "+game.getpScore() );
        scoreLabel.setFont(new Font(12));
        scoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");


        Label cscoreLabel = new Label("Computer Score is: " );
        cscoreLabel.setFont(new Font(12));
        cscoreLabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");

        Label wordCount = new Label("Number of words found: ");
        wordCount.setFont(new Font(12));
        wordCount.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");

        Label pWordlabel = new Label("Computer Words Found: ");
        pWordlabel.setFont(new Font(12));
        pWordlabel.setAlignment(Pos.TOP_CENTER);
        pWordlabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");
        pWordlabel.setPrefHeight(Integer.MAX_VALUE);
        pWordlabel.setPrefWidth(Integer.MAX_VALUE);


        Label cWordlabel = new Label("Computer Words Found: ");
        cWordlabel.setFont(new Font(12));
        cWordlabel.setAlignment(Pos.TOP_CENTER);
        cWordlabel.setStyle("-fx-background-color: #ceffc6;-fx-padding: 10px;");
        cWordlabel.setPrefHeight(Integer.MAX_VALUE);
        cWordlabel.setPrefWidth(Integer.MAX_VALUE);


        // Alligns buttons vertically on the left side of borderpane to display
        VBox wordBox = new VBox(10, scoreLabel, cscoreLabel, wordCount, pWordlabel, cWordlabel);
        wordBox.setPadding(new Insets(10, 10, 10, 10));
        wordBox.setSpacing(30);
        wordBox.setAlignment(Pos.TOP_CENTER);
        wordBox.setPrefWidth(200);
        borderPane.setLeft(wordBox);

        // This calls the initRound method in Bogglegame to initilize the board and get our random letter input for the grid
        letters = game.initRound();

        // creates button and textfield to get inputs, and also formats text font and displays text through Text
        Button enter = new Button("ENTER");
        output = "Enter a word ";
        text.setText(output + "\n");
        text.setFont(new Font(12));
        text.setStyle("-fx-background-color: #ceffc6;-fx-padding: 50px;");

        String[] betModes = new String[]{BetMode.CHANCE.toString(), BetMode.MULTIPLIER.toString()};

        ComboBox selectBetMode = new ComboBox(FXCollections.observableArrayList(betModes));
        TextField betInput = new TextField();
        betInput.setPromptText("Please enter chance from 0 to 1...");

        selectBetMode.setOnAction(e -> {
            switch(BetMode.fromString((String)selectBetMode.getValue())) {
                case CHANCE: betInput.setPromptText("Please enter chance from 0 to 1..."); break;
                case MULTIPLIER: betInput.setPromptText("Please enter your bet amount..."); break;
            }
        });

        selectBetMode.setValue(BetMode.CHANCE.toString());

        Button betConfirm = new Button("Try your luck!");

        betConfirm.setOnMouseReleased(e -> {
            switch(BetMode.fromString((String)selectBetMode.getValue())) {
                case CHANCE:
                    if (game.getpScore() < 10) {
                        text.setText("You score is lower than the minimum amount for the chance bet (10)!");
                        return; // cant bet on chance if you have < 10 points
                    }

                    try {
                        double chance = Double.parseDouble(betInput.getText());
                        boolean won = Bets.chance(chance);
                        game.setpScore(Math.max(0, game.getpScore() - 10));
                        if (won) {
                            text.setText("You won!");
                            game.setpScore(game.getpScore() + (int)(1/chance) * 10);
                        } else {
                            text.setText("You lost.");
                        }
                    } catch (NumberFormatException | BadBetException exc) {
                        betInput.setText("");
                        text.setText("Bad input!");
                    }

                    break;
                case MULTIPLIER:
                    try {
                        int amount = Integer.parseInt(betInput.getText());

                        if (amount > game.getpScore()) {
                            text.setText("You score is lower than the bet amount!");
                            return;
                        }

                        game.setpScore(game.getpScore() - amount);
                        int new_amount = Bets.multiplier(amount);
                        game.setpScore(game.getpScore() + new_amount);
                        text.setText("You won: " + new_amount + " points.");
                    } catch (NumberFormatException | BadBetException exc) {
                        betInput.setText("");
                        text.setText("Bad input!");
                    }

                    break;
            }

            scoreLabel.setText("Player Score is: " + game.getpScore());
        });

        surpriseMechanic.setOnMouseReleased(e -> {
            if (!roundEnded) {
                SmInstructions(textBottom);
                betsVisible = !betsVisible;

                selectBetMode.setVisible(betsVisible);
                betInput.setVisible(betsVisible);
                betConfirm.setVisible(betsVisible);
            }
        });

        selectBetMode.setVisible(false);
        betInput.setVisible(false);
        betConfirm.setVisible(false);

        VBox comms = new VBox(selectBetMode, betInput, betConfirm, text, input, enter);
        comms.setPrefWidth(300);
        comms.setMaxWidth(280);
        text.maxWidth(300);
        comms.setAlignment(Pos.CENTER);
        comms.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setRight(comms);
        enter.setOnMouseReleased(e -> {
            //calls MoveResult in Boggle to play the game and see what player inputs is valid or not
            BoggleGame.MoveResult res = game.humanMoveOnce(input.getText());
            input.clear();

            switch (res) {
                case EMPTY -> {
                    cscoreLabel.setText("Computer Score is: " + game.getcScore());
                    cWordlabel.setText("Computer Words found:" + game.getComputerWords());
                    cWordlabel.setWrapText(true);
                    // Check, checks if user intitiated timeRush, will only run on TimeRush mode
                    if (this.Check) {
                        int minutes = (int) ((game.getTimeInSeconds(startTime) % 3600) / 60);
                        int seconds = (int) (game.getTimeInSeconds(startTime) % 60);
                        this.timerush = "Time Taken: " + String.format(minutes + " minutes and " + seconds + " seconds");
                        game.scoreMultiplier((minutes * 60) + seconds);
                        this.Check = false;
                    }
                    this.roundEnded = true;
                    String roundEnd = ("Player found " + game.getpwordCount() + " words this round\nComputer found "
                            + game.getcwordcount() + " words this round \nPlayer score is " + game.getpScore() +
                            " this round \nComputer score is " + game.getcScore() + " this round\n" + this.timerush + "\n\nPlay again? Type 'Y' or 'N' ");
                    text.setText(
                            roundEnd);

                    speaker.speak(roundEnd);

                    text.wrappingWidthProperty();
                    // this ends the round so we can tally up the scores
                    this.game.endRound();
                    game.sethintAllowed();
                    enter.setOnMouseReleased(f -> {
                        if (input.getText().equals("Y")) {
                            input.clear();
                            this.letters = game.initRound();
                            createBoard();
                        } else if (input.getText().equals("N")) {
                            String endGame = ("Good Game! \n\n" + game.getRound() + " rounds were played\nPlayer found average of " +
                                    game.getApwordCount() + " words\nPlayer scored " + game.getpScoreTotal() +
                                    " points\nComputer found " + game.getAcwordcount() + " words\nComputer scored "
                                    + game.getcScoreTotal() + " points");
                            text.setText(endGame);
                            speaker.speak(endGame);
                        }
                    });
                }
                case BAD_WORD -> {
                    text.setText("Invalid word, or already used, try again!!");
                    speaker.speak("Invalid word, or already used, try again!!");
                }
                case WORD_FOUND -> {
                    text.setText("Word is valid!! Good Job!!");
                    speaker.speak("Word is valid, Good Job");
                    scoreLabel.setText("Player Score is: " + game.getpScore());
                    wordCount.setText("Number of words found: " + game.getpwordCount());
                    pWordlabel.setText("Player words found: " + game.getPlayerWords().toString());
                    pWordlabel.setWrapText(true);

                }
            }
        });

        // calls creatureButtons to create the Buttons using the randomized letters
        createButtons(this.letters);
        // Creates a grid using gridPane to put the buttons in a grid
        createGrid(this.buttons);

        //if its the first round it'll display general game instructions
        if (first_round) {
            intro(textBottom);
            this.first_round = false;
        }
        text.setFont(new Font(12));
        text.setStyle("-fx-background-color: #ceffc6;-fx-padding: 50px;");

        //Alligns text at the bottom of borderPane using Hbox to display text horiztonally
        // BottomText is kept for Instructions to guide user while playing the game
        this.bottomText = new HBox(10, textBottom);
        bottomText.setAlignment(Pos.TOP_LEFT);
        bottomText.setPrefHeight(75);
        bottomText.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setBottom(bottomText);
        bottomText.setMaxWidth(Integer.MAX_VALUE);
        bottomText.setStyle("-fx-background-color: #ADD8E6;-fx-padding: 30px;");
        return borderPane;
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


    // creates buttons using a for loop and also clears any existing values in grid pane allowing board to refresh
    public void createButtons(String letters) {
        this.gridPane.getChildren().clear();
        for (int i = 0; i < buttons.length; i++) {
            this.buttons[i] = new Button(String.valueOf(letters.charAt(i)));
            this.buttons[i].setPrefWidth(Integer.MAX_VALUE);
            this.buttons[i].setPrefHeight(Integer.MAX_VALUE);
            this.buttons[i].setMinWidth(120);

            Button button = buttons[i];
            this.buttons[i].setOnAction(e -> {
                input.setText(input.getText() + button.getText());
            });
        }
    }

    //creates grid and assigns it to placement locations
    public void createGrid(Button[] buttons) {
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
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setCenter(gridPane);
    }

    // Introduction text
    public void intro(Text texti) {
        String intro = ("The Boggle board contains a grid of letters that are randomly placed." +
                "We're both going to try to find words in this grid by joining the letters.\n" +
                "You can form a word by connecting adjoining letters on the grid." +
                "Two letters adjoin if they are next to each other horizontally,\n" +
                "vertically, or diagonally. The words you find must be at least 4 letters long," +
                "and you can't use a letter twice in any single word. Your points\n" +
                "will be based on word length: a 4-letter word is worth 1 point, 5-letter" +
                "words earn 2 points, and so on. After you find as many words as you can,\n" +
                "I will find all the remaining words." +
                "Click on the letters so select them" +
                "Hit enter when your ready");
        texti.setText(intro);
        speaker.speak(intro);

    }

    //Hint instructions
    public void hintInstructions(Text texi) {
        String hintInstructions = ("Pressing the Hint button, allows you to recieve a Hint to help you find a " +
                "word in the board\nYou can only have one hint per , So use it wisely");
        texi.setText(hintInstructions);
        speaker.speak(hintInstructions);
    }

    //TimeRush instructions
    public void TrInstructions(Text texti) {
        String TrInstructions = ("A timer has started, if you finish finding words in under 90 seconds you'll get triple the " +
                "points\nIf you finish under 180 seconds you'll get double the points.\nIf you fail to " +
                "finish under 180 seconds you'll only get base points \nEnter your words quickly!");
        texti.setText(TrInstructions);

        speaker.speak(TrInstructions);
    }

    public void SmInstructions(Text texti){
        String SmInstructions = ("Feeling Lucky?\nPlay Multiplier, give some points lets see if they grow or shrink." +
                "\nPlay Chance, pay 10 points and see if you gain much more or lose it all");
        texti.setText(SmInstructions);
        speaker.speak(SmInstructions);
    }

    // Nightmode, it changes font colors and borderpane and Hbox colors to make a nightmode vibe
    public void switchAppearance(boolean dark) {
        if (dark) {
            theme = new NightTheme();
        } else {
            theme = new LightTheme();
        }

        theme.apply(this.text, this.textBottom, this.borderPane, this.bottomText, this.nightmode);
    }
}
