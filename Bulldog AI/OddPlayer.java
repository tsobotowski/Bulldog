public class OddPlayer extends Player {


	public OddPlayer () {
		this("Bold");
	}


	public OddPlayer (String name) {
		super(name);
	}


	public int play() {
		int score = 0;
		while(true) {
		int roll = (int) (Math.random()*6 + 1);
			if(roll == 6) {
				System.out.println(getName() + " rolled a 6, ending their turn.");
				return 0;
			}
			score += roll;
			if(roll % 2 == 0) {
				return score;
			}
			
		}
		
	}
}