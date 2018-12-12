package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


public class ShowUsedColors {
    public static void display (){
        Stage window = new Stage();
        GridPane layout = new GridPane();
        Scene scene = new Scene(layout, 300, 100);
        window.setScene(scene);
        window.setScene(scene);
        window.setTitle("Used Colors");
        Text text = new Text("You've used " + (Random_Graphs.getNumColors()) + " colors so far.");
        int x = Backtracking_Alg.getChromNumber();
        if(x > (Random_Graphs.getNumColors())){
            Text text2 = new Text("You can use more colours");
            layout.add(text2, 0, 50, 2, 1);
        }
        else if( x == (Random_Graphs.getNumColors())){
            Text text2 = new Text("You are doing just fine");
            layout.add(text2, 0, 50, 2, 1);

        }
        else{
            Text text2 = new Text("You've already used too many colors :(");
            layout.add(text2, 0, 50, 2, 1);

        }
        layout.add(text, 0, 0, 2, 1);
        window.show();
    }
}
