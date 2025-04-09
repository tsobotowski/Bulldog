import java.util.ArrayList;

/**
 * Manages the collection of players for the Bulldog Dice Game.
 * Provides methods to add players, retrieve player information,
 * and update player scores.
 */
public class PlayerManager {
    private ArrayList<Player> players;
    
    /**
     * Constructs a new PlayerManager with an empty players list.
     */
    public PlayerManager() {
        players = new ArrayList<>();
    }
    
    /**
     * Adds a player to the collection.
     * 
     * @param player The Player object to add
     * @return true if the player was added successfully
     */
    public boolean addPlayer(Player player) {
        if (player != null) {
            return players.add(player);
        }
        return false;
    }
    
    /**
     * Removes a player from the collection.
     * 
     * @param player The Player object to remove
     * @return true if the player was removed successfully
     */
    public boolean removePlayer(Player player) {
        return players.remove(player);
    }
    
    /**
     * Removes a player at the specified index.
     * 
     * @param index The index of the player to remove
     * @return The removed Player object
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Player removePlayerAt(int index) {
        return players.remove(index);
    }
    
    /**
     * Returns the name of the player at the specified index.
     * 
     * @param index The index of the player
     * @return The name of the player
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String getPlayerName(int index) {
        return players.get(index).getName();
    }
    
    /**
     * Sets the score for the player at the specified index.
     * 
     * @param index The index of the player
     * @param score The new score to set
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void setPlayerScore(int index, int score) {
        players.get(index).setScore(score);
    }
    
    /**
     * Gets the score for the player at the specified index.
     * 
     * @param index The index of the player
     * @return The player's current score
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public int getPlayerScore(int index) {
        return players.get(index).getScore();
    }
    
    /**
     * Gets the Player object at the specified index.
     * 
     * @param index The index of the player
     * @return The Player object
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Player getPlayer(int index) {
        return players.get(index);
    }
    
    /**
     * Gets all players in the collection.
     * 
     * @return An ArrayList containing all Player objects
     */
    public ArrayList<Player> getAllPlayers() {
        return new ArrayList<>(players);
    }
    
    /**
     * Gets the number of players in the collection.
     * 
     * @return The number of players
     */
    public int getPlayerCount() {
        return players.size();
    }
    
    /**
     * Checks if the collection contains the specified player.
     * 
     * @param player The Player object to check for
     * @return true if the player exists in the collection
     */
    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }
    
    /**
     * Clears all players from the collection.
     */
    public void clearPlayers() {
        players.clear();
    }
}