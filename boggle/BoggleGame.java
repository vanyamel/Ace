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
    private BoggleStats gameStats;

    private Hints hint;
    private boolean hintGet;

    private TimeRush TR;

    private Dictionary boggleDict = new Dictionary("wordlist.txt");
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

    public void scoreMultiplier(float time) {
        if (time <= 90) {
            int a = this.gameStats.getpScore() * 3;
            this.gameStats.setPlayerScore(a);
        } else if (time <= 180) {
            int b = this.gameStats.getpScore() * 2;
            this.gameStats.setPlayerScore(b);
        }
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

    public float getTimeInSeconds(long starttime) {
        return Math.round((System.currentTimeMillis() - starttime) / 1000f);
    }

//
}
