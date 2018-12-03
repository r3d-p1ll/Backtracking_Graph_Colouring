package sample;

import javafx.scene.shape.Circle;

public class Force_Directed_Alg {
    static Circle [] nodes;
    static double posx;
    static double posy;

    public static void display(Circle vertex, int num_nodes, double posx, double posy) {
        double spring_base = 30; // spring rest length
        double repulsion = 10; // repulsive force constant
        double K_s = 5; // spring constant
        double stepsize = 0.0005; // time step

        double[] force_x = new double[num_nodes];
        double[] force_y = new double[num_nodes];

        nodes = new Circle[num_nodes];
        for (int i=0; i<num_nodes; i++){
            nodes[i]=vertex;
        }

        // initialize net forces
        for (int i = 0; i < num_nodes; i++) {
            force_x[i] = 0.0;
            force_y[i] = 0.0;
            for (int j = 0; j < num_nodes; j++) {
                if (i!=j) {
                    double deltax = nodes[j].getCenterX() - nodes[i].getCenterX();
                    double deltay = nodes[j].getCenterY() - nodes[i].getCenterY();
                    double d2 = (deltax * deltax) + (deltay * deltay);

                    if (d2 < 1) {
                        deltax = 0.1 * Math.random() + 1;
                        deltay = 0.1 * Math.random() + 1;
                        d2 = (deltax * deltax) + (deltay * deltay);
                    }
                    double distance = Math.sqrt(d2);

                    if (distance < 400) {
                        force_x[i] = (repulsion / d2) * deltax;
//                        System.out.println(force_x[i]);
                        force_y[i] = (repulsion / d2) * deltay;
//                        System.out.println(force_x[i]);
                    }
                    else {
                        force_x[i] += (distance - spring_base) * deltax;
                        force_y[i] += (distance - spring_base) * deltay;
                    }
                }
            }
        }
        for (int i=0; i<num_nodes; i++){
            nodes[i].setCenterX(nodes[i].getCenterX()+force_x[i] * stepsize);
            nodes[i].setCenterY(nodes[i].getCenterY()+force_y[i] * stepsize);
        }
    }
    public static double getX(){
        for (int i=0; i< nodes.length; i++){
            posx = nodes[i].getCenterX();
        }
        return posx;
    }
    public static double getY(){
        for (int i=0; i< nodes.length; i++){
            posy = nodes[i].getCenterY();
        }
        return posy;
    }
}