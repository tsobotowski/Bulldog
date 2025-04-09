import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a cautious player strategy in a dice game where the player 
 * always rolls only once per turn.
 * 
 * A WimpPlayer is characterized by its conservative play style:
 * - Rolls the die only once per turn
 * - Immediately ends the turn after a single roll
 * - Loses all points if a six is rolled
 * 
 * @author David Levine
 * @version 1.0
 * @since Summer 2024
 */
public class WimpPlayer extends Player {
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
     * Creates a default WimpPlayer with the name "Wimp".
     */
    public WimpPlayer() {
        this("Wimp");
    }

    /**
     * Creates a new WimpPlayer with a specified name.
     * 
     * @param name the name to assign to the player
     */
    public WimpPlayer(String name) {
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
     * Executes a single turn for the WimpPlayer.
     * 
     * A WimpPlayer's turn consists of:
     * - Rolling the die exactly once
     * - Ending the turn immediately after the roll
     * - Scoring zero points if a six is rolled
     * 
     * @return the score earned during the turn (0 if a six was rolled)
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
                doSingleRoll();
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
     * Performs a single die roll and determines the turn's outcome.
     * 
     * This method:
     * - Generates a random die roll (1-6)
     * - Logs the roll result
     * - Sets turn score to 0 if a six is rolled
     * - Always ends the turn after one roll
     */
    private void doSingleRoll() {
        // Roll the die
        int roll = die.roll();
        
        // Log the roll
        logMessage(getName() + " rolled a " + roll);
        
        // Check if rolled a 6
        if (roll == 6) {
            logMessage("Rolled a 6. Turn ends with 0 points.");
            turnScore = 0;
        } else {
            turnScore = roll;
            logMessage("Being cautious, " + getName() + " chooses not to roll again.");
            logMessage("Turn ends with " + turnScore + " points.");
        }
        
        // Always end turn after one roll (this is a wimp player)
        continueTurn = false;
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