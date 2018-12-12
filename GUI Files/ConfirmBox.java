package sample;

import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * ConfirmBox class is invoked whenever the user wants to quit the game.
 * Window pops up and asks the user for confirmation, before quitting.
 */
public class ConfirmBox {
    private static boolean answer;
    private static final double MIN_WIDTH = 250;
    private static final double MIN_HEIGHT = 160;

    /**
     * display method for invoking the class.
     * @param title of the screen
     * @param message of the screen.
     */
    public static boolean display (String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(MIN_WIDTH);
        window.setMinHeight(MIN_HEIGHT);

        Label label = new Label();
        label.setMinWidth(MIN_WIDTH);
        label.setMinHeight(MIN_HEIGHT/2);
        label.setText(message);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");


        //Create two buttons
        Button yesButton = new Button("Yes");
        yesButton.setPrefWidth(MIN_WIDTH/2);
        yesButton.setPrefHeight(MIN_WIDTH/3.5);
        yesButton.setStyle("-fx-background-color: #fff");

        Button noButton = new Button("No");
        noButton.setPrefWidth(MIN_WIDTH/2);
        noButton.setPrefHeight(MIN_WIDTH/3.5);
        noButton.setStyle("-fx-background-color: #fff");

        //hover effects
        yesButton.setOnMouseEntered(e -> yesButton.setStyle("-fx-background-color: #2EE59D;"));
        noButton.setOnMouseEntered(e -> noButton.setStyle("-fx-background-color: #2EE59D;"));
        yesButton.setOnMouseExited(e -> yesButton.setStyle("-fx-background-color: #fff"));
        noButton.setOnMouseExited(e -> noButton.setStyle("-fx-background-color: #fff"));


        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox master = new VBox(0); //main layout
        HBox layout1 = new HBox(10); //layout for text
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label);

        HBox layout2 = new HBox(10);
        layout2.getChildren().addAll(yesButton, noButton);
        layout2.setOnKeyPressed((event) -> { if(event.getCode() == KeyCode.ENTER) {
            answer = true;
            window.close(); } });

        master.getChildren().addAll(layout1, layout2);
        master.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(master);
//        File f = new File("myDialogs.css");
//        scene.getStylesheets().add(ConfirmBox.class.getResource("myDialog.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
