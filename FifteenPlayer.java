import java.util.Scanner;

public class FifteenPlayer extends Player {


	public FifteenPlayer () {
		this("Fifteen");
	}


	public FifteenPlayer (String name) {
		super(name);
	}


	public int play() {
		int turn = 0;
		while(turn <= 15) {
			int roll = (int) (Math.random()*6 + 1);
			if(roll == 6) {
				System.out.println("Player " + getName() + " rolled a 6, ending their turn.");
				return 0;
			}
			turn += roll;
			System.out.println(getName() + " rolled a " + roll + ", turn score is "+ turn + "\nRolling again...");
		}
		return turn;
	}
}