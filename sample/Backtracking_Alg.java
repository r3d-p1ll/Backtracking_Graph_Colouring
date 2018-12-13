
        package sample;
public class Backtracking_Alg {

    /// !!! BACKTRACKING ALGORITHM !!! ///
    // Use matrix to compare vertices.
    private static int chrom_number;

    public static void display(int [][] adj_matrix, int n, int m){
        // Check it's a complete graph. If yes - print the amount of vertices (which is equal to the chromatic number) and stop the program.
        if ((n*(n-1))/2 == m){
            System.out.println("This a complete graph and the chromatic number is equal to the number of vertices. This graph has " + n + " vertices.");
        }

        // User input for the amount of colours we're testing with.
        else{
            int number_colours = n;
            graphColouring(adj_matrix, number_colours);
        }
    }

    // THIS METHOD WOULD RETURN FALSE IF THE COLOURS ARE NOT ENOUGH TO COLOUR THE GRAPH.
    // OTHERWISE IT WOULD PRINT THE SOLUTION (AT LEAST ONE OF THE POSSIBLE SOLUTIONS).
    public static boolean graphColouring(int adj_matrix[][], int number_colours) {
        // Create a colour matrix where we assign colour values to each vertex. Initial values set to 0.
        int [][] colour_table = new int [2][adj_matrix.length];
        int f = 0;
        for (int j = 0; j < colour_table[0].length; j++){
            f+=1;
            colour_table[0][j] += f;
        }
        // Call vertRecursion for the first vertex.
        if (!vertRecursion(adj_matrix, number_colours, colour_table, 0)){
            System.out.println(number_colours + " colours are not enough. More colours needed!");
            return false;
        }
        // If assigning the colours was successful, print solution.
        else{
            System.out.println("Coloring successful using " + number_colours + " colours in the following way (starting from vertex 1): ");
            System.out.println(colour_table[0].length);
            for (int j = 0; j < colour_table[1].length; j++){
                System.out.print(colour_table[1][j] + " ");
            }

            chrom_number = 0;
            for (int i=0; i<colour_table[1].length; i++){
                if (chrom_number < colour_table[1][i]){
                    chrom_number = colour_table[1][i];
                }
            }
            System.out.println();
            System.out.println("CHROMATIC NUMBER " + chrom_number);
            System.out.println();
        }
        return true;
    }

    // RECURSIVE METHOD TO HELP SOLVE THE M-COLOURING PROBLEM. IF SOLUTION IS NOT POSSIBLE, METHOD RETURNS FALSE.
    public static boolean vertRecursion (int adj_matrix[][], int number_colours, int colour_table[][], int v) {
        // Base case for when the vertex that we're testing
        if (v > adj_matrix.length-1)
            return true;

        for (int c = 1; c <= number_colours; c++){
            // Check if we can assign colour c to vertex v
            if (checkAdjVert(v, adj_matrix, colour_table, c)){
                colour_table[1][v] = c;

                // Recursion for all v + 1 values
                if (vertRecursion(adj_matrix, number_colours, colour_table, v + 1)){
                    return true;
                }
                // Set the colour value back to 0 if we can't assign this colour to the vertex
                else{
                    colour_table[1][v] = 0;
                }
            }
        }
        return false;
    }

    // CHECK ADJACENCY AND IF THE COLOUR IS SAFE
    public static boolean checkAdjVert (int v, int adj_matrix[][], int colour_table[][], int c) {
        for (int i = 0; i < adj_matrix.length; i++){
            if (adj_matrix[v][i] == 1 && c == colour_table[1][i])
                return false;
        }
        return true;
    }

    public static int getChromNumber(){
        return chrom_number;
    }
}
