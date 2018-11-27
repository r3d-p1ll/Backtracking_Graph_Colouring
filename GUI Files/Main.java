package sample;

import javafx.application.Application;
import javafx.beans.binding.ObjectExpression;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        button1 = new Button("Random Graphs");
        button1.setOnAction(e -> randomGraph());

        button2 = new Button("Time Graphs");
        button2.setOnAction(e -> timeGraph());

        button3 = new Button("Fixed Graphs");
        button3.setOnAction(e -> fixedGraph());

        GridPane layout1 = new GridPane();
        layout1.add(button1, 1, 2, 1, 1);
        layout1.add(button2, 1, 4, 1, 1);
        layout1.add(button3, 1, 6, 1, 1);
        layout1.setHgap(10);
        layout1.setVgap(10);

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
        AlertBox.display("Set parameters", "Please set the number of vertices and edges:");
//        Random_Graphs.display("Random Graph", "Good luck!");
    }
    
    private void timeGraph() {
        Timed_Graphs.display("Timed Mode", "Think fast!");
    }
    
    private void fixedGraph(){
        Fixed_graphs.display("Random Graph", "Good luck!");
    }

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
