package sample;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.layout.VBox;


public class Hint {
    public static void display (String title, String message, int colorsUsed, int chromaticnumber){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("hints");
        window.setMinWidth(100);
        window.setMinHeight(50);

        VBox layout = new VBox();

        Label direct = new Label("Double click a vertice");
        direct.setStyle("-fx-border-style: solid; -fx-border-width: 2.5px;");

        Button easy = new Button("I need a bit of help");
        layout.setAlignment(Pos.CENTER);
        easy.setOnAction(e -> UsedColors (colorsUsed,chromaticnumber));

        Button hard = new Button("I am lost");
        hard.setOnAction( e -> startingPoint ());



        layout.getChildren().addAll(direct,easy,hard);
        Scene scene = new Scene(layout, 150, 100);
        window.setScene(scene);
        window.showAndWait();

    }
    private static void UsedColors(int colorsUsed, int chromaticnumber) {
        ShowUsedColors.display(colorsUsed, chromaticnumber );
    }

    private static void startingPoint() {
        HowToStart.display("StartingPoint");
    }

}