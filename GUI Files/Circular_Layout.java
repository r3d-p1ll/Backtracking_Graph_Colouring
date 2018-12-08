package sample;

public class Circular_Layout {
    public static void display(Circles [] cir, int num_vert)
    {

        // Moves the vertices to build a circle. Makes sure the
        // radius is large enough for the vertices to not
        // overlap

            // Gets all vertices inside the parent and finds
            // the maximum dimension of the largest vertex
            double max = 0;
            Double top = null;
            Double left = null;

            for (int i = 0; i < num_vert; i++)
            {
                top = cir[i].Circle1.getCenterY();
                left = cir[i].Circle1.getCenterX();
                max = Math.max(max, Math.max(Random_Graphs.getWidth(), Random_Graphs.getHeight()));
            }

            double r = Math.max(num_vert * max / Math.PI, 15);
            circle(cir, r, left.doubleValue(), top.doubleValue());
    }

    /**
     * Executes the circular layout for the specified array
     * of vertices and the given radius.
     */
    public static void circle(Circles[] vertices, double r, double left, double top)
    {
        int vertexCount = vertices.length;
        double phi = (2 * Math.PI) / vertexCount;

        for (int i = 0; i < vertexCount; i++) {
            vertices[i].Circle1.setCenterX(((left + r + r * Math.sin(i * phi)))/(vertexCount+Math.PI));
            vertices[i].Circle1.setCenterY(((top + r + r * Math.cos(i * phi)))/(vertexCount+Math.PI));
        }
    }
}