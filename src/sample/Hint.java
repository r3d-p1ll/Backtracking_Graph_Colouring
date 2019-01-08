package sample;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.layout.VBox;

/** the Hint class shows the hints that the players can use during the second and the third game mode
 * the player gets to choose between 2 types of hints
 */
public class Hint {
    /**
     *
     * @param title gives the title of the Stage
     * @param message gives a message to the Stage
     * @param colorsUsed is the amount of colors that the player used
     * @param chromaticnumber is the chromaticnumber of the graph
     */
    public static void display (String title, String message, int colorsUsed, int chromaticnumber){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(100);
        window.setMinHeight(50);

        VBox layout = new VBox();

        Label direct = new Label("Double click a vertice");
        direct.setStyle("-fx-border-style: solid; -fx-border-width: 2.5px;");

        Button easy = new Button("I need a bit of help");
        layout.setAlignment(Pos.CENTER);
        easy.setOnAction(e -> UsedColors (colorsUsed,chromaticnumber));

        Button hard = new Button("I am lost");
        hard.setOnAction( e -> startingPoint ());



        layout.getChildren().addAll(direct,easy,hard);
        Scene scene = new Scene(layout, 150, 100);
        window.setScene(scene);
        window.showAndWait();

    }

    /**
     *
     * @param colorsUsed the amount of colors the player used
     * @param chromaticnumber the chromaticnumber of the graph
     */
    private static void UsedColors(int colorsUsed, int chromaticnumber) {
        ShowUsedColors.display(colorsUsed, chromaticnumber );
    }

    /**
     * shows the user a good startingpoint
     */
    private static void startingPoint() {
        HowToStart.display("StartingPoint");
    }

}