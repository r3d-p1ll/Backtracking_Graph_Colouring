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

    public static boolean CheckColors(int adj [][]){
        boolean seen = true;
        for (int d=0; d<adj.length; d++){
            if(cir[d].Circle1.getFill().equals(Color.TRANSPARENT)){
                seen = false;
            }
        }
        return seen;
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
        pane_graph.setMinSize(1100, 700);
        pane_graph.setStyle(
                "-fx-background-position: center center;"+
                        "-fx-effect: dropshadow(three-pass-box, grey, 30, 0.2, 0, 0);");

        //Adding a gridpane for the buttons, text and colorpicker.
        GridPane pane = new GridPane();
        VBox vbox = new VBox(pane_graph, pane);

        //Adding Text to the pane.
        Text text_color = new Text("Pick Your Color." + "\n" + "After that right-click on vertex you'd like to color. ");
        text_color.setFont(Font.font ("Verdana", 14));
        text_color.setFill(Color.BLACK);

        // Text display for how many colors have been used so far.
        final Text text = new Text("\nColors used: 0");
        text.setText("\nColors used: 0");
        text.setFont(Font.font ("Verdana", 14));
        pane.add(text,8,1,1,1);

        //Adding HELP button
        Button buttonhint = new Button("HELP");
        pane.add(buttonhint, 5,0,1,1);
        buttonhint.setOnAction(e -> Hint_Random.display("Hint", "Need help?"));
        buttonhint.setPrefWidth(80);
        buttonhint.setPrefHeight(40);
        buttonhint.setStyle("-fx-background-color: #e6e6e6");
        buttonhint.setOnMouseEntered(e -> buttonhint.setStyle("-fx-background-color: #2EE59D;"));
        buttonhint.setOnMouseExited(e -> buttonhint.setStyle("-fx-background-color: #e6e6e6"));

        // Adding FINISH button for when the user is done with the coloring
        Button end = new Button("FINISH");
        pane.add(end, 8,0,1,1);
        end.setPrefWidth(80);
        end.setPrefHeight(40);
        end.setOnAction(e -> {
            if(CheckColors(adj)){
                EndMode.display();
                window.close();
            }
            else{
                text.setText("YOU HAVEN'T COLORED EVERY VERTICE");
                text.setFill(Color.RED);
            }
        });
        end.setStyle("-fx-background-color: #e6e6e6");
        end.setOnMouseEntered(e -> end.setStyle("-fx-background-color: #2EE59D;"));
        end.setOnMouseExited(e -> end.setStyle("-fx-background-color: #e6e6e6"));

        Label label = new Label();
        label.setText(message);
        pane_graph.getChildren().add(label);

        // Reading adjacency matrix for random generated values and creating the graph
        cir = new Circles[adj.length];
        for (int d=0; d<adj.length; d++) {
            int random_width = (int)(Math.random()*(width-200));
            int random_height = (int)(Math.random()*(height-200));
//            int z = array_random[d];
            cir[d] = new Circles();
            cir[d].Circle1 = createCircle(random_width, random_height, 15, Color.TRANSPARENT);
            Text number = new Text(String.valueOf(d+1));
            number.xProperty().bind(cir[d].Circle1.centerXProperty());
            number.yProperty().bind(cir[d].Circle1.centerYProperty());
            pane_graph.getChildren().addAll(cir[d].Circle1, number);
            cir[d].Circle1.toFront();
            number.toBack();
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
        button_layout.setPrefWidth(80);
        button_layout.setPrefHeight(40);
        button_layout.setStyle("-fx-background-color: #e6e6e6");
        button_layout.setOnMouseEntered(e -> button_layout.setStyle("-fx-background-color: #2EE59D;"));
        button_layout.setOnMouseExited(e -> button_layout.setStyle("-fx-background-color: #e6e6e6"));
        button_layout.setOnAction(e ->  {
            // Code for reordering the graph is executed three times for better results of the X and Y coordinates.
            for (int k=0; k<3; k++) {
                pane_graph.getChildren().clear();
                Circular_Layout.display(cir, adj.length);

                //Re-adjusting circles
                for (int d = 0; d < adj.length; d++) {
                    int x = (int) (cir[d].Circle1.getCenterX() + 230);
                    int y = (int) (cir[d].Circle1.getCenterY());
//                    int z = adj[d];
                    cir[d].Circle1.setCenterX(x);
                    cir[d].Circle1.setCenterY(y);
                    Text number1 = new Text(String.valueOf(d+1));
                    number1.xProperty().bind(cir[d].Circle1.centerXProperty());
                    number1.yProperty().bind(cir[d].Circle1.centerYProperty());
                    pane_graph.getChildren().addAll(cir[d].Circle1, number1);
                    cir[d].Circle1.toFront();
                    number1.toBack();
                }
                //Re-connecting the circles for the new layout
                for (int i = 0; i < adj.length; i++) {
                    for (int j = 0; j < adj[i].length; j++) {
                        if (adj[i][j] == 1) {
                            Line line1 = connect(cir[i].Circle1, cir[j].Circle1);
                            pane_graph.getChildren().add(line1);
                            line1.toBack();
                        }
                    }
                }
            }
        });

        // Adding the color picker
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.TRANSPARENT);
        colorPicker.setPrefWidth(200);
        colorPicker.setPrefHeight(40);
        colorPicker.setStyle("-fx-background-color: #e6e6e6");
        colorPicker.setOnMouseEntered(e -> colorPicker.setStyle("-fx-background-color: #2EE59D;"));
        colorPicker.setOnMouseExited(e -> colorPicker.setStyle("-fx-background-color: #e6e6e6"));
        colorPicker.setOnAction((ActionEvent t) -> {color_holder = colorPicker.getValue();});
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.add(colorPicker, 0, 0, 1, 1);
        pane.add(text_color,0,1,1,1);

        //Adding Event Filter to check which circle was clicked
        num_of_colors = new Paint[cir.length]; //An array to hold the used colors.
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
                    // It also checks if the user is following the right order or not. If not - they are not allowed to color the vertex.
                    else if (mouseEvent.getButton() == MouseButton.SECONDARY && checkAdj(adj, temp_i, colorPicker.getValue())){
                        if (temp_i == 0 && cir[0].Circle1.getFill() == Color.TRANSPARENT) {
                            cir[temp_i].Circle1.setFill(colorPicker.getValue());
                            num_of_colors[temp_i] = cir[temp_i].Circle1.getFill();
                            text.setText("\nColors used: " + (getNumColors()));
                            text.setFill(Color.BLACK);
                        }
                        else if (cir[temp_i].Circle1.getFill() == Color.TRANSPARENT && cir[temp_i-1].Circle1.getFill() != Color.TRANSPARENT) {
                            cir[temp_i].Circle1.setFill(colorPicker.getValue());
                            num_of_colors[temp_i] = cir[temp_i].Circle1.getFill();
                            text.setText("\nColors used: " + (getNumColors()));
                            text.setFill(Color.BLACK);
                        }
                        else if (cir[temp_i-1].Circle1.getFill() == Color.TRANSPARENT){
                            text.setText("FOLLOW THE ORDER");
                            text.setFill(Color.RED);
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