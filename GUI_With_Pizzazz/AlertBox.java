package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.io.File;

public class AlertBox {
    public static void display (String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(300);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(10);

        Label label_random = new Label(message);
        GridPane.setConstraints(label_random,0,0);

        TextField num_vert = new TextField();
        num_vert.setPromptText("Vertices");
        GridPane.setConstraints(num_vert, 0 ,2);

        TextField num_edges = new TextField();
        num_edges.setPromptText("Edges");
        GridPane.setConstraints(num_edges, 0, 3);

        Button goButton = new Button("Go!");
        goButton.setOnMouseEntered(e -> {
            goButton.setStyle("-fx-background-color: #2EE59D");
        });
        goButton.setOnMouseExited(e -> {
            goButton.setStyle("-fx-background-color: #cccccc");
        });
        goButton.setOnAction(e -> {
            if(isInt(num_vert, num_edges)){
                RandomGeneratorModeThree.random_gen(Integer.parseInt(num_vert.getText()), Integer.parseInt(num_edges.getText()));
                e.consume();
                window.close();
            }
        });

        layout.setOnKeyPressed((event) -> { if(event.getCode() == KeyCode.ENTER) goButton.fire(); });
        GridPane.setConstraints(goButton, 3, 3);

        Label label_file = new Label("OR");
        Label label_file_2 = new Label("Choose from a file:");
        GridPane.setConstraints(label_file,0,5);
        GridPane.setConstraints(label_file_2,0,6);

        FileChooser file_chooser = new FileChooser();
        Button file_button  = new Button("Choose File");
        file_button.setOnMouseEntered(e -> {
            file_button.setStyle("-fx-background-color: #2EE59D");
        });
        file_button.setOnMouseExited(e -> {
            file_button.setStyle("-fx-background-color: #cccccc");
        });
        file_button.setOnAction(e -> {
            File file = file_chooser.showOpenDialog(window);
            if (file != null) {
                Read_File_Backtracking.display(file);
            }
        });
        GridPane.setConstraints(file_button, 0, 7);

        layout.getChildren().addAll(label_random, goButton, num_vert, num_edges, label_file, label_file_2, file_button);
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
