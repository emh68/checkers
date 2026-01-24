/* Board is 8x8 and is responsible for keeping track of piece position. 
The board owns piece positions. Board initializes starting positions.
Empty squares are represented by empty = no piece present. 
The board is not responsible for player turnor win detection */
package model;

public class Board {

    // Initialize board
    Piece[][] board = new Piece[8][8];

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

    public boolean checkAnyCaptureAvailable(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPieceAt(row, col);
                if (piece != null && piece.getColor().equals(color)) {
                    if (canCapture(row, col)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canCapture(int row, int col) {
        Piece pieceToMove = getPieceAt(row, col);
        if (pieceToMove == null) {
            return false;
        }
        String movingColor = pieceToMove.getColor();
        boolean isKing = pieceToMove.isKing();

        // RED PIECES or KINGS: Check downward directions (Directions 1 & 2)
        if (movingColor.equals("red") || isKing) {
            int adjRowDown = row + 1;
            int landRowDown = row + 2;

            // Direction 1: Down-Right
            int adjColDr = col + 1;
            int landColDr = col + 2;
            if (isOnBoard(landRowDown, landColDr) && isOnBoard(adjRowDown, adjColDr)) {
                Piece adjacentPiece = getPieceAt(adjRowDown, adjColDr);
                Piece landingSpace = getPieceAt(landRowDown, landColDr);
                if (adjacentPiece != null && !adjacentPiece.getColor().equals(movingColor) && landingSpace == null) {
                    return true;
                }
            }

            // Direction 2: Down-Left
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
        // BLACK PIECES or KINGS: Check upward directions (Directions 3 & 4)
        if (movingColor.equals("black") || isKing) {
            int adjRowUp = row - 1;
            int landRowUp = row - 2;

            // Direction 3: Up-Right
            int adjColUr = col + 1;
            int landColUr = col + 2;
            if (isOnBoard(landRowUp, landColUr) && isOnBoard(adjRowUp, adjColUr)) {
                Piece adjacentPiece = getPieceAt(adjRowUp, adjColUr);
                Piece landingSpace = getPieceAt(landRowUp, landColUr);
                if (adjacentPiece != null && !adjacentPiece.getColor().equals(movingColor) && landingSpace == null) {
                    return true;
                }
            }

            // Direction 4: Up-Left
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
        if (!isOnBoard(startRow, startCol) || !isOnBoard(endRow, endCol)) {
            return null;
        }
        // Check if the piece to move and the space moving to are in bounds (on the board)
        Piece pieceToMove = getPieceAt(startRow, startCol);
        if (pieceToMove == null || getPieceAt(endRow, endCol) != null) {
            return null;
        }

        String movingColor = pieceToMove.getColor();
        int rowDistance = Math.abs(endRow - startRow);
        int colDistance = Math.abs(endCol - startCol);
        boolean isKing = pieceToMove.isKing();

        // Forced capture rule: if any piece can capture, player must capture (jump)
        if (checkAnyCaptureAvailable(movingColor) && rowDistance != 2) {
            return null;
        }

        // Normal move one space diagonally (distance 1)
        if (rowDistance == 1 && colDistance == 1) {
            if (!isKing) {
                if (movingColor.equals("red") && (endRow - startRow) != 1) {
                    return null;
                }
                if (movingColor.equals("black") && (endRow - startRow) != -1) {
                    return null;
                }
            }

            // Place the piece at the destination
            board[endRow][endCol] = pieceToMove;
            // Clear the source square
            board[startRow][startCol] = null;
        } // Capture move two spaces diagonally (Distance 2)  
        else if (rowDistance == 2 && colDistance == 2) {
            if (!isKing) {
                if (movingColor.equals("red") && (endRow - startRow) != 2) {
                    return null;
                }
                if (movingColor.equals("black") && (endRow - startRow) != -2) {
                    return null;
                }
            }

            // Calculate and get capture piece coordinates
            int jumpedRow = (startRow + endRow) / 2;
            int jumpedCol = (startCol + endCol) / 2;
            Piece jumpedPiece = getPieceAt(jumpedRow, jumpedCol);

            // If jumped piece not null and not same color as piece moving complete capture
            if (jumpedPiece != null && !jumpedPiece.getColor().equals(movingColor)) {
                board[endRow][endCol] = pieceToMove;
                board[jumpedRow][jumpedCol] = null;
                board[startRow][startCol] = null;
            } else {
                return null; // No valid piece to jump
            }
        } else {
            return null; // Invalid move distance
        }

        // King promotion
        if (movingColor.equals("red") && endRow == 7) {
            pieceToMove.setKing(true);
        } else if (movingColor.equals("black") && endRow == 0) {
            pieceToMove.setKing(true);
        }

        return pieceToMove;
    }

    // Counts how many pieces of a given color are still on the board
    public int countPieces(String color) {
        int count = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPieceAt(row, col);
                if (piece != null && piece.getColor().equals(color)) {
                    count++;
                }
            }
        }
        return count;
    }

    // Returns true if the player has at least one normal (non-capture) move available
    public boolean hasAnySimpleMove(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPieceAt(row, col);
                if (piece == null || !piece.getColor().equals(color)) {
                    continue;
                }

                boolean isKing = piece.isKing();

                // Red moves "down" (+1 row), black moves "up" (-1 row).
                // Kings can move both directions.
                if (color.equals("red") || isKing) {
                    if (isOnBoard(row + 1, col + 1) && getPieceAt(row + 1, col + 1) == null) {
                        return true;
                    }
                    if (isOnBoard(row + 1, col - 1) && getPieceAt(row + 1, col - 1) == null) {
                        return true;
                    }
                }

                if (color.equals("black") || isKing) {
                    if (isOnBoard(row - 1, col + 1) && getPieceAt(row - 1, col + 1) == null) {
                        return true;
                    }
                    if (isOnBoard(row - 1, col - 1) && getPieceAt(row - 1, col - 1) == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Returns true if the player has any legal move (either a capture or a simple move)
    public boolean hasAnyLegalMove(String color) {
        // If any capture is possible, a legal move exists
        if (checkAnyCaptureAvailable(color)) {
            return true;
        }
        // Otherwise, if any simple move exists, a legal move exists
        return hasAnySimpleMove(color);
    }
}
