package boggle;

import boggle.surprise.BadBetException;
import boggle.surprise.BetMode;
import boggle.surprise.Bets;
import boggle.tts.Speaker;

import java.util.*;

/**
 * The BoggleGame class for the first Assignment in CSC207, Fall 2022
 */
public class BoggleGame {

    /**
     * scanner used to interact with the user via console
     */
    public Scanner scanner;
    /**
     * stores game statistics
     */
    private final BoggleStats gameStats;

    private final Hints hint;
    private boolean hintGet;

    private final TimeRush TR;

    private final Dictionary boggleDict = new Dictionary("wordlist.txt");
    private String Letters;

    /**
     * dice used to randomize letter assignments for a small grid
     */
    private final String[] dice_small_grid = //dice specifications, for small and large grids
            {"AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DEILRX", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "HLNNRZ"};
    /**
     * dice used to randomize letter assignments for a big grid
     */
    private final String[] dice_big_grid =
            {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DDHNOT", "DHHLOR",
                    "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    /*
     * BoggleGame constructor
     */
    public BoggleGame() {
        this.scanner = new Scanner(System.in);
        this.gameStats = new BoggleStats();
        this.TR = new TimeRush();
        this.hint = new Hints(this.gameStats);
    }

    /*
     * Provide instructions to the user, so they know how to play the game.
     */
    public void giveInstructions() {
        System.out.println("The Boggle board contains a grid of letters that are randomly placed.");
        System.out.println("We're both going to try to find words in this grid by joining the letters.");
        System.out.println("You can form a word by connecting adjoining letters on the grid.");
        System.out.println("Two letters adjoin if they are next to each other horizontally, ");
        System.out.println("vertically, or diagonally. The words you find must be at least 4 letters long, ");
        System.out.println("and you can't use a letter twice in any single word. Your points ");
        System.out.println("will be based on word length: a 4-letter word is worth 1 point, 5-letter");
        System.out.println("words earn 2 points, and so on. After you find as many words as you can,");
        System.out.println("I will find all the remaining words.");
        System.out.println("\nHit return when you're ready...");
    }

    private void scoreMultiplier(float time) {
        // scoreMultiplier
        if (time <= 90) {
            int a = this.gameStats.getpScore() * 3;
            this.gameStats.setPlayerScore(a);
        } else if (time <= 180) {
            int b = this.gameStats.getpScore() * 2;
            this.gameStats.setPlayerScore(b);
        }
    }

    public int[] runTimeRush(long startTime) {
        int minutes = (int) ((this.getTimeInSeconds(startTime) % 3600) / 60);
        int seconds = (int) (this.getTimeInSeconds(startTime) % 60);

        scoreMultiplier((minutes * 60) + seconds);

        return new int[] {minutes, seconds};
    }

    /*
     * Gets information from the user to initialize a new Boggle game.
     * It will loop until the user indicates they are done playing.
     */
    public void playGame() {
        Speaker speaker = Speaker.getInstance();
        speaker.speak("Welcome to Boggle!");

        int boardSize;
        while (true) {
            System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
            String choiceGrid = scanner.nextLine();
            //get grid size preference
            if (choiceGrid == "") break; //end game if user inputs nothing
            while (!choiceGrid.equals("1") && !choiceGrid.equals("2")) {
                System.out.println("Please try again.");
                System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
                choiceGrid = scanner.nextLine();
            }

            if (choiceGrid.equals("1")) boardSize = 5;
            else boardSize = 4;

            //get letter choice preference
            System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
            String choiceLetters = scanner.nextLine();

            if (choiceLetters == "") break; //end game if user inputs nothing
            while (!choiceLetters.equals("1") && !choiceLetters.equals("2")) {
                System.out.println("Please try again.");
                System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
                choiceLetters = scanner.nextLine();
            }

            if (choiceLetters.equals("1")) {
                playRound(boardSize, randomizeLetters(boardSize));
            } else {
                System.out.println("Input a list of " + boardSize * boardSize + " letters:");
                choiceLetters = scanner.nextLine();
                while (!(choiceLetters.length() == boardSize * boardSize)) {
                    System.out.println("Sorry, bad input. Please try again.");
                    System.out.println("Input a list of " + boardSize * boardSize + " letters:");
                    choiceLetters = scanner.nextLine();
                }
                playRound(boardSize, choiceLetters.toUpperCase());
            }

            //round is over! So, store the statistics, and end the round.
            this.gameStats.summarizeRound();
            this.gameStats.endRound();

            //Shall we repeat?
            System.out.println("Play again? Type 'Y' or 'N'");
            String choiceRepeat = scanner.nextLine().toUpperCase();

            if (choiceRepeat == "") break; //end game if user inputs nothing
            while (!choiceRepeat.equals("Y") && !choiceRepeat.equals("N")) {
                System.out.println("Please try again.");
                System.out.println("Play again? Type 'Y' or 'N'");
                choiceRepeat = scanner.nextLine().toUpperCase();
            }

            if (choiceRepeat == "" || choiceRepeat.equals("N")) break; //end game if user inputs nothing

        }

        //we are done with the game! So, summarize all the play that has transpired and exit.
        System.out.println("you spent " + TR.printTime() + " seconds completing this game");
        scoreMultiplier(TR.getTimeInSeconds());
        hint.deductScore(hintGet);
        this.gameStats.summarizeGame();
        System.out.println("Thanks for playing!");
    }

    /*
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
     */
    public void playRound(int size, String letters) {
        //step 1. initialize the grid
        BoggleGrid grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);
        //step 2. initialize the dictionary of legal words
        //Dictionary boggleDict = new Dictionary("wordlist.txt"); //you may have to change the path to the wordlist, depending on where you place it.
        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
        Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
        findAllWords(allWords, boggleDict, grid);
        //step 4. allow the user to try to find some words on the grid
        humanMove(grid, allWords);
        //step 5. allow the computer to identify remaining words
        computerMove(allWords);
    }

    private Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();

    private BoggleGrid grid = new BoggleGrid(4);

    public BoggleGrid getGrid() {
        return grid;
    }

    public String initRound() {
        int boardSize = 4;

        String letters = randomizeLetters(boardSize);

        this.grid.initalizeBoard(letters);

        findAllWords(this.allWords, boggleDict, grid);

        return letters;
    }

    public enum MoveResult {
        BAD_WORD,
        EMPTY,
        WORD_FOUND
    }

    public MoveResult humanMoveOnce(String word) {
        if (word.equals("")) {
            computerMove(allWords);
            return MoveResult.EMPTY;
        }

        if (allWords.containsKey(word.toUpperCase())) {
            this.gameStats.addWord(word, BoggleStats.Player.Human);
            allWords.remove(word.toUpperCase());
            return MoveResult.WORD_FOUND;
        }
        return MoveResult.BAD_WORD;
    }

    /*
     * This method should return a String of letters (length 16 or 25 depending on the size of the grid).
     * There will be one letter per grid position, and they will be organized left to right,
     * top to bottom. A strategy to make this string of letters is as follows:
     * -- Assign a one of the dice to each grid position (i.e. dice_big_grid or dice_small_grid)
     * -- "Shuffle" the positions of the dice to randomize the grid positions they are assigned to
     * -- Randomly select one of the letters on the given die at each grid position to determine
     *    the letter at the given position
     *
     * @return String a String of random letters (length 16 or 25 depending on the size of the grid)
     */
    private String randomizeLetters(int size) {
        Random rand = new Random();
        StringBuilder letters = new StringBuilder();
        if (size == 4) {
            for (int i = size - 1; i > 0; i--) {
                int index = rand.nextInt(i + 1);
                String temp = this.dice_small_grid[index];
                this.dice_small_grid[index] = this.dice_small_grid[i];
                this.dice_small_grid[i] = temp;
            }
            for (String dice : this.dice_small_grid) {
                letters.append(dice.charAt(rand.nextInt(dice.length() - 1)));
            }
        } else {
            for (int i = size - 1; i > 0; i--) {
                int index = rand.nextInt(i + 1);
                String temp = this.dice_big_grid[index];
                this.dice_big_grid[index] = this.dice_big_grid[i];
                this.dice_big_grid[i] = temp;
            }
            for (String dice : this.dice_big_grid) {
                letters.append(dice.charAt(rand.nextInt(dice.length() - 1)));
            }
        }
        this.Letters = letters.toString();
        return letters.toString();
    }

    /*
     * This should be a recursive function that finds all valid words on the boggle board.
     * Every word should be valid (i.e. in the boggleDict) and of length 4 or more.
     * Words that are found should be entered into the allWords HashMap.  This HashMap
     * will be consulted as we play the game.
     *
     * Note that this function will be a recursive function.  You may want to write
     * a wrapper for your recursion. Note that every legal word on the Boggle grid will correspond to
     * a list of grid positions on the board, and that the Position class can be used to represent these
     * positions. The strategy you will likely want to use when you write your recursion is as follows:
     * -- At every Position on the grid:
     * ---- add the Position of that point to a list of stored positions
     * ---- if your list of stored positions is >= 4, add the corresponding word to the allWords Map
     * ---- recursively search for valid, adjacent grid Positions to add to your list of stored positions.
     * ---- Note that a valid Position to add to your list will be one that is either horizontal, diagonal, or
     *      vertically touching the current Position
     * ---- Note also that a valid Position to add to your list will be one that, in conjunction with those
     *      Positions that precede it, form a legal PREFIX to a word in the Dictionary (this is important!)
     * ---- Use the "isPrefix" method in the Dictionary class to help you out here!!
     * ---- Positions that already exist in your list of stored positions will also be invalid.
     * ---- You'll be finished when you have checked EVERY possible list of Positions on the board, to see
     *      if they can be used to form a valid word in the dictionary.
     * ---- Food for thought: If there are N Positions on the grid, how many possible lists of positions
     *      might we need to evaluate?
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
    private void findAllWords(Map<String, ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {
        String word = "";
        ArrayList<Position> positionList = new ArrayList<>();
        int r = boggleGrid.numRows();
        int c = boggleGrid.numCols();
        boolean[][] traversed = new boolean[r][c];
        for (int i = 0; i < boggleGrid.numRows(); i++) {
            for (int j = 0; j < boggleGrid.numCols(); j++) {
                findAllWordsHelper(traversed, positionList, i, j, word, allWords, boggleDict, boggleGrid);
            }
        }
    }

    private void findAllWordsHelper(boolean[][] traversed, ArrayList<Position> positionList, int row, int col,
                                    String word, Map<String, ArrayList<Position>> allWords, Dictionary boggleDict,
                                    BoggleGrid boggleGrid) {

        String letter = String.valueOf(boggleGrid.getCharAt(row, col));
        Position p = new Position();
        traversed[row][col] = true;
        int r = boggleGrid.numRows();
        int c = boggleGrid.numCols();

        if (boggleDict.containsWord(word) && word.length() >= 4) {
            allWords.put(word, positionList);
        }
        word += letter;
        if (boggleDict.isPrefix(word)) {
            p.setRow(row);
            p.setCol(col);
            positionList.add(p);
            for (int i = row - 1; i <= row + 1 && i < r; i++) {
                for (int j = col - 1; j <= col + 1 && j < c; j++) {

                    if (i >= 0 && j >= 0 && !traversed[i][j]) {
                        findAllWordsHelper(traversed, positionList, i, j, word, allWords, boggleDict, boggleGrid);
                    }

                }
            }
        }
        positionList.remove(p);
        traversed[row][col] = false;

    }

    private void handleBet(Scanner scanner, BetMode mode) {
        System.out.println("Current points: " + gameStats.getPlayerScore());

        switch (mode) {
            case MULTIPLIER:
                System.out.println("Multiplier mode selected. Enter the amount of points you wish to bet: ");
                int num = scanner.nextInt();
                scanner.nextLine(); // skip one \n after the number

                if (num < 0) {
                    System.out.println("Good try! Cheating the system is not allowed. Deducting 10 points...");
                    // make sure we don't take so many points that the player ends up in the negatives,
                    // that would just be cruel
                    gameStats.setPlayerScore(Math.max(0, gameStats.getPlayerScore() - 10));
                } else if (num > gameStats.getPlayerScore()) {
                    System.out.println("You don't have that many points. Returning...");
                } else {
                    try {
                        int newPoints = Bets.multiplier(num);
                        gameStats.setPlayerScore(gameStats.getPlayerScore() - num + newPoints);
                        System.out.println("You now have: " + gameStats.getPlayerScore() + " points!");
                    } catch (BadBetException e) {
                        System.out.println("Bad bet! Returning...");
                        System.err.println(e.getMessage());
                    }
                }
                break;
            case CHANCE:
                if (gameStats.getPlayerScore() < 10) {
                    System.out.println("You don't have 10 points yet, you cannot bet on the chance mode!");
                    return;
                }

                System.out.println("Chance mode selected. Enter the chance you wish to have (from 0 to 1): ");
                double chance = scanner.nextDouble();
                scanner.nextLine(); // skip one \n after the number

                try {
                    boolean result = Bets.chance(chance);
                    if (result) {
                        System.out.println("You won! adding " + (int) (10 * chance) + " to your points...");
                        gameStats.setPlayerScore(gameStats.getPlayerScore() + (int) (10 * chance));
                    } else {
                        System.out.println("You lost 10 points!");
                        gameStats.setPlayerScore(Math.max(0, gameStats.getPlayerScore() - 10));
                    }
                } catch (BadBetException e) {
                    System.out.println("Bad bet! Returning...");
                    System.err.println(e.getMessage());
                }
                break;
            default:
                System.err.println("Invalid bet mode in BoggleGame::handleBet");
                break;
        }
    }

    /*
     * Gets words from the user.  As words are input, check to see that they are valid.
     * If yes, add the word to the player's word list (in boggleStats) and increment
     * the player's score (in boggleStats).
     * End the turn once the user hits return (with no word).
     *
     * @param board The boggle board
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void humanMove(BoggleGrid board, Map<String, ArrayList<Position>> allWords) {
        System.out.println("It's your turn to find some words!");
        while (true) {
            //You write code here!
            //step 1. Print the board for the user, so they can scan it for words
            //step 2. Get a input (a word) from the user via the console
            //step 3. Check to see if it is valid (note validity checks should be case-insensitive)
            //step 4. If it's valid, update the player's word list and score (stored in boggleStats)
            //step 5. Repeat step 1 - 4
            //step 6. End when the player hits return (with no word choice).
            System.out.println(board.toString());
            String word = scanner.nextLine();
            if (word.equals("")) {
                break;
            } else if (word.equals("B")) {
                System.out.println("Switching to betting mode! Please enter M[ultiplier] or C[hance] to select the mode!");
                String mode = scanner.nextLine();

                if (mode.equals("M") || mode.equals("Multiplier")) {
                    handleBet(scanner, BetMode.MULTIPLIER);
                } else if (mode.equals("C") || mode.equals("Chance")) {
                    handleBet(scanner, BetMode.CHANCE);
                } else {
                    System.out.println("None of the modes were selected, returning to normal mode...");
                }
            } else if (Objects.equals(word, "1")) {
                hintGet = hint.getHint(allWords, hintGet);
                System.out.println(word + " is a valid word");
                System.out.println("Timer:" + TR.printTime());
                this.gameStats.addWord(word, BoggleStats.Player.Human);
                allWords.remove(word.toUpperCase());
            } else {
                System.out.println("not a valid word or is already used");
                System.out.println("Timer:" + TR.printTime());
            }
        }
    }


    /*
     * Gets words from the computer.  The computer should find words that are
     * both valid and not in the player's word list.  For each word that the computer
     * finds, update the computer's word list and increment the
     * computer's score (stored in boggleStats).
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void computerMove(Map<String, ArrayList<Position>> all_words) {
        for (String word : all_words.keySet()) {
            if (!this.gameStats.getPlayerWords().contains(word)) {
                this.gameStats.addWord(word, BoggleStats.Player.Computer);
            }
        }
    }

    public String getLetters() {
        return this.Letters;
    }

    public int getpScore() {
        return gameStats.getpScore();
    }

    public void setpScore(int score) {
        this.gameStats.setPlayerScore(score);
    }

    public int getpScoreTotal() {
        return gameStats.getpScoreTotal();
    }

    public int getcScore() {
        return gameStats.getcScore();
    }

    public int getcScoreTotal() {
        return gameStats.getcScoreTotal();
    }

    public Set<String> getPlayerWords() {
        return gameStats.getPlayerWords();
    }

    public Set<String> getComputerWords() {
        return gameStats.getComputerWords();
    }

    public int getpwordCount() {
        return gameStats.getpwordCount();
    }

    public int getcwordcount() {
        return gameStats.getcwordcount();
    }

    public double getApwordCount() {
        return gameStats.getApwordCount();
    }

    public double getAcwordcount() {
        return gameStats.getAcwordcount();
    }

    public int getRound() {
        return gameStats.getRound();
    }

    public void endRound() {
        gameStats.endRound();
    }

    public boolean hintAllowed() {
        return this.hintGet;
    }

    public void sethintAllowed() {
        this.hintGet = false;
    }

    public String hintWord() {
        hintGet = hint.getHint(allWords, hintGet);
        return hint.getHintWord();
    }

    private float getTimeInSeconds(long starttime) {
        return Math.round((System.currentTimeMillis() - starttime) / 1000f);
    }

//
}
