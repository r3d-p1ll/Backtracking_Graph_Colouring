package sample;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class EndMode  {
    static Stage finish = new Stage();
    public static void display(){

        GridPane layout3 = new GridPane();


        finish.setTitle("CONGRATZZZZ!");
        finish.setMinWidth(10);
        finish.setMinHeight(50);
        Text a = new Text("You finished this game mode, well done");
        if(Random_Graphs.getNumColors() > Backtracking_Alg.getChromNumber()){
            Text b = new Text("The amount of colors you used is:" + Random_Graphs.getNumColors() );
            Text c = new Text("This isn't the minimum amount of colors you can use so try to use less the next time. " );
            layout3.add(b,1,50,1,1);
            layout3.add(c,1,100,1,1);

        }
        if(Random_Graphs.getNumColors() == Backtracking_Alg.getChromNumber()) {
            Text b = new Text("The amount of colors you used is: " + Random_Graphs.getNumColors());
            Text c = new Text("You used exactly the minimum amount of colors, good job!!" );
            layout3.add(b,1,50,1,1);
            layout3.add(c,1,100,1,1);
        }


        layout3.add(a, 1, 1,1,1);



        Scene scene = new Scene(layout3, 450, 70);
        finish.setScene(scene);
        finish.showAndWait();

        finish.setOnCloseRequest(e -> finish.close());

    }

}
