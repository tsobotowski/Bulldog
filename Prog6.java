import java.util.ArrayList;
import java.util.Scanner;

public class Prog6 {

	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<Player>();
		int win = 104;
		
        Scanner sc = new Scanner(System.in);
		System.out.println("Please enter number of players: ");	
		int numPlayers = sc.nextInt();
		
		for(int i = 0; i < numPlayers; i++) {
			System.out.println("Please select player type for player " + (i + 1));	
			System.out.println("options are: \n 1: Human Player\n 2: Random Player\n 3: Fifteen Player\n 4: Odd Player\n 5: Wimp Player");
			int select = sc.nextInt();
			sc.nextLine(); //Clear the input buffer
			
			System.out.println("Please name this player");
			String name = sc.nextLine();
			
			switch(select) {
			case 1: //Human player
				players.add(new HumanPlayer(name));
				break;
			case 2: //Random player
				players.add(new RandomPlayer(name));
				break;
			case 3: //Fifteen player
				players.add(new FifteenPlayer(name));
				break;
			case 4: //Unique player
				players.add(new OddPlayer(name));
				break;
			case 5: //Wimp player
				players.add(new WimpPlayer(name));
				break;
			}
		}
		boolean won = false;
	        while (!won) { //Game loop
	        	for(Player player : players) {
	        		System.out.println(player.getName() + "'s turn.\n");
	        		int playerScore = player.play(); //Returns score from current player's turn
	        		player.setScore(player.getScore() + playerScore); //Updates the current player's score  
	        		System.out.println("Turn over, " + player.getName() + " rolled " + playerScore + " during their turn, their current score " + player.getScore() + ".\n");
	        		
	        		if(player.getScore() >= 104) {
	        			System.out.println("\n\t~~~ Game Over ~~~\n\t Congratulations player: " + player.getName());
	        			won = true;
	        			break;
	        		}
	            }
	        }
		
		
	

}
}
