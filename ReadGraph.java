import java.io.*;
import java.util.*;
import java.util.Arrays;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class ReadGraph

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
/**
 * code begins here.
 */

  int [] color = new int[1000];
  int count = 1;


//  for(int i = 1; i < n; i++) {
//	  e[i].v = 1;
//	  e[i].u = 1;
//  }

  e[0].v = 1;

  boolean[] available = new boolean[1000];

  for (int i = 0; i < n; i++) {
	  available[i] = true;
  }

  int [][] adj_matrix = new int[1000][1000];
				for (int i = 0; i < e.length; i++){
					adj_matrix[e[i].u][e[i].v] +=1;
					adj_matrix[e[i].v][e[i].u] +=1;
				}
  for(int u = 1; u < n - 1; u++) {
	  int[][] adjacencyMatrix = new int[1000][1000];
	  e[u].v = 0;
	  int startingPoint;


	  int[] sumRowAdj = new int[1000];
	  sumRowAdj[u] = 0;
	  for (int j = 0; j < sumRowAdj.length; j++) {
		  sumRowAdj[j] += adj_matrix[u][j];
	  }

	  int minIncident = sumRowAdj[1000];
	  for (int k = 1; k < sumRowAdj.length; k++) {
		  if (sumRowAdj[k] < minIncident) {
			  minIncident = sumRowAdj[k];
		  }
	  }


	  // Find the first available color
	  /**
	   * Counter starts here
	   */
	  int cr;
	  for (cr = 0; cr < n; cr++) {
		  if (available[cr])
			  break;
	  }

	  /**
	   * counter not updating
	   */

	//starting point

	  color[minIncident] = cr;

	  if(color[e[u].v] == color[e[u].u]){
		  color[e[u].u]++;
		  count++;
	  }
//	  if(color[u -1] < color[u] ) {
//		  count = color[u];
//	  }

	  for (int j = 0; j < n; j++) {
		  available[j] = true;
	  }

//	  start at one cause vertex of 1 is X(G)=1
//	  e[0].v = 0;

	  //Working one. Breaks at v=28, and bug at e%3.
//	  for (int i = 0; i < n; i++) {
//		  for(int j = 0; j < n; j++) {
//			  color[e[i].v] = 0;
//			  if (color[e[i].v] == color[e[j].u]) {
//				  e[i].u += 1;
//				  if(j<i){
//					  color[i]++;
//				  }
//				  else {
//					  color[j]++;
//				  }
//			  }
//
//
//		  }
//	  }
  }

	  System.out.println("Using a Greedy algorithm with modification of start location X(G)="+ count);
  }
}
