


        package sample;

        import javafx.event.EventHandler;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Cursor;
        import javafx.scene.Group;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.ColorPicker;
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
        import java.util.Iterator;

public class Timed_Graph12 {

    static Scene scene1;
    static Stage window;
    static Stage gameOverWindow;
    static Label layout;
    static double orgSceneX, orgSceneY;
    static final Integer starttime = 10; //edit how long the timer is from here.
    static Integer seconds = starttime;
    static Paint[] num_of_colors;
    static Color color_holder = Color.WHITE;

    //Game over screen method.
    private static void setGameOver(){
        gameOverWindow = new Stage();
        GridPane grid = new GridPane();
        gameOverWindow.setTitle("Time's up!");
        Label timeUsed = new Label("Time: ");
        GridPane.setConstraints(timeUsed,0,0);
        Label chromaUsed = new Label("You used " + Random_Graphs.getNumColors() + "colors");
        GridPane.setConstraints(chromaUsed, 0,1);

        // uncomment when we can implement the chromatic number
    /*if(Random_Graphs.getNumColors() > CHROMATICNUMBER){
        Label useLess = new Label("You can use less colors");
        GridPane.setConstraints(useLess, 0,1);
    }
    if(Random_Graphs.getNumColors() == CHROMATICNUMBER){
        Label usedGood = new Label("You used exactly the right amount of colors");
        GridPane.setConstraints(usedGood, 0,1);
    }*/

        grid.getChildren().addAll(timeUsed,chromaUsed);
        Scene gameOverScene = new Scene(grid);
        gameOverWindow.setScene(gameOverScene);
        gameOverWindow.show();
    }

    //Timer method.
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

    //Mouse click listener.
    private static EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
    {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();

        Circle c = (Circle) (t.getSource());
        c.toFront();
    };

    public static void Hinter(){
        Hint.display("Hint", "Need help?", 3,1);
    }
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
    private static Circle createCircle(double x, double y, double r, Color color)
    {
        Circle circle = new Circle(x, y, r, color);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);

        circle.setCursor(Cursor.CROSSHAIR);

        circle.setOnMousePressed(mousePressedEventHandler);
        circle.setOnMouseDragged(mouseDraggedEventHandler);

        return circle;
    }

    //Create GUI object line.
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

        //Group pane_graph = new Group();
        //scene1 = new Scene(pane_graph,900, 815);

        Circle Circle1 = createCircle(430, 85, 15, Color.WHITE);
        Circle Circle2 = createCircle(430, 140, 15, Color.WHITE);
        Circle Circle3 = createCircle(430, 230, 15, Color.WHITE);
        Circle Circle4 = createCircle(430, 290, 15, Color.WHITE);
        Circle Circle5 = createCircle(430, 350, 15, Color.WHITE);
        Circle Circle6 = createCircle(430, 440, 15, Color.WHITE);
        Circle Circle7 = createCircle(430, 500, 15, Color.WHITE);
        Circle Circle8 = createCircle(430, 555, 15, Color.WHITE);
        Circle Circle9 = createCircle(430, 640, 15, Color.WHITE);
        Circle Circle10 = createCircle(430, 700, 15, Color.WHITE);
        Circle Circle11 = createCircle(95, 190, 15, Color.WHITE);
        Circle Circle12 = createCircle(95, 395, 15, Color.WHITE);
        Circle Circle13 = createCircle(95, 600, 15, Color.WHITE);
        Circle Circle14 = createCircle(760, 190, 15, Color.WHITE);
        Circle Circle15 = createCircle(760, 395, 15, Color.WHITE);
        Circle Circle16 = createCircle(760, 600, 15, Color.WHITE);

        Line line1 = connect(Circle1, Circle2);
        Line line2 = connect(Circle1, Circle11);
        Line line3 = connect(Circle1, Circle14);
        Line line4 = connect(Circle2, Circle11);
        Line line5 = connect(Circle2, Circle14);
        Line line6 = connect(Circle11, Circle14);
        Line line7 = connect(Circle3, Circle11);
        Line line8 = connect(Circle3, Circle14);
        Line line9 = connect(Circle3, Circle4);
        Line line10 = connect(Circle11, Circle4);
        Line line11 = connect(Circle4, Circle14);
        Line line12 = connect(Circle4, Circle5);
        Line line13 = connect(Circle4, Circle12);
        Line line14 = connect(Circle4, Circle15);
        Line line15 = connect(Circle5, Circle12);
        Line line16 = connect(Circle5, Circle15);
        Line line17 = connect(Circle12, Circle15);
        Line line18 = connect(Circle12, Circle6);
        Line line19 = connect(Circle6, Circle15);
        Line line20 = connect(Circle6, Circle7);
        Line line21 = connect(Circle7, Circle12);
        Line line22 = connect(Circle7, Circle15);
        Line line23 = connect(Circle7, Circle8);
        Line line24 = connect(Circle7, Circle13);
        Line line25 = connect(Circle7, Circle16);
        Line line26 = connect(Circle8, Circle13);
        Line line27 = connect(Circle8, Circle16);
        Line line28 = connect(Circle13, Circle16);
        Line line29 = connect(Circle13, Circle9);
        Line line30 = connect(Circle16, Circle9);
        Line line31 = connect(Circle9, Circle10);
        Line line32 = connect(Circle10, Circle13);
        Line line33 = connect(Circle10, Circle16);


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

        ColorBox cbox = new ColorBox();
        num_of_colors = new Paint[16]; //An array to hold the used colors.

        //for the part to color the box.
        //Unfortunately I could not find a better solution than this for now.  Cause I am not 100% how you represent the graphs exactly.
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
        /*Iterator<Circle> iterator = list.iterator();
        while (iterator.hasNext()){
            Circle temp = iterator.next();
            temp.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    temp.setFill(cbox.getValue());
                }
            });
        }*/

        for (int i=0; i<list.size(); i++){
            final int temp_i = i;
            list.get(i).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    list.get(temp_i).setFill(cbox.getValue());
                    num_of_colors[temp_i] = list.get(temp_i).getFill();
                }
            });
        }

        // ADDING THE COLOR PICKER
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(null);
        Text text_color = new Text("Pick Your Color." + "\n" + "After that right-click on vertex you'd like to color.");
        text_color.setFont(Font.font ("Verdana", 14));
        text_color.setFill(Color.BLACK);
        colorPicker.setOnAction((ActionEvent t) -> {color_holder = colorPicker.getValue();});
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.add(colorPicker, 0, 0, 1, 1);
        pane.add(text_color,0,1,1,1);
        // Display the ColorBox.
        //cbox.display();
        scene1 = new Scene(vbox, 830, 850);
        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();

    }

}
