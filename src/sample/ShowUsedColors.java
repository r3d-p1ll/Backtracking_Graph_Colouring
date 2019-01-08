package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


/**
 * this class is a separate hint, that says if the player needs to use more or less colors
 */
public class ShowUsedColors {
    public static void display (int colorsUsed, int chromaticnumber){
        Stage window = new Stage();
        GridPane layout = new GridPane();
        Scene scene = new Scene(layout, 300, 100);
        window.setScene(scene);
        window.setScene(scene);
        window.setTitle("title");
        Text text = new Text("You've used " + colorsUsed + " colors so far.");
        /**
         * this if-statements says that the player can use more colors
         */
        if(chromaticnumber > colorsUsed){
            Text text2 = new Text("You can use more colours");
            layout.add(text2, 0, 50, 2, 1);
        }
        /**
         * this if-statements says that the player is using exactly the minimal amount of colors
         */
        else if( chromaticnumber == (colorsUsed)){
            Text text2 = new Text("You are doing just fine");
            layout.add(text2, 0, 50, 2, 1);

        }
        /**
         * this says to the player that he/she can use less colors
         */
        else{
            Text text2 = new Text("You can use less colours");
            layout.add(text2, 0, 50, 2, 1);

        }

        layout.add(text, 0, 0, 2, 1);

        window.show();
    }
}