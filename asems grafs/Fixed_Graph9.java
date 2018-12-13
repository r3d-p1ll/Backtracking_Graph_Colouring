package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Fixed_Graph9 {

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

	public static int getChrNum(){
		return chrNum;
	}

	private static void setGameOver(){
		gameOverWindow = new Stage();
		GridPane grid = new GridPane();
		gameOverWindow.setTitle("Time's up!");
		timeUsed = new Label("Time left: ");
		GridPane.setConstraints(timeUsed,2,2);
		Label realChroma = new Label("Chromatic number: 4");
		chromaUsed = new Label("Chromatic reached:   " + getNumColors());
		GridPane.setConstraints(chromaUsed, 2,4);
		GridPane.setConstraints(realChroma, 2, 3);
		grid.getChildren().addAll(timeUsed, realChroma, chromaUsed);
		Scene gameOverScene = new Scene(grid);
		gameOverWindow.setScene(gameOverScene);
		gameOverWindow.show();
	}

	private static void doTime() {
		Timeline time = new Timeline();
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
				{0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
				{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
				{0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
				{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
				{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0},
		};

		Circle Circle1 = createCircle(385, 41, 15, Color.WHITE);
		Circle Circle2 = createCircle(213, 99, 15, Color.WHITE);
		Circle Circle3 = createCircle(118, 251, 15, Color.WHITE);
		Circle Circle4 = createCircle(145, 430, 15, Color.WHITE);
		Circle Circle5 = createCircle(282, 549, 15, Color.WHITE);
		Circle Circle6 = createCircle(463, 553, 15, Color.WHITE);
		Circle Circle7 = createCircle(605, 442, 15, Color.WHITE);
		Circle Circle8 = createCircle(642, 265, 15, Color.WHITE);
		Circle Circle9 = createCircle(554, 108, 15, Color.WHITE);
		Circle Circle10 = createCircle(548, 194, 15, Color.WHITE);
		Circle Circle11 = createCircle(485, 189, 15, Color.WHITE);
		Circle Circle12 = createCircle(426, 156, 15, Color.WHITE);
		Circle Circle13 = createCircle(198, 214, 15, Color.WHITE);
		Circle Circle14 = createCircle(225, 270, 15, Color.WHITE);
		Circle Circle15 = createCircle(225, 338, 15, Color.WHITE);
		Circle Circle16 = createCircle(390, 504, 15, Color.WHITE);
		Circle Circle17 = createCircle(426, 452, 15, Color.WHITE);
		Circle Circle18 = createCircle(485, 419, 15, Color.WHITE);
		Circle Circle19 = createCircle(426, 385, 15, Color.WHITE);
		Circle Circle20 = createCircle(426, 223, 15, Color.WHITE);
		Circle Circle21 = createCircle(284, 304, 15, Color.WHITE);
		Circle Circle22 = createCircle(339, 304, 15, Color.WHITE);
		Circle Circle23 = createCircle(398, 338, 15, Color.WHITE);
		Circle Circle24 = createCircle(398, 270, 15, Color.WHITE);


		Line line1 = connect(Circle1, Circle2);
		Line line2 = connect(Circle2, Circle3);
		Line line3 = connect(Circle3, Circle4);
		Line line4 = connect(Circle4, Circle5);
		Line line5 = connect(Circle5, Circle6);
		Line line6 = connect(Circle6, Circle7);
		Line line7 = connect(Circle7, Circle8);
		Line line8 = connect(Circle8, Circle9);
		Line line9 = connect(Circle9, Circle1);
		Line line10 = connect(Circle11, Circle12);
		Line line11 = connect(Circle12, Circle20);
		Line line12 = connect(Circle20, Circle11);
		Line line13 = connect(Circle22, Circle23);
		Line line14 = connect(Circle23, Circle24);
		Line line15 = connect(Circle24, Circle22);
		Line line16 = connect(Circle17, Circle18);
		Line line17 = connect(Circle18, Circle19);
		Line line18 = connect(Circle19, Circle17);
		Line line19 = connect(Circle14, Circle15);
		Line line20 = connect(Circle15, Circle21);
		Line line21 = connect(Circle21, Circle14);
		Line line22 = connect(Circle20, Circle24);
		Line line23 = connect(Circle19, Circle23);
		Line line24 = connect(Circle21, Circle22);
		Line line25 = connect(Circle10, Circle11);
		Line line26 = connect(Circle16, Circle17);
		Line line27 = connect(Circle13, Circle14);
		Line line28 = connect(Circle9, Circle10);
		Line line29 = connect(Circle10, Circle8);
		Line line30 = connect(Circle6, Circle16);
		Line line31 = connect(Circle16, Circle5);
		Line line32 = connect(Circle2, Circle13);
		Line line33 = connect(Circle13, Circle3);
		Line line34 = connect(Circle1, Circle12);
		Line line35 = connect(Circle7, Circle18);
		Line line36 = connect(Circle4, Circle15);


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
		gr1.getChildren().add(Circle16);
		gr1.getChildren().add(Circle17);
		gr1.getChildren().add(Circle18);
		gr1.getChildren().add(Circle19);
		gr1.getChildren().add(Circle20);
		gr1.getChildren().add(Circle21);
		gr1.getChildren().add(Circle22);
		gr1.getChildren().add(Circle23);
		gr1.getChildren().add(Circle24);

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
		gr1.getChildren().add(line27);
		gr1.getChildren().add(line28);
		gr1.getChildren().add(line29);
		gr1.getChildren().add(line30);
		gr1.getChildren().add(line31);
		gr1.getChildren().add(line32);
		gr1.getChildren().add(line33);
		gr1.getChildren().add(line34);
		gr1.getChildren().add(line35);
		gr1.getChildren().add(line36);

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

		//for the timer
		layout = new Label();
		layout.setText("T: 0");
		doTime();
		gr1.getChildren().addAll(layout);

		num_of_colors = new Paint[6]; //An array to hold the used colors.
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

		scene1 = new Scene(vbox, 900, 800);
		window.setScene(scene1);
		window.setTitle("Graph Coloring Game");
		window.show();
	}
}

