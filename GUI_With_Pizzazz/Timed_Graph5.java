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

/*class Time_Graph5 to display a graph, by circles and lines,
run the time,
count the chromatic number used by the player,
compare that chromatic number to the original chromatic number pre-determined,
display gameover window once the game is over,
and to create a game with graph5
@param method setGameOver() sets the gameover window with timer
@param doTime() keeps record of the time
@param dispaly() displays the graph
 */
public class Timed_Graph5 {

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
        Circle Circle1 = createCircle(150, 170, 15, Color.WHITE,1);
        Circle Circle2 = createCircle(220, 170, 15, Color.WHITE,1);
        Circle Circle3 = createCircle(220, 235, 15, Color.WHITE,1);
        Circle Circle4 = createCircle(150, 235, 15, Color.WHITE,1);
        Circle Circle5 = createCircle(290, 235, 15, Color.WHITE,1);
        Circle Circle6 = createCircle(290, 295, 15, Color.WHITE,1);
        Circle Circle7 = createCircle(220, 295, 15, Color.WHITE,1);
        Circle Circle8 = createCircle(360, 295, 15, Color.WHITE,1);
        Circle Circle9 = createCircle(360, 355, 15, Color.WHITE,1);
        Circle Circle10 = createCircle(290, 355, 15, Color.WHITE,1);
        Circle Circle11 = createCircle(430, 355, 15, Color.WHITE,1);
        Circle Circle12 = createCircle(430, 415, 15, Color.WHITE,1);
        Circle Circle13 = createCircle(360, 415, 15, Color.WHITE,1);
        Circle Circle14 = createCircle(500, 415, 15, Color.WHITE,1);
        Circle Circle15 = createCircle(500, 470, 15, Color.WHITE,1);
        Circle Circle16 = createCircle(430, 470, 15, Color.WHITE,1);
        Circle Circle17 = createCircle(570, 470, 15, Color.WHITE,1);
        Circle Circle18 = createCircle(570, 530, 15, Color.WHITE,1);
        Circle Circle19 = createCircle(500, 530, 15, Color.WHITE,1);
        Circle Circle20 = createCircle(640, 530, 15, Color.WHITE,1);
        Circle Circle21 = createCircle(640, 590, 15, Color.WHITE,1);
        Circle Circle22 = createCircle(570, 590, 15, Color.WHITE,1);
        Circle Circle23 = createCircle(710, 590, 15, Color.WHITE,1);
        Circle Circle24 = createCircle(710, 650, 15, Color.WHITE,1);
        Circle Circle25 = createCircle(640, 650, 15, Color.WHITE,1);
        Circle Circle26 = createCircle(70, 170, 15, Color.WHITE,1);
        Circle Circle27 = createCircle(95, 120, 15, Color.WHITE,1);
        Circle Circle28 = createCircle(150, 105, 15, Color.WHITE,1);
        Circle Circle29 = createCircle(220, 105, 15, Color.WHITE,1);
        Circle Circle30 = createCircle(70, 235, 15, Color.WHITE,1);
        Circle Circle31 = createCircle(360, 235, 15, Color.WHITE,1);
        Circle Circle32 = createCircle(360, 170, 15, Color.WHITE,1);
        Circle Circle33 = createCircle(220, 355, 15, Color.WHITE,1);
        Circle Circle34 = createCircle(150, 355, 15, Color.WHITE,1);
        Circle Circle35 = createCircle(430, 295, 15, Color.WHITE,1);
        Circle Circle36 = createCircle(430, 235, 15, Color.WHITE,1);
        Circle Circle37 = createCircle(290, 415, 15, Color.WHITE,1);
        Circle Circle38 = createCircle(220, 415, 15, Color.WHITE,1);
        Circle Circle39 = createCircle(500, 355, 15, Color.WHITE,1);
        Circle Circle40 = createCircle(360, 470, 15, Color.WHITE,1);
        Circle Circle41 = createCircle(570, 415, 15, Color.WHITE,1);
        Circle Circle42 = createCircle(640, 415, 15, Color.WHITE,1);
        Circle Circle43 = createCircle(430, 530, 15, Color.WHITE,1);
        Circle Circle44 = createCircle(430, 590, 15, Color.WHITE,1);
        Circle Circle45 = createCircle(640, 470, 15, Color.WHITE,1);
        Circle Circle46 = createCircle(710, 470, 15, Color.WHITE,1);
        Circle Circle47 = createCircle(500, 590, 15, Color.WHITE,1);
        Circle Circle48 = createCircle(500, 650, 15, Color.WHITE,1);
        Circle Circle49 = createCircle(780, 590, 15, Color.WHITE,1);
        Circle Circle50 = createCircle(780, 650, 15, Color.WHITE,1);
        Circle Circle51 = createCircle(765, 695, 15, Color.WHITE,1);
        Circle Circle52 = createCircle(710, 710, 15, Color.WHITE,1);
        Circle Circle53 = createCircle(640, 710, 15, Color.WHITE,1);

        //connet specified circles with lines
        Line line1 = connect(Circle1, Circle2);
        Line line2 = connect(Circle2, Circle3);
        Line line3 = connect(Circle3, Circle4);
        Line line4 = connect(Circle4, Circle1);
        Line line5 = connect(Circle3, Circle5);
        Line line6 = connect(Circle5, Circle6);
        Line line7 = connect(Circle6, Circle7);
        Line line8 = connect(Circle7, Circle3);
        Line line9 = connect(Circle6, Circle8);
        Line line10 = connect(Circle8, Circle9);
        Line line11 = connect(Circle9, Circle10);
        Line line12 = connect(Circle10, Circle6);
        Line line13 = connect(Circle9, Circle11);
        Line line14 = connect(Circle11, Circle12);
        Line line15 = connect(Circle12, Circle13);
        Line line16 = connect(Circle13, Circle9);
        Line line17 = connect(Circle12, Circle14);
        Line line18 = connect(Circle14, Circle15);
        Line line19 = connect(Circle15, Circle16);
        Line line20 = connect(Circle16, Circle12);
        Line line21 = connect(Circle15, Circle17);
        Line line22 = connect(Circle17, Circle18);
        Line line23 = connect(Circle18, Circle19);
        Line line24 = connect(Circle19, Circle15);
        Line line25 = connect(Circle18, Circle20);
        Line line26 = connect(Circle20, Circle21);
        Line line27 = connect(Circle21, Circle22);
        Line line28 = connect(Circle22, Circle18);
        Line line29 = connect(Circle21, Circle23);
        Line line30 = connect(Circle23, Circle24);
        Line line31 = connect(Circle24, Circle25);
        Line line32 = connect(Circle25, Circle21);
        Line line33 = connect(Circle1, Circle26);
        Line line34 = connect(Circle1, Circle27);
        Line line35 = connect(Circle1, Circle28);
        Line line36 = connect(Circle2, Circle29);
        Line line37 = connect(Circle4, Circle30);
        Line line38 = connect(Circle5, Circle31);
        Line line39 = connect(Circle31, Circle32);
        Line line40 = connect(Circle7, Circle33);
        Line line41 = connect(Circle33, Circle34);
        Line line42 = connect(Circle8, Circle35);
        Line line43 = connect(Circle35, Circle36);
        Line line44 = connect(Circle10, Circle37);
        Line line45 = connect(Circle37, Circle38);
        Line line46 = connect(Circle11, Circle39);
        Line line47 = connect(Circle39, Circle14);
        Line line48 = connect(Circle13, Circle40);
        Line line49 = connect(Circle40, Circle16);
        Line line50 = connect(Circle17, Circle41);
        Line line51 = connect(Circle41, Circle42);
        Line line52 = connect(Circle19, Circle43);
        Line line53 = connect(Circle43, Circle44);
        Line line54 = connect(Circle20, Circle45);
        Line line55 = connect(Circle45, Circle46);
        Line line56 = connect(Circle22, Circle47);
        Line line57 = connect(Circle47, Circle48);
        Line line58 = connect(Circle23, Circle49);
        Line line59 = connect(Circle24, Circle50);
        Line line60 = connect(Circle24, Circle51);
        Line line61 = connect(Circle24, Circle52);
        Line line62 = connect(Circle25, Circle53);
        Line line63 = connect(Circle2, Circle32);
        Line line64 = connect(Circle4, Circle34);
        Line line65 = connect(Circle31, Circle36);
        Line line66 = connect(Circle38, Circle33);
        Line line67 = connect(Circle36, Circle42);
        Line line68 = connect(Circle38, Circle44);
        Line line69 = connect(Circle45, Circle42);
        Line line70 = connect(Circle47, Circle44);
        Line line71 = connect(Circle48, Circle25);
        Line line72 = connect(Circle46, Circle23);


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
        pane_graph.getChildren().add(Circle35);
        pane_graph.getChildren().add(Circle36);
        pane_graph.getChildren().add(Circle37);
        pane_graph.getChildren().add(Circle38);
        pane_graph.getChildren().add(Circle39);
        pane_graph.getChildren().add(Circle40);
        pane_graph.getChildren().add(Circle41);
        pane_graph.getChildren().add(Circle42);
        pane_graph.getChildren().add(Circle43);
        pane_graph.getChildren().add(Circle44);
        pane_graph.getChildren().add(Circle45);
        pane_graph.getChildren().add(Circle46);
        pane_graph.getChildren().add(Circle47);
        pane_graph.getChildren().add(Circle48);
        pane_graph.getChildren().add(Circle49);
        pane_graph.getChildren().add(Circle50);
        pane_graph.getChildren().add(Circle51);
        pane_graph.getChildren().add(Circle52);
        pane_graph.getChildren().add(Circle53);

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
        pane_graph.getChildren().add(line59);
        pane_graph.getChildren().add(line60);
        pane_graph.getChildren().add(line61);
        pane_graph.getChildren().add(line62);
        pane_graph.getChildren().add(line63);
        pane_graph.getChildren().add(line64);
        pane_graph.getChildren().add(line65);
        pane_graph.getChildren().add(line66);
        pane_graph.getChildren().add(line67);
        pane_graph.getChildren().add(line68);
        pane_graph.getChildren().add(line69);
        pane_graph.getChildren().add(line70);
        pane_graph.getChildren().add(line71);
        pane_graph.getChildren().add(line72);

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
        Circle35.toFront();
        Circle36.toFront();
        Circle37.toFront();
        Circle38.toFront();
        Circle39.toFront();
        Circle40.toFront();
        Circle41.toFront();
        Circle42.toFront();
        Circle43.toFront();
        Circle44.toFront();
        Circle45.toFront();
        Circle46.toFront();
        Circle47.toFront();
        Circle48.toFront();
        Circle49.toFront();
        Circle50.toFront();
        Circle51.toFront();
        Circle52.toFront();
        Circle53.toFront();

        num_of_colors = new Paint[53]; //An array to hold the used colors.
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
        list.add(Circle35);
        list.add(Circle36);
        list.add(Circle37);
        list.add(Circle38);
        list.add(Circle39);
        list.add(Circle30);
        list.add(Circle31);
        list.add(Circle32);
        list.add(Circle33);
        list.add(Circle34);
        list.add(Circle35);
        list.add(Circle36);
        list.add(Circle37);
        list.add(Circle38);
        list.add(Circle39);
        list.add(Circle40);
        list.add(Circle41);
        list.add(Circle42);
        list.add(Circle43);
        list.add(Circle44);
        list.add(Circle45);
        list.add(Circle46);
        list.add(Circle47);
        list.add(Circle48);
        list.add(Circle49);
        list.add(Circle50);
        list.add(Circle51);
        list.add(Circle52);
        list.add(Circle53);

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

        scene1 = new Scene(vbox, 870, 800);
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