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

        if (Backtracking_Alg.getChromNumber() < Random_Graphs.getNumColors()){
            Text text2 = new Text("You've used " + (Random_Graphs.getNumColors()) + " colors so far.\n" + " You could use less.");
            layout.add(text2, 0, 50, 2, 1);
        }
        else if (Backtracking_Alg.getChromNumber() == Random_Graphs.getNumColors()){
            Text text2 = new Text("You've used " + (Random_Graphs.getNumColors()) + " colors so far, which is the chromatic number\n" + "Congratulations!" );
            layout.add(text2, 0, 50, 2, 1);
        }
        else{
            Text text2 = new Text("You've used " + (Random_Graphs.getNumColors()) + " colors so far.\n" + "You need more colors.");
            layout.add(text2, 0, 50, 2, 1);
        }

        // IMPLEMENT LOWERBOUND
        window.show();
    }
}