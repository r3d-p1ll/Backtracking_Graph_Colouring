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

public class Fixed_Graph7 {

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

        Circle Circle1 = createCircle(425, 55, 20, Color.WHITE);
        Circle Circle2 = createCircle(100, 220, 20, Color.WHITE);
        Circle Circle3 = createCircle(425, 220, 20, Color.WHITE);
        Circle Circle4 = createCircle(760, 220, 20, Color.WHITE);
        Circle Circle5 = createCircle(425, 390, 20, Color.WHITE);
        Circle Circle6 = createCircle(100, 555, 20, Color.WHITE);
        Circle Circle7 = createCircle(425, 555, 20, Color.WHITE);
        Circle Circle8 = createCircle(760, 555, 20, Color.WHITE);
        Circle Circle9 = createCircle(425, 725, 20, Color.WHITE);

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

