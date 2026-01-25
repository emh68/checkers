package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Board;
import model.Game;
import model.Piece;

public class CheckersApp extends Application {

    private Game game;
    private Button[][] squares;
    private Label statusLabel;

    // Selection state
    private Integer selectedRow = null;
    private Integer selectedCol = null;

    @Override
    public void start(Stage stage) {
        game = new Game();
        squares = new Button[8][8];
        statusLabel = new Label();

        GridPane grid = buildBoardGrid();
        updateUI();

        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(e -> {
            game = new Game();
            selectedRow = null;
            selectedCol = null;
            updateUI();
        });

        HBox topBar = new HBox(12, statusLabel, newGameBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(grid);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root);
        stage.setTitle("Checkers (MVP)");
        stage.setScene(scene);
        stage.show();
    }

    private GridPane buildBoardGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button btn = new Button();
                btn.setMinSize(55, 55);
                btn.setMaxSize(55, 55);

                final int r = row;
                final int c = col;

                btn.setOnAction(e -> handleSquareClick(r, c));

                squares[row][col] = btn;
                grid.add(btn, col, row);
            }
        }

        return grid;
    }

    private void handleSquareClick(int row, int col) {
        // If game ended, ignore clicks
        if (game.getWinner() != null) {
            return;
        }

        // First click selects a start square
        if (selectedRow == null) {
            Piece piece = game.getBoard().getPieceAt(row, col);

            // Only allow selecting a piece that matches current turn
            if (piece == null || !piece.getColor().equals(game.getCurrentTurn())) {
                return;
            }

            selectedRow = row;
            selectedCol = col;
            updateUI();
            return;
        }

        // Second click attempts a move
        int startRow = selectedRow;
        int startCol = selectedCol;

        String beforeTurn = game.getCurrentTurn();
        boolean ok = game.executePlayerMove(startRow, startCol, row, col);
        String afterTurn = game.getCurrentTurn();

        if (ok) {
            // If the turn did NOT change, it is likely a forced multi-jump continuation.
            // Keep selection on the moved piece to guide the player.
            if (beforeTurn.equals(afterTurn)) {
                selectedRow = row;
                selectedCol = col;
            } else {
                // Turn ended normally; clear selection
                selectedRow = null;
                selectedCol = null;
            }
        } else {
            // Invalid move: keep selection so player can try a different destination
        }

        updateUI();
    }

    private void updateUI() {
        Board board = game.getBoard();

        // Update status text
        if (game.getWinner() != null) {
            statusLabel.setText("Winner: " + game.getWinner());
        } else if (selectedRow != null) {
            statusLabel.setText("Turn: " + game.getCurrentTurn() + " | Selected: (" + selectedRow + "," + selectedCol + ")");
        } else {
            statusLabel.setText("Turn: " + game.getCurrentTurn());
        }

        // Update each square button text + highlight selection
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button btn = squares[row][col];
                Piece piece = board.getPieceAt(row, col);

                String text = "";
                if (piece != null) {
                    // Show kings using RK = red king and BK = black king
                    if (piece.getColor().equals("red")) {
                        text = piece.isKing() ? "RK" : "R";
                    } else {
                        text = piece.isKing() ? "BK" : "B";
                    }
                }
                btn.setText(text);

                // Color board squares
                boolean darkSquare = (row + col) % 2 == 1;
                String baseStyle = darkSquare
                        ? "-fx-background-color: #666666; -fx-text-fill: white; -fx-font-size: 16px;"
                        : "-fx-background-color: #DDDDDD; -fx-text-fill: black; -fx-font-size: 16px;";

                // Highlight selected square
                if (selectedRow != null && selectedRow == row && selectedCol == col) {
                    baseStyle += " -fx-border-color: yellow; -fx-border-width: 3px;";
                } else {
                    baseStyle += " -fx-border-color: black; -fx-border-width: 1px;";
                }

                btn.setStyle(baseStyle);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
