package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class ShowLB {
    public static void display (String title){
        Stage window = new Stage();
        GridPane layout = new GridPane();
        Scene scene = new Scene(layout, 300, 100);
        window.setScene(scene);
        window.setScene(scene);
        window.setTitle("title");
        Text text = new Text("The lowerbound is a minimum of colours. You've used " + (Random_Graphs.getNumColors()) + " colors so far.");
        Text text2 = new Text("that you at least need to use, it could be more");
        layout.add(text, 0, 0, 2, 1);
        layout.add(text2, 0, 50, 2, 1);
        Text text3 = new Text("Your lowerbound is:");
        layout.add(text3, 0, 100, 2, 2);
        // IMPLEMENT LOWERBOUND
        window.show();
    }
}