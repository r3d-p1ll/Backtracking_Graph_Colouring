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

public class Fixed_graphs {

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

        line.setStrokeWidth(1);
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
        scene1 = new Scene(gr1,800, 600);

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

        // bring the circles to the front of the lines
        Circle1.toFront();
        Circle2.toFront();
        Circle3.toFront();
        Circle4.toFront();
        Circle5.toFront();
        Circle6.toFront();

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