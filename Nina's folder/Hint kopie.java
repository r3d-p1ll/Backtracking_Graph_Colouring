package sample;


import javafx.scene.control.Button;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class Hint {
    public static void display (String title, String message){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("hints");
        window.setMinWidth(100);
        window.setMinHeight(50);

        GridPane layout = new GridPane();
        Button easy = new Button("I need a bit of help");
        layout.getChildren().add(easy);
        layout.setAlignment(Pos.CENTER);
        layout.setConstraints(easy, 50 ,20);
        easy.setOnAction(e -> UsedColors ());

        Button hard = new Button("I am lost");
        layout.getChildren().add(hard);
        layout.setConstraints(hard, 50, 40);
        hard.setOnAction( e -> startingPoint ());


        Scene scene = new Scene(layout, 150, 100);
        window.setScene(scene);
        window.showAndWait();

    }
    private static void UsedColors() {
        ShowUsedColors.display();
    }

    private static void startingPoint() {
        HowToStart.display("StartingPoint");
    }



}
