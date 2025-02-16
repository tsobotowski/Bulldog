import java.util.Scanner;

public class HumanPlayer extends Player {


	public HumanPlayer () {
		this("Human");
	}


	public HumanPlayer (String name) {
		super(name);
	}


	public int play() {
		Scanner humSc = new Scanner(System.in);
		int turn = 0; //Turn score

		while(true) {
			int roll = (int) (Math.random()*6 + 1);

			if(roll == 6) { //End turn if player rolls a 6
				System.out.println(this.getName() + " rolled a 6, ending their turn.");
				return 0;
			}

			turn += roll;	
			System.out.println(this.getName() + " rolled a " + roll + ". \n Would you like to roll again [Y/n]?");

			while(true) {
				String input = humSc.nextLine();

				if (input.toLowerCase().equals("n")) {
					return turn;
				}
				else if(input.toLowerCase().equals("y")) {
					break;
				}
				else {
					System.out.println("Invalid input, please try again.");
				}
			}
		}
	}
}

