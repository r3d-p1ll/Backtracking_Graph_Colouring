
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class TestGcHybrid {

  public static void main(String[] args) throws Exception {

    Scanner console = new Scanner(System.in);
    System.out.print("Enter Chromatic number: ");
    int k = console.nextInt();  //Chromatic number
    console.close();
    System.out.println();

    String f = args[0];
    int popSize = 500;
    int timeToImprove = 1500;

    Random rand = new Random();
    GcProblem problem = GcReader.read(f);
    GcInitializer initializer = new GcInitializer(rand);
    GcTabuSearchRunner lsRunner = new GcTabuSearchRunner(40, .6, rand);

    //generate population
    GcSolution[] population = new GcSolution[popSize];
    for (int i = 0; i < popSize; i++) {
      population[i] = initializer.makeInitialColoring(problem, k);
    }

    for (GcSolution sol : population) {
      System.out.println("solution cost before local search: " + sol.getCost());
    }

    for (int i = 0; i < popSize; i++) {
      population[i] = lsRunner.run(problem, population[i], k, timeToImprove);
      System.out.println("solution cost after local search: " + population[i].getCost());
    }

    GcBreeder breeder = new GcBreeder(rand);
    while (true) {
      int parent1Index = rand.nextInt(popSize);
      int parent2Index = parent1Index;
      while (parent2Index == parent1Index) {
        parent2Index = rand.nextInt(popSize);
      }

      GcSolution child = breeder.cross(population[parent1Index], population[parent2Index], problem.getNodeNeighbors(), k);
      System.out.println("child cost: " + child.getCost());
      child = lsRunner.run(problem, child, k, timeToImprove);
      System.out.println("child cost after local search: " + child.getCost());
      if (child.getCost() == 0) {
        break;
      }

      //evict the worst solution
      int worstSolCost = Integer.MIN_VALUE;
      int worstSolIndex = -1;
      for (int i = 0; i < popSize; i++) {
        if (population[i].getCost() > worstSolCost) {
          worstSolCost = population[i].getCost();
          worstSolIndex = i;
        }
      }

      population[worstSolIndex] = child;
    }
  }
}
