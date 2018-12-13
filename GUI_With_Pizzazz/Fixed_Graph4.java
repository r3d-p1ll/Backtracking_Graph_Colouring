package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class Fixed_Graph4 {

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
	private final static int chrNum = 2;
	static ArrayList<Circle> list;
	static Button end;
	static int lastTime;
	private static Timeline time;
	static int[][] multi;

	private static Label hintLabel;

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

	private static Circle createCircle(double x, double y, double r, Color color, Integer colorAttached)
	{
		Circle circle = new Circle(x, y, r, color);
		circle.setStrokeWidth(4);
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.TRANSPARENT);
		circle.setStrokeWidth(1.6);
		circle.setCursor(Cursor.CROSSHAIR);
		circle.setOnMousePressed((t) ->
		{
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();

			Circle c = (Circle) (t.getSource());
			c.toFront();

			if(t.getClickCount() == 2) {
				Stage hintWindow = new Stage();
				VBox hint = new VBox();
				Label hintExplain = new Label("Colors surrounding this vertice:");
				hintLabel = new Label();
				hintLabel.setText(Integer.toString(colorAttached));
				hintLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
				hint.setAlignment(Pos.CENTER);
				hint.getChildren().addAll(hintExplain,hintLabel);
				Scene hintScene = new Scene(hint);
				hintWindow.setScene(hintScene);
				hintWindow.showAndWait();
			}
		});
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
		Button buttonhint = new Button("HELP");
		pane.add(buttonhint, 5,0,1,1);
		buttonhint.setOnAction(e ->  Hint.display("Hint", "Need help?", getNumColors(), getChrNum()));
		buttonhint.setPrefWidth(80);
		buttonhint.setPrefHeight(40);
		buttonhint.setStyle("-fx-background-color: #e6e6e6");
		buttonhint.setOnMouseEntered(e -> buttonhint.setStyle("-fx-background-color: #2EE59D;"));
		buttonhint.setOnMouseExited(e -> buttonhint.setStyle("-fx-background-color: #e6e6e6"));




		multi = new int[][]{

				{ 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
				{ 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0 },
		};

		Circle Circle1 = createCircle(300, 50, 15, Color.WHITE,1);
		Circle Circle2 = createCircle(500, 50, 15, Color.WHITE,1);
		Circle Circle3 = createCircle(600, 200, 15, Color.WHITE,1);
		Circle Circle4 = createCircle(600, 400, 15, Color.WHITE,1);
		Circle Circle5 = createCircle(500, 500, 15, Color.WHITE,1);
		Circle Circle6 = createCircle(300, 500, 15, Color.WHITE,1);
		Circle Circle7 = createCircle(200, 400, 15, Color.WHITE,1);
		Circle Circle8 = createCircle(200, 200, 15, Color.WHITE,1);
		Circle Circle9 = createCircle(350, 200, 15, Color.WHITE,1);
		Circle Circle10 = createCircle(450, 200, 15, Color.WHITE,1);
		Circle Circle11 = createCircle(500, 250, 15, Color.WHITE,1);
		Circle Circle12 = createCircle(500, 350, 15, Color.WHITE,1);
		Circle Circle13 = createCircle(450, 400, 15, Color.WHITE,1);
		Circle Circle14 = createCircle(350, 400, 15, Color.WHITE,1);
		Circle Circle15 = createCircle(300, 350, 15, Color.WHITE,1);
		Circle Circle16 = createCircle(300, 250, 15, Color.WHITE,1);



		Line line1 = connect(Circle1, Circle10);
		Line line2 = connect(Circle2, Circle11);
		Line line3 = connect(Circle3, Circle12);
		Line line4 = connect(Circle4, Circle13);
		Line line5 = connect(Circle5, Circle14);
		Line line6 = connect(Circle6, Circle15);
		Line line7 = connect(Circle7, Circle16);
		Line line8 = connect(Circle8, Circle9);
		Line line9 = connect(Circle10, Circle15);
		Line line10 = connect(Circle10, Circle13);
		Line line11 = connect(Circle11, Circle16);
		Line line12 = connect(Circle11, Circle14);
		Line line13 = connect(Circle12, Circle9);
		Line line14 = connect(Circle12, Circle15);
		Line line15 = connect(Circle13, Circle16);
		Line line16 = connect(Circle14, Circle9);
		Line line17 = connect(Circle1, Circle2);
		Line line18 = connect(Circle2, Circle3);
		Line line19 = connect(Circle3, Circle4);
		Line line20 = connect(Circle4, Circle5);
		Line line21 = connect(Circle5, Circle6);
		Line line22 = connect(Circle6, Circle7);
		Line line23 = connect(Circle7, Circle8);
		Line line24 = connect(Circle8, Circle1);



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
		Circle10.toFront();
		Circle11.toFront();
		Circle12.toFront();
		Circle13.toFront();
		Circle14.toFront();
		Circle15.toFront();
		Circle16.toFront();

		//for the timer
		layout = new Label();
		layout.setText("T: 0");
		doTime();
		gr1.getChildren().addAll(layout);

		num_of_colors = new Paint[16]; //An array to hold the used colors.
		//adding all circles to an array, to calculate the colors used by the user
		list = new ArrayList<Circle>();
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

