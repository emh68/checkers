
import java.util.Scanner;
import model.Board;
import model.Game;

public class Main {

    public static void main(String[] args) {
        // Reads player input from console
        Scanner scanner = new Scanner(System.in);

        boolean playAgain = true;

        while (playAgain) {
            // Start a new game only if the players choose to play again
            Game game = new Game();

            while (true) {
                // Always displays the current game state before asking for the next move
                Board board = game.getBoard();
                System.out.println("\nTurn: " + game.getCurrentTurn());
                board.printBoard();

                // If there is a winner, declare winner and end the current game
                if (game.getWinner() != null) {
                    System.out.println("\nWinner: " + game.getWinner());
                    break;
                }

                // Input format is 4 integers: startRow, startCol, endRow, endCol
                // Example: "5 0 4 1"
                System.out.println("Enter move as: startRow startCol endRow endCol (or 'q' to quit)");
                String line = scanner.nextLine().trim();

                // Quit game if letter 'q' is typed (exits the entire program)
                if (line.equalsIgnoreCase("q")) {
                    playAgain = false;
                    break;
                }

                // Split input so extra spaces is not invalid
                String[] parts = line.split("\\s+");

                // Ensure player enters exactly 4 integers, if not provide feedback
                if (parts.length != 4) {
                    System.out.println("Invalid input format.");
                    continue;
                }

                // Convert each part entered into an integer
                int startRow, startCol, endRow, endCol;
                try {
                    startRow = Integer.parseInt(parts[0]);
                    startCol = Integer.parseInt(parts[1]);
                    endRow = Integer.parseInt(parts[2]);
                    endCol = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    // If non-numeric input provide feedback to player
                    System.out.println("Please enter 4 integers.");
                    continue;
                }

                // If move is illegal (i.e. invalid move, forced capture ignored, etc.) provide feedback to player
                boolean ok = game.executePlayerMove(startRow, startCol, endRow, endCol);
                if (!ok) {
                    System.out.println("Invalid move. Try again.");
                }
            }

            // If the user quit with 'q', do not ask to play again
            if (!playAgain) {
                break;
            }

            // Ask players if they want to play again after a winner is declared
            System.out.print("\nPlay again? (y/n): ");
            String answer = scanner.nextLine().trim();

            if (!answer.equalsIgnoreCase("y")) {
                playAgain = false;
            }
        }

        // Clean up resources before exiting.
        scanner.close();
        System.out.println("Game ended.");
    }
}
