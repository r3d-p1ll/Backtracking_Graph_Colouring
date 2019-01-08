package sample;

/**
 * class that loads the graph for the player based on what the user inputs into the choice box when loading up the game
 */
public class Fixed_graphs {

    static int n;

    /**
     * creates a sample of the graph to show the player based on the entries in the choice box
     * @param graph is the graph that has been chosen
     */
    public static void choose_graph(Integer graph){
        n = graph;
        if(n == 1){
            sample.Fixed_Graph1.display("Fixed Graph 1", "GoodLuck!");}
        if(n == 2){
            sample.Fixed_Graph2.display("Fixed Graph 2", "GoodLuck!");}
        if(n == 3){
            sample.Fixed_Graph3.display("Fixed Graph 3", "GoodLuck!");}
        if(n == 4){
            sample.Fixed_Graph4.display("Fixed Graph 4", "GoodLuck!");}
        if(n == 5){
            sample.Fixed_Graph5.display("Fixed Graph 5", "GoodLuck!");}
        if(n == 6){
            sample.Fixed_Graph6.display("Fixed Graph 6", "GoodLuck!");}
        if(n == 7){
            sample.Fixed_Graph7.display("Fixed Graph 7", "GoodLuck!");}
        if(n == 8){
            sample.Fixed_Graph8.display("Fixed Graph 8", "GoodLuck!");}
        if(n == 9){
            sample.Fixed_Graph9.display("Fixed Graph 9", "GoodLuck!");}
        if(n == 10){
            sample.Fixed_Graph10.display("Fixed Graph 10", "GoodLuck!");}
        if(n == 11){
            sample.Fixed_Graph11.display("Fixed Graph 11", "GoodLuck!");}
        if(n == 12){
            sample.Fixed_Graph12.display("Fixed Graph 12", "GoodLuck!");}
        if(n == 13){
            sample.Fixed_Graph13.display("Fixed Graph 13", "GoodLuck!");}
        if(n == 14){
            sample.Fixed_Graph14.display("Fixed Graph 14", "GoodLuck!");}
        if(n == 15){
            sample.Fixed_Graph15.display("Fixed Graph 15", "GoodLuck!");}
    }

}
