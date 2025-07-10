package com.dsagameshub.games;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MemoryGame {
    private static final int GRID_SIZE = 4;
    private static final String[] SYMBOLS = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private static final Color CARD_COLOR = Color.LIGHTBLUE;
    private static final Color MATCHED_COLOR = Color.LIGHTGREEN;
    
    private HashMap<Button, String> cardMap = new HashMap<>();
    private Stack<Button> flippedCards = new Stack<>();
    private int matchedPairs = 0;
    private int attempts = 0;
    
    private GridPane grid;
    private Text statusText;
    
    public MemoryGame() {
        initializeGame();
    }
    
    private void initializeGame() {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        // Create pairs of cards
        String[] cards = new String[GRID_SIZE * GRID_SIZE];
        for (int i = 0; i < cards.length/2; i++) {
            cards[2*i] = SYMBOLS[i];
            cards[2*i+1] = SYMBOLS[i];
        }
        
        // Shuffle the cards
        Collections.shuffle(java.util.Arrays.asList(cards));
        
        // Create the game board
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Button card = new Button();
                card.setPrefSize(80, 100);
                card.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
                
                String symbol = cards[i*GRID_SIZE + j];
                cardMap.put(card, symbol);
                
                // Set card back design
                Rectangle back = new Rectangle(70, 90, CARD_COLOR);
                back.setArcWidth(10);
                back.setArcHeight(10);
                card.setGraphic(back);
                
                card.setOnAction(e -> handleCardClick(card));
                grid.add(card, j, i);
            }
        }
        
        statusText = new Text("Attempts: 0 | Matched: 0/8");
        statusText.setFont(Font.font(16));
    }
    
    private void handleCardClick(Button card) {
        // Ignore if already matched or already flipped
        if (card.getGraphic() == null || flippedCards.size() >= 2) {
            return;
        }
        
        // Flip the card
        String symbol = cardMap.get(card);
        card.setText(symbol);
        card.setGraphic(null);
        flippedCards.push(card);
        
        // Check for match when two cards are flipped
        if (flippedCards.size() == 2) {
            attempts++;
            Button card1 = flippedCards.get(0);
            Button card2 = flippedCards.get(1);
            
            if (cardMap.get(card1).equals(cardMap.get(card2))) {
                // Match found
                card1.setStyle("-fx-background-color: " + toHex(MATCHED_COLOR) + ";");
                card2.setStyle("-fx-background-color: " + toHex(MATCHED_COLOR) + ";");
                flippedCards.clear();
                matchedPairs++;
                
                if (matchedPairs == GRID_SIZE * GRID_SIZE / 2) {
                    statusText.setText("You won in " + attempts + " attempts!");
                }
            } else {
                // No match - flip back after delay
                new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            javafx.application.Platform.runLater(() -> {
                                Rectangle back1 = new Rectangle(70, 90, CARD_COLOR);
                                Rectangle back2 = new Rectangle(70, 90, CARD_COLOR);
                                card1.setText("");
                                card2.setText("");
                                card1.setGraphic(back1);
                                card2.setGraphic(back2);
                                flippedCards.clear();
                            });
                        }
                    },
                    1000
                );
            }
            
            statusText.setText("Attempts: " + attempts + " | Matched: " + matchedPairs + "/8");
        }
    }
    
    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }
    
    public VBox getView() {
        Button resetBtn = new Button("Reset Game");
        resetBtn.setOnAction(e -> initializeGame());
        
        VBox layout = new VBox(20, 
            new Text("Memory Game (Stack/HashMap)"), 
            statusText, 
            grid, 
            resetBtn);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
        return layout;
    }
}