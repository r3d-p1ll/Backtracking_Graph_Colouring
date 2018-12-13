package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ShowUsedColors {
    public static void display (int colorsUsed, int chromaticnumber){
        Stage window = new Stage();
        GridPane layout = new GridPane();
        Scene scene = new Scene(layout, 300, 100);
        window.setScene(scene);
        window.setScene(scene);
        window.setTitle("title");
        Text text = new Text("You've used " + colorsUsed + " colors so far.");

        if(chromaticnumber > colorsUsed){
            Text text2 = new Text("You can use more colours");
            layout.add(text2, 0, 50, 2, 1);
        }
        else if( chromaticnumber == (colorsUsed)){
            Text text2 = new Text("You are doing just fine");
            layout.add(text2, 0, 50, 2, 1);

        }
        else{
            Text text2 = new Text("You can use less colours");
            layout.add(text2, 0, 50, 2, 1);

        }

        layout.add(text, 0, 0, 2, 1);

        window.show();
    }
}