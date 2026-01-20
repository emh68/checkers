
import model.Board;
import model.Piece;

public class Main {

    public static void main(String[] args) {
        Board myBoard = new Board();
        myBoard.initializePieces();
        myBoard.printBoard();
        System.out.println();
        myBoard.movePiece(5, 2, 4, 3);

        // 2. Test Capture Move
        System.out.println("\n--- Testing Capture Move ---");
        // Clear board or just use a fresh one
        Board captureBoard = new Board();

        // Place a Black piece and a Red piece in a capture position
        Piece black = new Piece("black");
        Piece red = new Piece("red");

        captureBoard.placePiece(5, 2, black);
        captureBoard.placePiece(4, 3, red); // Red is diagonal to black

        System.out.println("Before Capture:");
        captureBoard.printBoard();

        // Black jumps over Red (5,2) -> (3,4)
        Piece result = captureBoard.movePiece(5, 2, 3, 4);

        if (result != null) {
            System.out.println("\nCapture Successful!");
        } else {
            System.out.println("\nCapture Failed!");
        }

        captureBoard.printBoard();

        // if (result == null) {
        //     System.out.println("Move failed");
        // } else {
        //     System.out.println("Move succeeded");
        // }
        // System.out.println();
        // myBoard.printBoard();
        // System.out.println("Hello");
    }
}
