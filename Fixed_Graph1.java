
        package sample;

        import javafx.event.EventHandler;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Cursor;
        import javafx.scene.Group;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Circle;
        import javafx.scene.shape.Line;
        import javafx.scene.shape.StrokeLineCap;
        import javafx.stage.Modality;
        import javafx.stage.Stage;

public class Fixed_Graph1 {

    static Scene scene1;
    static double orgSceneX, orgSceneY;


    private static EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
    {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();

        Circle c = (Circle) (t.getSource());
        c.toFront();
    };

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

    private static Circle createCircle(double x, double y, double r, Color color)
    {
        Circle circle = new Circle(x, y, r, color);
        circle.setStrokeWidth(4);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);

        circle.setCursor(Cursor.CROSSHAIR);

        circle.setOnMousePressed(mousePressedEventHandler);
        circle.setOnMouseDragged(mouseDraggedEventHandler);

        return circle;
    }

    private static Line connect(Circle c1, Circle c2)
    {
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

    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Group gr1 = new Group();
        scene1 = new Scene(gr1,900, 815);

        Circle Circle1 = createCircle(564, 285, 20, Color.WHITE);
        Circle Circle2 = createCircle(576, 80, 20, Color.WHITE);
        Circle Circle3 = createCircle(304, 705, 20, Color.WHITE);
        Circle Circle4 = createCircle(564, 515, 20, Color.WHITE);
        Circle Circle5 = createCircle(576, 705, 20, Color.WHITE);
        Circle Circle6 = createCircle(304, 80, 20, Color.WHITE);
        Circle Circle7 = createCircle(220, 280, 20, Color.WHITE);
        Circle Circle8 = createCircle(642, 280, 20, Color.WHITE);
        Circle Circle9 = createCircle(304, 515, 20, Color.WHITE);
        Circle Circle10 = createCircle(220, 518, 20, Color.WHITE);
        Circle Circle11 = createCircle(433, 634, 20, Color.WHITE);
        Circle Circle12 = createCircle(304, 285, 20, Color.WHITE);
        Circle Circle13 = createCircle(642, 518, 20, Color.WHITE);
        Circle Circle14 = createCircle(433, 398, 20, Color.WHITE);
        Circle Circle15 = createCircle(433, 161, 20, Color.WHITE);

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

        //add the circles
        gr1.getChildren().add(Circle1);
        gr1.getChildren().add(Circle2);
        gr1.getChildren().add(Circle3);
        gr1.getChildren().add(Circle4);
        gr1.getChildren().add(Circle5);
        gr1.getChildren().add(Circle6);
        gr1.getChildren().add(Circle7);
        gr1.getChildren().add(Circle8);
        gr1.getChildren().add(Circle9);
        gr1.getChildren().add(Circle10);
        gr1.getChildren().add(Circle11);
        gr1.getChildren().add(Circle12);
        gr1.getChildren().add(Circle13);
        gr1.getChildren().add(Circle14);
        gr1.getChildren().add(Circle15);

        // add the lines
        gr1.getChildren().add(line1);
        gr1.getChildren().add(line2);
        gr1.getChildren().add(line3);
        gr1.getChildren().add(line4);
        gr1.getChildren().add(line5);
        gr1.getChildren().add(line6);
        gr1.getChildren().add(line7);
        gr1.getChildren().add(line8);
        gr1.getChildren().add(line9);
        gr1.getChildren().add(line10);
        gr1.getChildren().add(line11);
        gr1.getChildren().add(line12);
        gr1.getChildren().add(line13);
        gr1.getChildren().add(line14);
        gr1.getChildren().add(line15);
        gr1.getChildren().add(line16);
        gr1.getChildren().add(line17);
        gr1.getChildren().add(line18);
        gr1.getChildren().add(line19);
        gr1.getChildren().add(line20);
        gr1.getChildren().add(line21);
        gr1.getChildren().add(line22);
        gr1.getChildren().add(line23);
        gr1.getChildren().add(line24);
        gr1.getChildren().add(line25);
        gr1.getChildren().add(line26);

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

//    Creating a mouse event handler for a circle
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                Circle1.setFill(Color.RED);
            }
        };

        Circle1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();

    }

}

