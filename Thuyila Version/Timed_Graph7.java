package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * class Time_Graph2 to display a graph, by circles and lines,
 *  run the time,
 *  count the chromatic number used by the player,
 *  compare that chromatic number to the original chromatic number pre-determined,
 *  display gameover window once the game is over,
 *  and to create a game with graph2
 */
public class Timed_Graph7 {

    private static Scene scene1;
    private static Stage window;
    private static Stage gameOverWindow;
    private static Label layout;
    private static double orgSceneX, orgSceneY;
    private static final Integer starttime = 20;
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
    private static Label hintLabel;
    private static Text text2;
    private static Button end;
    private static int lastTime;
    private static ArrayList<Circle> list;
    private static int[][] adj_matrix = new int[][]{
            {0, 1, 1, 1, 0, 1, 0, 1, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 1, 0, 1, 1, 1, 0, 1, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {0, 1, 1, 1, 0, 1, 1, 1, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {0, 1, 0, 1, 0, 1, 1, 1, 0}
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
     * checks if the colors are colored to adjacency vertices
     * @param adj adjacency matrix
     * @return true or false
     */
    public static boolean CheckColors(int adj [][]){
        boolean seen = true;
        for (int d=0; d<adj.length; d++){
            if(list.get(d).getFill().equals(Color.TRANSPARENT)){
                seen = false;
            }
        }
        return seen;
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
            Timed_Graph7.ShowUsedColors.display(chromaticNumber);
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
     * @param x starting pint of x-axis
     * @param y starting point of y-axis
     * @param r radius of the circle
     * @param color color of the circle
     * @param colorAttached number of colors that can be colored around a certain vertex
     * @return the circle drawn
     */
    private static Circle createCircle(double x, double y, double r, Color color, Integer colorAttached)
    {
        Circle circle = new Circle(x, y, r, color);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);

        circle.setCursor(Cursor.CROSSHAIR);
        //change this and delete the EventMethod that you just deleted.
        circle.setOnMousePressed((t) ->
        {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Circle c = (Circle) (t.getSource());
            c.toFront();

            if(t.getClickCount() == 2) {
                Stage hintWindow = new Stage();
                VBox hint = new VBox();
                Label hintExplain = new Label("Colors surrounding this vertice:");
                hintLabel = new Label();
                hintLabel.setText(Integer.toString(colorAttached));
                hintLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
                hint.setAlignment(Pos.CENTER);
                hint.getChildren().addAll(hintExplain,hintLabel);
                Scene hintScene = new Scene(hint);
                hintWindow.setScene(hintScene);
                hintWindow.showAndWait();
            }
        });
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

        /*
         * Adding HINTS button
         */

        Button buttonhint = new Button("HINTS");
        pane.add(buttonhint, 5,0,1,1);
        buttonhint.setOnAction(e ->  Timed_Graph1.Hint.display(" ", " ", chromaticNumber));

        Label label = new Label();
        label.setText(message);
        pane_graph.getChildren().add(label);
        /*
         * create circle objects
         */
        Circle Circle1 = createCircle(425, 55, 15, Color.TRANSPARENT,2);
        Circle Circle2 = createCircle(100, 220, 15, Color.TRANSPARENT,2);
        Circle Circle3 = createCircle(425, 220, 15, Color.TRANSPARENT,2);
        Circle Circle4 = createCircle(760, 220, 15, Color.TRANSPARENT,2);
        Circle Circle5 = createCircle(425, 390, 15, Color.TRANSPARENT,2);
        Circle Circle6 = createCircle(100, 555, 15, Color.TRANSPARENT,2);
        Circle Circle7 = createCircle(425, 555, 15, Color.TRANSPARENT,2);
        Circle Circle8 = createCircle(760, 555, 15, Color.TRANSPARENT,2);
        Circle Circle9 = createCircle(425, 725, 15, Color.TRANSPARENT,2);

        /*connect circles specified with a line
         *
         */
        Line line1 = connect(Circle1, Circle2);
        Line line2 = connect(Circle1, Circle4);
        Line line3 = connect(Circle1, Circle3);
        Line line4 = connect(Circle1, Circle6);
        Line line5 = connect(Circle1, Circle8);
        Line line6 = connect(Circle2, Circle5);
        Line line7 = connect(Circle2, Circle3);
        Line line8 = connect(Circle2, Circle7);
        Line line9 = connect(Circle2, Circle9);
        Line line10 = connect(Circle3, Circle5);
        Line line11 = connect(Circle3, Circle4);
        Line line12 = connect(Circle3, Circle6);
        Line line13 = connect(Circle3, Circle8);
        Line line14 = connect(Circle4, Circle5);
        Line line15 = connect(Circle4, Circle7);
        Line line16 = connect(Circle4, Circle9);
        Line line17 = connect(Circle5, Circle6);
        Line line18 = connect(Circle5, Circle8);
        Line line19 = connect(Circle5, Circle7);
        Line line20 = connect(Circle7, Circle6);
        Line line21 = connect(Circle7, Circle9);
        Line line22 = connect(Circle7, Circle8);
        Line line23 = connect(Circle6, Circle9);
        Line line24 = connect(Circle8, Circle9);

        /*add the circles
         *
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

        /*add the lines
         *
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

        /*for the timer
         *
         */
        layout = new Label();
        layout.setText("T: 10");
        doTime();
        pane_graph.getChildren().addAll(layout);

        /*bring the circles to the front of the lines
         *
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

        /*An array to hold the used colors.
         *
         */
        num_of_colors = new Paint[9];
        /*adding all circles to an array, to calculate the colors used by the user
         *
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

        /*
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

        /*
         * final scene
         */
        scene1 = new Scene(vbox, 850, 850);
        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();

    }

}