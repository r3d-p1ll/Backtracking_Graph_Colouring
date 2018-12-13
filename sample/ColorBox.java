


        package sample;

        import javafx.event.*;
        import javafx.scene.Scene;
        import javafx.scene.control.ColorPicker;
        import javafx.geometry.Insets;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import javafx.scene.text.*;
        import javafx.stage.Stage;

public class ColorBox {
    Color color_holder = Color.TRANSPARENT;

    public void display(){
        Stage window = new Stage();
        window.setX(1000);
        window.setY(400);

        window.setTitle("ColorPicker");
        Scene scene = new Scene(new VBox(20), 400, 300);
        VBox layout = (VBox) scene.getRoot();
        layout.setPadding(new Insets(5, 5, 5, 5));
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.TRANSPARENT);

        Text text = new Text("Pick Your Color." + "\n" + "After that just click on vertex you'd like to color.");
        text.setFont(Font.font ("Verdana", 14));
        text.setFill(Color.BLACK);

        colorPicker.setOnAction((ActionEvent t) -> {color_holder = colorPicker.getValue();});

        layout.getChildren().addAll(text, colorPicker);

        window.setScene(scene);
        window.requestFocus();
        window.show();
    }

    public Color getValue(){
        return color_holder;
    }
}
