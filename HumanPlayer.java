import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

/**
 * Represents a human player in a dice game with an interactive graphical interface.
 * 
 * The HumanPlayer provides a user-friendly interface that allows:
 * - Manual dice rolling
 * - Interactive turn management
 * - Real-time score tracking
 * - Game log for turn information
 * 
 * Key features include:
 * - Roll dice button
 * - End turn button
 * - Visual dice roll display
 * - Detailed game logging
 * 
 * @author Unknown
 * @version 1.0
 * @since Summer 2024
 */
public class HumanPlayer extends Player {
    /** Dice object for rolling */
    private Dice die;
    /** Panel containing all player-specific UI components. */
    private JPanel playerPanel;
    
    /** Label displaying the current player's turn status. */
    private JLabel statusLabel;
    
    /** Text area for logging game events and player actions. */
    private JTextArea gameLog;
    
    /** Scroll pane to enable scrolling through game log. */
    private JScrollPane scrollPane;
    
    /** Button to initiate a dice roll. */
    private JButton rollButton;
    
    /** Button to end the current turn. */
    private JButton endTurnButton;
    
    /** Label to display the result of dice rolls. */
    private JLabel diceLabel;
    
    /** Tracks the score accumulated during the current turn. */
    private int turnScore;
    
    /** Synchronization mechanism to coordinate turn completion. */
    private CountDownLatch turnLatch;

    /**
     * Creates a default HumanPlayer with the name "Human".
     */
    public HumanPlayer() {
        this("Human");
    }

    /**
     * Creates a new HumanPlayer with a specified name.
     * 
     * @param name the name to assign to the player
     */
    public HumanPlayer(String name) {
        super(name);
        /** Create a six-sided die */
        die = new Dice(6);
        initializePanel();
    }

    /**
     * Initializes the graphical user interface panel for the player.
     * 
     * Sets up:
     * - Status label
     * - Dice display
     * - Game log
     * - Roll and End Turn buttons
     * - Action listeners for interactive gameplay
     */
    private void initializePanel() {
        playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());

        // Status label at the top
        statusLabel = new JLabel(getName() + "'s turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerPanel.add(statusLabel, BorderLayout.NORTH);

        // Center panel with dice and game log
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Dice display area
        diceLabel = new JLabel("Roll the dice!", SwingConstants.CENTER);
        diceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        diceLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        centerPanel.add(diceLabel, BorderLayout.NORTH);
        
        // Game log
        gameLog = new JTextArea(8, 30);
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        scrollPane = new JScrollPane(gameLog);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        playerPanel.add(centerPanel, BorderLayout.CENTER);

        // Control buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        rollButton = new JButton("Roll Dice");
        rollButton.setFont(new Font("Arial", Font.BOLD, 14));
        rollButton.setPreferredSize(new Dimension(120, 40));
        
        endTurnButton = new JButton("End Turn");
        endTurnButton.setFont(new Font("Arial", Font.BOLD, 14));
        endTurnButton.setPreferredSize(new Dimension(120, 40));
        endTurnButton.setEnabled(false); // Disabled initially
        
        buttonPanel.add(rollButton);
        buttonPanel.add(endTurnButton);
        
        playerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set action listeners
        rollButton.addActionListener(e -> rollDice());
        endTurnButton.addActionListener(e -> endTurn());

        setPlayerFrame(playerPanel);
    }

    /**
     * Executes a turn for the HumanPlayer.
     * 
     * Manages the turn lifecycle:
     * - Resets turn state
     * - Prepares UI for player interaction
     * - Waits for player to complete their turn
     * 
     * @return the total score earned during the turn
     */
    @Override
    public int play() {
        // Reset turn state
        turnScore = 0;
        turnLatch = new CountDownLatch(1);
        
        // Reset UI
        diceLabel.setText("Roll the dice!");
        gameLog.setText("");
        rollButton.setEnabled(true);
        endTurnButton.setEnabled(false);
        
        logMessage("Starting " + getName() + "'s turn. Press 'Roll Dice' to begin.");
        
        // Wait for player to complete their turn
        try {
            turnLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return turnScore;
    }

    /**
     * Handles the dice rolling action for the human player.
     * 
     * Performs the following actions:
     * - Generates a random dice roll
     * - Updates the dice display
     * - Handles special cases like rolling a six
     * - Updates turn score and UI state
     */
    private void rollDice() {
        int roll = die.roll();
        
        // Update dice display
        diceLabel.setText("You rolled: " + roll);
        
        // Check if rolled a 6
        if (roll == 6) {
            logMessage("You rolled a 6! Turn ends with 0 points.");
            turnScore = 0;
            
            // Disable buttons
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
            
            // Display message and add short delay before ending turn
            Timer endTimer = new Timer(1500, e -> turnLatch.countDown());
            endTimer.setRepeats(false);
            endTimer.start();
            
            return;
        }
        
        // Add to turn score
        turnScore += roll;
        logMessage("You rolled a " + roll + ". Turn score is now: " + turnScore);
        
        // Enable end turn button after first successful roll
        endTurnButton.setEnabled(true);
    }

    /**
     * Handles ending the player's turn.
     * 
     * Performs the following actions:
     * - Logs the final turn score
     * - Disables interaction buttons
     * - Signals turn completion
     */
    private void endTurn() {
        logMessage("You ended your turn with " + turnScore + " points.");
        
        // Disable buttons
        rollButton.setEnabled(false);
        endTurnButton.setEnabled(false);
        
        // End the turn
        turnLatch.countDown();
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
