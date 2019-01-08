package sample;

/**
 * Class Circular_Layout is used change the graph layout to circular
 */
public class Circular_Layout {
    /**
     * display method to invoke the Circular_Layout
     * @param cir gets a list of Circles.
     * @param num_vert is the number of vertices.
     */
    public static void display(Circles [] cir, int num_vert)
    {
        double max = 0;
        double y = 0;
        double x = 0;

        for (int i = 0; i < num_vert; i++) {
            y = cir[i].Circle1.getCenterY();
            x = cir[i].Circle1.getCenterX();
            max = Math.max(max, Math.max(Random_Graphs.getWidth(), Random_Graphs.getHeight()));
        }

        double r = Math.max(num_vert * max / Math.PI, 15);
        circle(cir, r, x, y);
    }

    /**
     * Circle method - calculates and executes the circular layout for the specified array of vertices and the given radius.
     * @param vertices is the list of Circles
     * @param r radius
     * @param x x coordinate
     * @param y y coordinate
     */
    public static void circle(Circles[] vertices, double r, double x, double y)
    {
        int num_of_vertices = vertices.length;
        double phi = (2 * Math.PI) / num_of_vertices;
        for (int i = 0; i < num_of_vertices; i++) {
            vertices[i].Circle1.setCenterX(((x + r + r * Math.sin(i * phi)))/(num_of_vertices+Math.PI));
            vertices[i].Circle1.setCenterY(((y + r + r * Math.cos(i * phi)))/(num_of_vertices+Math.PI));
        }
    }
}