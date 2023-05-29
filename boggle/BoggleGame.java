package boggle;

import java.util.*;

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

    public Dictionary boggleDict = new Dictionary("C:\\Users\\ali_n\\IdeaProjects\\untitled\\Ace\\boggle\\wordlist.txt");
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
        TimeRush TR = new TimeRush();
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

    private void findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {
        int rows = boggleGrid.numRows(),
                cols = boggleGrid.numCols();
        boolean[][] locationsVisited = new boolean[rows][cols]; // array to check if position is valid or false
        String word = "";
        for (int row = 0; row < rows; row++) { // loop to go through every grid sqaure to check for all possibilities
            for (int col = 0; col < cols; col++) {
                ArrayList<Position> list = new ArrayList<Position>();
                findWord(allWords, boggleDict, boggleGrid, locationsVisited, word, new Position(row, col), list);
                // helper function to help find words and navigate word possibilities
            }
        }
    }
    /*
    This is a recursive helper function, which on each step adds the position to the Arraylist I made for position,
    it comes useful by holding onto the position if its still a valid path, If the path becomes invalid we update our
    boolean array making that position false, but if the position completes off a word, and the word is in our
    legal word list (aka boggleDict) we check if we already have that word in our hashmap, if not we add it as a key,
    and its positions as its values.
    Then we recursively go through the grid squares above us and beside us and diagnol ones, with the same steps,
    updating our hashmap along the way. Which becomes a hashmap with all possible words on the grid.
     */
    private void findWord(Map<String, ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid,
                          boolean[][] locationsVisited, String word, Position pos, ArrayList<Position> list) {
        list.add(pos);
        locationsVisited[pos.getRow()][pos.getCol()] = true; // makes locations true for now, will change to false if
        // found not a prefix in later if statement
        word = word + boggleGrid.getCharAt(pos.getRow(), pos.getCol()); // adds character from position to word

        if (!boggleDict.isPrefix(word)) { // if word isn't a prefix or prefix anymore, it changes its position to false
            // and removes it from the position list
            locationsVisited[pos.getRow()][pos.getCol()] = false;
            list.remove(pos);
            return;
        } else if (boggleDict.containsWord(word) && word.length() >= 4){
            if (!allWords.containsKey(word)){// if word isn't already in the hashmap it adds it
                allWords.put(word, new ArrayList<Position>(list));
            }

        }
        // recursive step to go through each grid square
        for (int row = pos.getRow() - 1; row < boggleGrid.numRows() && row < pos.getRow() + 2; row++){
            for (int col = pos.getCol() - 1; col < boggleGrid.numCols() && col < pos.getCol() + 2; col++){
                if (col > -1 && row > -1 && !locationsVisited[row][col]){
                    findWord(allWords, boggleDict, boggleGrid, locationsVisited, word, new Position(row, col), list);
                }
            }
        }
        list.remove(pos); // removes positions that didn't end up making a word
        locationsVisited[pos.getRow()][pos.getCol()] = false;
    }


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
