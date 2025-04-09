import javax.swing.*;
import java.awt.*;

/**
 * Represents a generic player in a dice game.
 * 
 * This base class provides fundamental attributes and methods 
 * that can be inherited and extended by specific player types.
 * It manages basic player information such as name, score, 
 * and a graphical user interface panel.
 * 
 * @author Unknown
 * @version 1.0
 * @since Summer 2024
 */
public class Player {
    /** The name of the player. */
    private String name;

    /** The current score of the player. */
    private int score;

    /** The graphical panel associated with this player. */
    private JPanel playerFrame;

    /**
     * Constructs a new Player with the specified name.
     * 
     * Initializes the player with:
     * - The given name
     * - An initial score of 0
     * - A new, empty JPanel
     * 
     * @param name the name to assign to the player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.playerFrame = new JPanel();
    }

    /**
     * Retrieves the player's name.
     * 
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     * 
     * @param name the new name for the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the player's current score.
     * 
     * @return the current score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     * 
     * @param score the new score for the player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Retrieves the player's graphical frame.
     * 
     * @return the JPanel associated with the player
     */
    public JPanel getPlayerFrame() {
        return playerFrame;
    }

    /**
     * Sets the player's graphical frame.
     * 
     * @param playerFrame the JPanel to associate with the player
     */
    public void setPlayerFrame(JPanel playerFrame) {
        this.playerFrame = playerFrame;
    }

    /**
     * Base play method to be overridden by subclasses.
     * 
     * This method provides a default implementation that 
     * subclasses should override with their specific 
     * turn-taking logic.
     * 
     * @return the score earned during the player's turn
     */
    public int play() {
        return 0;
    }
}