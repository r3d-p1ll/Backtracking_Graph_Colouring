package sample;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.util.Duration;
import javafx.scene.control.Label;
import java.util.*;

/**
 * class Time_Graph1 to display a graph, by circles and lines,
 *  run the time,
 *  count the chromatic number used by the player,
 *  compare that chromatic number to the original chromatic number pre-determined,
 *  display gameover window once the game is over,
 *  and to create a game with graph1
 */
public class Timed_Graph1 {

    private static Scene scene1;
    private static Stage window;
    private static Stage gameOverWindow;
    private static Label layout;
    private static double orgSceneX, orgSceneY;
    private static final Integer starttime = 10;
    private static Integer seconds = starttime;
    private static Paint[] num_of_colors;
    private static Color color_holder = Color.TRANSPARENT;
    private static Label timeUsed;
    private static Label chromaUsed;
    private static ColorPicker colorPicker;
    private static ArrayList<String> colorList;
    private static Set<String> allColors;
    private static int colorListLength;
    private static int chromaticNumber = 3;
    private static String comparision;

    int[][] adj_matrix = new int[][]{
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0},
            {1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0}
    };


    /**
     * Game over screen method.
     * setGameOver() keeps record of the time and once the chromatic number is reached or the time is over, shows the gameover window
     */

    private static void setGameOver(){
        gameOverWindow = new Stage();
        GridPane grid = new GridPane();
        gameOverWindow.setTitle("Time's up!");
        timeUsed = new Label("TIME'S UP!");
        timeUsed.setFont(Font.font ("Verdana", 14));
        GridPane.setConstraints(timeUsed,4,4);

        if(getNumColors() == chromaticNumber)
                {comparision = "Well done! Well played!";}
        else if(getNumColors() > chromaticNumber)
                {comparision = "Good attempt! Using less colors might've helped.";}
        else { comparision = "Good attempt! You could've used more colors.";}

        Label compareChroma = new Label(comparision);
        compareChroma.setFont(Font.font ("Verdana", 14));
        chromaUsed = new Label("You've reached " + getNumColors() + " chromatic colors.");
        chromaUsed.setFont(Font.font ("Verdana", 14));
        GridPane.setConstraints(chromaUsed, 4,6);
        GridPane.setConstraints(compareChroma, 4, 8);
        grid.getChildren().addAll(timeUsed, chromaUsed, compareChroma);
        Scene gameOverScene = new Scene(grid);
        gameOverWindow.setMinWidth(300);
        gameOverWindow.setMinHeight(100);
        gameOverWindow.setScene(gameOverScene);
        gameOverWindow.show();
    }

    /**
     * doTime() method starts the timer from a given time, and till the timer reaches zero or the game is done
     */
    private static void doTime() {
        Timeline time= new Timeline();


        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {

                seconds--;
                layout.setText("T: "+seconds.toString());
                if(seconds<=0){
                    seconds = starttime;
                    time.stop();
                    window.close();
                    setGameOver();
                }
            }
        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if(time!= null){
            time.stop();
        }
        time.play();
    }

    /**
     * Mouse click listener enables the action specified once the mouse is clicked
     */
    private static EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
    {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();

        Circle c = (Circle) (t.getSource());
        c.toFront();
    };

    /**
     * class ShowUsedColors to display how many colors have been used so far
     */
    public static class ShowUsedColors {
        /**
         * method display() to display the ShowUsedColors window
         * @param chromaticNumber the chromatic of number of specific graph
         */
        public static void display (int chromaticNumber){
            Stage window = new Stage();
            GridPane layout = new GridPane();
            Scene scene = new Scene(layout, 300, 100);
            window.setScene(scene);
            window.setScene(scene);
            window.setTitle("title");
            Text text = new Text("You've used " + getNumColors() + " colors so far.");

            if(chromaticNumber > getNumColors()){
                Text text2 = new Text("You can use more colours");
                layout.add(text2, 0, 50, 2, 1);
            }
            else if( chromaticNumber == (getNumColors())){
                Text text2 = new Text("You are doing just fine");
                layout.add(text2, 0, 50, 2, 1);

            }
            else{
                Text text2 = new Text("You can use less colours");
                layout.add(text2, 0, 50, 2, 1);

            }

            layout.add(text, 0, 0, 2, 1);

            window.show();
        }
    }
    /**
     * Hint() class to help the user when lost
     */
    public static class Hint {
        /**
         * method display() to display the Hint window
         * @param title title of the Hint window
         * @param message message through the window
         * @param chromaticNumber chromatic number of the graph
         */
        public static void display (String title, String message, int chromaticNumber){

            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("hints");
            window.setMinWidth(100);
            window.setMinHeight(50);

            GridPane layout = new GridPane();
            Button easy = new Button("I need a bit of help");
            layout.getChildren().add(easy);
            layout.setAlignment(Pos.CENTER);
            layout.setConstraints(easy, 50 ,20);
            easy.setOnAction(e -> UsedColors (chromaticNumber));

            Button hard = new Button("I am lost");
            layout.getChildren().add(hard);
            layout.setConstraints(hard, 50, 40);
            hard.setOnAction( e -> startingPoint ());




            Scene scene = new Scene(layout, 150, 100);
            window.setScene(scene);
            window.showAndWait();

        }

        /**
         * method UsedColors to display the number of colors used by the user
         * @param ChromNum number of colors used by the player
         */
        private static void UsedColors(int ChromNum) {
            ShowUsedColors.display(ChromNum);
        }

        /**
         * method startingPoint() to show the users where/how to start
         */
        private static void startingPoint() {
            HowToStart.display("StartingPoint");
        }
    }

    /**
     * Mouse drag listener enables dragging option by the mouse
     */
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

    /**
     * createCircle() creates a circle object with (x,y), the radius and the color
     * @param x
     * @param y
     * @param r
     * @param color
     * @return
     */
    private static Circle createCircle(double x, double y, double r, Color color)
    {
        Circle circle = new Circle(x, y, r, color);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);

        circle.setCursor(Cursor.CROSSHAIR);

        circle.setOnMousePressed(mousePressedEventHandler);
        circle.setOnMouseDragged(mouseDraggedEventHandler);

        return circle;
    }

    /**
     * connect() connects the two circles specified with a line
     * @param c1 the first circle to connect the line from
     * @param c2 the other circle to connect the line to
     * @return a line that's connected by the two specific circles
     */
    private static Line connect(Circle c1, Circle c2)
    {
        Line line = new Line();

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStrokeWidth(2);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        line.getStrokeDashArray().setAll(3.0, 4.0);

        return line;
    }

    /**
     * getNumColors() method to return the number of colors used for coloring the graph
     * @return the number of colors used
     */
    public static int getNumColors() {
        Set<Paint> newset = new HashSet<Paint>();
        for (int i=0; i < num_of_colors.length; i++) {
            newset.add(num_of_colors[i]);
        }
        if (newset.iterator().next() != null) {
            return newset.size();
        }
        System.out.println(num_of_colors.length);
        System.out.println(newset.size());

        return newset.size()-1;
    }

    /**
     * method display() to display the graph chosen by the user
     * @param title title of the displayed graph window
     * @param message message of through the window
     */
    public static void display(String title, String message){
        window = new Stage();

        window.setTitle(title);
        window.setMinWidth(250);

        Pane pane_graph = new Pane();
        pane_graph.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        pane_graph.setStyle(
                "-fx-background-position: center center;"+
                        "-fx-effect: dropshadow(three-pass-box, grey, 30, 0.2, 0, 0);");

        GridPane pane = new GridPane();
        VBox vbox = new VBox(pane_graph, pane);

        /**
         * Adding HINTS button
         */

        Button buttonhint = new Button("HINTS");
        pane.add(buttonhint, 5,0,1,1);
        buttonhint.setOnAction(e ->  Hint.display(" ", " ", 3));

        Label label = new Label();
        label.setText(message);
        pane_graph.getChildren().add(label);
        /**
         * create circle objects
         */
        Circle Circle1 = createCircle(564, 285, 15, Color.TRANSPARENT);
        Circle Circle2 = createCircle(576, 80, 15, Color.TRANSPARENT);
        Circle Circle3 = createCircle(304, 705, 15, Color.TRANSPARENT);
        Circle Circle4 = createCircle(564, 515, 15, Color.TRANSPARENT);
        Circle Circle5 = createCircle(576, 705, 15, Color.TRANSPARENT);
        Circle Circle6 = createCircle(304, 80, 15, Color.TRANSPARENT);
        Circle Circle7 = createCircle(220, 280, 15, Color.TRANSPARENT);
        Circle Circle8 = createCircle(642, 280, 15, Color.TRANSPARENT);
        Circle Circle9 = createCircle(304, 515, 15, Color.TRANSPARENT);
        Circle Circle10 = createCircle(220, 518, 15, Color.TRANSPARENT);
        Circle Circle11 = createCircle(433, 634, 15, Color.TRANSPARENT);
        Circle Circle12 = createCircle(304, 285, 15, Color.TRANSPARENT);
        Circle Circle13 = createCircle(642, 518, 15, Color.TRANSPARENT);
        Circle Circle14 = createCircle(433, 398, 15, Color.TRANSPARENT);
        Circle Circle15 = createCircle(433, 161, 15, Color.TRANSPARENT);

        /**
         *connect circles specified with a line
         */
        Line line1 = connect(Circle2, Circle8);
        Line line2 = connect(Circle2, Circle15);
        Line line3 = connect(Circle8, Circle15);
        Line line4 = connect(Circle15, Circle6);
        Line line5 = connect(Circle15, Circle12);
        Line line6 = connect(Circle15, Circle7);
        Line line7 = connect(Circle6, Circle7);
        Line line8 = connect(Circle1, Circle12);
        Line line9 = connect(Circle14, Circle8);
        Line line10 = connect(Circle14, Circle7);
        Line line11 = connect(Circle14, Circle13);
        Line line12 = connect(Circle14, Circle10);
        Line line13 = connect(Circle1, Circle4);
        Line line14 = connect(Circle4, Circle9);
        Line line15 = connect(Circle12, Circle9);
        Line line16 = connect(Circle4, Circle11);
        Line line17 = connect(Circle9, Circle11);
        Line line18 = connect(Circle10, Circle11);
        Line line19 = connect(Circle13, Circle11);
        Line line20 = connect(Circle13, Circle5);
        Line line21 = connect(Circle10, Circle3);
        Line line22 = connect(Circle3, Circle11);
        Line line23 = connect(Circle11, Circle5);
        Line line24 = connect(Circle15, Circle1);
        Line line25 = connect(Circle7, Circle10);
        Line line26 = connect(Circle8, Circle13);

        /**
         *add the circles
         */
        pane_graph.getChildren().add(Circle1);
        pane_graph.getChildren().add(Circle2);
        pane_graph.getChildren().add(Circle3);
        pane_graph.getChildren().add(Circle4);
        pane_graph.getChildren().add(Circle5);
        pane_graph.getChildren().add(Circle6);
        pane_graph.getChildren().add(Circle7);
        pane_graph.getChildren().add(Circle8);
        pane_graph.getChildren().add(Circle9);
        pane_graph.getChildren().add(Circle10);
        pane_graph.getChildren().add(Circle11);
        pane_graph.getChildren().add(Circle12);
        pane_graph.getChildren().add(Circle13);
        pane_graph.getChildren().add(Circle14);
        pane_graph.getChildren().add(Circle15);

        /**
         *add the lines
         */
        pane_graph.getChildren().add(line1);
        pane_graph.getChildren().add(line2);
        pane_graph.getChildren().add(line3);
        pane_graph.getChildren().add(line4);
        pane_graph.getChildren().add(line5);
        pane_graph.getChildren().add(line6);
        pane_graph.getChildren().add(line7);
        pane_graph.getChildren().add(line8);
        pane_graph.getChildren().add(line9);
        pane_graph.getChildren().add(line10);
        pane_graph.getChildren().add(line11);
        pane_graph.getChildren().add(line12);
        pane_graph.getChildren().add(line13);
        pane_graph.getChildren().add(line14);
        pane_graph.getChildren().add(line15);
        pane_graph.getChildren().add(line16);
        pane_graph.getChildren().add(line17);
        pane_graph.getChildren().add(line18);
        pane_graph.getChildren().add(line19);
        pane_graph.getChildren().add(line20);
        pane_graph.getChildren().add(line21);
        pane_graph.getChildren().add(line22);
        pane_graph.getChildren().add(line23);
        pane_graph.getChildren().add(line24);
        pane_graph.getChildren().add(line25);
        pane_graph.getChildren().add(line26);

        /**
         *for the timer
         */
        layout = new Label();
        layout.setText("T: 10");
        doTime();
        pane_graph.getChildren().addAll(layout);

        /**
         *bring the circles to the front of the lines
         */
        Circle1.toFront();
        Circle2.toFront();
        Circle3.toFront();
        Circle4.toFront();
        Circle5.toFront();
        Circle6.toFront();
        Circle7.toFront();
        Circle8.toFront();
        Circle9.toFront();
        Circle10.toFront();
        Circle11.toFront();
        Circle12.toFront();
        Circle13.toFront();
        Circle14.toFront();
        Circle15.toFront();

        /**
         *An array to hold the used colors.
         */
        num_of_colors = new Paint[15];

        /**
         *adding all circles to an array, to calculate the colors used by the user
         */
        ArrayList<Circle> list = new ArrayList<Circle>();
        list.add(Circle1);
        list.add(Circle2);
        list.add(Circle3);
        list.add(Circle4);
        list.add(Circle5);
        list.add(Circle6);
        list.add(Circle7);
        list.add(Circle8);
        list.add(Circle9);
        list.add(Circle10);
        list.add(Circle11);
        list.add(Circle12);
        list.add(Circle13);
        list.add(Circle14);
        list.add(Circle15);

        /**
         * ADDING THE COLOR PICKER
         */
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.TRANSPARENT);
        Text text_color = new Text("Pick Your Color and right-click on the vertex you'd like to color.");
        text_color.setFont(Font.font ("Verdana", 14));
        text_color.setFill(Color.BLACK);
        colorPicker.setOnAction((ActionEvent t) -> {color_holder = colorPicker.getValue();});
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.add(colorPicker, 0, 0, 1, 1);
        pane.add(text_color,0,1,1,1);

        final Text text = new Text("                                Colors used: 0");
        text.setFont(Font.font ("Verdana", 14));
        for (int i=0; i<list.size(); i++){
            final int temp_i = i;
            list.get(i).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    text.setText("");
                    if (mouseEvent.getButton() == MouseButton.SECONDARY && colorPicker.getValue() == Color.TRANSPARENT){
                        text.setText("Pick a color first");
                        text.setFill(Color.RED);
                    }

                    else if (mouseEvent.getButton() == MouseButton.SECONDARY){
                        list.get(temp_i).setFill(colorPicker.getValue());
                        num_of_colors[temp_i] = list.get(temp_i).getFill();
                        text.setText("                              Colors used: " + (getNumColors()));
                        text.setFill(Color.BLACK);
                    }

                    else if (mouseEvent.getButton() == MouseButton.PRIMARY){
                        text.setText("                              Colors used: " + (getNumColors()));
                        text.setFill(Color.BLACK);
                    }

                    else{
                        text.setText("Adjacent circles can't have the same color!");
                        text.setFill(Color.RED);
                    }
                }
            });
        }
        pane.add(text,5,1,1,1);

        /**final scene
         *
         */
        scene1 = new Scene(vbox, 850, 850);
        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();

    }

}