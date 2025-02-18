public class UniquePlayer extends Player {

    public UniquePlayer(String name) {
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
            System.out.println("Turn score: " + turnScore);
            if (turnScore >= 10) {
                boolean continueRoll = Math.random() < 0.25;
                System.out.println("Unique strategy: 25% chance to continue. Decision: " + continueRoll);
                if (!continueRoll) {
                    System.out.println("Ending turn with " + turnScore + " points.");
                    return turnScore;
                }
            } else {
                System.out.println("Continuing to roll.");
            }
        }
    }
}