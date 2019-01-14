
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GcReader {
  public final static boolean DEBUG = true;

  public final static String COMMENT = "//";

  public static GcProblem read(String f) throws IOException {
    int n = -1; //number of vertices
    int m = -1; //number of edges
    String record = new String();

    BufferedReader br = new BufferedReader(new FileReader(f));

    String line;
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

    record = br.readLine();

    if( record.startsWith("EDGES = ")) {
      m = Integer.parseInt( record.substring(8) );
      if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
    }

    //read numbers
//    while ((line = br.readLine()).startsWith("[0-9]"));


    int nNodes = n;  //node = vertex
    List<Integer>[] nodeNeighborLists = new List[nNodes];
    for (int i = 0; i < nNodes; i++) {
      nodeNeighborLists[i] = new ArrayList<Integer>();
    }
    while ((line = br.readLine()) != null) {
      String[] tokens = line.split("\\s");
      tokens = line.split("\\s");
      int node1 = Integer.parseInt(tokens[0]) - 1;
      int node2 = Integer.parseInt(tokens[1]) - 1;
      nodeNeighborLists[node1].add(node2);
      nodeNeighborLists[node2].add(node1);
    }
    int[][] nodeNeighbors = new int[nNodes][];
    for (int i = 0; i < nNodes; i++) {
      nodeNeighbors[i] = new int[nodeNeighborLists[i].size()];
      for (int j = 0; j < nodeNeighborLists[i].size(); j++) {
        nodeNeighbors[i][j] = nodeNeighborLists[i].get(j);
      }
    }



    return new GcProblem(nodeNeighbors);
  }
}
