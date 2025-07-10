package com.dsagameshub.games;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Sudoku {
    private int[][] board;       //board variable
    private GridPane grid;
    
    public Sudoku() {
        board = new int[9][9];
        initializeBoard();
        setupUI();
    }
    
    private void initializeBoard() {
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        
        for (int i = 0; i < 9; i++) {
            System.arraycopy(puzzle[i], 0, board[i], 0, 9);
        }
    } 
    
    private void setupUI() {
        grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField cell = new TextField();
                cell.setPrefSize(40, 40);
                cell.setAlignment(javafx.geometry.Pos.CENTER);
                cell.setFont(javafx.scene.text.Font.font(18));
                
                if (board[i][j] != 0) {
                    cell.setText(String.valueOf(board[i][j]));
                    cell.setEditable(true);
                }
                
                final int row = i;
                final int col = j;
                cell.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal.matches("\\d*") || newVal.length() > 1) {
                        cell.setText(oldVal);
                    } else if (!newVal.isEmpty()) {
                        board[row][col] = Integer.parseInt(newVal);
                    } else {
                        board[row][col] = 0;
                    }
                });
                
                grid.add(cell, j, i);
            }
        }
    }
    
    public VBox getView() {
        Button solveBtn = new Button("Solve");
        solveBtn.setOnAction(e -> solvePuzzle());
        
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> resetBoard());
        
        HBox buttons = new HBox(10, solveBtn, resetBtn);
        buttons.setAlignment(javafx.geometry.Pos.CENTER);
        
        return new VBox(20, new Text("Sudoku Solver (Backtracking)"), grid, buttons);
    }
    
    private boolean solvePuzzle() {
        return solve(0, 0);
    }
    
    private boolean solve(int row, int col) {
        if (row == 9) return true;
        if (col == 9) return solve(row + 1, 0);
        if (board[row][col] != 0) return solve(row, col + 1);
        
        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                updateCell(row, col, num);
                
                if (solve(row, col + 1)) return true;
                
                board[row][col] = 0;
                updateCell(row, col, 0);
            }
        }
        return false;
    }
    
    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) return false;
            if (board[i][col] == num) return false;
        }
        
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num) return false;
            }
        }
        return true;
    }
    
    private void updateCell(int row, int col, int num) {
        TextField cell = (TextField) grid.getChildren().get(row * 9 + col);
        cell.setText(num == 0 ? "" : String.valueOf(num));
    }
    
    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((TextField) grid.getChildren().get(i * 9 + j)).isEditable()) {
                    board[i][j] = 0;
                    updateCell(i, j, 0);
                }
            }
        }
    }
}