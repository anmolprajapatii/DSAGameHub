package com.dsagameshub.gui;
import com.dsagameshub.games.MemoryGame;
import com.dsagameshub.games.Sudoku;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameLauncher {
    public GameLauncher(Stage primaryStage, String gameName) {
        BorderPane root = new BorderPane();
        Button backBtn = new Button("Back to Menu");
        root.setTop(backBtn);
        
        try {
            switch (gameName) {
                case "Sudoku":
                    Sudoku sudoku = new Sudoku();
                    root.setCenter(sudoku.getView());
                    break;
                
                case "Memory Game":
                    MemoryGame memoryGame = new MemoryGame();
                    root.setCenter(memoryGame.getView());
                    break;
                    
                default:
                    root.setCenter(new Text("Game not implemented yet!"));
            }
        } catch (Exception e) {
            root.setCenter(new Text("Error loading game: " + e.getMessage()));
            e.printStackTrace();
        }
        
        backBtn.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(primaryStage);
            Scene scene = new Scene(mainMenu, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles/light-theme.css").toExternalForm());
            primaryStage.setScene(scene);
        });
        
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/light-theme.css").toExternalForm());
        primaryStage.setScene(scene);
    }
}