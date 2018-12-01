package sample;

import javafx.application.Application;
import javafx.beans.binding.ObjectExpression;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.text.TextAlignment;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;
import java.util.Random;

public class Main extends Application {

    Button button1, button2, button3;
    Scene scene1, scene2;
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;

        //BUTTON 1
        button1 = new Button("Mode 1: You decide!");
        button1.setOnAction(e -> randomGraph());
        Tooltip button1tip = new Tooltip("Graphs generated with user's input of number of vertices and edges. Colors chosen once, CANNOT be changed!");
        button1tip.setShowDelay(Duration.seconds(0.09));
        button1.setTooltip(button1tip);
        //button1.setTooltip(new Tooltip("Graphs generated with user's edges' and vertex's input. Colors choses once, CANNOT be changed!"));

        button2 = new Button("Mode 2: Think fast !");
        button2.setOnAction(e -> timeGraph());

        button3 = new Button("Mode 3: To the end!");
        button3.setOnAction(e -> fixedGraph());

        GridPane layout1 = new GridPane();
        layout1.add(button1, 1, 9, 1, 1);
        layout1.add(button2, 1, 13, 1, 1);
        layout1.add(button3, 1, 17, 1, 1);
        layout1.setHgap(10);
        layout1.setVgap(10);
        layout1.setAlignment(Pos.TOP_CENTER);

        layout1.setStyle("-fx-background-image: url('https://ip4img.goiphonewallpapers.com/2012/02/05/4421f179adb1bdbb_640x960.jpg')");
        //Close window
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        scene1 = new Scene(layout1, 400, 600);

        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();

    }

    private void randomGraph(){
        AlertBox.display("Set parameters", "Enter number of vertices and edges:");
//        Random_Graphs.display("Random Graph", "Good luck!");
    }
    
    private void timeGraph() {
        Timed_Graphs.display("Timed Mode", "Think fast!");
    }

    private void fixedGraph(){
        ChoiceBox.display("Choose a Graph", "Please choose a Graph, 1-10");
    }
    //private void fixedGraph(){
        //Fixed_graphs.display("Fixed Graph", "Good luck!");
    //}

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
