package sample;


import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
/** this class gives the explanation of the game*/
public class Explanation {
    public static void display() {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Explanation");
        window.setMinWidth(100);
        window.setMinHeight(50);

        GridPane layout = new GridPane();
        Text explain = new Text(" The purpose of the game is to give the circles a color,");
        Text explain2 = new Text(" such that the circles that are connected to eachother can't have the same colour");
        Text explain3 = new Text(" Your goal is to use the smallest amount of colors as possible.");
        Text explain4 = new Text(" The mimimum amount of colors that you can use is the Chromatic number.");
        Text explain5 = new Text(" Goodluck!");

        layout.add(explain, 1, 1,1,1);
        layout.add(explain2,1,50,1,1);
        layout.add(explain3,1,100,1,1);
        layout.add(explain4,1,150,1,1);
        layout.add(explain5,1,200,1,1);


        Scene scene = new Scene(layout, 510, 80);
        window.setScene(scene);
        window.showAndWait();

    }
}
