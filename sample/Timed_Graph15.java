
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

public class Timed_Graph15 {

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

        Circle Circle1 = createCircle(430, 425, 15, Color.WHITE);
        Circle Circle2 = createCircle(330, 425, 15, Color.WHITE);
        Circle Circle3 = createCircle(280, 340, 15, Color.WHITE);
        Circle Circle4 = createCircle(380, 345, 15, Color.WHITE);
        Circle Circle5 = createCircle(430, 255, 15, Color.WHITE);
        Circle Circle6 = createCircle(480, 345, 15, Color.WHITE);
        Circle Circle7 = createCircle(575, 345, 15, Color.WHITE);
        Circle Circle8 = createCircle(525, 425, 15, Color.WHITE);
        Circle Circle9 = createCircle(570, 510, 15, Color.WHITE);
        Circle Circle10 = createCircle(480, 510, 15, Color.WHITE);
        Circle Circle11 = createCircle(430, 600, 15, Color.WHITE);
        Circle Circle12 = createCircle(375, 510, 15, Color.WHITE);
        Circle Circle13 = createCircle(280, 510, 15, Color.WHITE);
        Circle Circle14 = createCircle(255, 425, 15, Color.WHITE);
        Circle Circle15 = createCircle(175, 285, 15, Color.WHITE);
        Circle Circle16 = createCircle(340, 285, 15, Color.WHITE);
        Circle Circle17 = createCircle(430, 140, 15, Color.WHITE);
        Circle Circle18 = createCircle(520, 285, 15, Color.WHITE);
        Circle Circle19 = createCircle(685, 285, 15, Color.WHITE);
        Circle Circle20 = createCircle(600, 425, 15, Color.WHITE);
        Circle Circle21 = createCircle(685, 565, 15, Color.WHITE);
        Circle Circle22 = createCircle(520, 565, 15, Color.WHITE);
        Circle Circle23 = createCircle(430, 705, 15, Color.WHITE);
        Circle Circle24 = createCircle(335, 565, 15, Color.WHITE);
        Circle Circle25 = createCircle(175, 565, 15, Color.WHITE);
        Circle Circle26 = createCircle(185, 425, 15, Color.WHITE);
        Circle Circle27 = createCircle(65, 230, 15, Color.WHITE);
        Circle Circle28 = createCircle(300, 230, 15, Color.WHITE);
        Circle Circle29 = createCircle(430, 45, 15, Color.WHITE);
        Circle Circle30 = createCircle(560, 230, 15, Color.WHITE);
        Circle Circle31 = createCircle(795, 230, 15, Color.WHITE);
        Circle Circle32 = createCircle(675, 425, 15, Color.WHITE);
        Circle Circle33 = createCircle(790, 610, 15, Color.WHITE);
        Circle Circle34 = createCircle(555, 610, 15, Color.WHITE);
        Circle Circle35 = createCircle(430, 795, 15, Color.WHITE);
        Circle Circle36 = createCircle(300, 610, 15, Color.WHITE);
        Circle Circle37 = createCircle(65, 610, 15, Color.WHITE);

        Line line1 = connect(Circle1, Circle2);
        Line line2 = connect(Circle1, Circle3);
        Line line3 = connect(Circle1, Circle4);
        Line line4 = connect(Circle1, Circle5);
        Line line5 = connect(Circle1, Circle6);
        Line line6 = connect(Circle1, Circle7);
        Line line7 = connect(Circle1, Circle8);
        Line line8 = connect(Circle1, Circle9);
        Line line9 = connect(Circle1, Circle10);
        Line line10 = connect(Circle1, Circle11);
        Line line11 = connect(Circle1, Circle12);
        Line line12 = connect(Circle1, Circle3);
        Line line13 = connect(Circle2, Circle3);
        Line line14 = connect(Circle3, Circle4);
        Line line15 = connect(Circle4, Circle5);
        Line line16 = connect(Circle5, Circle6);
        Line line17 = connect(Circle6, Circle7);
        Line line18 = connect(Circle7, Circle8);
        Line line19 = connect(Circle8, Circle9);
        Line line20 = connect(Circle9, Circle10);
        Line line21 = connect(Circle10, Circle11);
        Line line22 = connect(Circle11, Circle12);
        Line line23 = connect(Circle12, Circle13);
        Line line24 = connect(Circle13, Circle2);
        Line line25 = connect(Circle2, Circle14);
        Line line26 = connect(Circle3, Circle15);
        Line line27 = connect(Circle4, Circle16);
        Line line28 = connect(Circle5, Circle17);
        Line line29 = connect(Circle6, Circle18);
        Line line30 = connect(Circle7, Circle19);
        Line line31 = connect(Circle8, Circle20);
        Line line32 = connect(Circle9, Circle21);
        Line line33 = connect(Circle10, Circle22);
        Line line34 = connect(Circle11, Circle23);
        Line line35 = connect(Circle12, Circle24);
        Line line36 = connect(Circle13, Circle25);
        Line line37 = connect(Circle14, Circle15);
        Line line38 = connect(Circle15, Circle16);
        Line line39 = connect(Circle16, Circle17);
        Line line40 = connect(Circle17, Circle18);
        Line line41 = connect(Circle18, Circle19);
        Line line42 = connect(Circle19, Circle20);
        Line line43 = connect(Circle20, Circle21);
        Line line44 = connect(Circle21, Circle22);
        Line line45 = connect(Circle22, Circle23);
        Line line46 = connect(Circle23, Circle24);
        Line line47 = connect(Circle24, Circle25);
        Line line48 = connect(Circle25, Circle14);
        Line line49 = connect(Circle15, Circle27);
        Line line50 = connect(Circle17, Circle29);
        Line line51 = connect(Circle19, Circle31);
        Line line52 = connect(Circle21, Circle33);
        Line line53 = connect(Circle23, Circle35);
        Line line54 = connect(Circle25, Circle37);
        Line line55 = connect(Circle27, Circle28);
        Line line56 = connect(Circle28, Circle29);
        Line line57 = connect(Circle29, Circle30);
        Line line58 = connect(Circle30, Circle31);
        Line line59 = connect(Circle31, Circle32);
        Line line60 = connect(Circle32, Circle33);
        Line line61 = connect(Circle33, Circle34);
        Line line62 = connect(Circle34, Circle35);
        Line line63 = connect(Circle35, Circle36);
        Line line64 = connect(Circle36, Circle37);
        Line line65 = connect(Circle37, Circle26);
        Line line66 = connect(Circle26, Circle27);


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

        ColorBox cbox = new ColorBox();
        num_of_colors = new Paint[37]; //An array to hold the used colors.

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
