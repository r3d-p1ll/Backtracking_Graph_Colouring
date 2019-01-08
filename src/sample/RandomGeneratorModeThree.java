
        package sample;

        import java.util.Random;

        /**
         * this class makes a random graph, by making a random adjacency matrix
         */
        public class RandomGeneratorModeThree {
    static int [] arrayRandom;

            /**
             * it will make a random adj matrix
             * @param vert this reads in the amount of vertices the player types in
             * @param edge this reads in the amount of edges the player types in
             */
    public static void random_gen(int vert, int edge){

        Random rand = new Random();
        int v = vert;
        int e = edge;
        int [][] adjArray = new int[v][v] ;

        /** If the user gives 2 to the amount of vertices and 1 to the amount of edges, it will make a adj matrix for the combination only
         * because this combination is an exception on the rest of the code
         */
        if (v == 2 & e == 1) {
            System.out.println("2 and 1");
            adjArray[0][1] = 1;
            adjArray[1][0] = 1;
            System.out.println("edgeArray result:");
            for (int m = 0; m < adjArray.length; m++) {
                for (int n = 0; n < adjArray[0].length; n++) {
                    System.out.print(adjArray[m][n] + " ");
                }
                System.out.println();
            }
            Random_Graphs.display("Graph Coloring Game", "", adjArray);
            Backtracking_Alg.display(adjArray, v, e);


        }
        else {


            int count = 1;
            int redo = 1;
            if (e <= ((v * (v - 1)) / 2)) {
                if (e >= v - 1) {
                    while (redo < e) {
                        while (count <= e) {
                            int x = rand.nextInt((((v - 1) - 0) + 1) + 0);
                            int y = rand.nextInt((((v - 1) - 0) + 1) + 0);
                            redo = e - 1; //base case.
                            if (adjArray[x][y] == 0 && adjArray[y][x] != 1) {
                                if (adjArray[y][x] != 1) {
                                    if (x != y) {
                                        count++;
                                        adjArray[x][y] = 1;
                                        adjArray[y][x] = 1;
                                    }
                                }
                            }
                        }
                        if (checkZero(adjArray)) {
                            System.out.println(" ONE VERTICE WAS NOT CONNECTED, please try again");
                            count = 1;
                            for (int i = 0; i < adjArray.length; i++) {
                                for (int j = 0; j < adjArray[0].length; j++) {
                                    adjArray[i][j] = 0;
                                }
                            }
                            redo = 1; //recursive call.
                        } else {
                            redo++; //stop condition.
                            System.out.println("edgeArray result:");
                            for (int m = 0; m < adjArray.length; m++) {
                                for (int n = 0; n < adjArray[0].length; n++) {
                                    System.out.print(adjArray[m][n] + " ");
                                }
                                System.out.println();
                            }
                            Random_Graphs.display("Graph Coloring Game", "", adjArray);
                            Backtracking_Alg.display(adjArray, v, e);
                        }
                    }
                } else {
                    System.out.println("too few edges");
                }
            } else {
                System.out.println("too many edges");
            }
        }
    }

            /**
             * it checks if not one vertices is loose of the graph. so it goes trough the empty rows and checks if the
             * matching column is also 0
             * @param array takes in the adjacency matrix
             * @return true if there is a loose vertice, returns false if every vertice is connected
             */
    public static boolean checkZero(int array[][]){
        int sumrow =0;
        int sumcolumn =0;
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
                sumrow += array[i][j];
            }
            if(sumrow == 0){
                int k = i;
                for( int l =0; l< array.length ; l++){
                    sumcolumn += array[l][k];
                }
                if(sumcolumn == 0){
                    return true;
                }
                sumcolumn = 0;
            }
            else{
                sumrow =0;
            }
        }

        return false;
    }

}

