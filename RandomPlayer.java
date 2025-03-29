import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a player with a random strategy in a dice game.
 * 
 * A RandomPlayer is characterized by:
 * - Rolling the die multiple times per turn
 * - Randomly deciding whether to continue rolling or stop
 * - Risking losing all points if a six is rolled
 * 
 * The player's decisions are made randomly, adding an element of unpredictability
 * to their gameplay strategy.
 * 
 * @author Unknown
 * @version 1.0
 * @since Summer 2024
 */
public class RandomPlayer extends Player {
    /** Dice object for rolling */
    private Dice die;
    /** Dice object for rolling */
    private Dice reRoll;
    /** Panel displaying the player's game information. */
    private JPanel playerPanel;
    
    /** Label showing the current player's turn status. */
    private JLabel statusLabel;
    
    /** Text area for logging game events and player actions. */
    private JTextArea gameLog;
    
    /** Scroll pane to enable scrolling through game log. */
    private JScrollPane scrollPane;
    
    /** Timer used to create a delay between actions. */
    private Timer delayTimer;
    
    /** Tracks the score accumulated during the current turn. */
    private int turnScore;
    
    /** Flag to control the continuation of the player's turn. */
    private boolean continueTurn;

    /**
     * Creates a default RandomPlayer with the name "Random".
     */
    public RandomPlayer() {
        this("Random");
    }

    /**
     * Creates a new RandomPlayer with a specified name.
     * 
     * @param name the name to assign to the player
     */
    public RandomPlayer(String name) {
        super(name);
        /** Create a six-sided die */
        die = new Dice(6);
        /** Create a 2-sided die */
        reRoll = new Dice(2);
        initializePanel();
    }

    /**
     * Initializes the graphical user interface panel for the player.
     * Sets up a status label and a scrollable game log area.
     */
    private void initializePanel() {
        playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        
        statusLabel = new JLabel(getName() + "'s turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        gameLog = new JTextArea(10, 30);
        gameLog.setEditable(false);
        scrollPane = new JScrollPane(gameLog);
        
        playerPanel.add(statusLabel, BorderLayout.NORTH);
        playerPanel.add(scrollPane, BorderLayout.CENTER);
        
        setPlayerFrame(playerPanel);
    }
    
    /**
     * Executes a turn for the RandomPlayer.
     * 
     * The turn consists of:
     * - Rolling the die multiple times
     * - Randomly deciding whether to continue rolling
     * - Potentially losing all points if a six is rolled
     * 
     * @return the total score earned during the turn
     */
    @Override
    public int play() {
        turnScore = 0;
        continueTurn = true;
        gameLog.setText("");
        
        logMessage(getName() + " is starting their turn.");
        
        // Begin the rolling sequence with a timer for delay
        delayTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continuePlay();
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
        
        // Wait until the turn is complete
        while (continueTurn) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return turnScore;
    }
    
    /**
     * Continues the player's turn by rolling the die and making random decisions.
     * 
     * This method:
     * - Rolls a die
     * - Adds the roll to the turn score if not a six
     * - Randomly decides whether to continue rolling or stop
     * - Ends the turn if a six is rolled or the player decides to stop
     */
    private void continuePlay() {
        // Roll the die
        int roll = die.roll();
        
        // Log the roll
        logMessage(getName() + " rolled a " + roll);
        
        // Check if rolled a 6
        if (roll == 6) {
            logMessage("Oh no! Rolled a 6. Turn ends with 0 points.");
            turnScore = 0;
            continueTurn = false;
            return;
        }
        
        // Add to turn score
        turnScore += roll;
        logMessage("Turn score is now: " + turnScore);
        
        // Make a random decision to continue or stop (50/50 chance)
        if (reRoll.roll() == 1) {
            logMessage(getName() + " randomly decides to stop with " + turnScore + " points.");
            continueTurn = false;
        } else {
            logMessage(getName() + " randomly decides to roll again.");
            delayTimer.setInitialDelay(500);
            delayTimer.start();
        }
    }
    
    /**
     * Adds a message to the game log and scrolls to the bottom.
     * 
     * @param message the text message to be logged
     */
    private void logMessage(String message) {
        gameLog.append(message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
}
