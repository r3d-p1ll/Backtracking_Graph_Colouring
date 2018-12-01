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

    static int n;

    public static void choose_graph(Integer graph){
        n = graph;
        if(n == 1){Fixed_Graph1.display("Fixed Graph", "GoodLuck!");}
        if(n == 7){Fixed_Graph7.display("Fixed Graph", "GoodLuck!");}
    }

}
