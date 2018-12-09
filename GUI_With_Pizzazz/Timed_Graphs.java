package sample;

//add pause button
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Timed_Graphs {

    static Scene scene1;
    static Stage window;
    static Stage gameOverWindow;
    static Label layout;
    static double orgSceneX, orgSceneY;
    static final Integer starttime = 30; //edit how long the timer is from here, this is seconds.
    static Integer seconds = starttime;
//    static Paint[] num_of_colors;
    static int hintArraySize;

    //Game over screen method.
    private static void setGameOver() {
        gameOverWindow = new Stage();
        GridPane grid = new GridPane();
        gameOverWindow.setTitle("Time's up!");
        Label timeUsed = new Label("Time: ");
        GridPane.setConstraints(timeUsed, 0, 0);
        Label chromaUsed = new Label("Chromatic reached: "); //add getNumColor here
        GridPane.setConstraints(chromaUsed, 0, 1);
        grid.getChildren().addAll(timeUsed, chromaUsed);
        Scene gameOverScene = new Scene(grid);
        gameOverWindow.setScene(gameOverScene);
        gameOverWindow.show();
    }

    //Timer method.
    private static void doTime() {
        Timeline time = new Timeline();


        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                //@Asem change this to ++ and modify the instance variable to start at zero for your part.  Also change the if condition.
                seconds--;
                layout.setText("T: " + seconds.toString());
                //add the style.
                layout.setStyle("-fx-font-size: 16px; -fx-font-style: bold; -fx-text-fill: #ff6600");
                if (seconds <= 0) {
                    seconds = starttime;
                    time.stop();
                    window.close();
                    setGameOver();
                }

            }


        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if (time != null) {
            time.stop();
        }
        time.play();


    }

    //Mouse click listener.
    private static EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
    {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();

        Circle c = (Circle) (t.getSource());
        c.toFront();
    };

    //Mouse drag listener.
    private static EventHandler<MouseEvent> mouseDraggedEventHandler = (t) ->
    {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;

        Circle c = (Circle) (t.getSource());

        c.setCenterX(c.getCenterX() + offsetX);
        c.setCenterY(c.getCenterY() + offsetY);

        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
    };

    //Create GUI object Circle.
    private static Circle createCircle(double x, double y, double r, Color color) {
        Circle circle = new Circle(x, y, r, color);
        circle.setStrokeWidth(4);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);

        circle.setCursor(Cursor.CROSSHAIR);

        circle.setOnMousePressed(mousePressedEventHandler);
        circle.setOnMouseDragged(mouseDraggedEventHandler);
        hintArraySize++;

        return circle;
    }

    //Create GUI object line.
    private static Line connect(Circle c1, Circle c2) {
        Line line = new Line();

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStrokeWidth(2);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        line.getStrokeDashArray().setAll(1.0, 4.0);

        return line;
    }
        /* Ignore this also attempt at hint.
    public static int connectionCount(Circle c1, Circle c2){
        if()
    }
    */
    public static void display(String title, String message) {
        window = new Stage();

        window.setTitle(title);
        window.setMinWidth(250);

        Group gr1 = new Group();

        gr1.setStyle("-fx-background-image: url('https://en.wikipedia.org/wiki/The_Great_Wave_off_Kanagawa#/media/File:Tsunami_by_hokusai_19th_century.jpg'); -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: cover;");
        scene1 = new Scene(gr1, 800, 600);
        Circle Circle1 = createCircle(100, 50, 20, Color.GREY);
        Circle Circle2 = createCircle(220, 100, 20, Color.GREY);
        Circle Circle3 = createCircle(440, 350, 20, Color.GREY);
        Circle Circle4 = createCircle(360, 220, 20, Color.GREY);
        Circle Circle5 = createCircle(580, 300, 20, Color.GREY);
        Circle Circle6 = createCircle(290, 500, 20, Color.GREY);

        Line line1 = connect(Circle1, Circle2);
        Line line2 = connect(Circle1, Circle3);
        Line line3 = connect(Circle1, Circle4);
        Line line4 = connect(Circle2, Circle3);
        Line line5 = connect(Circle4, Circle5);
        Line line6 = connect(Circle4, Circle6);
        Line line7 = connect(Circle5, Circle6);

        //hint attempt, ignore this.
        /*
        Method connectionMethod = Timed_Graphs.connect();
        Parameter para


        //hint
        ArrayList<Line> hint = new ArrayList<>();
        hint.add(line1);
        hint.add(line2);
        hint.add(line3);
        hint.add(line4);
        hint.add(line5);
        hint.add(line6);
        hint.add(line7);
        int[] connectionCount = new int[hintArraySize];
        Iterator<Line> hintIterator = hint.iterator();
        while(hintIterator.hasNext()) {
            Line temp = hintIterator.next();
            temp.
            //use a array to generate the connection count of a circle.
             //size should be how many vertices there are

        }
        */

        //add the circles
        gr1.getChildren().add(Circle1);
        gr1.getChildren().add(Circle2);
        gr1.getChildren().add(Circle3);
        gr1.getChildren().add(Circle4);
        gr1.getChildren().add(Circle5);
        gr1.getChildren().add(Circle6);

        // add the lines
        gr1.getChildren().add(line1);
        gr1.getChildren().add(line2);
        gr1.getChildren().add(line3);
        gr1.getChildren().add(line4);
        gr1.getChildren().add(line5);
        gr1.getChildren().add(line6);
        gr1.getChildren().add(line7);

        //for the timer, added some style.
        StackPane timing = new StackPane();
        LinearGradient timeBoxBackground = new LinearGradient(0, 0, 1f, 1f, true, CycleMethod.REPEAT, new Stop[] {
                        new Stop(0, Color.WHITE),
                        new Stop(1, Color.GREY),
                });
        Rectangle timeBox = new Rectangle(50, 40, timeBoxBackground);
        layout = new Label();
        layout.setStyle("-fx-background-color: #fff");
        doTime();
        timing.getChildren().addAll(timeBox, layout);

        //

        gr1.getChildren().add(timing);


        // bring the circles to the front of the lines
        Circle1.toFront();
        Circle2.toFront();
        Circle3.toFront();
        Circle4.toFront();
        Circle5.toFront();
        Circle6.toFront();


        ColorBox cbox = new ColorBox();
        //for the part to color the box.
        ArrayList<Circle> list = new ArrayList<Circle>();
        list.add(Circle1);
        list.add(Circle2);
        list.add(Circle3);
        list.add(Circle4);
        list.add(Circle5);
        list.add(Circle6);
        Iterator<Circle> iterator = list.iterator();

        while (iterator.hasNext()) {
            Circle temp = iterator.next();
            temp.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    temp.setFill(cbox.getValue());
                }
            });
        }
        // Display the ColorBox.
        cbox.display();


        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();

    }
}