package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileInputStream;

/** Implementing the Main mode of the game.
 * The user is presented with the initial window and buttons for the three modes of the games.
 * Additional buttons are added for instructions and the music.
 * @author Vladislav Kolev
 * @author Thomas Sijpkens
 * @author Asemtakumi Nagata
 * @author Nina Krommedijk
 * @author Thuyila Robinson
 */

public class Main extends Application {
    private Button button1, button2, button3, explanation;
    private Scene scene1;
    private Stage window;
    //for the music
    private Media media;
    private MediaPlayer player;

    /** start method implements
     * The user is presented with the initial window and buttons for the three modes of the games.
     * Additional buttons are added for instructions and the music, as well as background images and CSS effects.
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;

        //for the music, change the directory of where it it installed.
        String path = "src/sample/actual.wav";  //use uri so that it would not have to modify
        media = new Media(new File(path).toURI().toString());
        player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        player.play();

        //Random Order button
        button1 = new Button("Random Order");
        button1.setOnAction(e -> randomGraph());
        Tooltip button1tip = new Tooltip("Graphs generated with user's input of number of vertices and edges. Color the edges in the given sequence" +
                " Colors chosen once, CANNOT be changed!");
        button1tip.setShowDelay(Duration.seconds(0.09));
        button1.setTooltip(button1tip);

        //Best Upper Bound Button
        button2 = new Button("Best Upper Bound");
        button2.setOnAction(e -> timeGraph());
        Tooltip button2tip = new Tooltip("Try to get the smallest amount of used colors in the given time, HURRY UP!");
        button2tip.setShowDelay(Duration.seconds(0.09));
        button2.setTooltip(button2tip);

        //Bitter End Button
        button3 = new Button("Until The Bitter End!");
        button3.setOnAction(e -> fixedGraph());
        Tooltip button3tip = new Tooltip("You can only finish this game if you used the exact the minimum amount of colors");
        button3tip.setShowDelay(Duration.seconds(0.09));
        button3.setTooltip(button3tip);

        // Music Button
        ToggleButton musicButton = new ToggleButton("Music");
        musicButton.setOnAction(event -> {
            if (musicButton.isSelected()) {
                player.pause();
            }else {
                player.play();
            }
        });

        explanation = new Button("?");
        explanation.setOnAction( e -> explainMode());
        Tooltip extip = new Tooltip("Explanation of the game");
        extip.setShowDelay(Duration.seconds(0.09));
        explanation.setTooltip(extip);

        GridPane layout1 = new GridPane();
        layout1.add(button1, 1, 9, 1, 1);
        layout1.add(button2, 1, 13, 1, 1);
        layout1.add(button3, 1, 17, 1, 1);
        layout1.add(musicButton, 0, 0, 1, 1);
        layout1.add(explanation, 0,1,1,1);
        layout1.setHgap(10);
        layout1.setVgap(10);
        layout1.setAlignment(Pos.TOP_CENTER);


//        String imagePath = new String("/Users/slav/Documents/JAVA%20projects/Graph_Game/src/sample/oriental.jpg");
//        layout1.setStyle("-fx-background-image: url('file://"+imagePath+"');" +
//                "-fx-background-size: cover");

        layout1.setStyle("-fx-background-image: url('https://ae01.alicdn.com/kf/HTB11jNebVmWBuNjSspdq6zugXXa2/Home-decoration-art-oriental-girl-flowers-fan-Silk-Fabric-Poster-Print-DM172.jpg_640x640.jpg');" +
                "-fx-background-size: cover");

        //Close window
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        //for the image.  Change directory of the where image is installed.
        Image titleword = new Image(new FileInputStream("src/sample/Capture.png")); //logos from shopify.com
        ImageView imageView = new ImageView(titleword);
        imageView.setFitHeight(120);
        imageView.setFitWidth(180);
        layout1.add(imageView, 1, 1, 5, 1);

        Text bottom = new Text("By the \"Oriental\" group");
        bottom.setFill(Color.WHITE);
        bottom.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        layout1.add(bottom, 2, 20, 5, 1);
        scene1 = new Scene(layout1, 1000, 600);

        scene1.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();
    }

    /**
     * randomGraph invokes the Random mode of the game.
     */
    private void randomGraph(){
        AlertBox.display("Set parameters", "Enter number of vertices and edges:");
    }

    /**
     * timeGraph invokes the "Best upped bound" mode of the game.
     */
    private void timeGraph() {
        TimeChoiceBox.display("Timed Mode", "Please choose a Graph, 1-15");
    }

    /**
     * fixedGraph invokes the "Until the bitter end" mode of the game.
     */
    private void fixedGraph(){
        FixedChoiceBox.display("Choose a Graph", "Please choose a Graph, 1-15");
    }

    /**
     * explainMode invokes the window for the rules of the game, when the button is clicked.
     */
    private void explainMode() {
        Explanation.display();
    }

    /**
     * closeProgram invokes the window for closing the window, asking for confirmation from the user.
     */
    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Title", "Sure you want to exit?");
        if (answer) {
            window.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
