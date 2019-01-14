
import java.io.File;
import java.util.Random;
import java.util.Scanner;

//records previous graphs.  i.e. learn pattern
//import org.apache.log4j.Logger;

public class TestGcLocalSearch {
//  private static final Logger LOG = Logger.getLogger(TestGcLocalSearch.class);

  public static void main(String[] args) throws Exception {
    String f = args[0];
    Scanner console = new Scanner(System.in);
    System.out.print("Enter Chromatic number: ");
    int k = console.nextInt();  //Chromatic number
    console.close();
    System.out.println();

    Random rand = new Random();
    GcProblem problem = GcReader.read(f);
    GcInitializer initializer = new GcInitializer(rand);
    GcSolution initSol = initializer.makeInitialColoring(problem, k);

    System.out.println("Initial solution cost: " + initSol.getCost());
    System.out.println("Calculated cost: " + initSol.calcCost(problem));

    GcTabuSearchRunner lsRunner = new GcTabuSearchRunner(40, .6, rand);
    GcSolution improved = lsRunner.run(initSol.getNodeColors(), problem.getNodeNeighbors(), k, initSol.getCost(),
        System.currentTimeMillis() + 60000);
    System.out.println("Final cost: " + improved.getCost());
    System.out.println("Calculated cost: " + improved.calcCost(problem));
  }

}

