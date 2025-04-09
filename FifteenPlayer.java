import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a player in a dice game with a specific strategy of stopping at 15 points.
 * This class extends the base Player class and provides a graphical user interface
 * to visualize the player's turn and decision-making process.
 */
public class FifteenPlayer extends Player {
    // Dice object for rolling
    private Dice die;
	
	// Private UI and game state components
    private JPanel playerPanel;
    private JLabel statusLabel;
    private JTextArea gameLog;
    private JScrollPane scrollPane;
    private Timer delayTimer;
    private int turnScore;
    private boolean continueTurn;
    private JLabel scoreValueLabel;
    private JPanel dicePanel;
    private JLabel diceLabel;

    /**
     * Default constructor that creates a FifteenPlayer with the name "Fifteen".
     */
    public FifteenPlayer() {
        this("Fifteen");
    }

    /**
     * Constructs a FifteenPlayer with a specified name.
     * 
     * @param name The name of the player
     */
    public FifteenPlayer(String name) {
        super(name);
        /** Create a six-sided die */
        die = new Dice(6);
        initializePanel();
    }

    /**
     * Initializes the graphical user interface for the FifteenPlayer.
     * Sets up panels, labels, and visual components to display game state.
     */
    private void initializePanel() {
        // Existing implementation remains the same
    	// Main panel with BorderLayout and styling to match main game
        playerPanel = new JPanel(new BorderLayout(10, 10));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        playerPanel.setBackground(new Color(240, 240, 245)); // Match main game background
        
        // Player info panel at the top
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(148, 0, 211)); // Dark Violet (matches the color from main game)
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Status label with styling matching main game
        statusLabel = new JLabel(getName() + "'s turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(Color.WHITE);
        
        // Strategy label
        JLabel strategyLabel = new JLabel("Strategy: Stop at 15 points", SwingConstants.CENTER);
        strategyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        strategyLabel.setForeground(new Color(240, 240, 245));
        
        // Add components to info panel
        infoPanel.add(statusLabel, BorderLayout.CENTER);
        infoPanel.add(strategyLabel, BorderLayout.SOUTH);
        
        // Center panel with player information and dice visualization
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1), "Player Actions"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Player info in the center
        JPanel playerInfoPanel = new JPanel(new BorderLayout());
        playerInfoPanel.setBackground(Color.WHITE);
        
        // Player type indicator with icon
        JPanel playerTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerTypePanel.setBackground(Color.WHITE);
        
        JLabel playerTypeLabel = new JLabel("Fifteen Player", SwingConstants.CENTER);
        playerTypeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerTypeLabel.setForeground(new Color(148, 0, 211)); // Match player type color
        
        // Add a small icon next to the player type
        JLabel playerIcon = new JLabel("🤖", SwingConstants.CENTER);
        playerIcon.setFont(new Font("Arial", Font.PLAIN, 32));
        
        playerTypePanel.add(playerIcon);
        playerTypePanel.add(playerTypeLabel);
        playerInfoPanel.add(playerTypePanel, BorderLayout.NORTH);
        
        // Dice and score display in the center
        JPanel gamePlayPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        gamePlayPanel.setBackground(Color.WHITE);
        
        // Dice panel with visualization
        dicePanel = new JPanel(new BorderLayout());
        dicePanel.setBackground(new Color(245, 245, 255));
        dicePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(148, 0, 211), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Dice label will show the current roll
        diceLabel = new JLabel("?", SwingConstants.CENTER);
        diceLabel.setFont(new Font("Arial", Font.BOLD, 42));
        
        JLabel diceTitle = new JLabel("Current Roll", SwingConstants.CENTER);
        diceTitle.setFont(new Font("Arial", Font.BOLD, 14));
        
        dicePanel.add(diceTitle, BorderLayout.NORTH);
        dicePanel.add(diceLabel, BorderLayout.CENTER);
        
        // Score indicator panel
        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.setBackground(new Color(245, 245, 255));
        scorePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(148, 0, 211), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel scoreTitle = new JLabel("Turn Score", SwingConstants.CENTER);
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 14));
        
        scoreValueLabel = new JLabel("0", SwingConstants.CENTER);
        scoreValueLabel.setFont(new Font("Arial", Font.BOLD, 42));
        
        scorePanel.add(scoreTitle, BorderLayout.NORTH);
        scorePanel.add(scoreValueLabel, BorderLayout.CENTER);
        
        gamePlayPanel.add(dicePanel);
        gamePlayPanel.add(scorePanel);
        
        playerInfoPanel.add(gamePlayPanel, BorderLayout.CENTER);
        
        // Add strategy explanation
        JPanel strategyExplanationPanel = new JPanel(new BorderLayout());
        strategyExplanationPanel.setBackground(new Color(248, 248, 255));
        strategyExplanationPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 5, 5, 5)
        ));
        
        JTextArea strategyExplanation = new JTextArea(
            "This player will stop rolling when the turn score reaches 15 or more points." +
            " This is a moderately conservative strategy that aims to consistently score points" +
            " while avoiding too much risk."
        );
        strategyExplanation.setFont(new Font("Arial", Font.ITALIC, 12));
        strategyExplanation.setLineWrap(true);
        strategyExplanation.setWrapStyleWord(true);
        strategyExplanation.setEditable(false);
        strategyExplanation.setBackground(new Color(248, 248, 255));
        
        strategyExplanationPanel.add(strategyExplanation, BorderLayout.CENTER);
        playerInfoPanel.add(strategyExplanationPanel, BorderLayout.SOUTH);
        
        centerPanel.add(playerInfoPanel, BorderLayout.CENTER);
        
        // Game log with improved styling to match main game
        gameLog = new JTextArea(8, 30);
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gameLog.setBackground(new Color(252, 252, 252));
        gameLog.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Scroll pane with cleaner border
        scrollPane = new JScrollPane(gameLog);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1), "Turn Log"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Add everything to the main panel
        playerPanel.add(infoPanel, BorderLayout.NORTH);
        playerPanel.add(centerPanel, BorderLayout.CENTER);
        playerPanel.add(scrollPane, BorderLayout.SOUTH);
        
        // Set this panel as the player frame
        setPlayerFrame(playerPanel);
    }

    /**
     * Executes the player's turn in the game.
     * 
     * @return The total score accumulated during the turn
     */
    @Override
    public int play() {
        turnScore = 0;
        continueTurn = true;
        gameLog.setText("");
        
        // Reset displays
        scoreValueLabel.setText("0");
        diceLabel.setText("?");
        statusLabel.setText(getName() + "'s turn");

        logMessage(getName() + " is starting their turn.");

        // Begin the rolling sequence with a timer for delay
        delayTimer = new Timer(800, new ActionListener() {
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
     * Continues the player's turn by rolling a die and making strategic decisions.
     * This method implements the "fifteen" strategy of stopping when turn score reaches 15.
     */
    private void continuePlay() {
        // Roll the die
        int roll = die.roll();

        // Update the dice display
        diceLabel.setText(String.valueOf(roll));
        
        // Briefly flash the dice panel to indicate a roll
        flashComponent(dicePanel);

        // Log the roll
        logMessage(getName() + " rolled a " + roll);

        // Check if rolled a 6
        if (roll == 6) {
            statusLabel.setText(getName() + " busted!");
            logMessage("Oh no! Rolled a 6. Turn ends with 0 points.");
            turnScore = 0;
            scoreValueLabel.setText("0");
            continueTurn = false;
            return;
        }

        // Add to turn score
        turnScore += roll;
        scoreValueLabel.setText(String.valueOf(turnScore));
        flashComponent(scoreValueLabel.getParent());
        logMessage("Turn score is now: " + turnScore);

        // Decide whether to continue rolling based on the "fifteen" strategy
        if (turnScore <= 15) {
            logMessage("Score is <= 15. Rolling again...");
            statusLabel.setText(getName() + " will roll again");
            delayTimer.setInitialDelay(800);
            delayTimer.start();
        } else {
            statusLabel.setText(getName() + " stops at " + turnScore);
            logMessage(getName() + " stops at " + turnScore + " points.");
            continueTurn = false;
        }
    }

    /**
     * Provides visual feedback by temporarily changing a component's background color.
     * 
     * @param component The UI component to flash
     */
    private void flashComponent(Component component) {
        Color originalColor = component.getBackground();
        component.setBackground(new Color(230, 220, 255)); // Light purple flash
        
        // Timer to restore original color
        Timer flashTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.setBackground(originalColor);
            }
        });
        flashTimer.setRepeats(false);
        flashTimer.start();
    }

    /**
     * Logs a message to the game log text area and scrolls to the bottom.
     * 
     * @param message The message to log
     */
    private void logMessage(String message) {
        gameLog.append(message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
}