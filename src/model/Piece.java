package model;

public class Piece {

    private String color;
    private boolean isKing = false;

    // Constructor
    public Piece(String color) {
        this.color = color;
    }

    // Getter
    public String getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }
}
