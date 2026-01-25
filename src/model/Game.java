package model;

import java.util.ArrayList;

public class Game {

    private Board board;
    private String currentTurn;
    private Piece multiJumpPiece; // Tracks if a piece MUST jump again
    private String winner;

    // Stores history of successful moves
    private ArrayList<String> moveHistory;

    public Game() {
        board = new Board();
        board.initializePieces();
        currentTurn = "black"; // Black usually starts
        winner = null; // No winner at start of game

        moveHistory = new ArrayList<>();
    }

    public boolean executePlayerMove(int startRow, int startCol, int endRow, int endCol) {
        // If there is a winner, do not allow more moves
        if (winner != null) {
            return false;
        }

        Piece pieceToMove = board.getPieceAt(startRow, startCol);

        // Enforce turn
        if (pieceToMove == null || !pieceToMove.getColor().equals(currentTurn)) {
            return false;
        }

        // Enforce multi-jump: If player is mid-jump, they must move the same piece
        if (multiJumpPiece != null && pieceToMove != multiJumpPiece) {
            return false;
        }

        // Try the move
        Piece moved = board.movePiece(startRow, startCol, endRow, endCol);
        if (moved == null) {
            return false;
        }

        // Record successful move in history
        String moveRecord = moved.getColor().toUpperCase() + ": (" + startRow + "," + startCol + ") -> (" + endRow + "," + endCol + ")";
        moveHistory.add(moveRecord);

        String movingColor = moved.getColor();
        String opponentColor = movingColor.equals("red") ? "black" : "red";

        // If opponent has no pieces left declare player who is moving winner
        if (board.countPieces(opponentColor) == 0) {
            winner = movingColor;
            return true;
        }

        // Handle Multi-jump
        int rowDist = Math.abs(endRow - startRow);
        if (rowDist == 2 && board.canCapture(endRow, endCol)) {
            multiJumpPiece = moved; // Player must jump again
            return true;
        }

        // End turn
        multiJumpPiece = null;
        currentTurn = (currentTurn.equals("red")) ? "black" : "red";

        // If opponent has no legal moves at the start of their turn declare winner
        if (!board.hasAnyLegalMove(currentTurn)) {
            winner = movingColor; // the player who just moved wins
        }

        return true;
    }

    public Board getBoard() {
        return board;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public String getWinner() {
        return winner;
    }

    public ArrayList<String> getMoveHistory() {
        return moveHistory;
    }
}
