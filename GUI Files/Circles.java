package sample;

import javafx.scene.shape.Circle;

public class Circles {
    Circle Circle1 = new Circle();
    int posx;
    int posy;

    public Circles(int posx, int posy){
        this.posx = posx;
        this.posy = posy;
    }
    public int getPosx(){
        return posx;
    }
    public int getPosy(){
        return posy;
    }
}
