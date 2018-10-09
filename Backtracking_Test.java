import java.io.*;
import java.util.*;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class Backtracking_Test
		{
		
		public final static boolean DEBUG = true;
		
		public final static String COMMENT = "//";
		
		public static void main( String args[] )
			{
			if( args.length < 1 )
				{
				System.out.println("Error! No filename specified.");
				System.exit(0);
				}

				
			String inputfile = args[0];
			
			boolean seen[] = null;
			
			//! n is the number of vertices in the graph
			int n = -1;
			
			//! m is the number of edges in the graph
			int m = -1;
			
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try 	{ 
			    	FileReader fr = new FileReader(inputfile);
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
			    System.out.println("Error! Problem reading file "+inputfile);
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
			int [][] adj_matrix = new int [n+1][n+1];

			for (int i = 0; i < e.length; i++){
				adj_matrix[e[i].u][e[i].v] +=1;
				adj_matrix[e[i].v][e[i].u] +=1;
			}

			// This is the number of colours we're testing with. 
			// It's a fixed value, meaning that each time we would like to test a graph for less or more colours, this needs to be changed manually.
			int number_colours = 7;

		    graphColouring(adj_matrix, number_colours);
		}

		// CHECK ADJACENCY AND IF THE COLOUR IS SAFE
		public static boolean checkAdjVert (int v, int adj_matrix[][], int colour_table[][], int c)
		{
		    for (int i = 1; i <= adj_matrix.length-1; i++){
		        if (adj_matrix[v][i] == 1 && c == colour_table[1][i-1])
		            return false;
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
		            colour_table[1][v-1] = c;
		 
		            // Recursion for all v + 1 values
		            if (vertRecursion(adj_matrix, number_colours, colour_table, v + 1)){
		                return true;
		            }
		            // Set the colour value back to 0 if we can't assign this colour to the vertex
		            else{
		            	colour_table[1][v-1] = 0;
		            }
		        }
		    }
		    return false;
		}

		// THIS METHOD WOULD RETURN FALSE IF THE COLOURS ARE NOT ENOUGH TO COLOUR THE GRAPH.
		// OTHERWISE IT WOULD PRINT THE SOLUTION (AT LEAST ONE OF THE POSSIBLE SOLUTIONS).
		public static boolean graphColouring(int adj_matrix[][], int number_colours)
		{
			// Create a colour matrix where we assign colour values to each vertex. Initial values set to 0.
			int [][] colour_table = new int [2][adj_matrix.length-1];
			int f = 0;
			for (int j = 0; j < colour_table[0].length; j++){
				f+=1;
				colour_table[0][j] += f;
			}

		    // Call vertRecursion for the first vertex.
		    if (!vertRecursion(adj_matrix, number_colours, colour_table, 1)){
		        System.out.println(number_colours + " colours are not enough. More colours needed!");
		        return false;
		    }

		    // If assigning the colours was successful, print solution.
		    else{
		    	System.out.println("Coloring successful using " + number_colours + " colours in the following way (first row is the number of the vertex, second row is the colour it has):");
				for (int i = 0; i < colour_table.length; i++){
					for (int j = 0; j < colour_table[i].length; j++){
						System.out.print(colour_table[i][j] + " ");
					}
					System.out.println();
				}
		    }
		    return true;
		 }
}
