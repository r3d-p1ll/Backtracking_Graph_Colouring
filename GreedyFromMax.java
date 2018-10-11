import java.io.*;
import java.util.*;
import java.util.Arrays;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class GreedyFromMax

{
	public final static boolean DEBUG = true;

	public final static String COMMENT = "//";

	public static void main(String args[]) {
		if (args.length < 1) {
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

		try {
			FileReader fr = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(fr);

			String record = new String();

			//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
			//! These comments are only allowed at the top of the file.

			//! -----------------------------------------
			while ((record = br.readLine()) != null) {
				if (record.startsWith("//")) continue;
				break; // Saw a line that did not start with a comment -- time to start reading the data in!
			}

			if (record.startsWith("VERTICES = ")) {
				n = Integer.parseInt(record.substring(11));
				if (DEBUG) System.out.println(COMMENT + " Number of vertices = " + n);
			}

			seen = new boolean[n + 1];

			record = br.readLine();

			if (record.startsWith("EDGES = ")) {
				m = Integer.parseInt(record.substring(8));
				if (DEBUG) System.out.println(COMMENT + " Expected number of edges = " + m);
			}

			e = new ColEdge[m];

			for (int d = 0; d < m; d++) {
				if (DEBUG) System.out.println(COMMENT + " Reading edge " + (d + 1));
				record = br.readLine();
				String data[] = record.split(" ");
				if (data.length != 2) {
					System.out.println("Error! Malformed edge line: " + record);
					System.exit(0);
				}
				e[d] = new ColEdge();

				e[d].u = Integer.parseInt(data[0]);
				e[d].v = Integer.parseInt(data[1]);

				seen[e[d].u] = true;
				seen[e[d].v] = true;

				if (DEBUG) System.out.println(COMMENT + " Edge: " + e[d].u + " " + e[d].v);

			}

			String surplus = br.readLine();
			if (surplus != null) {
				if (surplus.length() >= 2) if (DEBUG)
					System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '" + surplus + "'");
			}

		} catch (IOException ex) {
			// catch possible io errors from readLine()
			System.out.println("Error! Problem reading file " + inputfile);
			System.exit(0);
		}

		for (int x = 1; x <= n; x++) {
			if (seen[x] == false) {
				if (DEBUG)
					System.out.println(COMMENT + " Warning: vertex " + x + " didn't appear in any edge : it will be considered a disconnected vertex on its own.");
			}
		}
/**
 * code begins here.
 */

		int[] color = new int[n];
		int count = 1;

		

		boolean[] available = new boolean[n];

		//Set all colors available.
		for (int i = 0; i < n; i++) {
			available[i] = true;
		}

		int[][] adj_Matrix = new int[n + 1][n + 1];
		for (int i = 0; i < e.length; i++) {
			adj_Matrix[e[i].u][e[i].v] += 1;
			adj_Matrix[e[i].v][e[i].u] += 1;
		}

		/**
		 * Ignore this part for now this is the optimization part.
		 */
//		Sum the row of the adjacency matrix in order to find the least-edge vertix.
		int[] sumRowAdj = new int[n + 1];
		for(int i = 0; i<adj_Matrix.length;i++){
			int total = 0;
			for (int j = 0; j < adj_Matrix[0].length; j++){
				total += adj_Matrix[i][j];
			}
			sumRowAdj[i] = total;
		}

		int maxIncident = sumRowAdj[0]; //
		for (int k = 1; k < sumRowAdj.length; k++) {
			if (sumRowAdj[k - 1] < sumRowAdj[k]) {
				maxIncident = sumRowAdj[k];
			}
		}
		
		e[maxIncident].v = 1;
		
		for (int y = 1; y < n; y++) {
			//counter starts here
			int cr;
			for (cr = 0; cr < n - 1; cr++) {
				if (available[cr])
					break;
			}

			/*
			This is where most of the error is.,  I plan to implement the minIncident here, but I want to fix this first.
			 */
			adj_Matrix[y][cr] = 1;
			for (int i = 1; i < adj_Matrix.length; i++) {
				if (adj_Matrix[y][i] == 1) {
					color[y]++;
//					count++;
				}
			}

			//Count goes here.
			count = Math.max(color[y - 1], color[y]);

			//Set all colors to available again
			for (int j = 0; j < n; j++) {
				available[j] = true;
			}
			cr++;//redundant?
		}
		System.out.println("X(G)=" + count + "  with the Greedy Algorithm");
	}

}