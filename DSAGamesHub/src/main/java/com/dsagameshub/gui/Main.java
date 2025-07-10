package com.dsagameshub.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu(primaryStage);
        Scene scene = new Scene(mainMenu, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/light-theme.css").toExternalForm());
        primaryStage.setTitle("DSA Games Hub ");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}