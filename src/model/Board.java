/* Board is 8x8 and is responsible for keeping track of piece position. 
The board owns piece positions. Board initializes starting positions.
Empty squares are represented by empty = no piece present. 
The board is not responsible for player turnor win detection */
package model;

public class Board {

    // Initialize board
    Piece[][] board = new Piece[8][8];

    // print board info
    // public void printBoardInfo() {
    //     System.out.println("Board has " + board.length + " rows and " + board[0].length + " columns");
    // }
    // place a piece at a specific location
    public void placePiece(int row, int col, Piece p) {
        board[row][col] = p;
    }

    // initialize red and black pieces
    public void initializePieces() {
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    Piece redPiece = new Piece("red");
                    placePiece(row, col, redPiece);
                }
            }
        }

        for (int row = 5; row <= 7; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    Piece blackPiece = new Piece("black");
                    placePiece(row, col, blackPiece);
                }
            }
        }
    }

    // Print the board to the console
    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board[row][col];
                if (p == null) {
                    System.out.print(". ");   // empty square
                } else if (p.getColor().equals("red")) {
                    System.out.print("R ");   // red piece
                } else {
                    System.out.print("B ");   // black piece
                }
            }
            System.out.println();  // move to the next row
        }
    }

    public Piece getPieceAt(int row, int col) {
        return board[row][col];
    }

    private boolean isOnBoard(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    public boolean canCapture(int row, int col) {
        Piece pieceToMove = getPieceAt(row, col);
        if (pieceToMove == null) {
            return false;
        }
        String movingColor = pieceToMove.getColor();
        // Movement down (Red 'man' pieces)
        int adjRowDown = row + 1;
        int landRowDown = row + 2;

        // Movement up (Black 'man' pieces)
        int adjRowUp = row - 1;
        int landRowUp = row - 2;

        // RED PIECES: Only check downward directions (Directions 1 & 2)
        if (movingColor.equals("red")) {
            // Direction 1: Down-Right (column increases)
            int adjColDr = col + 1;
            int landColDr = col + 2;
            if (isOnBoard(landRowDown, landColDr) && isOnBoard(adjRowDown, adjColDr)) {
                Piece adjacentPiece = getPieceAt(adjRowDown, adjColDr);
                Piece landingSpace = getPieceAt(landRowDown, landColDr);

                if (adjacentPiece != null && !adjacentPiece.getColor().equals(movingColor) && landingSpace == null) {
                    return true;
                }
            }

            // Direction 2: Down-Left (column decreases)
            int adjColDl = col - 1;
            int landColDl = col - 2;
            if (isOnBoard(landRowDown, landColDl) && isOnBoard(adjRowDown, adjColDl)) {
                Piece adjacentPiece = getPieceAt(adjRowDown, adjColDl);
                Piece landingSpace = getPieceAt(landRowDown, landColDl);

                if (adjacentPiece != null && !adjacentPiece.getColor().equals(movingColor) && landingSpace == null) {
                    return true;
                }
            }
        }
        // BLACK PIECES: Only check upward directions (Directions 3 & 4)
        if (movingColor.equals("black")) {
            // Direction 3: Up-Right (column increases)
            int adjColUr = col + 1;
            int landColUr = col + 2;
            if (isOnBoard(landRowUp, landColUr) && isOnBoard(adjRowUp, adjColUr)) {
                Piece adjacentPiece = getPieceAt(adjRowUp, adjColUr);
                Piece landingSpace = getPieceAt(landRowUp, landColUr);

                if (adjacentPiece != null && !adjacentPiece.getColor().equals(movingColor) && landingSpace == null) {
                    return true;
                }
            }
            // Direction 4: Up-Left (column decreases)
            int adjColUl = col - 1;
            int landColUl = col - 2;
            if (isOnBoard(landRowUp, landColUl) && isOnBoard(adjRowUp, adjColUl)) {
                Piece adjacentPiece = getPieceAt(adjRowUp, adjColUl);
                Piece landingSpace = getPieceAt(landRowUp, landColUl);

                if (adjacentPiece != null && !adjacentPiece.getColor().equals(movingColor) && landingSpace == null) {
                    return true;
                }
            }
        }
        return false; // If no valid captures found for specific color
    }

    public Piece movePiece(int startRow, int startCol, int endRow, int endCol) {
        // Rejects moves with coordinates outside 0-7 (the board)
        if (startRow < 0 || startRow > 7
                || startCol < 0 || startCol > 7
                || endRow < 0 || endRow > 7
                || endCol < 0 || endCol > 7) {
            return null;
        }
        // Get the piece at the source square. Fail if empty/no piece
        Piece pieceToMove = board[startRow][startCol];
        if (pieceToMove == null) {
            return null;
        }
        // Fail if destination square is already occupied
        if (board[endRow][endCol] != null) {
            return null;
        }

        int rowDistance = Math.abs(endRow - startRow);
        int colDistance = Math.abs(endCol - startCol);
        // Normal move one space diagonally
        if (rowDistance == 1 && colDistance == 1) {
            // Ensures pieces move in the correct direction based on color
            if (pieceToMove.getColor().equals("red")) {
                if ((endRow - startRow) != 1) {
                    return null;
                }
            } else if (pieceToMove.getColor().equals("black")) {
                if ((endRow - startRow) != -1) {
                    return null;
                }
            }
            // Place the piece at the destination
            board[endRow][endCol] = pieceToMove;
            // Clear the source square
            board[startRow][startCol] = null;
            return pieceToMove;
            // Capture move    
        } else if (rowDistance == 2 && colDistance == 2) {
            // Get calculate and get capture piece coordinates
            int jumpedRow = (startRow + endRow) / 2;
            int jumpedCol = (startCol + endCol) / 2;
            Piece jumpedPiece = board[jumpedRow][jumpedCol];
            // If no piece to jump, cannot capture
            if (jumpedPiece == null) {
                return null;
            }// If there is a piece in the middle, check its color
            else {
                String movingColor = pieceToMove.getColor();
                String jumpedColor = jumpedPiece.getColor();
                // If piece to move and jumped piece are the same color, no capture
                if (movingColor.equals(jumpedColor)) {
                    return null;
                }
                // Ensures pieces move in the correct direction based on color
                if (movingColor.equals("red")) {
                    if ((endRow - startRow) != 2) {
                        return null;
                    }
                } else if (movingColor.equals("black")) {
                    if ((endRow - startRow) != -2) {
                        return null;
                    }
                }
                // If piece to move and jumped piece are not same color, capture opponent piece clear start piece and jump piece and move to new destination
                board[endRow][endCol] = pieceToMove;
                board[jumpedRow][jumpedCol] = null;
                board[startRow][startCol] = null;
                return pieceToMove;
            }
        } else {
            return null;
        }

    }

// public Piece removePieceAt(int row, int col)
// {
// }
}
