import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ScoreboardViewer is a reusable component that displays and manages
 * player scores in a visually appealing scoreboard panel.
 */
public class ScoreboardViewer extends JPanel {
    private JPanel scoreListPanel;
    private ArrayList<Player> players;
    private Map<Player, JPanel> playerPanels;
    private Player currentPlayer;
    
    /**
     * Creates a new ScoreboardViewer with an empty player list
     */
    public ScoreboardViewer() {
        this(new ArrayList<>());
    }
    
    /**
     * Creates a new ScoreboardViewer with the specified players
     * 
     * @param players List of players to display in the scoreboard
     */
    public ScoreboardViewer(ArrayList<Player> players) {
        this.players = players;
        this.playerPanels = new HashMap<>();
        initializeUI();
    }
    
    /**
     * Initializes the scoreboard UI components
     */
    private void initializeUI() {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1), "Player Scores"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 0));
        
        // Score display with styled player entries
        scoreListPanel = new JPanel();
        scoreListPanel.setLayout(new BoxLayout(scoreListPanel, BoxLayout.Y_AXIS));
        scoreListPanel.setBackground(Color.WHITE);
        
        // Add initial score entries
        updatePlayers();
        
        JScrollPane scoreScroll = new JScrollPane(scoreListPanel);
        scoreScroll.setBorder(BorderFactory.createEmptyBorder());
        scoreScroll.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scoreScroll, BorderLayout.CENTER);
    }
    
    /**
     * Sets the list of players to display in the scoreboard
     * 
     * @param players The players to display
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        updatePlayers();
    }
    
    /**
     * Adds a player to the scoreboard
     * 
     * @param player The player to add
     * @param playerType The type of player (e.g., "Human Player")
     */
    public void addPlayer(Player player, String playerType) {
        if (!players.contains(player)) {
            players.add(player);
            JPanel scoreEntry = createScoreEntry(player, player.getScore(), playerType);
            playerPanels.put(player, scoreEntry);
            scoreListPanel.add(scoreEntry);
            scoreListPanel.add(Box.createVerticalStrut(5));
            revalidate();
            repaint();
        }
    }
    
    /**
     * Updates all player entries in the scoreboard
     */
    private void updatePlayers() {
        scoreListPanel.removeAll();
        playerPanels.clear();
        
        for (Player player : players) {
            JPanel scoreEntry = createScoreEntry(player, player.getScore(), getPlayerTypeName(player));
            playerPanels.put(player, scoreEntry);
            scoreListPanel.add(scoreEntry);
            scoreListPanel.add(Box.createVerticalStrut(5));
        }
        
        revalidate();
        repaint();
    }
    
    /**
     * Creates a score entry panel for a specific player.
     * 
     * @param player The Player whose score is being displayed
     * @param score The initial score for the player
     * @param playerType The type of player as a string
     * @return A JPanel representing the player's score entry
     */
    private JPanel createScoreEntry(Player player, int score, String playerType) {
        JPanel scoreEntry = new JPanel(new BorderLayout(10, 0));
        scoreEntry.setBackground(Color.WHITE);
        scoreEntry.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, getPlayerTypeColor(playerType)),
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
        
        return scoreEntry;
    }
    
    /**
     * Updates scores for all players in the scoreboard
     */
    public void updateScores() {
        for (Player player : players) {
            updateScore(player);
        }
    }
    
    /**
     * Updates the score display for a specific player
     * 
     * @param player The player whose score needs updating
     */
    public void updateScore(Player player) {
        JPanel panel = playerPanels.get(player);
        if (panel != null) {
            Component[] components = panel.getComponents();
            if (components.length > 1 && components[1] instanceof JLabel) {
                JLabel scoreLabel = (JLabel) components[1];
                scoreLabel.setText(String.valueOf(player.getScore()));
            }
        }
    }
    
    /**
     * Highlights the current player in the scoreboard
     * 
     * @param player The player whose turn is active
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        
        for (Map.Entry<Player, JPanel> entry : playerPanels.entrySet()) {
            Player panelPlayer = entry.getKey();
            JPanel panel = entry.getValue();
            
            if (panelPlayer.equals(player)) {
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
        
        revalidate();
        repaint();
    }
    
    /**
     * Highlights the winner with a special visual effect
     * 
     * @param winner The player who won the game
     */
    public void setWinner(Player winner) {
        JPanel panel = playerPanels.get(winner);
        if (panel != null) {
            panel.setBackground(new Color(255, 255, 220));
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(255, 215, 0)), // Gold
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(218, 165, 32), 1),
                    BorderFactory.createEmptyBorder(7, 7, 7, 0)
                )
            ));
            
            Component[] components = panel.getComponents();
            if (components.length > 0 && components[0] instanceof JLabel) {
                JLabel nameLabel = (JLabel) components[0];
                nameLabel.setText(winner.getName() + " 🏆");
            }
            
            revalidate();
            repaint();
        }
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
    
    /**
     * Test main method to demonstrate the ScoreboardViewer functionality
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            // Create test frame
            JFrame testFrame = new JFrame("Scoreboard Viewer Test");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(400, 500);
            testFrame.setLayout(new BorderLayout());
            
            // Create some test players
            ArrayList<Player> testPlayers = new ArrayList<>();
            
            // Mock Player classes for testing
            testPlayers.add(new MockPlayer("Alice", "Human Player"));
            testPlayers.add(new MockPlayer("Bob", "Random Player"));
            testPlayers.add(new MockPlayer("Charlie", "Fifteen Player"));
            testPlayers.add(new MockPlayer("Diana", "Odd Player"));
            testPlayers.add(new MockPlayer("Evan", "Wimp Player"));
            
            // Assign initial scores
            testPlayers.get(0).setScore(25);
            testPlayers.get(1).setScore(42);
            testPlayers.get(2).setScore(18);
            testPlayers.get(3).setScore(37);
            testPlayers.get(4).setScore(29);
            
            // Create scoreboard viewer
            ScoreboardViewer scoreboardViewer = new ScoreboardViewer(testPlayers);
            
            // Set a current player
            scoreboardViewer.setCurrentPlayer(testPlayers.get(1));  // Highlight Bob
            
            // Create a button panel
            JPanel buttonPanel = new JPanel();
            JButton updateButton = new JButton("Update Scores");
            updateButton.addActionListener(e -> {
                // Show dialog box
                JOptionPane.showMessageDialog(testFrame, 
                    "Scores will be updated when you click OK", 
                    "Update Scores", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Update player scores
                for (Player player : testPlayers) {
                    player.setScore(player.getScore() + (int)(Math.random() * 20));
                }
                
                // Update display
                scoreboardViewer.updateScores();
                
                // Check for winner
                for (Player player : testPlayers) {
                    if (player.getScore() >= 100) {
                        scoreboardViewer.setWinner(player);
                        break;
                    }
                }
            });
            
            JButton cyclePlayerButton = new JButton("Switch Current Player");
            cyclePlayerButton.addActionListener(e -> {
                // Find current player and move to next
                int currentIndex = testPlayers.indexOf(scoreboardViewer.currentPlayer);
                int nextIndex = (currentIndex + 1) % testPlayers.size();
                scoreboardViewer.setCurrentPlayer(testPlayers.get(nextIndex));
            });
            
            buttonPanel.add(updateButton);
            buttonPanel.add(cyclePlayerButton);
            
            // Add components to frame
            testFrame.add(scoreboardViewer, BorderLayout.CENTER);
            testFrame.add(buttonPanel, BorderLayout.SOUTH);
            
            // Display the frame
            testFrame.setLocationRelativeTo(null);
            testFrame.setVisible(true);
        });
    }
    
    /**
     * Mock Player class for testing the ScoreboardViewer
     */
    private static class MockPlayer extends Player {
        private String type;
        
        public MockPlayer(String name, String type) {
            super(name);
            this.type = type;
        }
        
        @Override
        public int play() {
            return 0; // Not used in the test
        }
        
        @Override
        public JPanel getPlayerFrame() {
            return null; // Not used in the test
        }
        
        // This method helps with identifying player type for coloring
        @Override
        public String toString() {
            return getName() + " (" + type + ")";
        }
    }
}