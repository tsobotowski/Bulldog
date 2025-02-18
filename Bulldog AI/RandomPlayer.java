public class RandomPlayer extends Player {

    public RandomPlayer(String name) {
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
            boolean continueRoll = Math.random() < 0.5;
            System.out.println("Turn score: " + turnScore + ". Random decision to continue: " + continueRoll);
            if (!continueRoll) {
                System.out.println("Ending turn with " + turnScore + " points.");
                return turnScore;
            }
        }
    }
}