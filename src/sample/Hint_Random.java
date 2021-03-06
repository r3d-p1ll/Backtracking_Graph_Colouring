package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

/** This class gives the hints for the random game-mode
 *
 */
public class Hint_Random {
    /**
     *
     * @param title gives the title of the Stage
     * @param message the user can give message to the Stage
     */
    public static void display (String title, String message){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(100);
        window.setMinHeight(50);

        GridPane layout = new GridPane();
        Label label = new Label();
        label.setText("Too complicated?\n" +
                "Try the ORDER button for a simpler view of the graph!\n" +
                "If that doesn't help, choose one of the options below:\n" +
                " ");
        layout.getChildren().add(label);
        layout.setConstraints(label, 0 ,0);

        Button easy = new Button("How am I doing so far?");
        layout.getChildren().add(easy);
        layout.setAlignment(Pos.CENTER);
        layout.setConstraints(easy, 0 ,2);
        easy.setOnAction(e -> UsedColors ());

        Scene scene = new Scene(layout, 500, 200);
        window.setScene(scene);
        window.showAndWait();

    }

    /**
     * it shows the amount of colors the user used and says if they are on the good way or not
     */
    private static void UsedColors() {
        ShowUsedColors.display(Random_Graphs.getNumColors(), Backtracking_Alg.getChromNumber());
    }
}
