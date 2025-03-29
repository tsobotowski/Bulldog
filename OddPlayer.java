import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a player with a unique strategy based on odd and even dice rolls.
 * 
 * An OddPlayer is characterized by:
 * - Continuing to roll only when odd numbers are rolled
 * - Stopping immediately when an even number is rolled
 * - Losing all points if a six is rolled
 * 
 * This player strategy adds an interesting twist to the traditional 
 * dice game mechanics by basing turn continuation on roll parity.
 * 
 * @author Unknown
 * @version 1.0
 * @since Summer 2024
 */
public class OddPlayer extends Player {
    /** Dice object for rolling */
    private Dice die;
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
     * Creates a default OddPlayer with the name "Bold".
     */
    public OddPlayer() {
        this("Bold");
    }

    /**
     * Creates a new OddPlayer with a specified name.
     * 
     * @param name the name to assign to the player
     */
    public OddPlayer(String name) {
        super(name);
        /** Create a six-sided die */
        die = new Dice(6);
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
     * Executes a turn for the OddPlayer.
     * 
     * The turn consists of:
     * - Rolling the die multiple times
     * - Continuing only when odd numbers are rolled
     * - Stopping immediately when an even number is rolled
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
     * Continues the player's turn by rolling the die and applying 
     * the odd number strategy.
     * 
     * This method:
     * - Rolls a die
     * - Adds the roll to the turn score if not a six
     * - Stops the turn if an even number is rolled
     * - Continues rolling if an odd number is rolled
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
        
        // Check if rolled an even number (2 or 4)
        if (roll % 2 == 0) {
            logMessage("Rolled an even number (" + roll + "). Stopping with " + turnScore + " points.");
            continueTurn = false;
        } else {
            logMessage("Rolled an odd number (" + roll + "). Rolling again...");
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
