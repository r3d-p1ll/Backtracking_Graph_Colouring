package sample;


import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class ShowUsedColors {
    public static void display (String title){

        Stage window = new Stage();


        GridPane layout = new GridPane();

        Scene scene = new Scene(layout, 300, 100);
        window.setScene(scene);


        window.setScene(scene);
        window.setTitle("title");


        Text text = new Text("The amount of colors you used is: ");
        Text text2 = new Text("The amount of colors you need to use is:");
        layout.add(text, 0, 0, 2, 1);
        layout.add(text2, 0, 50, 2, 1);
        // IMPLEMENT LOWERBOUND
        window.show();
    }
}