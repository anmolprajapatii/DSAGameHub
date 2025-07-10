package com.dsagameshub.gui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends BorderPane {
    public MainMenu(Stage primaryStage) {
        Text title = new Text("DSA Games Hub by Anmol Prajapati\n and Navin Paul");
        title.setFont(Font.font("Arial", 36));
        
        Button sudokuBtn = createGameButton("Sudoku");
        Button memoryBtn = createGameButton("Memory Game");
        
        ToggleButton themeToggle = new ToggleButton("Dark Mode");
        
        VBox gameButtons = new VBox(20, sudokuBtn, memoryBtn);
        gameButtons.setAlignment(Pos.CENTER);
        
        VBox centerLayout = new VBox(40, title, gameButtons);
        centerLayout.setAlignment(Pos.CENTER);
        
        HBox topBar = new HBox(themeToggle);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(10));
        
        this.setTop(topBar);
        this.setCenter(centerLayout);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        
        sudokuBtn.setOnAction(e -> new GameLauncher(primaryStage, "Sudoku"));
        memoryBtn.setOnAction(e -> new GameLauncher(primaryStage, "Memory Game"));
        
        themeToggle.setOnAction(e -> {
            if (themeToggle.isSelected()) {
                this.getScene().getStylesheets().clear();
                this.getScene().getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());
            } else {
                this.getScene().getStylesheets().clear();
                this.getScene().getStylesheets().add(getClass().getResource("/styles/light-theme.css").toExternalForm());
            }
        });
    }
    
    private Button createGameButton(String text) {
        Button btn = new Button(text);
        btn.setPrefSize(200, 50);
        btn.setFont(Font.font("Arial", 16));
        return btn;
    }
}