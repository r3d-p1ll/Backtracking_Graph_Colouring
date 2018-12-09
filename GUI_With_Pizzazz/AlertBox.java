package sample;

//I'm trying to imitaite https://codepen.io/joshadamous/pen/yyyqJZ
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;

import java.util.Random;


public class AlertBox {
    static TextField num_vert;
    public static void display (String title, String message){
        //the material design thing that I wanted to implement needed an external library(JFoenix) and I just remembered we are not allowed to use them.
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);

        GridPane layout = new GridPane();
        //add the style.
        layout.setStyle("-fx-background-color: #db4c25;");
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(10);

        Label label = new Label(message);
        //modified the font
        label.setFont(new Font("Calibre", 14));
        GridPane.setConstraints(label,0,0);

        TextField num_vert = new TextField();
        num_vert.setPromptText("Vertices");
        //modified the font
        num_vert.setFont(new Font("Gruppo", 12));
        GridPane.setConstraints(num_vert, 0 ,2);

        TextField num_edges = new TextField();
        num_edges.setPromptText("Edges");
        //modified the font
        num_edges.setFont(new Font("Gruppo", 12));
        GridPane.setConstraints(num_edges, 0, 3);

        //event listener mouse hover.
        Button closeButton = new Button("Go!");
        closeButton.setOnMouseEntered(e -> {
            closeButton.setStyle("-fx-background-color: #2EE59D");
        });
        closeButton.setOnMouseExited(e -> {
            closeButton.setStyle("-fx-background-color: #fff");
        });


        closeButton.setOnAction(e -> {
            if(isInt(num_vert, num_edges)){
                RandomGeneratorModeThree.random_gen(Integer.parseInt(num_vert.getText()), Integer.parseInt(num_edges.getText()));
                e.consume();
                window.close();
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
