
import model.Board;
import model.Piece;

public class Main {

    public static void main(String[] args) {
        Board myBoard = new Board();
        myBoard.initializePieces();
        myBoard.printBoard();
        System.out.println();
        Piece result = myBoard.movePiece(5, 2, 4, 3);
        if (result == null) {
            System.out.println("Move failed");
        } else {
            System.out.println("Move succeeded");
        }
        System.out.println();
        myBoard.printBoard();

        // System.out.println("Hello");
    }
}
