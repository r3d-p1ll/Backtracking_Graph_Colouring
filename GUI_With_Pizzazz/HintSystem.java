//package sample;
//
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Line;
//import javafx.scene.shape.StrokeLineCap;
//
//import java.util.ArrayList;
//
//public class HintSystem {
//    static Circle circle1;
//    static Circle circle2;
//    static int countOfConnection
//    private int connectionCount {
//        if(c1)
//    }
//
//    private Line connect(Circle c1, Circle c2) {
//        Line line = new Line();
//
//        line.startXProperty().bind(c1.centerXProperty());
//        line.startYProperty().bind(c1.centerYProperty());
//
//        line.endXProperty().bind(c2.centerXProperty());
//        line.endYProperty().bind(c2.centerYProperty());
//
//        line.setStrokeWidth(2);
//        line.setStrokeLineCap(StrokeLineCap.BUTT);
//        line.getStrokeDashArray().setAll(1.0, 4.0);
//        this.circle1 = c1;
//        this.circle2 = c2;
//        return line;
//    }
//}
//    private static Line connect(Circle c1, Circle c2) {
//        Line line = new Line();
//
//        line.startXProperty().bind(c1.centerXProperty());
//        line.startYProperty().bind(c1.centerYProperty());
//
//        line.endXProperty().bind(c2.centerXProperty());
//        line.endYProperty().bind(c2.centerYProperty());
//
//        line.setStrokeWidth(2);
//        line.setStrokeLineCap(StrokeLineCap.BUTT);
//        line.getStrokeDashArray().setAll(1.0, 4.0);
//
//        return line;
//    }
//
//
//    Circle Circle1 = createCircle(100, 50, 20, Color.GREY);
//    Circle Circle2 = createCircle(220, 100, 20, Color.GREY);
//    Circle Circle3 = createCircle(440, 350, 20, Color.GREY);
//    Circle Circle4 = createCircle(360, 220, 20, Color.GREY);
//    Circle Circle5 = createCircle(580, 300, 20, Color.GREY);
//    Circle Circle6 = createCircle(290, 500, 20, Color.GREY);
//
//    Line line1 = connect(Circle1, Circle2);
//    Line line2 = connect(Circle1, Circle3);
//    Line line3 = connect(Circle1, Circle4);
//    Line line4 = connect(Circle2, Circle3);
//    Line line5 = connect(Circle4, Circle5);
//    Line line6 = connect(Circle4, Circle6);
//    Line line7 = connect(Circle5, Circle6);
//
//    //hint
//    ArrayList<Line> hint = new ArrayList<>();
//        hint.add(line1);
//                hint.add(line2);
//                hint.add(line3);
//                hint.add(line4);
//                hint.add(line5);
//                hint.add(line6);
//                hint.add(line7);
//                int[] connectionCount = new int[hintArraySize];
//                Iterator<Line> hintIterator = hint.iterator();
//        while(hintIterator.hasNext()) {
//        Line temp = hintIterator.next();
//        temp.
//        //use a data to generate the connection count.
//        //size should be how many vertices there are
//
//        }