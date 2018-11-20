package sample;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ColorBox {
    public static void display(){
        Stage window = new Stage();

        window.setTitle("ColorPicker");
        Scene scene = new Scene(new HBox(20), 400, 100);
        HBox layout = (HBox) scene.getRoot();
        layout.setPadding(new Insets(5, 5, 5, 5));
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.CORAL);

        Text text = new Text("Try the color picker!");
        text.setFont(Font.font ("Verdana", 20));
        text.setFill(colorPicker.getValue());

        colorPicker.setOnAction((ActionEvent t) -> {text.setFill(colorPicker.getValue());});

        layout.getChildren().addAll(colorPicker, text);

        window.setScene(scene);
        window.show();
    }

}