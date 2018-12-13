
        package sample;

        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.input.KeyCode;
        import javafx.scene.layout.GridPane;
        import javafx.stage.Modality;
        import javafx.stage.Stage;

public class TimeChoiceBox {
    public static void display (String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(10);
        layout.setVgap(10);

        Label label = new Label(message);
        GridPane.setConstraints(label,0,0);

        TextField num_graph = new TextField();
        num_graph.setPromptText("Enter Graph number 1-10");
        GridPane.setConstraints(num_graph, 0 ,2);

        Button closeButton = new Button("Go!");
        closeButton.setOnAction(e -> {
            if(isInt(num_graph)){
                Timed_Graphs.choose_graph(Integer.parseInt(num_graph.getText()));
                e.consume();
                window.close();
            }
        });
        layout.setOnKeyPressed((event) -> { if(event.getCode() == KeyCode.ENTER) closeButton.fire(); });
        GridPane.setConstraints(closeButton, 4, 3);

        layout.getChildren().addAll(label, closeButton, num_graph);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static boolean isInt(TextField input1){
        try{
            Integer.parseInt(input1.getText());
            return true;
        }
        catch (NumberFormatException e){
            System.out.println("Not a number, please enter digits");
            return false;
        }
    }
}
