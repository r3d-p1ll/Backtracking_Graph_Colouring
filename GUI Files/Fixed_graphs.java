package sample;

public class Fixed_graphs {

    static int n;

    public static void choose_graph(Integer graph){
        n = graph;
        if(n == 1){Fixed_Graph1.display("Fixed Graph", "GoodLuck!");}
        if(n == 7){Fixed_Graph7.display("Fixed Graph", "GoodLuck!");}
    }

}
