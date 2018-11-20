package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.awt.*;
import java.util.Random;

public class AlertBox {
    public static void display (String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(10);

        Label label = new Label(message);
        GridPane.setConstraints(label,0,0);

        TextField num_vert = new TextField();
        num_vert.setPromptText("Vertices");
        GridPane.setConstraints(num_vert, 0 ,2);

        TextField num_edges = new TextField();
        num_edges.setPromptText("Edges");
        GridPane.setConstraints(num_edges, 0, 3);

        Button closeButton = new Button("Go!");
        closeButton.setOnAction(e -> {
            if(isInt(num_vert, num_edges)){
                RandomGeneratorModeThree.random_gen(Integer.parseInt(num_vert.getText()), Integer.parseInt(num_edges.getText()));
            }
        });
        GridPane.setConstraints(closeButton, 4, 3);

        layout.getChildren().addAll(label, closeButton, num_vert, num_edges);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static boolean isInt(TextField input1, TextField input2){
        try{
            Integer.parseInt(input1.getText());
            Integer.parseInt(input2.getText());
            return true;
        }
        catch (NumberFormatException e){
            System.out.println("Not a number, please enter digits");
            return false;
        }
    }
}
