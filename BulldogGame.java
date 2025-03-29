import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Manages the Bulldog Dice Game, providing a graphical user interface 
 * for player setup, game play, and score tracking.
 * 
 * The game involves multiple players rolling dice to reach winning number of points,
 * with different player strategies and a turn-based gameplay mechanism.
 */

public class BulldogGame {
    // Public constant for the winning score
    public static final int WINNING_SCORE = 104;
	
    private JFrame frame;
    private JPanel playerPanel, gamePanel, playerListPanel;
    private JButton addPlayerButton, startGameButton;
    private JComboBox<String> playerTypeBox;
    private JTextField playerNameField;
    private ArrayList<Player> players;
    
    /**
     * Constructs a new BulldogGame, initializing the main game frame
     * and setting up the initial player setup interface.
     */
    
    public BulldogGame() {
        players = new ArrayList<>();
        frame = new JFrame("Bulldog Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());
        
        // Create the player setup panel with improved styling
        createPlayerSetupPanel();
        
        frame.setVisible(true);
    }
    
    private void createPlayerSetupPanel() {
        // Main player setup panel - using BorderLayout for better organization
        JPanel setupPanel = new JPanel(new BorderLayout());
        setupPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title at the top of the setup screen
        JLabel titleLabel = new JLabel("Bulldog Game Setup", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        setupPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Input panel for adding players (top area)
        playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        playerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Add New Player"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Styled components
        JLabel typeLabel = new JLabel("Player Type:");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        playerTypeBox = new JComboBox<>(new String[]{"Human Player", "Random Player", "Fifteen Player", "Odd Player", "Wimp Player"});
        playerTypeBox.setFont(new Font("Arial", Font.PLAIN, 14));
        playerTypeBox.setPreferredSize(new Dimension(150, 30));
        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        playerNameField = new JTextField(10);
        playerNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        playerNameField.setPreferredSize(new Dimension(150, 30));
        
        addPlayerButton = new JButton("Add Player");
        addPlayerButton.setFont(new Font("Arial", Font.BOLD, 14));
        addPlayerButton.setPreferredSize(new Dimension(120, 30));
        
        startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        startGameButton.setPreferredSize(new Dimension(120, 30));
        startGameButton.setEnabled(false);
        
        // Add components to the player panel
        playerPanel.add(typeLabel);
        playerPanel.add(playerTypeBox);
        playerPanel.add(nameLabel);
        playerPanel.add(playerNameField);
        playerPanel.add(addPlayerButton);
        playerPanel.add(startGameButton);
        
        // Create player list panel with a title (center area)
        JPanel playerListContainer = new JPanel(new BorderLayout());
        playerListContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Current Players"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Using a panel with GridLayout for displaying player boxes
        playerListPanel = new JPanel();
        playerListPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 players per row, with gaps
        playerListPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Add a scroll pane in case there are many players
        JScrollPane scrollPane = new JScrollPane(playerListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        playerListContainer.add(scrollPane, BorderLayout.CENTER);
        
        // Add action listeners
        addPlayerButton.addActionListener(e -> addPlayer());
        startGameButton.addActionListener(e -> startGame());
        
        // Add panels to the setup panel
        setupPanel.add(playerPanel, BorderLayout.NORTH);
        setupPanel.add(playerListContainer, BorderLayout.CENTER);
        
        // Add instruction panel at the bottom
        JPanel instructionPanel = new JPanel(new BorderLayout());
        JTextArea instructionArea = new JTextArea(
            "Game Instructions:\n" +
            "1. Add 2-7 players to start the game\n" +
            "2. Each turn, players roll a die to earn points\n" +
            "3. Rolling a 6 ends your turn with 0 points\n" +
            "4. First player to reach " + WINNING_SCORE + " points wins!"
        );
        instructionArea.setEditable(false);
        instructionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionArea.setBackground(new Color(240, 240, 240));
        instructionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructionPanel.add(instructionArea, BorderLayout.CENTER);
        instructionPanel.setBorder(BorderFactory.createTitledBorder("How to Play"));
        
        setupPanel.add(instructionPanel, BorderLayout.SOUTH);
        
        // Add the setup panel to the frame
        frame.add(setupPanel);
    }
    
    private void addPlayer() {
        String name = playerNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a player name", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String type = (String) playerTypeBox.getSelectedItem();
        Player newPlayer = null;
        
        switch (type) {
            case "Human Player": newPlayer = new HumanPlayer(name); break;
            case "Random Player": newPlayer = new RandomPlayer(name); break;
            case "Fifteen Player": newPlayer = new FifteenPlayer(name); break;
            case "Odd Player": newPlayer = new OddPlayer(name); break;
            case "Wimp Player": newPlayer = new WimpPlayer(name); break;
        }
        
        if (newPlayer != null) {
            players.add(newPlayer);
            addPlayerBox(newPlayer, type);
            playerNameField.setText("");
        }
        
        if (players.size() >= 2 && players.size() <= 7) {
            startGameButton.setEnabled(true);
        } else if (players.size() > 7) {
            addPlayerButton.setEnabled(false);
            JOptionPane.showMessageDialog(frame, "Maximum 7 players allowed", "Player Limit", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Creates a visual representation of a player in the player list.
     * 
     * @param player The Player object to be displayed
     * @param type The type of player (e.g., "Human Player", "Random Player")
     */
    private void addPlayerBox(Player player, String type) {
        // Create a panel for each player with a box-like appearance
        JPanel playerBox = new JPanel();
        playerBox.setLayout(new BorderLayout());
        playerBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        playerBox.setBackground(getPlayerTypeColor(type));
        
        // Player name in bold at the top of the box
        JLabel nameLabel = new JLabel(player.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        
        // Player type at the bottom of the box
        JLabel typeLabel = new JLabel(type, SwingConstants.CENTER);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        typeLabel.setForeground(new Color(240, 240, 240));
        
        // Add a remove button
        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeButton.addActionListener(e -> {
            players.remove(player);
            playerListPanel.remove(playerBox);
            playerListPanel.revalidate();
            playerListPanel.repaint();
            
            // Update start button state
            startGameButton.setEnabled(players.size() >= 2 && players.size() <= 7);
            addPlayerButton.setEnabled(true);
        });
        
        // Add components to the player box
        playerBox.add(nameLabel, BorderLayout.NORTH);
        playerBox.add(typeLabel, BorderLayout.CENTER);
        playerBox.add(removeButton, BorderLayout.SOUTH);
        
        // Set a preferred size for consistent box appearance
        playerBox.setPreferredSize(new Dimension(180, 100));
        
        // Add the player box to the list panel
        playerListPanel.add(playerBox);
        playerListPanel.revalidate();
        playerListPanel.repaint();
    }
    
    /**
     * Determines the color associated with a specific player type.
     * 
     * @param type The player type
     * @return A Color object representing the player type
     */
    private Color getPlayerTypeColor(String type) {
        switch (type) {
            case "Human Player": return new Color(70, 130, 180); // Steel Blue
            case "Random Player": return new Color(60, 179, 113); // Medium Sea Green
            case "Fifteen Player": return new Color(148, 0, 211); // Dark Violet
            case "Odd Player": return new Color(205, 92, 92); // Indian Red
            case "Wimp Player": return new Color(184, 134, 11); // Dark Goldenrod
            default: return new Color(100, 100, 100); // Dark Gray
        }
    }
    
    private void startGame() {
        // Remove the player setup UI
        frame.getContentPane().removeAll();
        
        // Create the main game panel with a clean, modern design
        createGamePanel();
        
        frame.revalidate();
        frame.repaint();
        
        // Start the game in a separate thread to prevent UI freezing
        new Thread(this::runGame).start();
    }
    
    private void createGamePanel() {
        // Main game panel with BorderLayout
        gamePanel = new JPanel(new BorderLayout(10, 10));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        gamePanel.setBackground(new Color(240, 240, 245));
        
        // Create title panel at the top
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel gameTitleLabel = new JLabel("Bulldog Game", SwingConstants.CENTER);
        gameTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameTitleLabel.setForeground(Color.WHITE);
        
        JLabel turnLabel = new JLabel("Game in Progress", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        turnLabel.setForeground(new Color(240, 240, 245));
        
        titlePanel.add(gameTitleLabel, BorderLayout.CENTER);
        titlePanel.add(turnLabel, BorderLayout.SOUTH);
        
        // Create player area in the center (will be filled during gameplay)
        JPanel playerAreaPanel = new JPanel(new BorderLayout());
        playerAreaPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1), "Current Player"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        playerAreaPanel.setBackground(Color.WHITE);
        
        // Create score panel on the right
        JPanel scorePanel = new JPanel(new BorderLayout(0, 10));
        scorePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1), "Player Scores"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scorePanel.setBackground(Color.WHITE);
        scorePanel.setPreferredSize(new Dimension(200, 0));
        
        // Score display with styled player entries
        JPanel scoreListPanel = new JPanel();
        scoreListPanel.setLayout(new BoxLayout(scoreListPanel, BoxLayout.Y_AXIS));
        scoreListPanel.setBackground(Color.WHITE);
        
        // Add initial score entries
        for (Player player : players) {
            JPanel scoreEntryPanel = createScoreEntry(player, 0);
            scoreListPanel.add(scoreEntryPanel);
            scoreListPanel.add(Box.createVerticalStrut(5)); // Add spacing between entries
        }
        
        JScrollPane scoreScroll = new JScrollPane(scoreListPanel);
        scoreScroll.setBorder(BorderFactory.createEmptyBorder());
        scoreScroll.getVerticalScrollBar().setUnitIncrement(16);
        
        scorePanel.add(scoreScroll, BorderLayout.CENTER);
        
        // Add a home button to return to setup
        JButton homeButton = new JButton("End Game");
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to end the current game?", 
                "End Game", JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION) {
                frame.dispose();
                new BulldogGame();
            }
        });
        
        scorePanel.add(homeButton, BorderLayout.SOUTH);
        
        // Create game log panel at the bottom
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1), "Game Log"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        logPanel.setBackground(Color.WHITE);
        logPanel.setPreferredSize(new Dimension(0, 150));
        
        JTextArea gameLogArea = new JTextArea();
        gameLogArea.setEditable(false);
        gameLogArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gameLogArea.setBackground(new Color(252, 252, 252));
        gameLogArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane gameLogScroll = new JScrollPane(gameLogArea);
        gameLogScroll.setBorder(BorderFactory.createEmptyBorder());
        
        logPanel.add(gameLogScroll, BorderLayout.CENTER);
        
        // Assemble the main game panel
        gamePanel.add(titlePanel, BorderLayout.NORTH);
        gamePanel.add(playerAreaPanel, BorderLayout.CENTER);
        gamePanel.add(scorePanel, BorderLayout.EAST);
        gamePanel.add(logPanel, BorderLayout.SOUTH);
        
        // Store references to components that will be updated during gameplay
        playerAreaPanel.putClientProperty("turnLabel", turnLabel);
        playerAreaPanel.putClientProperty("gameLogArea", gameLogArea);
        scorePanel.putClientProperty("scoreListPanel", scoreListPanel);
        
        // Add the game panel to the frame
        frame.add(gamePanel);
    }
    /**
     * Creates a score entry panel for a specific player.
     * 
     * @param player The Player whose score is being displayed
     * @param score The initial score for the player
     * @return A JPanel representing the player's score entry
     */
    private JPanel createScoreEntry(Player player, int score) {
        JPanel scoreEntry = new JPanel(new BorderLayout(10, 0));
        scoreEntry.setBackground(Color.WHITE);
        scoreEntry.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, getPlayerTypeColor(getPlayerTypeName(player))),
            BorderFactory.createEmptyBorder(8, 8, 8, 0)
        ));
        scoreEntry.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel scoreLabel = new JLabel(String.valueOf(score));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        scoreEntry.add(nameLabel, BorderLayout.WEST);
        scoreEntry.add(scoreLabel, BorderLayout.EAST);
        
        // Store reference to the player for updating
        scoreEntry.putClientProperty("player", player);
        scoreEntry.putClientProperty("scoreLabel", scoreLabel);
        
        return scoreEntry;
    }
    
    /**
     * Determines the type name of a player based on its class.
     * 
     * @param player The Player object to identify
     * @return A string representation of the player type
     */
    private String getPlayerTypeName(Player player) {
        if (player instanceof HumanPlayer) return "Human Player";
        if (player instanceof RandomPlayer) return "Random Player";
        if (player instanceof FifteenPlayer) return "Fifteen Player";
        if (player instanceof OddPlayer) return "Odd Player";
        if (player instanceof WimpPlayer) return "Wimp Player";
        return "Unknown Player";
    }
    
    private void runGame() {
        JLabel turnLabel = (JLabel) ((JPanel) gamePanel.getComponent(1)).getClientProperty("turnLabel");
        JTextArea gameLogArea = (JTextArea) ((JPanel) gamePanel.getComponent(1)).getClientProperty("gameLogArea");
        JPanel scoreListPanel = (JPanel) ((JPanel) gamePanel.getComponent(2)).getClientProperty("scoreListPanel");
        
        // Log game start
        logMessage(gameLogArea, "Game started with " + players.size() + " players");
        
        boolean won = false;
        while (!won) {
            for (Player player : players) {
                if (won) break;
                
                // Update turn label
                SwingUtilities.invokeLater(() -> {
                    turnLabel.setText(player.getName() + "'s Turn");
                });
                
                // Set up the player's panel in the center
                SwingUtilities.invokeLater(() -> {
                    // Get the player area panel (component 1 in the gamePanel)
                    JPanel playerAreaPanel = (JPanel) gamePanel.getComponent(1);
                    
                    // Clear existing components except the border title
                    playerAreaPanel.removeAll();
                    
                    // Add the current player's frame
                    JPanel playerFrame = player.getPlayerFrame();
                    if (playerFrame != null) {
                        playerAreaPanel.add(playerFrame, BorderLayout.CENTER);
                    }
                    
                    // Highlight the current player in the score panel
                    highlightCurrentPlayer(scoreListPanel, player);
                    
                    playerAreaPanel.revalidate();
                    playerAreaPanel.repaint();
                });
                
                logMessage(gameLogArea, player.getName() + "'s turn begins");
                
                // Let the player play
                int turnScore = player.play();
                
                // Update the player's score
                player.setScore(player.getScore() + turnScore);
                
                logMessage(gameLogArea, player.getName() + " earned " + turnScore + " points this turn");
                
                // Update score display
                updateScoreDisplay(scoreListPanel);
                
                // Check for win condition
                if (player.getScore() >= WINNING_SCORE) {
                    logMessage(gameLogArea, player.getName() + " has won the game!");
                    highlightWinner(scoreListPanel, player);
                    JOptionPane.showMessageDialog(frame, 
                        "Congratulations " + player.getName() + "! You win!", 
                        "Winner!", JOptionPane.INFORMATION_MESSAGE);
                    won = true;
                    break;
                }
                
                // Pause between turns for non-human players
                if (!(player instanceof HumanPlayer)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        // Show win screen
        showWinScreen();
    }
    
    /**
     * Highlights the current player in the score panel.
     * 
     * @param scoreListPanel The panel containing player scores
     * @param currentPlayer The player whose turn is currently active
     */
    private void highlightCurrentPlayer(JPanel scoreListPanel, Player currentPlayer) {
        Component[] components = scoreListPanel.getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Player panelPlayer = (Player) panel.getClientProperty("player");
                
                if (panelPlayer != null) {
                    if (panelPlayer.equals(currentPlayer)) {
                        panel.setBackground(new Color(245, 245, 255));
                        panel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 5, 0, 0, getPlayerTypeColor(getPlayerTypeName(panelPlayer))),
                            BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                                BorderFactory.createEmptyBorder(7, 7, 7, 0)
                            )
                        ));
                    } else {
                        panel.setBackground(Color.WHITE);
                        panel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 5, 0, 0, getPlayerTypeColor(getPlayerTypeName(panelPlayer))),
                            BorderFactory.createEmptyBorder(8, 8, 8, 0)
                        ));
                    }
                }
            }
        }
        
        scoreListPanel.revalidate();
        scoreListPanel.repaint();
    }
    /**
     * Highlights the winner of the game with a special visual effect.
     * 
     * @param scoreListPanel The panel containing player scores
     * @param winner The Player who won the game
     */
    private void highlightWinner(JPanel scoreListPanel, Player winner) {
        Component[] components = scoreListPanel.getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Player panelPlayer = (Player) panel.getClientProperty("player");
                
                if (panelPlayer != null && panelPlayer.equals(winner)) {
                    panel.setBackground(new Color(255, 255, 220));
                    panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(255, 215, 0)), // Gold
                        BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(218, 165, 32), 1),
                            BorderFactory.createEmptyBorder(7, 7, 7, 0)
                        )
                    ));
                    
                    JLabel nameLabel = (JLabel) panel.getComponent(0);
                    nameLabel.setText(winner.getName() + " 🏆");
                }
            }
        }
        
        scoreListPanel.revalidate();
        scoreListPanel.repaint();
    }
    /**
     * Updates the score display for all players.
     * 
     * @param scoreListPanel The panel containing player scores
     */
    private void updateScoreDisplay(JPanel scoreListPanel) {
        SwingUtilities.invokeLater(() -> {
            Component[] components = scoreListPanel.getComponents();
            
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    Player panelPlayer = (Player) panel.getClientProperty("player");
                    JLabel scoreLabel = (JLabel) panel.getClientProperty("scoreLabel");
                    
                    if (panelPlayer != null && scoreLabel != null) {
                        scoreLabel.setText(String.valueOf(panelPlayer.getScore()));
                    }
                }
            }
        });
    }
    /**
     * Logs a message to the game log area.
     * 
     * @param gameLogArea The text area for game log messages
     * @param message The message to be logged
     */
    private void logMessage(JTextArea gameLogArea, String message) {
        SwingUtilities.invokeLater(() -> {
            gameLogArea.append(message + "\n");
            gameLogArea.setCaretPosition(gameLogArea.getDocument().getLength());
        });
    }
    
    private void showWinScreen() {
        SwingUtilities.invokeLater(() -> {
            int response = JOptionPane.showConfirmDialog(frame, 
                "Game Over! Would you like to play again?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
                
            if (response == JOptionPane.YES_OPTION) {
                frame.dispose();
                new BulldogGame();
            } else {
                frame.dispose();
            }
        });
    }
    
    /**
     * Main method to launch the Bulldog Game.
     * Sets the system look and feel and initializes the game.
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Set the look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(BulldogGame::new);
    }
}
