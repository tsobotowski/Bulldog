import java.util.ArrayList;
import java.util.Scanner;

public class Prog6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();

        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + (i + 1) + ":");
            System.out.print("Enter type (H/R/F/U/W/O): ");
            String type = scanner.nextLine().trim().toUpperCase();
            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();

            switch (type) {
                case "H":
                    players.add(new HumanPlayer(name));
                    break;
                case "R":
                    players.add(new RandomPlayer(name));
                    break;
                case "F":
                    players.add(new FifteenPlayer(name));
                    break;
                case "U":
                    players.add(new UniquePlayer(name));  //Should be unique player
                    break;
                case "W":
                    players.add(new WimpPlayer(name));
                    break;
                case "O":
                    players.add(new OddPlayer(name));
                    break;
                default:
                    System.out.println("Invalid type. Defaulting to Wimp.");
                    players.add(new WimpPlayer(name));
            }
        }

        boolean gameOver = false;
        while (!gameOver) {
            for (Player player : players) {
                System.out.println("\n*** " + player.getName() + "'s turn ***");
                int turnScore = player.play();
                player.setScore(player.getScore() + turnScore);

                System.out.println("\nScores:");
                for (Player p : players) {
                    System.out.println(p.getName() + ": " + p.getScore());
                }

                if (player.getScore() >= 104) {
                    System.out.println("\n*** " + player.getName() + " wins! ***");
                    gameOver = true;
                    break;
                }
            }
        }
        scanner.close();
    }
}