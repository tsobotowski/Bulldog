public class FifteenPlayer extends Player {

    public FifteenPlayer(String name) {
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
            if (turnScore >= 15) {
                System.out.println("Reached 15 points. Ending turn.");
                return turnScore;
            }
            System.out.println("Continuing to roll.");
        }
    }
}