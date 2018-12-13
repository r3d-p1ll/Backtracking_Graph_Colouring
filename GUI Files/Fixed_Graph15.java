package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Fixed_Graph15 {

	static Scene scene1;
	static Stage window;
	static Stage gameOverWindow;
	static Label layout;
	static double orgSceneX, orgSceneY;
	static final Integer starttime = 0; //edit how long the timer is from here.
	static Integer seconds = starttime;
	static Paint[] num_of_colors;
	static Color color_holder = Color.TRANSPARENT;
	static Label timeUsed;
	static Label chromaUsed;
	static ColorPicker colorPicker;
	static ArrayList<String> colorList;
	static Set<String> allColors;
	static int colorListLength;
	private final static int chrNum = 3;
	static ArrayList<Circle> list;
	static Text text2;
	static Button end;
	static int lastTime;
	private static Timeline time;
	static int[][] multi;

	public static int getChrNum(){
		return chrNum;
	}

	private static void setGameOver(){
		gameOverWindow = new Stage();
		GridPane grid = new GridPane();
		gameOverWindow.setTitle("Good job");
		timeUsed = new Label("It took you: " +  lastTime + " seconds");
		GridPane.setConstraints(timeUsed,2,2);

		grid.getChildren().addAll(timeUsed);
		Scene gameOverScene = new Scene(grid, 250, 150);
		gameOverWindow.setScene(gameOverScene);
		gameOverWindow.show();
	}

	private static void doTime() {
		time = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				//@Asem change this to ++ and modify the instance variable to start at zero for your part.  Also change the if condition.
				seconds++;
				layout.setText("T: " + seconds.toString());
				if (seconds <= 0) {
					seconds = starttime;
					time.stop();
					window.close();
					setGameOver();
				}
			}
		});

		time.setCycleCount(Timeline.INDEFINITE);
		time.getKeyFrames().add(frame);
		if (time != null) {
			time.stop();
		}
		time.play();
	}

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
		circle.setFill(Color.TRANSPARENT);
		circle.setStrokeWidth(1.6);
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
	public static boolean CheckColors(int adj [][]){
		boolean seen = true;
		for (int d=0; d<adj.length; d++){
			if(list.get(d).getFill().equals(Color.TRANSPARENT)){
				seen = false;
			}
		}
		return seen;
	}
	public static boolean checkAdj(int [][] adj_matrix, int v, Paint c){
		for (int i = 0; i < adj_matrix.length; i++){
			if (adj_matrix[v][i] == 1) {
				if (list.get(i).getFill() == c) {
					return false;
				}
			}
		}
		return true;
	}

	public static void display(String title, String message){
		window = new Stage();
		window.setTitle(title);
		window.setMinWidth(250);

		Pane gr1 = new Pane();
		gr1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		gr1.setStyle(
				"-fx-background-position: center center;"+
						"-fx-effect: dropshadow(three-pass-box, grey, 30, 0.2, 0, 0);");

		GridPane pane = new GridPane();
		VBox vbox = new VBox(gr1, pane);

		//Adding HINTS button
		Button buttonhint = new Button("HINTS");
		pane.add(buttonhint, 5,0,1,1);
		buttonhint.setOnAction(e ->  Hint.display("Hint", "Need help?", getNumColors(), getChrNum()));

		int[][] multi = new int[][]{
				{0, 1, 0, 1, 1, 0},
				{1, 0, 1, 1, 0, 0},
				{0, 1, 0, 1, 0, 0},
				{1, 1, 1, 0, 1, 1},
				{1, 0, 0, 1, 0, 0},
				{0, 0, 0, 1, 0, 0},
		};

		Circle Circle1 = createCircle(480, 101, 15, Color.WHITE);
		Circle Circle2 = createCircle(291, 101, 15, Color.WHITE);
		Circle Circle3 = createCircle(149, 189, 15, Color.WHITE);
		Circle Circle4 = createCircle(385, 370, 15, Color.WHITE);
		Circle Circle5 = createCircle(622, 189, 15, Color.WHITE);
		Circle Circle6 = createCircle(385, 503, 15, Color.WHITE);

		Line line1 = connect(Circle1, Circle2);
		Line line2 = connect(Circle2, Circle3);
		Line line3 = connect(Circle3, Circle4);
		Line line4 = connect(Circle4, Circle5);
		Line line5 = connect(Circle5, Circle1);
		Line line6 = connect(Circle2, Circle4);
		Line line7 = connect(Circle4, Circle1);
		Line line8 = connect(Circle4, Circle6);

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
		gr1.getChildren().add(line8);

		// bring the circles to the front of the lines
		Circle1.toFront();
		Circle2.toFront();
		Circle3.toFront();
		Circle4.toFront();
		Circle5.toFront();
		Circle6.toFront();
		//for the timer
		layout = new Label();
		layout.setText("T: 0");
		doTime();
		gr1.getChildren().addAll(layout);

		num_of_colors = new Paint[6]; //An array to hold the used colors.
		//adding all circles to an array, to calculate the colors used by the user
		list = new ArrayList<Circle>();
		list.add(Circle1);
		list.add(Circle2);
		list.add(Circle3);
		list.add(Circle4);
		list.add(Circle5);
		list.add(Circle6);

		// ADDING THE COLOR PICKER
		colorPicker = new ColorPicker();
		colorPicker.setValue(Color.TRANSPARENT);
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

					else if (mouseEvent.getButton() == MouseButton.SECONDARY && checkAdj(multi, temp_i, colorPicker.getValue())){
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
		end = new Button("FINISH");
		end.setPrefWidth(80);
		end.setPrefHeight(40);
		end.setOnMousePressed(e -> {
			lastTime = seconds;
			if(getNumColors() == getChrNum()) {
				if(CheckColors(multi)){
					time.stop();
					setGameOver();
					seconds = starttime;
					window.close();
				}
				else{
					text.setText(" YOU HAVEN'T COLORD EVERY VERTICE");
					text.setFill(Color.RED);
				}
			}
			else{
				text.setText("YOU HAVEN'T REACHED THE MINIMUM AMOUNT OF COLORS, TRY AGAIN");
				text.setFill(Color.RED);
			}
		});


		end.setStyle("-fx-background-color: #e6e6e6");
		end.setOnMouseEntered(e -> end.setStyle("-fx-background-color: #2EE59D;"));
		end.setOnMouseExited(e -> end.setStyle("-fx-background-color: #e6e6e6"));

		pane.add(end, 8,0,1,1);
		pane.add(text,5,1,1,1);

		scene1 = new Scene(vbox, 900, 800);
		window.setScene(scene1);
		window.setTitle("Graph Coloring Game");
		window.show();
	}
}

