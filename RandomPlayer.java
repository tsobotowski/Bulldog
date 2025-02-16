
public class RandomPlayer extends Player {


	public RandomPlayer () {
		this("Random");
	}


	public RandomPlayer (String name) {
		super(name);
	}


	public int play() {
		int turn = 0;
		while(true) {
			int roll = (int) (Math.random()*6 + 1);
			if(roll == 6) {
				System.out.println("Player " + getName() + " rolled a 6, ending their turn.");
				return 0;
			}
			else {
				System.out.println("Player " + getName() + " rolled a " + roll);
				turn += roll;
				if((int) (Math.random()*2) == 0) {
					System.out.println("Player " + getName() + " chose to end their turn.");
					return turn;
				}
			}
		}
	}
}
