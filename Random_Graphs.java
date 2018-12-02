

        package sample;

        import javafx.event.EventHandler;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Cursor;
        import javafx.scene.Group;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.ColorPicker;
        import javafx.scene.control.Label;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Circle;
        import javafx.scene.shape.Line;
        import javafx.scene.shape.StrokeLineCap;
        import javafx.scene.text.Font;
        import javafx.scene.text.Text;
        import javafx.stage.Modality;
        import javafx.stage.Stage;

public class Random_Graphs {

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
        line.toBack();

        return line;
    }

    public static void display(String title, String message, int [][] adj, int array[]) {
        Stage window = new Stage();

        window.setTitle(title);
        window.setMinWidth(250);

        int[][] adj_matrix = adj;

        Group gr1 = new Group();
        scene1 = new Scene(gr1, 1024, 800);

        Button buttonhint = new Button("HINTS");
        gr1.getChildren().add(buttonhint);
        buttonhint.setOnAction(e -> Hinter());




        Label label = new Label();
        label.setText(message);
        gr1.getChildren().add(label);

        // Reading adjacency matrix for random generated values and creating the graph
        final Circles cir[] = new Circles[adj_matrix.length];
        for (int d = 0; d < adj_matrix.length; d++ ) {

                    //while(i<= array.length){
                    cir[d] = new Circles();
                    double x = (Math.random() * 800);
                    double y = (Math.random() * 600);
                    int z = array[d];
                    cir[d].Circle1 = createCircle((int) x, (int) y, 20, Color.GREY); // Generating circles in random places
                    Text number = new Text(x, y, String.valueOf(z));
                    gr1.getChildren().addAll(cir[d].Circle1, number);
                    cir[d].Circle1.toFront();
                    number.toFront();



        }

        for (int i = 0; i < adj_matrix.length; i++) {
            for (int j = 0; j < adj_matrix[i].length; j++) {
                if (adj_matrix[i][j] == 1) {
                    Line line1 = connect(cir[i].Circle1, cir[j].Circle1);
                    gr1.getChildren().add(line1);
                    line1.toBack();

                }
            }
        }

        //Adding Event Filter to check which circle was clicked
        ColorBox cbox = new ColorBox();
        for (int i = 0; i < cir.length; i++) {
            final int temp_i = i;
            cir[i].Circle1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    cir[temp_i].Circle1.setFill(cbox.getValue());
                }
            });
        }


        // Adding The ColorBox
        cbox.display();

        window.setScene(scene1);
        window.setTitle("Graph Coloring Game");
        window.show();
    }

        private static void Hinter() {
            Hint.display("Set parameters", "Please set the number of vertices and edges:");
            //        Hint.display("Random Graph", "Good luck!");
        }



}
