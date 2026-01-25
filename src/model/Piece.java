package model;

public class Piece {

    // "red" or "black"
    private String color;

    // Indicates whether this piece has been promoted to a king
    private boolean isKing = false;

    // Creates a new checkers piece of the given color
    public Piece(String color) {
        this.color = color;
    }

    // Returns the color of the piece ("red" or "black")
    public String getColor() {
        return color;
    }

    // Returns true if this piece is a king
    public boolean isKing() {
        return isKing;
    }

    // Promotes this piece to a king
    public void setKing(boolean king) {
        isKing = king;
    }
}
