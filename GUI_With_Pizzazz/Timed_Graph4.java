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

/*class Time_Graph4 to display a graph, by circles and lines,
run the time,
count the chromatic number used by the player,
compare that chromatic number to the original chromatic number pre-determined,
display gameover window once the game is over,
and to create a game with graph4
@param method setGameOver() sets the gameover window with timer
@param doTime() keeps record of the time
@param dispaly() displays the graph
 */
public class Timed_Graph4 {

    static Scene scene1;
    static Stage window;
    static Stage gameOverWindow;
    static Label layout;
    static double orgSceneX, orgSceneY;
    static final Integer starttime = 30; //edit how long the timer is from here.
    static Integer seconds = starttime;
    static Paint[] num_of_colors;
    static Color color_holder = Color.WHITE;
    static Label timeUsed;
    static Label chromaUsed;
    static ColorPicker colorPicker;
    static Color colorBeingUsed = Color.WHITE;
    static int colorCounter;
    static ArrayList<String> colorList;
    static Set<String> allColors;
    static int colorListLength;

    //hint
    static Integer colorAttached;
    static Label hintLabel;

    /**@method setGameOver() is called when doTime is 0 and displays a new window.
     */
    private static void setGameOver(){
        gameOverWindow = new Stage();
        GridPane grid = new GridPane();
        gameOverWindow.setTitle("Time's up!");
        timeUsed = new Label("Time left: ");
        GridPane.setConstraints(timeUsed,2,2);
        Label realChroma = new Label("Chromatic number: 3");
        chromaUsed = new Label("Chromatic reached:   " + colorCounter);
        GridPane.setConstraints(chromaUsed, 2,4);
        GridPane.setConstraints(realChroma, 2, 3);
        grid.getChildren().addAll(timeUsed, realChroma, chromaUsed);
        Scene gameOverScene = new Scene(grid);
        gameOverWindow.setScene(gameOverScene);
        gameOverWindow.show();
    }

    /**@method doTime() method starts the timer from a given time, and till the timer reaches zero or the game is done
     *@obj TimeLine records the time
     *@obj KeyFrame records the duration between time
     *@event handle() records the time from starting to end, and closes the window once the game is over
     */
    private static void doTime() {
        Timeline time= new Timeline();


        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {

                //@Asem change this to ++ and modify the instance variable to start at zero for your part.  Also change the if condition.
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

    /*Mouse click listener enables the action specified once the mouse is clicked
    @param t MouseEvent specified
    @param Circle the circle clicked
     */

    /* Hinter() method to help the user when lost
    @param display() method of Hint
     */
    public static void Hinter(){
        Hint.display("Hint", "Need help?");
    }
    /**Mouse drag listener enables dragging option by the mouse
     *@param t specified MouseEvent
     *@param Circle circle to be dragged
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

    /**createCircle() creates a circle object with (x,y), the radius and the color
     *@param x starting coordinate of x-axis of the circle
     *@param y starting coordinate of y-axis of the circle
     *@param r the radius of the circle to be drawn
     *@param color Color of the circle
     * @param colorAttached is the number of color attached to this vertice and serves as the hint.
     * @return a circle with x coordinate and r radius
     */
    private static Circle createCircle(double x, double y, double r, Color color, Integer colorAttached)
    {
        Circle circle = new Circle(x, y, r, color);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);

        circle.setCursor(Cursor.CROSSHAIR);

        circle.setOnMousePressed((t) ->
        {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Circle c = (Circle) (t.getSource());
            c.toFront();

            if(t.getClickCount() == 2) {
//                setColorAttached(colorAttached);
//                System.out.println(getNumColors());
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

    /**connect() connects the two circles specified with a line
     *@param c1 the circle from, i.e the starting point
     *@param c2 the circle to, i.e the end point
     * @return a dash line that serves as edge.
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
    /**method display() to display the graph chosen by the user
     *@param title title of the graph
     *@param message message printed to the user
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

        //Adding HINTS button
        Button buttonhint = new Button("HINTS");
        pane.add(buttonhint, 5,0,1,1);
        buttonhint.setOnAction(e ->  Hinter());

        Label label = new Label();
        label.setText(message);
        pane_graph.getChildren().add(label);

        //create all circle objects
        Circle Circle1 = createCircle(220, 295, 15, Color.WHITE,1);
        Circle Circle2 = createCircle(110, 295, 15, Color.WHITE,1);
        Circle Circle3 = createCircle(145, 215, 15, Color.WHITE,1);
        Circle Circle4 = createCircle(220, 190, 15, Color.WHITE,1);
        Circle Circle5 = createCircle(300, 215, 15, Color.WHITE,1);
        Circle Circle6 = createCircle(330, 295, 15, Color.WHITE,1);
        Circle Circle7 = createCircle(300, 370, 15, Color.WHITE,1);
        Circle Circle8 = createCircle(220, 405, 15, Color.WHITE,1);
        Circle Circle9 = createCircle(145, 370, 15, Color.WHITE,1);
        Circle Circle10 = createCircle(630, 370, 15, Color.WHITE,1);
        Circle Circle11 = createCircle(520, 370, 15, Color.WHITE,1);
        Circle Circle12 = createCircle(555, 290, 15, Color.WHITE,1);
        Circle Circle13 = createCircle(630, 260, 15, Color.WHITE,1);
        Circle Circle14 = createCircle(710, 290, 15, Color.WHITE,1);
        Circle Circle15 = createCircle(735, 370, 15, Color.WHITE,1);
        Circle Circle16 = createCircle(710, 445, 15, Color.WHITE,1);
        Circle Circle17 = createCircle(630, 480, 15, Color.WHITE,1);
        Circle Circle18 = createCircle(555, 445, 15, Color.WHITE,1);
        Circle Circle19 = createCircle(60,230, 15, Color.WHITE,1);
        Circle Circle20 = createCircle(160, 140, 15, Color.WHITE,1);
        Circle Circle21 = createCircle(280, 140, 15, Color.WHITE,1);
        Circle Circle22 = createCircle(370, 230, 15, Color.WHITE,1);
        Circle Circle23 = createCircle(370, 360, 15, Color.WHITE,1);
        Circle Circle24 = createCircle(280, 450, 15, Color.WHITE,1);
        Circle Circle25 = createCircle(160, 450, 15, Color.WHITE,1);
        Circle Circle26 = createCircle(60, 360, 15, Color.WHITE,1);
        Circle Circle27 = createCircle(465, 300, 15, Color.WHITE,1);
        Circle Circle28 = createCircle(570, 215, 15, Color.WHITE,1);
        Circle Circle29 = createCircle(690, 215, 15, Color.WHITE,1);
        Circle Circle30 = createCircle(785, 300, 15, Color.WHITE,1);
        Circle Circle31 = createCircle(785, 435, 15, Color.WHITE,1);
        Circle Circle32 = createCircle(690, 525, 15, Color.WHITE,1);
        Circle Circle33 = createCircle(570, 525, 15, Color.WHITE,1);
        Circle Circle34 = createCircle(465, 435, 15, Color.WHITE,1);

        //connect specified circles with lines
        Line line1 = connect(Circle1, Circle2);
        Line line2 = connect(Circle1, Circle3);
        Line line3 = connect(Circle1, Circle4);
        Line line4 = connect(Circle1, Circle5);
        Line line5 = connect(Circle1, Circle6);
        Line line6 = connect(Circle1, Circle7);
        Line line7 = connect(Circle1, Circle8);
        Line line8 = connect(Circle1, Circle9);
        Line line9 = connect(Circle10, Circle11);
        Line line10 = connect(Circle10, Circle12);
        Line line11 = connect(Circle10, Circle13);
        Line line12 = connect(Circle10, Circle14);
        Line line13 = connect(Circle10, Circle15);
        Line line14 = connect(Circle10, Circle16);
        Line line15 = connect(Circle10, Circle17);
        Line line16 = connect(Circle10, Circle18);
        Line line17 = connect(Circle19, Circle2);
        Line line18 = connect(Circle19, Circle3);
        Line line19 = connect(Circle20, Circle3);
        Line line20 = connect(Circle20, Circle4);
        Line line21 = connect(Circle21, Circle4);
        Line line22 = connect(Circle21, Circle5);
        Line line23 = connect(Circle22, Circle5);
        Line line24 = connect(Circle22, Circle6);
        Line line25 = connect(Circle23, Circle6);
        Line line26 = connect(Circle23, Circle7);
        Line line27 = connect(Circle24, Circle7);
        Line line28 = connect(Circle24, Circle8);
        Line line29 = connect(Circle25, Circle8);
        Line line30 = connect(Circle25, Circle9);
        Line line31 = connect(Circle26, Circle9);
        Line line32 = connect(Circle26, Circle2);
        Line line33 = connect(Circle27, Circle11);
        Line line34 = connect(Circle27, Circle12);
        Line line35 = connect(Circle28, Circle12);
        Line line36 = connect(Circle28, Circle13);
        Line line37 = connect(Circle21, Circle28);
        Line line38 = connect(Circle24, Circle25);
        Line line39 = connect(Circle32, Circle33);
        Line line40 = connect(Circle29, Circle13);
        Line line41 = connect(Circle29, Circle14);
        Line line42 = connect(Circle30, Circle14);
        Line line43 = connect(Circle30, Circle15);
        Line line44 = connect(Circle22, Circle27);
        Line line45 = connect(Circle24, Circle33);
        Line line46 = connect(Circle31, Circle15);
        Line line47 = connect(Circle31, Circle16);
        Line line48 = connect(Circle32, Circle16);
        Line line49 = connect(Circle32, Circle17);
        Line line50 = connect(Circle23, Circle34);
        Line line51 = connect(Circle28, Circle29);
        Line line52 = connect(Circle17, Circle33);
        Line line53 = connect(Circle18, Circle33);
        Line line54 = connect(Circle18, Circle34);
        Line line55 = connect(Circle34, Circle11);
        Line line56 = connect(Circle21, Circle20);
        Line line57 = connect(Circle31, Circle30);
        Line line58 = connect(Circle26, Circle19);


        //add the circles
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
        pane_graph.getChildren().add(Circle16);
        pane_graph.getChildren().add(Circle17);
        pane_graph.getChildren().add(Circle18);
        pane_graph.getChildren().add(Circle19);
        pane_graph.getChildren().add(Circle20);
        pane_graph.getChildren().add(Circle21);
        pane_graph.getChildren().add(Circle22);
        pane_graph.getChildren().add(Circle23);
        pane_graph.getChildren().add(Circle24);
        pane_graph.getChildren().add(Circle25);
        pane_graph.getChildren().add(Circle26);
        pane_graph.getChildren().add(Circle27);
        pane_graph.getChildren().add(Circle28);
        pane_graph.getChildren().add(Circle29);
        pane_graph.getChildren().add(Circle30);
        pane_graph.getChildren().add(Circle31);
        pane_graph.getChildren().add(Circle32);
        pane_graph.getChildren().add(Circle33);
        pane_graph.getChildren().add(Circle34);

        // add the lines
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
        pane_graph.getChildren().add(line27);
        pane_graph.getChildren().add(line28);
        pane_graph.getChildren().add(line29);
        pane_graph.getChildren().add(line30);
        pane_graph.getChildren().add(line31);
        pane_graph.getChildren().add(line32);
        pane_graph.getChildren().add(line33);
        pane_graph.getChildren().add(line34);
        pane_graph.getChildren().add(line35);
        pane_graph.getChildren().add(line36);
        pane_graph.getChildren().add(line37);
        pane_graph.getChildren().add(line38);
        pane_graph.getChildren().add(line39);
        pane_graph.getChildren().add(line40);
        pane_graph.getChildren().add(line41);
        pane_graph.getChildren().add(line42);
        pane_graph.getChildren().add(line43);
        pane_graph.getChildren().add(line44);
        pane_graph.getChildren().add(line45);
        pane_graph.getChildren().add(line46);
        pane_graph.getChildren().add(line47);
        pane_graph.getChildren().add(line48);
        pane_graph.getChildren().add(line49);
        pane_graph.getChildren().add(line50);
        pane_graph.getChildren().add(line51);
        pane_graph.getChildren().add(line52);
        pane_graph.getChildren().add(line53);
        pane_graph.getChildren().add(line54);
        pane_graph.getChildren().add(line55);
        pane_graph.getChildren().add(line56);
        pane_graph.getChildren().add(line57);
        pane_graph.getChildren().add(line58);

        //for the timer
        layout = new Label();
        layout.setText("T: 10");
        doTime();
        pane_graph.getChildren().addAll(layout);

        // bring the circles to the front of the lines
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
        Circle16.toFront();
        Circle17.toFront();
        Circle18.toFront();
        Circle19.toFront();
        Circle20.toFront();
        Circle21.toFront();
        Circle22.toFront();
        Circle23.toFront();
        Circle24.toFront();
        Circle25.toFront();
        Circle26.toFront();
        Circle27.toFront();
        Circle28.toFront();
        Circle29.toFront();
        Circle30.toFront();
        Circle31.toFront();
        Circle32.toFront();
        Circle33.toFront();
        Circle34.toFront();

        num_of_colors = new Paint[34]; //An array to hold the used colors.
        //adding all circles to an array, to calculate the colors used by the user
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
        list.add(Circle16);
        list.add(Circle17);
        list.add(Circle18);
        list.add(Circle19);
        list.add(Circle20);
        list.add(Circle21);
        list.add(Circle22);
        list.add(Circle23);
        list.add(Circle24);
        list.add(Circle25);
        list.add(Circle26);
        list.add(Circle27);
        list.add(Circle28);
        list.add(Circle29);
        list.add(Circle30);
        list.add(Circle31);
        list.add(Circle32);
        list.add(Circle33);
        list.add(Circle34);

        // ADDING THE COLOR PICKER
        colorPicker = new ColorPicker();
        colorPicker.setValue(null);
        Text text_color = new Text("Pick Your Color." + "\n" + "After that right-click on vertex you'd like to color.");
        text_color.setFont(Font.font ("Verdana", 14));
        text_color.setFill(Color.BLACK);
        colorPicker.setOnAction((ActionEvent t) -> {color_holder = colorPicker.getValue();});
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.add(colorPicker, 0, 0, 1, 1);
        pane.add(text_color,0,1,1,1);

        final Text text = new Text("\nColors used: 0");
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
                        text.setText("\nColors used: " + (getNumColors()));
                        text.setFill(Color.BLACK);
                    }

                    else if (mouseEvent.getButton() == MouseButton.PRIMARY){
                        text.setText("\nColors used: " + (getNumColors()));
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

        scene1 = new Scene(vbox, 870, 780);
        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();

    }

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

}