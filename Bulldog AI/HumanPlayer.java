import java.util.Scanner;

public class HumanPlayer extends Player {
    private static Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public int play() {
        int turnScore = 0;
        while (true) {
            int roll = (int) (Math.random() * 6) + 1;
            System.out.println(getName() + " rolled a " + roll + ".");
            if (roll == 6) {
                System.out.println("Turn ends with 0 points.");
                return 0;
            }
            turnScore += roll;
            System.out.println("Current turn score: " + turnScore);
            System.out.print("Continue rolling? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("n")) {
                System.out.println("Ending turn with " + turnScore + " points.");
                return turnScore;
            }
            else if(!(input.equals("n") && !(input.equals("Y")))) {
            	System.out.println("Invalid input, please try again.");
            }
        }
    }
}