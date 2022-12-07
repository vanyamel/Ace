package boggle;

import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle 
 */
public class BoggleStats {

    /**
     * set of words the player finds in a given round 
     */  
    private Set<String> playerWords = new HashSet<String>();  
    /**
     * set of words the computer finds in a given round 
     */  
    private Set<String> computerWords = new HashSet<String>();  
    /**
     * the player's score for the current round
     */  
    private int pScore; 
    /**
     * the computer's score for the current round
     */  
    private int cScore; 
    /**
     * the player's total score across every round
     */  
    private int pScoreTotal; 
    /**
     * the computer's total score across every round
     */  
    private int cScoreTotal; 
    /**
     * the average number of words, per round, found by the player
     */  
    private double pAverageWords; 
    /**
     * the average number of words, per round, found by the computer
     */  
    private double cAverageWords; 
    /**
     * the current round being played
     */  
    private int round; 

    /**
     * enumarable types of players (human or computer)
     */  
    public enum Player {
        Human("Human"),
        Computer("Computer");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /* BoggleStats constructor
     * ----------------------
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for computer and human players.
     */
    public BoggleStats() {
        this.round = 0;
        this.pScore = 0;
        this.cScore = 0;
        this.pScoreTotal = 0;
        this.cScoreTotal = 0;
        this.pAverageWords = 0;
        this.cAverageWords = 0;
        this.playerWords = new HashSet<>();
        this.computerWords = new HashSet<>();
    }

    /* 
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
        int score = 0;
        for (int i = 1; i <= word.length(); i++){
            if (i >= 4){
                score += 1;
            }
        }
        switch (player) {
            case Human -> {
                this.playerWords.add(word);
                this.pScore += score;
            }
            case Computer -> {
                this.computerWords.add(word);
                this.cScore += score;
            }
        }
    }
    public int getPlayerScore() {
        return this.pScore;
    }

    public void setPlayerScore(int score) {
        this.pScore = score;
    }

    /* 
     * End a given round.
     * This will clear out the human and computer word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero.
     * Finally, increment the current round number by 1.
     */
    public void endRound() {
        this.pAverageWords = (this.playerWords.size() + this.pAverageWords)/(getRound()+1);
        this.cAverageWords = (this.computerWords.size() + this.cAverageWords)/(getRound()+1);
        this.playerWords = new HashSet<>();
        this.computerWords = new HashSet<>();
        this.pScoreTotal += this.pScore;
        this.cScoreTotal += this.cScore;
        this.pScore = 0;
        this.cScore = 0;
        this.round += 1;
    }
//
    /* 
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */
    public void summarizeRound() {
        System.out.println("player found the words " + getPlayerWords() + " this round");
        System.out.println("computer found the words " + this.computerWords + " this round");
        System.out.println("player found " + getPlayerWords().size() + " words this round");
        System.out.println("computer found " + this.computerWords.size() + " words this round");
        System.out.println("player score is " + getScore() + " this round");
        System.out.println("computer score is " + this.cScore + " this round");
    }

    /* 
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     */
    public void summarizeGame() {
        if (getRound() == 0){
            return;
        }
        System.out.println(getRound() + " rounds were played");
        System.out.println("the total score for player is " + this.pScoreTotal);
        System.out.println("the total score for computer is " + this.cScoreTotal);
        System.out.println("the average number of words found by player is " + this.pAverageWords);
        System.out.println("the average number of words found by computer is " + this.cAverageWords);
    }

    /* 
     * @return Set<String> The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }
    public Set<String> getComputerWords() {return this.computerWords;}

    /*
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
    * @return int The current player score
    */
    public int getScore() {
        return this.pScore;
    }
    public int getpScore() {
        return pScore;
    }

    public int getpScoreTotal() {
        return pScoreTotal;
    }
    public void setpScoreTotal(int num) {
        this.pScoreTotal =  num;
    }

    public int getcScore() {
        return cScore;
    }

    public int getcScoreTotal() {
        return this.cScoreTotal;
    }

    public int getpwordCount() {
        return playerWords.size();
    }
    public int getcwordcount() {
        return computerWords.size();
    }

    public double getApwordCount() {
        return this.pAverageWords;
    }
    public double getAcwordcount() {
        return this.cAverageWords;
    }



}
