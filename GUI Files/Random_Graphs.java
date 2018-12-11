package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
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
import javafx.stage.Stage;
import java.util.*;

/**
 * Implementing the "Random" mode of the game
 * The user can choose between entering the number of vertices and edges themselves or loading from a file.
 * The vertices are displayed in random places.
 * However the user can change the layout of the graph to a circular one, after pressing the "Order" button.
 */

public class Random_Graphs {

    private static double orgSceneX, orgSceneY;
    private static Paint[] num_of_colors;
    private static Circles [] cir;
    private static int width = 1200;
    private static int height = 800;
    private static Color color_holder = Color.TRANSPARENT;

    /**
     * Creating event handlers for detecting mouse events like clicking and dragging.
     */

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

    /**
     * Creating the circles and connecting them with lines.
     * for createCircle method:
     * @param x for x value.
     * @param y for y value.
     * @param r for radius.
     * @param color for color.
     * @return a circle.
     * The connect method takes two circles and connects them.
     * @return a line.
     */

    private static Circle createCircle(double x, double y, double r, Color color)
    {
        Circle circle = new Circle(x, y, r, color);
        circle.setStroke(Color.BLACK);
        circle.setCursor(Cursor.CROSSHAIR);
        circle.setStrokeWidth(1.6);

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

    /**
     * getNumColors - a Get method for the number of used colors.
     * The method is adding the colors to a HashSet, so that only the unique colors will be counted in the end (no repetition).
     * @return the size of the set.
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
     * getWidth and getHeight - two Get methods for the width and height of the window.
     * This is used in the Circular_Layout class when calculating the circular positions of the vertices.
     */

    public static int getWidth() {
        return width;
    }
    public static int getHeight() {
        return height;
    }

    /**
     * Hinter method is invoking the "Hint" window, whenever the "Hint" button is clicked.
     */

    public static void Hinter(){
        Hint.display("Hint", "Need help?");
    }

    /**
     * checkAdj method - check if the vertices are adjacent or not.
     * @param adj_matrix the adjacency matrix.
     * @param v the vertex.
     * @param c the color of the vertex.
     * @return false if vertices are adjacent.
     */

    public static boolean checkAdj(int [][] adj_matrix, int v, Paint c){
        for (int i = 0; i < adj_matrix.length; i++){
            if (adj_matrix[v][i] == 1) {
                if (cir[i].Circle1.getFill() == c) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * The display method implements all the elements that are added to the Random_Graph window.
     * This includes functionality of the elements as well.
     * @param title of the window.
     * @param message of the window.
     * @param adj is the adjacency matrix.
     * @param array_random random array for randomly assigning values to the vertices.
     */

    public static void display(String title, String message, int [][] adj, int [] array_random){
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(250);

        //Adding a pane for the elements of the graph
        Pane pane_graph = new Pane();
        pane_graph.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        pane_graph.setStyle(

//                "-fx-background-image: url('https://en.wikipedia.org/wiki/The_Great_Wave_off_Kanagawa#/media/File:Tsunami_by_hokusai_19th_century.jpg');"+
                "-fx-background-position: center center;"+
                "-fx-effect: dropshadow(three-pass-box, grey, 30, 0.2, 0, 0);");

        //Adding a gridpane for the buttons, text and colorpicker.
        GridPane pane = new GridPane();
        VBox vbox = new VBox(pane_graph, pane);

        //Adding HINTS button
        Button buttonhint = new Button("HINTS");
        pane.add(buttonhint, 5,0,1,1);
        buttonhint.setOnAction(e ->  Hinter());

        Label label = new Label();
        label.setText(message);
        pane_graph.getChildren().add(label);

        // Reading adjacency matrix for random generated values and creating the graph
        cir = new Circles[adj.length];
        for (int d=0; d<adj.length; d++) {
            int random_width = (int)(Math.random()*(width-50));
            int random_height = (int)(Math.random()*(height-50));
            int z = array_random[d];
            cir[d] = new Circles(random_width, random_height);
            cir[d].Circle1 = createCircle(random_width, random_height, 15, Color.TRANSPARENT);
            Text number = new Text(String.valueOf(z));
            number.xProperty().bind(cir[d].Circle1.centerXProperty());
            number.yProperty().bind(cir[d].Circle1.centerYProperty());
            pane_graph.getChildren().addAll(cir[d].Circle1, number);
            cir[d].Circle1.toFront();
            number.toFront();
        }

        //Connecting the circles
        for (int i=0; i<adj.length; i++){
            for (int j=0; j<adj[i].length; j++){
                if (adj[i][j] == 1){
                    Line line1 = connect(cir[i].Circle1, cir[j].Circle1);
                    pane_graph.getChildren().add(line1);
                    line1.toBack();
                }
            }
        }

        //Adding "ORDER" button which changes the graph layout to circular
        Button button_layout = new Button("ORDER");
        pane.add(button_layout, 6,0,1,1);
        button_layout.setOnAction(e ->  {
            pane_graph.getChildren().clear();
            Circular_Layout.display(cir, adj.length);

            //Re-adjusting circles
            for (int d=0; d<adj.length; d++) {
                int x = (int)(cir[d].Circle1.getCenterX()+230);
                int y = (int)(cir[d].Circle1.getCenterY());
                int z = array_random[d];
                cir[d].Circle1.setCenterX(x);
                cir[d].Circle1.setCenterY(y);
                Text number1 = new Text(String.valueOf(z));
                number1.xProperty().bind(cir[d].Circle1.centerXProperty());
                number1.yProperty().bind(cir[d].Circle1.centerYProperty());
                pane_graph.getChildren().addAll(cir[d].Circle1, number1);
                cir[d].Circle1.toFront();
                number1.toFront();
            }
            //Connecting the circles for the new layout
            for (int i=0; i<adj.length; i++){
                for (int j=0; j<adj[i].length; j++){
                    if (adj[i][j] == 1){
                        Line line1 = connect(cir[i].Circle1, cir[j].Circle1);
                        pane_graph.getChildren().add(line1);
                        line1.toBack();
                    }
                }
            }
        });

        // Adding the color picker
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.TRANSPARENT);
        Text text_color = new Text("Pick Your Color." + "\n" + "After that right-click on vertex you'd like to color.");
        text_color.setFont(Font.font ("Verdana", 14));
        text_color.setFill(Color.BLACK);
        colorPicker.setOnAction((ActionEvent t) -> {color_holder = colorPicker.getValue();});
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.add(colorPicker, 0, 0, 1, 1);
        pane.add(text_color,0,1,1,1);

        //Adding Event Filter to check which circle was clicked
        num_of_colors = new Paint[cir.length]; //An array to hold the used colors.
        final Text text = new Text("\nColors used: 0");
        text.setFont(Font.font ("Verdana", 14));

        for (int i=0; i<cir.length; i++){
            final int temp_i = i;
            cir[i].Circle1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    text.setText("");
                    // Checks if the user picked a color from the color box in the beginning.
                    if (mouseEvent.getButton() == MouseButton.SECONDARY && colorPicker.getValue() == Color.TRANSPARENT){
                        text.setText("Pick a color first");
                        text.setFill(Color.RED);
                    }
                    // If the user right-clicks on a circle, the adjacency method is called to check if it's allowed to color the vertex.
                    else if (mouseEvent.getButton() == MouseButton.SECONDARY && checkAdj(adj, temp_i, colorPicker.getValue())){
                        if (cir[temp_i].Circle1.getFill() == Color.TRANSPARENT) {
                            cir[temp_i].Circle1.setFill(colorPicker.getValue());
                            num_of_colors[temp_i] = cir[temp_i].Circle1.getFill();
                            text.setText("\nColors used: " + (getNumColors()));
                            text.setFill(Color.BLACK);
                        }
                        else {
                            text.setText("ALREADY COLORED");
                            text.setFill(Color.RED);
                        }
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

        // Highlight circles when hovering over then with the mouse
        for (int i=0; i<cir.length; i++) {
            final int temp_i = i;
            DropShadow shadow = new DropShadow();
            cir[i].Circle1.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            cir[temp_i].Circle1.setEffect(shadow);
                        }
                    });
        }
        for (int i=0; i<cir.length; i++) {
            final int temp_i = i;
            cir[i].Circle1.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            cir[temp_i].Circle1.setEffect(null);
                        }
                    });
        }

        Scene scene1 = new Scene(vbox, width+30, height+30);
        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();
    }
}