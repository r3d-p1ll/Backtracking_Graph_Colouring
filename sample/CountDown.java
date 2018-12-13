


        package sample;

        import javafx.animation.KeyFrame;
        import javafx.animation.Timeline;
        import javafx.application.Application;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Insets;
        import javafx.scene.layout.GridPane;
        import javafx.stage.Stage;
        import javafx.util.Duration;
        import javafx.scene.Group;
        import javafx.scene.Scene;
        import javafx.scene.control.Label;
        import javafx.scene.layout.HBox;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Font;


public class CountDown extends Application {

    Label layout;
    Stage windows;
    //copy this last three instance variable.
    Stage gameOver;
    private final Integer starttime=10; //edit how long the timer is from here.
    private Integer seconds= starttime;

    @Override
    public void start(Stage primaryStage) {
        windows= primaryStage;
        Group root= new Group();
        layout = new Label();

        //This is the part that you may need to add.....
        layout.setText("T minus: 10");
        layout.setFont(Font.font(24));
        layout.setTextFill(Color.RED);

        doTime();

        //for the initial countdown.
        HBox layout= new HBox(5);
        layout.getChildren().add(this.layout);
        root.getChildren().add(layout);
        Scene scene= new Scene(root, 300,70, Color.BLACK); //
        windows.setScene(scene);
        windows.show();

    }

    //copy this and call it the game mode.
    private void doTime() {
        Timeline time= new Timeline();


        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {


                seconds--;
                layout.setText("t-minus: "+seconds.toString());
                if(seconds<=0){
                    time.stop();
                    windows.close(); //I close the the window.  This can be edited at anytime depending what the game over screen should look like.
//                    gameOver.show(); //Something went wrong with my layout for the game over window.  I'll work on it later.  Ignore this for now.
                }

            }


        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if(time!= null){
            time.stop();
        }
        time.play();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
