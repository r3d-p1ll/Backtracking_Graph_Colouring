package sample;

import java.io.*;
import java.util.*;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class Read_File_Backtracking
		{
			private static int chrom_number;
			public final static boolean DEBUG = true;
			static int [] arrayRandom;
			static int n;
			static int m;

			public final static String COMMENT = "//";
		
		public static void display( File file)
			{
//			if( args.length < 1 )
//				{
//				System.out.println("Error! No filename specified.");
//				System.exit(0);
//				}


//			String inputfile = args[0];
			
			boolean seen[] = null;
			
			//! n is the number of vertices in the graph
			n = -1;

			//! m is the number of edges in the graph
			m = -1;
			
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try 	{ 
			    	FileReader fr = new FileReader(file);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();
					
					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.
					
					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}
	
					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );					
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];	
						
					record = br.readLine();
					
					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );					
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					e = new ColEdge[m];	
												
					for( int d=0; d<m; d++)
						{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						e[d] = new ColEdge();
						
						e[d].u = Integer.parseInt(data[0]);
						e[d].v = Integer.parseInt(data[1]);

						seen[ e[d].u ] = true;
						seen[ e[d].v ] = true;
						
						if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
				
						}
									
					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
						}
					
					}
			catch (IOException ex)
				{ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+ file);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
				if( seen[x] == false )
					{
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}

			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n


			/// !!! BACKTRACKING ALGORITHM !!! ///

			// Build adjacency matrix to compare vertices.
			int [][] adj_matrix = new int [n][n];

			for (int i = 0; i < e.length; i++){
				adj_matrix[e[i].u-1][e[i].v-1] +=1;
				adj_matrix[e[i].v-1][e[i].u-1] +=1;
			}

			for (int i = 0; i < adj_matrix.length; i++){
				for (int j = 0; j < adj_matrix[i].length; j++){
					System.out.print(adj_matrix[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();

			// Check it's a complete graph. If yes - print the amount of vertices (which is equal to the chromatic number) and stop the program.
			if ((n*(n-1))/2 == m){
				System.out.println("This a complete graph and the chromatic number is equal to the number of vertices. This graph has " + n + " vertices.");
			}

			// User input for the amount of colours we're testing with.
			else{
				int number_colours = n;
		    	graphColouring(adj_matrix, number_colours);
				rand_order(n);
				Random_Graphs.display("Graph Coloring Game", "", adj_matrix, arrayRandom);
			}
			}

		// THIS METHOD WOULD RETURN FALSE IF THE COLOURS ARE NOT ENOUGH TO COLOUR THE GRAPH.
		// OTHERWISE IT WOULD PRINT THE SOLUTION (AT LEAST ONE OF THE POSSIBLE SOLUTIONS).
		public static boolean graphColouring(int adj_matrix[][], int number_colours)
		{
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
		public static boolean vertRecursion (int adj_matrix[][], int number_colours, int colour_table[][], int v)
		{
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
		public static boolean checkAdjVert (int v, int adj_matrix[][], int colour_table[][], int c)
		{
		    for (int i = 0; i < adj_matrix.length; i++){
		        if (adj_matrix[v][i] == 1 && c == colour_table[1][i])
		            return false;
		    }
		    return true;
		}

		public static void rand_order(int n){
			Random rand = new Random();
			arrayRandom = new int [n];
			int count = 1;

			while(count <= n) {
				for (int i = 1; i <= n; i++) {
					int x = rand.nextInt(n);

					if (arrayRandom[x] == 0) {
						arrayRandom[x] = i;
						count++;
					} else {
						i = i - 1;
					}
				}
			}
		}

		public static int getChromNumber(){
			return chrom_number;
		}

}
