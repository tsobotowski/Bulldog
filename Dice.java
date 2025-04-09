import java.util.Random;

/**
 * Represents a multi-sided die with a configurable number of sides.
 * Uses a single static random number generator for efficiency.
 */
public class Dice {
    // Static random number generator shared across all Dice instances
    private static final Random RANDOM = new Random();

    // Number of sides on the die
    private final int sides;

    /**
     * Constructs a die with a specified number of sides.
     *
     * @param sides Number of sides on the die (must be > 0)
     * @throws IllegalArgumentException if sides is less than 1
     */
    public Dice(int sides) {
        if (sides < 1) {
            throw new IllegalArgumentException("Dice must have at least one side");
        }
        this.sides = sides;
    }

    /**
     * Rolls the die and returns a random number between 1 and the number of sides.
     *
     * @return A random integer between 1 and the number of sides (inclusive)
     */
    public int roll() {
        return RANDOM.nextInt(sides) + 1;
    }

    /**
     * Gets the number of sides on this die.
     *
     * @return The number of sides
     */
    public int getSides() {
        return sides;
    }
}